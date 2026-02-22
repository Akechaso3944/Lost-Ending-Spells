package com.akechaso.LOSTENDINGMOD.spell;

import com.akechaso.LOSTENDINGMOD.main.Lostending;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import net.minecraft.resources.ResourceLocation;

public class LESpellAnimations {
    public static ResourceLocation ANIMATION_RESOURCE = ResourceLocation.fromNamespaceAndPath(Lostending.MOD_ID, "animation");

    public static final AnimationHolder ANIMATION_CHARGE_GUN  = new AnimationHolder(Lostending.MOD_ID + ":charge_gun", true);
}