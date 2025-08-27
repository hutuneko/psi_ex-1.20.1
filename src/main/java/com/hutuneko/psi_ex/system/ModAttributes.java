package com.hutuneko.psi_ex.system;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.compat.PsiEXRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.world.entity.monster.ZombifiedPiglin.createAttributes;

@Mod.EventBusSubscriber(modid = PsiEX.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModAttributes {
    @SubscribeEvent
    public static void onEntityAttributeCreation(net.minecraftforge.event.entity.EntityAttributeCreationEvent e) {
        // 例: Villager を継承した PsiTestEntity
        e.put(PsiEXRegistry.PSI_TEST_ENTITY.get(), createAttributes().build());

        // もし LivingEntity 派生で自作なら：
        // e.put(ModEntities.PSI_TEST.get(),
        //     AttributeSupplier.builder()
        //         .add(Attributes.MAX_HEALTH, 20.0)
        //         .add(Attributes.MOVEMENT_SPEED, 0.3)
        //         .add(Attributes.ARMOR, 0.0)
        //         // 必要に応じて…
        //         .build());
    }
}
