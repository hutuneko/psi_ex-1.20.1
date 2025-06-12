package com.hutuneko.psi_ex.spell;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.internal.Vector3;
import net.minecraft.core.particles.ParticleTypes;

public class eidosrenewal extends SpellPiece implements eidosrenewal_in {

    public eidosrenewal(Spell spell) {
        super(spell);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Material getMaterial() {
        // atlas = blocks, path = assets/psi/textures/spell/icon/eidos_renewal.png
        return new Material(
                TextureAtlas.LOCATION_BLOCKS,
                new ResourceLocation("psi", "spell/icon/eidos_renewal")
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
    public Object evaluate() throws SpellCompilationException {
        return null;
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        if (!context.isInRadius(context.caster)) {
            throw new SpellRuntimeException(SpellRuntimeException.OUTSIDE_RADIUS);
        }
        Vector3 pos = Vector3.fromEntity(context.caster);
        context.caster.level().addParticle(ParticleTypes.END_ROD,
                pos.x, pos.y + 1, pos.z,
                0, 0, 0
        );
        return null;
    }
}
