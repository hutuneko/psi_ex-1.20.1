// PsiPocketPeripheral.java
package com.hutuneko.psi_ex.cc;

import com.hutuneko.psi_ex.compat.PsiEXRegistry;
import com.hutuneko.psi_ex.system.CuriosUtil;
import com.mojang.authlib.GameProfile;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotResult;
import vazkii.psi.api.cad.ISocketable;
import vazkii.psi.api.spell.ISpellAcceptor;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;

import java.util.Optional;
import java.util.UUID;

public class PsiPocketPeripheral implements IPeripheral {
    private final IPocketAccess pocket;

    public PsiPocketPeripheral(IPocketAccess pocket) { this.pocket = pocket; }

    @NotNull @Override
    public String getType() { return "psi_caster"; }

    @Override
    public boolean equals(@Nullable IPeripheral other) { return this == other; }

    @LuaFunction(mainThread = true)
    public final Object[] castCurioSpell(String playerId, int socketIndex, @Nullable Double x, @Nullable Double y, @Nullable Double z) {
        ServerLevel level = pocket.getLevel();
        MinecraftServer server = level.getServer();

        // プレイヤー解決（UUID優先、無理なら名前）
        ServerPlayer player = resolvePlayer(server, playerId);
        if (player == null) return new Object[]{ false, "player not found: " + playerId };
        Optional<SlotResult> found = CuriosUtil.findFirstByItem(player, PsiEXRegistry.PSI_CURIO_BULLET.get());
        if (found.isEmpty()) return new Object[]{ false, "psi_curio_bullet not equipped" };

        ItemStack curio = found.get().stack();
        final boolean hasAll = x != null && y != null && z != null;
        // ISocketable 取得
        ISocketable sock = ISocketable.socketable(curio);
        if (sock == null) return new Object[]{ false, "no ISocketable capability on curio" };

        int slots = sock.getLastSlot();
        if (socketIndex < 0 || socketIndex > slots) {
            return new Object[]{ false, "socket index out of range: " + socketIndex + " / " + slots };
        }

        // 指定ソケットの弾取得
        ItemStack bullet = sock.getBulletInSocket(socketIndex);
        if (bullet.isEmpty()) return new Object[]{ false, "empty bullet in socket " + socketIndex };

        // 術式受け口
        ISpellAcceptor acc = ISpellAcceptor.acceptor(bullet);
        if (acc == null) return new Object[]{ false, "bullet has no ISpellAcceptor" };
        if (!ISpellAcceptor.hasSpell(bullet)) return new Object[]{ false, "no spell stored in bullet" };

        try {
            Spell s = acc.getSpell();
            SpellContext sc;
            if (hasAll) {
                GameProfile prof = new GameProfile(UUID.randomUUID(), "psi_fake");
                ServerPlayer fake = FakePlayerFactory.get(level,prof);
                fake.setPos(x, y, z);
                fake.setYRot(player.getYRot());
                fake.setXRot(player.getXRot());
                fake.setYHeadRot(player.getYHeadRot());
                sc = new SpellContext().setSpell(s).setPlayer(fake);
            }else {
                sc = new SpellContext().setSpell(s).setPlayer(player);
            }
            sc.cspell.safeExecute(sc);
            return new Object[]{ true, "cast ok" };
        } catch (Throwable t) {
            return new Object[]{ false, "cast failed: " + t.getMessage() };
        }
    }

    private static @Nullable ServerPlayer resolvePlayer(MinecraftServer server, String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return server.getPlayerList().getPlayer(uuid);
        } catch (IllegalArgumentException ignored) {
            // UUID でなければ名前で検索
            return server.getPlayerList().getPlayerByName(id);
        }
    }
}
