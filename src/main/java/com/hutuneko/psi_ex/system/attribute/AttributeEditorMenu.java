package com.hutuneko.psi_ex.system.attribute;

import com.hutuneko.psi_ex.compat.PsiEXRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AttributeEditorMenu extends AbstractContainerMenu {

    // クライアント／サーバー共通で使える最小実装
    public AttributeEditorMenu(int windowId, Inventory playerInv) {
        super(PsiEXRegistry.ATTRIBUTE_EDITOR.get(), windowId);
        // スロット無し（属性編集UIは独自描画＋ネットワークで同期する前提）
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        // 任意：距離チェック等を入れたければここで
        return true;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        // スロット無しなので空を返す
        return ItemStack.EMPTY;
    }
}
