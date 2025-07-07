package com.hutuneko.psi_ex.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import vazkii.psi.api.cad.ISocketable;
import vazkii.psi.common.block.tile.TileCADAssembler;
@Mixin(targets = "vazkii.psi.common.block.tile.TileCADAssembler$CADStackHandler")

public class CustomCADStackHandler {
    @Unique
    private final TileCADAssembler psi_ex_1_20_1$tile;

    public CustomCADStackHandler(TileCADAssembler tile) {
        this.psi_ex_1_20_1$tile = tile;
    }

    @Unique
    public boolean psi_ex_1_20_1$isItemValid(int slot, ItemStack stack) {
        if (slot == 0 && ISocketable.isSocketable(stack)) {
            return ISocketable.isSocketable(stack);
        }

        return slot >= 1 && slot <= 5 && stack.getItem() instanceof vazkii.psi.api.cad.ICADComponent comp;

    }
}