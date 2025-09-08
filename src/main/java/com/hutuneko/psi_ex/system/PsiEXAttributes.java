package com.hutuneko.psi_ex.system;

import com.hutuneko.psi_ex.PsiEX;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// PsiEXAttributes.java
public final class PsiEXAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, PsiEX.MOD_ID);

    public static final RegistryObject<Attribute> PSI_SPELL_RANGE =
            ATTRIBUTES.register("psi_spell_range",
                    () -> new RangedAttribute(
                            "attribute.name.psi_ex.psi_spell_range",
                            32.0D, 0.0D, 1024.0D
                    ).setSyncable(true));
    public static void register(IEventBus modBus) {
        ATTRIBUTES.register(modBus);
    }
}

