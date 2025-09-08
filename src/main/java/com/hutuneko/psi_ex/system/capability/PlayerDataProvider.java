package com.hutuneko.psi_ex.system.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerDataProvider implements net.minecraftforge.common.capabilities.ICapabilityProvider, net.minecraftforge.common.util.INBTSerializable<CompoundTag> {

    public static final net.minecraftforge.common.capabilities.Capability<IPlayerData> CAP =
            net.minecraftforge.common.capabilities.CapabilityManager.get(new net.minecraftforge.common.capabilities.CapabilityToken<>(){});

    private final IPlayerData impl = new PlayerData();
    private final net.minecraftforge.common.util.LazyOptional<IPlayerData> opt = net.minecraftforge.common.util.LazyOptional.of(() -> impl);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == CAP ? opt.cast() : null;
    }

    @Override public CompoundTag serializeNBT(){ return impl.save(); }
    @Override public void deserializeNBT(CompoundTag nbt){ impl.load(nbt); }
}
