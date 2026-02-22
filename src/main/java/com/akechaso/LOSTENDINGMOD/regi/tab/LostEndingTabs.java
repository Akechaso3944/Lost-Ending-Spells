package com.akechaso.LOSTENDINGMOD.regi.tab;

import com.akechaso.LOSTENDINGMOD.main.Lostending;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraftforge.forgespi.Environment.build;

public class LostEndingTabs {

    public  static final DeferredRegister<CreativeModeTab> MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Lostending.MOD_ID);

    public static final RegistryObject<CreativeModeTab> LOSTENDING_TAB =
            MOD_TABS.register("lostending_tab",
                    () -> CreativeModeTab.builder()
                            .title(Component.translatable("creativetab.lostendingmod"))
                            .icon(() -> new ItemStack(Blocks.END_STONE))
                            .displayItems((params, output) -> {

                            })
                            .build()
            );
}
