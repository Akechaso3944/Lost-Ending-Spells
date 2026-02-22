package com.akechaso.LOSTENDINGMOD.regi;

import com.akechaso.LOSTENDINGMOD.main.Lostending;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Lostending.MOD_ID);

    public static final RegistryObject<SimpleParticleType> BITE =
            PARTICLES.register("bite", () -> new SimpleParticleType(false));

}
