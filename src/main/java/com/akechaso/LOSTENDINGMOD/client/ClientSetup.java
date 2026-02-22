package com.akechaso.LOSTENDINGMOD.client;

import com.akechaso.LOSTENDINGMOD.entity.mobs.wizards.CulticmancerRenderer;
import com.akechaso.LOSTENDINGMOD.entity.mobs.wizards.ElectromancerRenderer;
import com.akechaso.LOSTENDINGMOD.main.Lostending;
import com.akechaso.LOSTENDINGMOD.particle.BiteParticle;
import com.akechaso.LOSTENDINGMOD.regi.ModEntities;
import com.akechaso.LOSTENDINGMOD.regi.ModParticles;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        modid = Lostending.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ClientSetup {

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(
                ModParticles.BITE.get(),
                BiteParticle.Provider::new
        );
        System.out.println("✔ Bite particle provider registered");
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.BLOOD_WITCH.get(), CulticmancerRenderer::new);
        EntityRenderers.register(ModEntities.THUNDER_WITCH.get(), ElectromancerRenderer::new);


    }
}
