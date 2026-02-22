package com.akechaso.LOSTENDINGMOD.entity.mobs.wizards;

import com.akechaso.LOSTENDINGMOD.main.Lostending;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CulticmancerRenderer extends AbstractSpellCastingMobRenderer {

    public static ModelLayerLocation BLOODWITCH_MODEl_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Lostending.MOD_ID,"culticmancer"),"body");
    public static ModelLayerLocation  BLOODWITCH_INNER_ARMOR = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Lostending.MOD_ID,"culticmancer"),"inner_armor");
    static ModelLayerLocation BLOODWITCH_OUTER_ARMOR = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Lostending.MOD_ID,"culticmancer"),"outer_armor");

    public CulticmancerRenderer(EntityRendererProvider.Context context) {
        super(context, new CulticmancerModel());
    }
}