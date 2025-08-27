// PieceTrick_SummonBarrier.java
package com.hutuneko.psi_ex.spell.trick;

import net.minecraft.world.entity.Entity;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamEntity;
import vazkii.psi.api.spell.piece.PieceTrick;

public class PieceTrick_Kakusatu extends PieceTrick {
    private ParamEntity posParam;

    public PieceTrick_Kakusatu(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        addParam(posParam = new ParamEntity(SpellParam.GENERIC_NAME_TARGET, SpellParam.BLUE, false, false));
    }

    @Override
    public Object execute(SpellContext ctx) throws SpellRuntimeException {
        Entity p = this.getParamValue(ctx, posParam);
        if (p == null) throw new SpellRuntimeException(SpellRuntimeException.NULL_VECTOR);
        p.getTags().add("always_dead");
        System.out.println(p.getTags());
        return null;
    }

    private static float asFloat(Number n, float def) {
        return n != null ? n.floatValue() : def;
    }
}
