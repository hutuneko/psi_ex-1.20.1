package com.hutuneko.psi_ex.system;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;

import java.util.UUID;

/**
 * Utility for casting Psi spells via a FakePlayer at arbitrary coordinates.
 */
public class PsiSpellCasterUtil {

    /**
     * Casts a Spell by spawning a FakePlayer at (x,y,z) and invoking ctx.cspell.safeExecute(ctx).
     * @param serverWorld The server world to use.
     * @param x X coordinate where the spell is cast.
     * @param y Y coordinate where the spell is cast.
     * @param z Z coordinate where the spell is cast.
     * @param spell The Spell to cast.
     * @return true if the spell was executed.
     */
    public static boolean castFromLens(ServerLevel serverWorld,
                                       double x, double y, double z,
                                       Spell spell) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), "[PsiCaster]");
        FakePlayer fake = FakePlayerFactory.get(serverWorld, profile);
        fake.setPos(x, y, z);

        try {
            SpellContext ctx = new SpellContext()
                    .setPlayer(fake)
                    .setSpell(spell);

            ctx.tool = ItemStack.EMPTY;

            ctx.positionBroken = new BlockHitResult(
                    Vec3.atCenterOf(new BlockPos((int) x, (int) y, (int) z)),
                    fake.getDirection(),
                    new BlockPos((int) x, (int) y, (int) z),
                    false
            );
            ctx.cspell.safeExecute(ctx);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            fake.discard();
        }
    }
}




