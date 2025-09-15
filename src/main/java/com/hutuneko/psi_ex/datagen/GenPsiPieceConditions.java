package com.hutuneko.psi_ex.datagen;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.system.attribute.PsiEXAttributes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class GenPsiPieceConditions {
    public static PsiPieceConditionsProvider create(PackOutput out, String modid) {
        PsiPieceConditionsProvider p = new PsiPieceConditionsProvider(out, modid);
//        for (String id : Set.PSI_PIECES) {
//            ResourceLocation k = random();
//            double d = 100;
//            p.pieceAttributeAtLeast(
//                    rl("psi",id),
//                    k,
//                    d, "TOTAL",
//                    k + "が" + d + "必要です",
//                    id
//            );
//        }
        for (String id : Set.PSI_EX_PIECES) {
            ResourceLocation k = random();
            double d = 5000;
            p.pieceAttributeAtLeast(
                    rl(PsiEX.MOD_ID,id),
                    k,
                    d, "TOTAL",
                    k + "が" + d + "必要です",
                    id
            );
        }
        return p;
    }

    private static ResourceLocation random() {
        List<ResourceLocation> keys = new ArrayList<>();
        keys.add(PsiEXAttributes.PSI_ABSORPTION_POINT.getId());
        keys.add(PsiEXAttributes.PSI_CONVERGENCE_POINT.getId());
        keys.add(PsiEXAttributes.PSI_MOVEMENT_POINT.getId());
        keys.add(PsiEXAttributes.PSI_DIVERGENCE_POINT.getId());
        keys.add(PsiEXAttributes.PSI_WEIGHTING_POINT.getId());
        keys.add(PsiEXAttributes.PSI_EMISSION_POINT.getId());
        keys.add(PsiEXAttributes.PSI_ACCELERATION_POINT.getId());
        keys.add(PsiEXAttributes.PSI_VIBRATION_POINT.getId());
        keys.add(PsiEXAttributes.PSI_ANCIENTRITES_POINT.getId());
        Random rand = new Random();
        return keys.get(rand.nextInt(keys.size()));
    }
    private static ResourceLocation rl(String ns, String path) {
        return new ResourceLocation(ns, path);
    }
}
