package com.hutuneko.psi_ex.spell;

import com.hutuneko.psi_ex.PsiEX;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.piece.PieceTrick;
import vazkii.psi.api.spell.param.ParamEntity;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.SpellContext;

import java.util.List;
import java.util.Objects;

public class PieceTrick_coordinate_eidos_renewal extends PieceTrick {

//    SpellParam<Entity> targetParam;
//    SpellParam<Number> indexParam;
//    SpellParam<Number> valueParam;
    private ParamEntity targetParam;
    private ParamNumber indexParam;
    private ParamNumber valueParam;

    public PieceTrick_coordinate_eidos_renewal(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {

        addParam(targetParam = new ParamEntity(SpellParam.GENERIC_NAME_TARGET,
                SpellParam.GREEN,
                false,
                false
        ));
        addParam(indexParam = new ParamNumber(SpellParam.GENERIC_NAME_NUMBER1,
                SpellParam.BLUE,
                false,
                true
        ));
        addParam(valueParam = new ParamNumber(SpellParam.GENERIC_NAME_NUMBER2,
                SpellParam.RED,
                false,
                true
        ));
    }


    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 50);
        meta.addStat(EnumSpellStat.COST,    100);
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

        Entity e = this.getParamValue(context, targetParam);

        Number nraw = this.getParamValue(context, indexParam);
        double n = nraw.doubleValue();

        Number mraw = this.getParamValue(context, valueParam);
        double m = mraw.doubleValue();

        if (!context.isInRadius(e)) {
            throw new SpellRuntimeException(SpellRuntimeException.OUTSIDE_RADIUS);
        }
        if (!(e instanceof LivingEntity living)) {
            throw new SpellRuntimeException(SpellRuntimeException.NULL_TARGET);
        }

        List<String> validAttrs = PsiEX.listAllAttributeNames();

        String Name = validAttrs.get((int) n);

        ResourceLocation rl = new ResourceLocation(Name);

        Attribute attribute = ForgeRegistries.ATTRIBUTES.getValue(rl);

        if (attribute == null) {
            throw new SpellRuntimeException("Unknown attribute");
        }else{
            Objects.requireNonNull(living.getAttribute(attribute)).setBaseValue(m);
            if ((n == 0) && (living.getHealth() > m)) {
                living.setHealth((float) m);
            }
            context.caster.displayClientMessage(
                    Component.literal("変更した属性: " + rl.toString()),
                    true  // 真ならアクションバー、偽ならチャット欄
            );
        }
        return null;
    }

}
