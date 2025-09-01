package com.hutuneko.psi_ex.compat;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.entity.PsiBarrierEntity;
import com.hutuneko.psi_ex.item.PsiCuriosbullet;
import com.hutuneko.psi_ex.spell.operator.PieceOperator_getSpell;
import com.hutuneko.psi_ex.spell.trick.PieceTrick_ExecuteSpell;
import com.hutuneko.psi_ex.spell.trick.PieceTrick_SummonBarrier;
import moffy.addonapi.AddonModule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import vazkii.psi.api.PsiAPI;

public class CuriosCompatModule extends AddonModule {
    public CuriosCompatModule() {
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "pieceoperator_getspell"), PieceOperator_getSpell.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "piecetrick_executespell"), PieceTrick_ExecuteSpell.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "piecetrick_summonbarrier"), PieceTrick_SummonBarrier.class);

        PsiEXRegistry.PSI_CURIO_BULLET = PsiEXRegistry.ITEMS.register("psi_curio_bullet", () ->
                new PsiCuriosbullet(new Item.Properties().stacksTo(1))
        );
        PsiEXRegistry.PSI_BRRIER_ENTITY = PsiEXRegistry.ENTITIES.register("barrier",
                () -> EntityType.Builder.of(PsiBarrierEntity::new, MobCategory.MISC)
                        .sized(0.5f, 0.5f)
                        .clientTrackingRange(32)
                        .updateInterval(1)
                        .build(new ResourceLocation(PsiEX.MOD_ID, "barrier").toString()));
    }
}

