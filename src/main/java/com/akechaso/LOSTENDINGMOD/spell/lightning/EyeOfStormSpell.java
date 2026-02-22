package com.akechaso.LOSTENDINGMOD.spell.lightning;

import com.akechaso.LOSTENDINGMOD.main.Lostending;
import com.akechaso.LOSTENDINGMOD.spell.LESpellAnimations;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class EyeOfStormSpell extends AbstractSpell {

    private static final ResourceLocation SPELL_ID =
            new ResourceLocation(Lostending.MOD_ID, "eyeofstorm");

    private static final String STORM_TICK = "eye_of_storm_tick";
    private static final String CENTER_X = "eye_of_storm_x";
    private static final String CENTER_Y = "eye_of_storm_y";
    private static final String CENTER_Z = "eye_of_storm_z";
    private static final String BASE_YAW = "eye_of_storm_yaw";
    private static final String LIGHTNING_COUNT = "eye_of_storm_count";

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.LIGHTNING_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(30)
            .build();

    static {
        MinecraftForge.EVENT_BUS.register(EyeOfStormSpell.class);
    }

    public EyeOfStormSpell() {
        this.baseManaCost = 50;
        this.manaCostPerLevel = 6;
        this.baseSpellPower =  6;
        this.spellPowerPerLevel = 1;
        this.castTime = 40;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return SPELL_ID;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.SLASH_ANIMATION ;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity caster,
                       CastSource source, MagicData magicData) {

        if (!world.isClientSide()) {
            var tag = caster.getPersistentData();
            tag.putInt(STORM_TICK, 0);
            tag.putDouble(CENTER_X, caster.getX());
            tag.putDouble(CENTER_Y, caster.getY());
            tag.putDouble(CENTER_Z, caster.getZ());
            tag.putFloat(BASE_YAW, caster.getYRot());
            tag.putInt(LIGHTNING_COUNT,2 + spellLevel);
        }

        super.onCast(world, spellLevel, caster, source, magicData);
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity caster = event.getEntity();
        if (!(caster.level() instanceof ServerLevel level)) return;

        var data = caster.getPersistentData();
        if (!data.contains(STORM_TICK)) return;

        int tick = data.getInt(STORM_TICK);

        Vec3 center = new Vec3(
                data.getDouble(CENTER_X),
                data.getDouble(CENTER_Y),
                data.getDouble(CENTER_Z)
        );

        double radius = 1.5 + tick * 0.3;
        double thickness = 1.0;

        int points = data.getInt(LIGHTNING_COUNT);
        double baseYaw = Math.toRadians(-data.getFloat(BASE_YAW));

        for (int i = 0; i < points; i++) {
            double angle = (Math.PI * 2 / points) * i + baseYaw;
            double x = center.x + Math.cos(angle) * radius;
            double z = center.z + Math.sin(angle) * radius;

            level.sendParticles(
                    ParticleHelper.ELECTRICITY,
                    x, center.y + 0.3, z,
                    4,
                    0.03, 0.05, 0.03,
                    0.01
            );

            AABB particleHitbox = new AABB(
                    x - 0.1, center.y - 0.1, z - 0.1,
                    x + 0.1, center.y + 0.1, z + 0.1
            );

            List<LivingEntity> targets = level.getEntitiesOfClass(
                    LivingEntity.class,
                    particleHitbox,
                    e -> e != caster
            );

            for (LivingEntity target : targets) {
                strike(level, caster, target);
            }
        }

        if (radius > 20) {
            data.remove(STORM_TICK);
            data.remove(CENTER_X);
            data.remove(CENTER_Y);
            data.remove(CENTER_Z);
            data.remove(BASE_YAW);
            data.remove(LIGHTNING_COUNT);
            return;
        }

        data.putInt(STORM_TICK, tick + 1);
    }

    // lightning

    private static void strike(ServerLevel level,
                               LivingEntity caster,
                               LivingEntity target) {

        if (level.random.nextFloat() > 0.5f) return;

        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt == null) return;

        bolt.moveTo(target.getX(), target.getY(), target.getZ());

        if (caster instanceof ServerPlayer player) {
            bolt.setCause(player);
        }

        level.addFreshEntity(bolt);
    }
}