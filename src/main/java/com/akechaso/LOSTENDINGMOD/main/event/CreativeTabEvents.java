package com.akechaso.LOSTENDINGMOD.main.event;

import com.akechaso.LOSTENDINGMOD.regi.ModItems;
import com.akechaso.LOSTENDINGMOD.regi.tab.LostEndingTabs;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.akechaso.LOSTENDINGMOD.regi.ModItems.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabEvents {

    @SubscribeEvent
    public static void addToTabs(BuildCreativeModeTabContentsEvent event) {

        // ツールタブに追加
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.WOODEN_SCYTHE);
            event.accept(ModItems.STONE_SCYTHE);
            event.accept(ModItems.IRON_SCYTHE);
            event.accept(ModItems.GOLDEN_SCYTHE);
            event.accept(ModItems.DIAMOND_SCYTHE);
            event.accept(ModItems.NETHERITE_SCYTHE);
        }
        if (event.getTabKey() == LostEndingTabs.LOSTENDING_TAB.getKey()) {
            event.accept((CULTIST_SUMMON_EGG));
            event.accept((ELECTRO_SUMMON_EGG));
            event.accept((BLOOD_CRYSTAL));
            event.accept((LIGHTNING_CRYSTAL));
        }
    }
}
