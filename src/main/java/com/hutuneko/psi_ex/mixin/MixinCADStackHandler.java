package com.hutuneko.psi_ex.mixin;

import com.hutuneko.psi_ex.item.ModItems;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import vazkii.psi.api.cad.ISocketable;

@Mixin(targets = "vazkii.psi.common.block.tile.TileCADAssembler$CADStackHandler")
public abstract class MixinCADStackHandler {
    @Unique
    private MixinCADStackHandler psi_ex_1_20_1$getTile() {
        return this;
    }

    @Unique
    public boolean psi_ex_1_20_1$isItemValid(int slot, ItemStack stack) {
        if (slot == 0) {
            return ISocketable.isSocketable(stack);
        }
        return false;
    }
}