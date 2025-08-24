package com.hutuneko.psi_ex.system;

import com.hutuneko.psi_ex.compat.PsiEXRegistry;
import com.hutuneko.psi_ex.entity.PsiArrowRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers e) {
        e.registerEntityRenderer(
                PsiEXRegistry.PSI_ARROW_ENTITY.get(),
                PsiArrowRenderer::new
        );
    }
}
