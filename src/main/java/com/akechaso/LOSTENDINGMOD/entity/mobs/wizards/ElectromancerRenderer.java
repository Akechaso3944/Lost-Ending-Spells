package com.akechaso.LOSTENDINGMOD.entity.mobs.wizards;

import com.akechaso.LOSTENDINGMOD.main.Lostending;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ElectromancerRenderer extends AbstractSpellCastingMobRenderer {

    public static ModelLayerLocation THUNDERWITCH_MODEl_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Lostending.MOD_ID,"electromancer"),"body");
    public static ModelLayerLocation  THUNDERWITCH_INNER_ARMOR = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Lostending.MOD_ID,"electromancer"),"inner_armor");
    static ModelLayerLocation THUNDERWITCH_OUTER_ARMOR = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Lostending.MOD_ID,"electromancer"),"outer_armor");

    public ElectromancerRenderer(EntityRendererProvider.Context context) {
        super(context, new ElectromancerModel());
    }
}