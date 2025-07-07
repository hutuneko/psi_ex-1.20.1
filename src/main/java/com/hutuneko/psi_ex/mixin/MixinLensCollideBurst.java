package com.hutuneko.psi_ex.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.item.lens.Lens;
import vazkii.botania.api.internal.ManaBurst;
import vazkii.psi.api.spell.ISpellAcceptor;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.Spell;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;

@Mixin(Lens.class)
public class MixinLensCollideBurst {
    @Unique
    private static final String TAG_STORED = "StoredBullet";

    @Inject(
            method = "collideBurst(Lvazkii/botania/api/internal/ManaBurst;Lnet/minecraft/world/phys/HitResult;ZZLnet/minecraft/world/item/ItemStack;)Z",
            at = @At("TAIL")
    )
    private void onCollide(ManaBurst burst, HitResult hit, boolean isManaBlock, boolean shouldKill, ItemStack lensStack, CallbackInfoReturnable<Boolean> cir) {
        if (!isManaBlock && lensStack.hasTag() && Objects.requireNonNull(lensStack.getTag()).contains(TAG_STORED)) {
            CompoundTag tag = lensStack.getTag().getCompound(TAG_STORED);
            ItemStack bullet = ItemStack.of(tag);
            if (ISpellAcceptor.hasSpell(bullet)) {
                UUID shooter = burst.getShooterUUID();
                Player caster = shooter != null ? burst.entity().level().getPlayerByUUID(shooter) : null;
                SpellContext ctx = new SpellContext();
                if (caster != null) {
                    ctx.setPlayer(caster);
                    ctx.setFocalPoint(burst.entity());
                }
                Spell spell = ISpellAcceptor.acceptor(bullet).getSpell();
                ctx.setSpell(spell);
                if (hit instanceof net.minecraft.world.phys.BlockHitResult bhr) {
                    ctx.positionBroken = bhr;
                }
                ISpellAcceptor.acceptor(bullet).castSpell(ctx);
            }
        }
    }
}

