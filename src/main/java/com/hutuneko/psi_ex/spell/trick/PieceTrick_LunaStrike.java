package com.hutuneko.psi_ex.spell.trick;

import com.hutuneko.psi_ex.system.capability.PsionProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamEntity;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.piece.PieceTrick;

public class PieceTrick_LunaStrike extends PieceTrick {
    private ParamEntity targetParam;
    private ParamNumber damageParam;

    public PieceTrick_LunaStrike(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        addParam(targetParam = new ParamEntity(SpellParam.GENERIC_NAME_TARGET,SpellParam.RED,false,false
        ));
        addParam(damageParam = new ParamNumber("damage",SpellParam.BLUE,false,false
        ));
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 20);
        meta.addStat(EnumSpellStat.COST,   50);
    }

    @Override
    public EnumPieceType getPieceType() {
        return EnumPieceType.TRICK;
    }

    @Override
    public Class<?> getEvaluationType() {
        return Void.class;
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        Player player = context.caster;
        Level world = player.level();
        if (world.isClientSide) return null;
        Number n = getParamValue(context, damageParam);
        double d = n.doubleValue();
        Entity t = getParamValue(context, targetParam);
        if (!(t instanceof LivingEntity target)){
            throw new SpellRuntimeException("有効なターゲットを選択してください");
        }
        target.getCapability(PsionProvider.CAP).ifPresent(now -> now.setCurrent(d));
        return null;
    }
}
