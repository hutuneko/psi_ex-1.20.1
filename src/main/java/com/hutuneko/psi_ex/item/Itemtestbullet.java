package com.hutuneko.psi_ex.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Itemtestbullet extends Item {

    public Itemtestbullet(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
            if (!world.isClientSide && world instanceof ServerLevel serverWorld) {
                if (player.isShiftKeyDown()) player.getTags().remove("psiex:parade");
                double x = player.getX();
                double y = player.getY();
                double z = player.getZ();
                System.out.println(x + String.valueOf(y) + z);
                player.sendSystemMessage(Component.literal(x +","+ y +","+ z));
            }
            return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }
}
