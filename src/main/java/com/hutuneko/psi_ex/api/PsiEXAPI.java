package com.hutuneko.psi_ex.api;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import org.jetbrains.annotations.Nullable;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;

public class PsiEXAPI {
    public static void runPsiAt(Vec3 pos, Spell spell, Level level, @Nullable Entity e,boolean l) {
        SpellContext ctx = new SpellContext();
        if (l) {
            if (!(e instanceof LivingEntity livingEntity)) return;
            ctx.attackedEntity = livingEntity;
        }
        if (spell != null) {
            FakePlayer caster = FakePlayerFactory.getMinecraft((ServerLevel) level);
            double ox = pos.x, oy = pos.y, oz = pos.z;
            caster.setPos(ox, oy, oz);
            ctx.setPlayer(caster).setSpell(spell);
            ctx.cspell.safeExecute(ctx);
        }
    }
}
