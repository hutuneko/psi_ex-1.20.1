package com.hutuneko.psi_ex.spell;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import com.hutuneko.psi_ex.PsiEX;
import vazkii.psi.api.spell.piece.PieceTrick;

public class eidosrenewal extends PieceTrick {

    public eidosrenewal(Spell spell) {
        super(spell);
    }


    @OnlyIn(Dist.CLIENT)
    public Material getMaterial() {
        return new Material(
                TextureAtlas.LOCATION_BLOCKS,
                new ResourceLocation(PsiEX.MOD_ID, "spell/icon/eidos_renewal")
        );
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
    public Object evaluate() {
        return null;
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        if (!context.isInRadius(context.caster))
            throw new SpellRuntimeException(SpellRuntimeException.OUTSIDE_RADIUS);

        var pos = Vector3.fromEntity(context.caster);
        context.caster.level().addParticle(
                ParticleTypes.END_ROD,
                pos.x, pos.y + 1, pos.z,
                0, 0, 0
        );
        return null;
    }
}
