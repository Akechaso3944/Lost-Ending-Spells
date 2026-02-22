package com.akechaso.LOSTENDINGMOD.main;

import com.akechaso.LOSTENDINGMOD.regi.ModItems;
import com.akechaso.LOSTENDINGMOD.regi.ModEntities;
import com.akechaso.LOSTENDINGMOD.regi.ModParticles;
import com.akechaso.LOSTENDINGMOD.regi.ModSpellRegistry;
import com.akechaso.LOSTENDINGMOD.regi.tab.LostEndingTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("lostending")
public class Lostending {
    public static final String MOD_ID = "lostending";

    public Lostending(){

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        LostEndingTabs.MOD_TABS.register(bus);
        ModItems.ITEMS.register(bus);
        ModSpellRegistry.register(bus);
        ModParticles.PARTICLES.register(bus);
        ModEntities.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}

