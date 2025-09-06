package com.hutuneko.psi_ex.system;

import net.minecraft.nbt.CompoundTag;
import vazkii.psi.api.internal.Vector3;

public interface IPlayerData {
    void setVector(String key, Vector3 vec);
    void setDouble(String key, double val);

    Vector3 getValue(String key);
    Double getD(String key);
    CompoundTag save();
    void load(CompoundTag tag);
}
