package com.hutuneko.psi_ex.system;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;

import java.util.logging.Logger;

public class Psi_Mana_Lens_System {
    public Psi_Mana_Lens_System(ItemStack stack, Player burst){

            Spell spell = new MySpellAcceptor(stack).getSpell();
            if (spell == null) {
                Logger.getLogger("spell");
            }else {
            SpellContext ctx = new SpellContext()
                    .setPlayer(burst)
                    .setSpell(spell);
            ctx.cspell.safeExecute(ctx);}

    }
}
