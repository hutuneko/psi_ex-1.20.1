package com.hutuneko.psi_ex.item;

import com.hutuneko.psi_ex.system.MySpellAcceptor;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.spell.ISpellAcceptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Itemtestbullet extends Item {

    public Itemtestbullet(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
            if (!world.isClientSide && world instanceof ServerLevel serverWorld) {
            }
            return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag unusedNbt) {
        LazyOptional<ISpellAcceptor> cap = LazyOptional.of(() -> new MySpellAcceptor(stack));
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> requested, @Nullable Direction side) {
                return PsiAPI.SPELL_ACCEPTOR_CAPABILITY.orEmpty(requested, cap);
            }
        };
    }
}
