package com.akechaso.LOSTENDINGMOD.main.item.curios;

import com.akechaso.LOSTENDINGMOD.compat.Curios;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;

public class LightningCrystalItem extends SimpleDescriptiveCurio {
    public LightningCrystalItem() {
        super(ItemPropertiesHelper.equipment().stacksTo(1), Curios.CHARM_SLOT);
    }
}
