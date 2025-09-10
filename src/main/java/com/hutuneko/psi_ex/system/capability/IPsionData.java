package com.hutuneko.psi_ex.system.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public interface IPsionData {
    double getPsion();
    boolean isPsion();
    void setPsion(double v);
    void add(double v);
    void hurt(double v);
    void tickRegain(Player p);
    void save(CompoundTag tag);
    void load(CompoundTag tag);
}