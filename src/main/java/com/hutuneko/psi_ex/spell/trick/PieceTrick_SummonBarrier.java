// PieceTrick_SummonBarrier.java
package com.hutuneko.psi_ex.spell.trick;

import com.hutuneko.psi_ex.compat.PsiEXRegistry;
import com.hutuneko.psi_ex.entity.PsiBarrierEntity;
import com.hutuneko.psi_ex.system.ParamCompoundTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceTrick;

public class PieceTrick_SummonBarrier extends PieceTrick {
    private ParamVector posParam;
    private ParamNumber durationParam;
    private ParamCompoundTag spellParam;

    public PieceTrick_SummonBarrier(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        addParam(posParam = new ParamVector(SpellParam.GENERIC_NAME_POSITION, SpellParam.BLUE, false, false));
        addParam(durationParam = new ParamNumber("duration", SpellParam.YELLOW, false, false));
        addParam(spellParam = new ParamCompoundTag("spell"));
    }

    @Override
    public Object execute(SpellContext ctx) throws SpellRuntimeException {
        Vector3 p = this.getParamValue(ctx, posParam);
        if (p == null) throw new SpellRuntimeException(SpellRuntimeException.NULL_VECTOR);

        float w = (asFloat(this.getParamValue(ctx, durationParam), 2.0f) / 10f);
        int lifeTicks = (int) (asFloat(this.getParamValue(ctx, durationParam), 5.0f) * 20f);
        CompoundTag spell = (CompoundTag) this.getParamValue(ctx, spellParam);

        if (ctx.caster.level().isClientSide) throw new SpellRuntimeException(SpellRuntimeException.NULL_VECTOR);
        ServerLevel level = (ServerLevel) ctx.caster.level();

        PsiBarrierEntity e = new PsiBarrierEntity(PsiEXRegistry.PSI_BRRIER_ENTITY.get(), level);
        e.setPos(p.x, p.y, p.z);
        e.init(lifeTicks, w, spell);

        System.out.println(e);
        level.addFreshEntity(e);
        return null;
    }

    private static float asFloat(Number n, float def) {
        return n != null ? n.floatValue() : def;
    }
}
