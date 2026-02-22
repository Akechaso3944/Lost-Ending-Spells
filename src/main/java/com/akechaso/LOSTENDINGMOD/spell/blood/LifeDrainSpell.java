package com.akechaso.LOSTENDINGMOD.spell.blood;

import com.akechaso.LOSTENDINGMOD.main.Lostending;
import com.akechaso.LOSTENDINGMOD.regi.ModParticles;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;

public class LifeDrainSpell extends AbstractSpell {

    private static final ResourceLocation SPELL_ID =
            new ResourceLocation(Lostending.MOD_ID, "life_drain");

    private static final String RETURN_POS_TAG = "life_drain_return_pos";
    private static final String RETURN_TICK_TAG = "life_drain_return_tick";

    /* =============================
       Event登録（1回だけ）
     ============================= */
    static {
        MinecraftForge.EVENT_BUS.register(LifeDrainReturnHandler.class);
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.BLOOD_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(10)
            .build();

    public LifeDrainSpell() {
        this.baseManaCost = 20;
        this.manaCostPerLevel = 4;
        this.baseSpellPower = 6;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
    }

    @Override
    public CastType getCastType() { return CastType.INSTANT; }

    @Override
    public DefaultConfig getDefaultConfig() { return defaultConfig; }

    @Override
    public ResourceLocation getSpellResource() { return SPELL_ID; }

    @Override
    public boolean checkPreCastConditions(Level world, int spellLevel, LivingEntity caster, MagicData magicData) {
        return Utils.preCastTargetHelper(world, caster, magicData, this, 16, 0.1f);
    }

    @Override
    public void onClientPreCast(Level level, int spellLevel, LivingEntity entity,
                                InteractionHand hand, @Nullable MagicData magicData) {

        level.playLocalSound(
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                net.minecraft.sounds.SoundEvents.ENDERMAN_TELEPORT,
                net.minecraft.sounds.SoundSource.PLAYERS,
                1.2f,   // 音量
                1.0f,   // ピッチ
                false
        );

        super.onClientPreCast(level, spellLevel, entity, hand, magicData);

        double cx = entity.getX();
        double cy = entity.getY() + 0.1;
        double cz = entity.getZ();

        for (int i = 0; i < 80; i++) {
            double dx = (Utils.random.nextDouble() - 0.5) * 1.6;
            double dy = Utils.random.nextDouble() * 1.4;
            double dz = (Utils.random.nextDouble() - 0.5) * 1.6;

            level.addParticle(
                    ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                    cx, cy, cz,
                    dx, dy, dz
            );
        }
    }


    @Override
    public void onCast(Level world, int spellLevel, LivingEntity caster,
                       CastSource source, MagicData magicData) {

        // ===== 元の位置を保存 =====
        Vec3 originalPos = caster.position();
        caster.getPersistentData().putDouble(RETURN_POS_TAG + "_x", originalPos.x);
        caster.getPersistentData().putDouble(RETURN_POS_TAG + "_y", originalPos.y);
        caster.getPersistentData().putDouble(RETURN_POS_TAG + "_z", originalPos.z);
        caster.getPersistentData().putInt(RETURN_TICK_TAG, caster.tickCount + 5); // 1秒後

        // ===== ターゲット取得 =====
        var hit = Utils.raycastForEntity(world, caster, 16.0F, true);
        if (!(hit instanceof net.minecraft.world.phys.EntityHitResult ehr)) return;
        if (!(ehr.getEntity() instanceof LivingEntity target)) return;
        if (target == caster) return;

        float damage = getSpellPower(spellLevel, caster);
        if (damage <= 0) return;

        // ===== 敵の近くへ瞬間移動 =====
        Vec3 direction = target.position().subtract(caster.position()).normalize();
        Vec3 nearTargetPos = target.position().subtract(direction.scale(0.8)).add(0, 0.1, 0);
        caster.teleportTo(nearTargetPos.x, nearTargetPos.y, nearTargetPos.z);

        // ===== 攻撃 & 吸血 =====
        // 低音ゴリッ
        world.playSound(null,
                caster.getX(), caster.getY(), caster.getZ(),
                SoundEvents.EVOKER_FANGS_ATTACK,
                net.minecraft.sounds.SoundSource.PLAYERS,
                0.8f, 0.3f
        );

        target.hurt(caster.damageSources().indirectMagic(caster, caster), damage);
        caster.heal(damage * 0.3f);
        MagicManager.spawnParticles(
                world,
                ModParticles.BITE.get(),
                caster.getX(),
                caster.getY() + 1,
                caster.getZ(),
                1,
                0, 0.05, 0,
                0,
                false
        );

// 血が出る感じ
        world.playSound(null,
                caster.getX(), caster.getY(), caster.getZ(),
                SoundRegistry.BLOOD_STEP.get(),
                net.minecraft.sounds.SoundSource.PLAYERS,
                0.6f, 1.2f
        );

        // ===== 血パーティクル =====
        spawnBloodSplash(world, target);

        super.onCast(world, spellLevel, caster, source, magicData);
    }

    private static void spawnBloodSplash(Level world, LivingEntity target) {
        for (int i = 0; i < 25; i++) {
            MagicManager.spawnParticles(
                    world,
                    ParticleHelper.BLOOD,

                    // ▼ 出現位置：顔の少し前
                    target.getX(),
                    target.getY() + target.getEyeHeight() - 0.2,
                    target.getZ(),

                    1,

                    // ▼ 前方向に噴き出す（噛みつき）
                    target.getLookAngle().x * 0.35 + (Math.random() - 0.5) * 0.08,
                    target.getLookAngle().y * 0.15 + Math.random() * 0.05,
                    target.getLookAngle().z * 0.35 + (Math.random() - 0.5) * 0.08,

                    // ▼ 勢い
                    0.12,
                    false
            );
        }
    }


    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable(
                        "ui.irons_spellbooks.damage",
                        Utils.stringTruncation(getSpellPower(spellLevel, caster), 1)
                ),
                Component.literal("吸血: 30%")
        );
    }

    /* =================================================
       ★ 内部 EventHandler（ここがキモ）
     ================================================= */
    public static class LifeDrainReturnHandler {

        @SubscribeEvent
        public static void onLivingTick(LivingEvent.LivingTickEvent event) {
            LivingEntity entity = event.getEntity();
            if (entity.level().isClientSide()) return;

            var data = entity.getPersistentData();
            if (!data.contains(RETURN_TICK_TAG)) return;

            if (entity.tickCount >= data.getInt(RETURN_TICK_TAG)) {
                entity.teleportTo(
                        data.getDouble(RETURN_POS_TAG + "_x"),
                        data.getDouble(RETURN_POS_TAG + "_y"),
                        data.getDouble(RETURN_POS_TAG + "_z")
                );

                // 後処理（超重要）
                data.remove(RETURN_TICK_TAG);
                data.remove(RETURN_POS_TAG + "_x");
                data.remove(RETURN_POS_TAG + "_y");
                data.remove(RETURN_POS_TAG + "_z");
            }
        }
    }
}
