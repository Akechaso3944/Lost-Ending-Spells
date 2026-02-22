package com.akechaso.LOSTENDINGMOD.regi;

import com.akechaso.LOSTENDINGMOD.main.Lostending;
import com.akechaso.LOSTENDINGMOD.spell.blood.LifeDrainSpell;
import com.akechaso.LOSTENDINGMOD.spell.holy.TestHealSpell;
import com.akechaso.LOSTENDINGMOD.spell.lightning.EyeOfStormSpell;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModSpellRegistry {

    public static final DeferredRegister<AbstractSpell> SPELLS =
            DeferredRegister.create(
                    SpellRegistry.SPELL_REGISTRY_KEY,
                    Lostending.MOD_ID
            );

    // 既存：テスト回復魔法
    public static final RegistryObject<AbstractSpell> TEST_HEAL =
            SPELLS.register("test_heal", TestHealSpell::new);

    public static final RegistryObject<AbstractSpell> LIFE_DRAIN_SPELL =
            SPELLS.register("life_drain", LifeDrainSpell::new);

    public static final RegistryObject<AbstractSpell> EYE_OF_STORM_SPELL =
            SPELLS.register("eyeofstorm", EyeOfStormSpell::new);

    public static void register(IEventBus bus) {
        SPELLS.register(bus);
    }
}
