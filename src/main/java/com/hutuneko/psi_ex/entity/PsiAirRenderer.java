package com.hutuneko.psi_ex.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class PsiAirRenderer extends ThrownItemRenderer<PsiAirEntity> {
    public PsiAirRenderer(EntityRendererProvider.Context context) {
        super(context, 1.0F, true);
    }
}
