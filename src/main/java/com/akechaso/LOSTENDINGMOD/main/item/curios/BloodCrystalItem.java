package com.akechaso.LOSTENDINGMOD.main.item.curios;

import com.akechaso.LOSTENDINGMOD.compat.Curios;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import io.redspace.ironsspellbooks.render.CinderousRarity;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;

public class BloodCrystalItem extends SimpleDescriptiveCurio {
    public BloodCrystalItem() {
        super(ItemPropertiesHelper.equipment().stacksTo(1), Curios.CHARM_SLOT);
    }
}
