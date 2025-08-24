package com.hutuneko.psi_ex.system;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;
import java.util.function.Predicate;

public final class CuriosUtil {

    /** プレイヤーから Curios のハンドラを取得 */
    public static Optional<ICuriosItemHandler> getHandler(Player player) {
        return player.getCapability(CuriosCapability.INVENTORY).resolve();
    }

    /** 1) アイテムクラス一致で最初の1個を探す */
    public static Optional<SlotResult> findFirstByItem(Player player, Item target) {
        return getHandler(player).flatMap(inv ->
                inv.findFirstCurio(stack -> stack.getItem() == target)
        );
    }

    /** 2) 任意の条件で検索 */
    public static Optional<SlotResult> findFirst(Player player, Predicate<ItemStack> test) {
        return getHandler(player).flatMap(inv -> inv.findFirstCurio(test));
    }

    /** 3) スロットタイプとインデックスで直接取得（例: "charm", 0） */
    public static ItemStack getFromSlot(Player player, String slotType, int index) {
        return getHandler(player)
                .flatMap(inv -> inv.getStacksHandler(slotType))
                .map(h -> {
                    var stacks = h.getStacks();
                    return (index >= 0 && index < stacks.getSlots()) ? stacks.getStackInSlot(index) : ItemStack.EMPTY;
                })
                .orElse(ItemStack.EMPTY);
    }
}
