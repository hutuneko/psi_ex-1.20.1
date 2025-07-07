package com.hutuneko.psi_ex.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.api.mana.BasicLensItem;
import vazkii.psi.common.item.ItemSpellBullet;
import vazkii.psi.api.spell.ISpellAcceptor;
import vazkii.psi.api.spell.Spell;

import java.util.Objects;

@Mixin(BasicLensItem.class)
public class MixinPsiManaLensSocketable {
    @Unique
    private static final String TAG_STORED = "StoredBullet";

    @Unique
    private void psi_ex_1_20_1$onUse(Level world, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack lens = player.getItemInHand(hand);
        InteractionHand off = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack bullet = player.getItemInHand(off);
        if (bullet.getItem() instanceof ItemSpellBullet && ISpellAcceptor.hasSpell(bullet)) {
            Spell spell = ISpellAcceptor.acceptor(bullet).getSpell();
            lens.getOrCreateTag().put(TAG_STORED, Objects.requireNonNull(bullet.getTag()).copy());
            if (!player.isCreative()) bullet.shrink(1);
            cir.setReturnValue(InteractionResultHolder.success(lens));
        }
    }
}

