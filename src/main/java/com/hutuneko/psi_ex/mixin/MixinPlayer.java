package com.hutuneko.psi_ex.mixin;

import com.hutuneko.psi_ex.system.capability.PsionProvider;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class MixinPlayer {

    @Unique
    private LivingEntity psi_ex_1_20_1$E = (LivingEntity) (Object)this;
    @Unique
    private boolean psi_ex_1_20_1$P;

    @ModifyReturnValue(method = "isDeadOrDying", at = @At("RETURN"))
    public boolean isDeadOrDying(boolean o){
        if (!(psi_ex_1_20_1$E instanceof Player p)) return o;
        p.getCapability(PsionProvider.CAP).ifPresent(cap ->
                psi_ex_1_20_1$P = cap.getPsion() <= 0.0);
        return o || psi_ex_1_20_1$P;
    }
}
