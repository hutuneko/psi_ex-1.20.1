package com.hutuneko.psi_ex.system.capability;

import com.hutuneko.psi_ex.system.PsiEXAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class PsionData implements IPsionData {
    private double current = 100;

    @Override public double getCurrent(){ return current; }
    @Override public void setCurrent(double v){ current = Math.max(0, v); }
    @Override public void add(double v){ current = Math.max(0, current + v); }
    @Override public void hurt(double v){ current = Math.max(0, current - v); }

    @Override public void tickRegain(Player p){
        if (p.level().getGameTime() % 10 == 0) { // 0.5秒ごと
            double max = Objects.requireNonNull(p.getAttribute(PsiEXAttributes.PSI_PSION_POINT.get())).getValue();
            if (current < max) current = Math.min(max, current + 1.0);
        }
    }

    @Override public void save(CompoundTag tag){ tag.putDouble("cur", current); }
    @Override public void load(CompoundTag tag){ current = tag.getDouble("cur"); }
}