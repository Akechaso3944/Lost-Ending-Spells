package com.akechaso.LOSTENDINGMOD.spell.holy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.events.SpellHealEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class TestHealSpell extends AbstractSpell {

    private final ResourceLocation spellId =
            new ResourceLocation("lostending", "test_heal");

    // 呪文の基本設定
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.HOLY_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(30)
            .build();

    public TestHealSpell() {
        // レベル進行の設定
        this.manaCostPerLevel = 10;        // レベル1→10
        this.baseSpellPower = 5;           // 回復基礎値
        this.spellPowerPerLevel = 1;       // レベル補正
        this.castTime = 0;                 // 即時発動
        this.baseManaCost = 30;            // 初期消費マナ
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity caster,
                       CastSource source, MagicData playerMagicData) {

        // 回復量計算
        float healAmount = getSpellPower(spellLevel, caster);

        // イベント発行（他Modとの互換）
        MinecraftForge.EVENT_BUS.post(
                new SpellHealEvent(caster, caster, healAmount, getSchoolType())
        );

        // 実際の回復
        caster.heal(healAmount);

        // パーティクル演出
        int count = 16;
        float radius = 1.25f;
        for (int i = 0; i < count; i++) {
            double theta = Math.toRadians(360 / count) * i;
            double x = Math.cos(theta) * radius;
            double z = Math.sin(theta) * radius;
            MagicManager.spawnParticles(
                    world,
                    ParticleTypes.HEART,
                    caster.position().x + x,
                    caster.position().y,
                    caster.position().z + z,
                    1, 0, 0, 0, 0.1, false
            );
        }

        // super.onCast を呼ぶと
        // クールダウンや経験値処理も動く
        super.onCast(world, spellLevel, caster, source, playerMagicData);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.SELF_CAST_ANIMATION;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return java.util.List.of(
                Component.translatable("ui.irons_spellbooks.healing",
                        Utils.stringTruncation(getSpellPower(spellLevel, caster), 1))
        );
    }
}
