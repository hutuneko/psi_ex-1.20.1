package com.hutuneko.psi_ex.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class NeedleRenderer extends ThrownItemRenderer<PsiNeedleDartEntity> {
    public NeedleRenderer(EntityRendererProvider.Context context) {
        super(context, 1.0F, true);
    }
}
