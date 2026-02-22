package com.akechaso.LOSTENDINGMOD.regi;

import com.akechaso.LOSTENDINGMOD.compat.Curios;
import com.akechaso.LOSTENDINGMOD.main.Lostending;
import com.akechaso.LOSTENDINGMOD.main.item.curios.CurioBaseItem;
import com.akechaso.LOSTENDINGMOD.main.item.weapons.ScytheItem;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.item.*;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Lostending.MOD_ID);


    public static final RegistryObject<Item> WOODEN_SCYTHE = ITEMS.register("wooden_scythe",
            () -> new ScytheItem(Tiers.WOOD, 3, -3.4F, 1.5F, 0.5F, new Item.Properties().durability(80))
    );

    public static final RegistryObject<Item> STONE_SCYTHE = ITEMS.register("stone_scythe",
            () -> new ScytheItem(Tiers.STONE, 4, -3.4F, 1.5F, 0.55F, new Item.Properties().durability(180))
    );

    public static final RegistryObject<Item> IRON_SCYTHE = ITEMS.register("iron_scythe",
            () -> new ScytheItem(Tiers.IRON, 5, -3.4F, 1.5F, 0.6F, new Item.Properties().durability(350))
    );

    public static final RegistryObject<Item> GOLDEN_SCYTHE = ITEMS.register("golden_scythe",
            () -> new ScytheItem(Tiers.GOLD, 3, -3.1F, 1.5F, 0.6F, new Item.Properties().durability(60))
    );

    public static final RegistryObject<Item> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe",
            () -> new ScytheItem(Tiers.DIAMOND, 7, -3.4F, 1.75F, 0.7F, new Item.Properties().durability(2000))
    );

    public static final RegistryObject<Item> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe",
            () -> new ScytheItem(
                    Tiers.NETHERITE,
                    9,          // 表示攻撃力 ≒ 11
                    -3.4F,
                    2.0F,       // 広い範囲
                    0.75F,      // 範囲ダメも強め
                    new Item.Properties().durability(2500).fireResistant()
            )
    );

    public static final RegistryObject<ForgeSpawnEggItem> CULTIST_SUMMON_EGG = ITEMS.register("culticmancer_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BLOOD_WITCH,0x222842,0xEA2029, ItemPropertiesHelper.material().stacksTo(64))
    );
    public static final RegistryObject<ForgeSpawnEggItem> ELECTRO_SUMMON_EGG = ITEMS.register("electromancer_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.THUNDER_WITCH,0x6ED2DA,0xE89A60, ItemPropertiesHelper.material().stacksTo(64))
    );

    public static final Supplier<CurioBaseItem> BLOOD_CRYSTAL = ITEMS.
            register("blood_crystal", () -> new CurioBaseItem(ItemPropertiesHelper.equipment(1))
                    .withCharmAttributes(Curios.CHARM_SLOT,
                            new AttributeContainer(AttributeRegistry.BLOOD_SPELL_POWER,0.20, AttributeModifier.Operation.MULTIPLY_BASE)));
    public static final Supplier<CurioBaseItem> LIGHTNING_CRYSTAL = ITEMS.
            register("lightning_crystal", () -> new CurioBaseItem(ItemPropertiesHelper.equipment(1))
                    .withCharmAttributes(Curios.CHARM_SLOT,
                            new AttributeContainer(AttributeRegistry.LIGHTNING_SPELL_POWER,0.20, AttributeModifier.Operation.MULTIPLY_BASE)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
