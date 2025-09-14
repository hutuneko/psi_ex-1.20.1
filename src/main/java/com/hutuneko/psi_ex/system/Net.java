package com.hutuneko.psi_ex.system;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.system.attribute.C2SSetAttribute;
import com.hutuneko.psi_ex.system.attribute.S2COpenEditor;
import com.hutuneko.psi_ex.system.capability.SyncPsionS2C;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Net {

    // チャンネルは 1 本化（"main"）
    private static final String PROTOCOL = "1"; // 旧 "0" から上げてもOK。左右一致が必須
    public static SimpleChannel CHANNEL;
    private static int id = 0;

    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CHANNEL = NetworkRegistry.newSimpleChannel(
                    new ResourceLocation(PsiEX.MOD_ID, "main"),
                    () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals
            );

            id = 0;

            // --- Client 向け（サーバ→クライアント）
            CHANNEL.messageBuilder(SyncPsionS2C.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                    .encoder(SyncPsionS2C::encode)
                    .decoder(SyncPsionS2C::decode)
                    .consumerMainThread(SyncPsionS2C::handle)
                    .add();

            // --- Server 向け（クライアント→サーバ）
            CHANNEL.messageBuilder(C2SSetAttribute.class, id++, NetworkDirection.PLAY_TO_SERVER)
                    .encoder(C2SSetAttribute::encode)
                    .decoder(C2SSetAttribute::decode)
                    .consumerMainThread(C2SSetAttribute::handle)
                    .add();

            // --- Client 向け（サーバ→クライアント）
            CHANNEL.messageBuilder(S2COpenEditor.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                    .encoder(S2COpenEditor::encode)
                    .decoder(S2COpenEditor::decode)
                    .consumerMainThread(S2COpenEditor::handle)
                    .add();
        });
    }

    // ★ 旧コードの以下は削除
    // private static final String PROTOCOL0 = "0";
    // public static SimpleChannel CHANNEL; （上で再定義済）
    // public static final String PROTOCOL1 = "1";
    // public static final SimpleChannel CH = ...;
    // public static void init() { CH.registerMessage(...); ... }
}

