package com.akechaso.LOSTENDINGMOD.regi;

import com.akechaso.LOSTENDINGMOD.entity.mobs.wizards.CulticmancerEntity;
import com.akechaso.LOSTENDINGMOD.entity.mobs.wizards.ElectromancerEntity;
import com.akechaso.LOSTENDINGMOD.main.Lostending;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Lostending.MOD_ID);

    public static final RegistryObject<EntityType<CulticmancerEntity>> BLOOD_WITCH =
            ENTITY_TYPES.register("culticmancer", () ->
                    EntityType.Builder.of(CulticmancerEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.8f)
                            .clientTrackingRange(64)
                            .build(new ResourceLocation(Lostending.MOD_ID, "culticmancer").toString())
            );

    public static final RegistryObject<EntityType<ElectromancerEntity>> THUNDER_WITCH =
            ENTITY_TYPES.register("electromancer", () ->
                    EntityType.Builder.of(ElectromancerEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.8f)
                            .clientTrackingRange(64)
                            .build(new ResourceLocation(Lostending.MOD_ID, "electromancer").toString())
            );
    @Mod.EventBusSubscriber(modid = Lostending.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class ModEntityAttributes {

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(ModEntities.BLOOD_WITCH.get(),
                    CulticmancerEntity.createAttributes().build());
            event.put(ModEntities.THUNDER_WITCH.get(),
                    ElectromancerEntity.createAttributes().build());
        }
    }
}
