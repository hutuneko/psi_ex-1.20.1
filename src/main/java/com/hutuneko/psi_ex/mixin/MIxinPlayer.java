package com.hutuneko.psi_ex.mixin;

import com.hutuneko.psi_ex.system.capability.PsionProvider;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class MIxinPlayer {

    @Unique
    private LivingEntity psi_ex_1_20_1$E = (LivingEntity) (Object)this;
    @Unique
    private double psi_ex_1_20_1$P;


    @ModifyReturnValue(method = "isDeadOrDying", at = @At("RETURN"))
    public boolean isDeadOrDying(boolean o){
        psi_ex_1_20_1$E.getCapability(PsionProvider.CAP).ifPresent(cap ->
                psi_ex_1_20_1$P = cap.getCurrent());
        return o || psi_ex_1_20_1$P <= 0;
    }
}
