package com.hutuneko.psi_ex.system;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
public class MaxAllowedBase {
    public static double maxAllowedBase(AttributeInstance inst, double cap) {
        double add = 0.0;
        double multBase = 0.0;
        double multTotal = 0.0;

        for (AttributeModifier mod : inst.getModifiers(AttributeModifier.Operation.ADDITION)) {
            add += mod.getAmount();
        }
        for (AttributeModifier mod : inst.getModifiers(AttributeModifier.Operation.MULTIPLY_BASE)) {
            multBase += mod.getAmount();
        }
        for (AttributeModifier mod : inst.getModifiers(AttributeModifier.Operation.MULTIPLY_TOTAL)) {
            multTotal += mod.getAmount();
        }

        double denom = (1.0 + multBase) * (1.0 + multTotal);
        if (denom <= 0) denom = 1.0; // 念のため

        double bMax = (cap / denom) - add;

        // 属性レンジでクランプ
        Attribute attr = inst.getAttribute();
        if (attr instanceof net.minecraft.world.entity.ai.attributes.RangedAttribute ra) {
            bMax = Mth.clamp(bMax, ra.getMinValue(), ra.getMaxValue());
        }
        return bMax;
    }
}
