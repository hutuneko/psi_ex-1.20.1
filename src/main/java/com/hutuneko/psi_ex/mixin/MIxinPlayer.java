package com.hutuneko.psi_ex.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class MIxinPlayer {
    @Shadow public abstract float getHealth();

    @ModifyReturnValue(method = "isDeadOrDying", at = @At("RETURN"))
    public boolean isDeadOrDying(boolean o){
        return o || getHealth() >= 80;
    }
}
