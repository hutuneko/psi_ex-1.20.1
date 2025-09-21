package com.hutuneko.psi_ex.api;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.compat.PsiEXRegistry;
import com.hutuneko.psi_ex.item.PsiSpellBook;
import com.hutuneko.psi_ex.system.CuriosUtil;
import com.hutuneko.psi_ex.system.attribute.PsiEXAttributes;
import com.hutuneko.psi_ex.system.PsiPieceConditionReloadListener;
import com.hutuneko.psi_ex.system.capability.PsionProvider;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import moffy.ticex.modules.general.TicEXRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.SlotResult;
import vazkii.psi.api.cad.ISocketable;
import vazkii.psi.api.spell.ISpellAcceptor;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = PsiEX.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEventBus {
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onSpellDamage(SpellDamageEvent e) {
        var level = e.getEntity().level();
        if (level.isClientSide) return;

        var caster = e.getSpellDamageSource().getEntity();
        if (!(caster instanceof Player p)) return;
        if (!hasMySpecialSpellbook(p)) return;

        Optional<SlotResult> res = CuriosUtil.findFirstByItem(p, PsiEXRegistry.PSI_SPELLBOOK.get());
        if (res.isEmpty()) return;
        ItemStack spellbook = res.get().stack();
        if (spellbook.isEmpty()) return;
        ISocketable sock = ISocketable.socketable(spellbook);
        if (sock == null ) return;
        int idx = PsiSpellBook.getIndex(spellbook);
        ItemStack bullet = sock.getBulletInSocket(idx);
        ISpellAcceptor acc = ISpellAcceptor.acceptor(bullet);
        if (acc == null || !ISpellAcceptor.hasSpell(bullet)) return;
        Spell spell = acc.getSpell();
        Entity entity = e.getEntity();
        if (!(entity instanceof LivingEntity livingEntity)) return;
        SpellContext spellContext = new SpellContext();
        spellContext.attackedEntity = livingEntity;
        spellContext.setSpell(spell).setPlayer(p);

        spellContext.cspell.safeExecute(spellContext);
    }

    private static boolean hasMySpecialSpellbook(Player p) {
        return CuriosUtil.findFirstByItem(p, PsiEXRegistry.PSI_CURIO_BULLET.get()).isEmpty() || CuriosUtil.findFirstByItem(p, TicEXRegistry.CATALYST_IRONS_SPELLBOOK.get()).isEmpty();
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e){
//        if (e.phase != TickEvent.Phase.END || e.player.level().isClientSide) return;
//        e.player.getCapability(PsionProvider.CAP).ifPresent(cap -> cap.tickRegain(e.player));
    }
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            Player oldPlayer = event.getOriginal();
            Player newPlayer = event.getEntity();
            newPlayer.getCapability(PsionProvider.CAP).ifPresent(cap -> cap.setPsion(newPlayer.getAttributeValue(PsiEXAttributes.PSI_PSION_POINT.get())));
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onChangeTarget(LivingChangeTargetEvent ev) {
        if (ev.getNewTarget() instanceof FakePlayer) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLivingAttack(LivingAttackEvent ev) {
        if (ev.getEntity() instanceof FakePlayer) {
            ev.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new PsiPieceConditionReloadListener());
    }

    private final Map<UUID, Long> recentlyShotPlayers = new HashMap<>();
    private final Map<UUID, ItemStack> recentlyShotBows = new HashMap<>();

    @SubscribeEvent
    public void onArrowLoose(ArrowLooseEvent event) {
        Player player = event.getEntity();
        ItemStack bow = event.getBow();
        // 矢を撃ったプレイヤーを記録（タイムスタンプ付き）
        recentlyShotPlayers.put(player.getUUID(), System.currentTimeMillis());
        recentlyShotBows.put(player.getUUID(), bow);
    }

    @SubscribeEvent
    public void onArrowSpawn(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof AbstractArrow arrow)) return;

        Entity shooter = arrow.getOwner();
        if (!(shooter instanceof Player player)) return;

        // 直近で矢を撃ったプレイヤーかどうか確認
        Long shotTime = recentlyShotPlayers.get(player.getUUID());
        ItemStack bow = recentlyShotBows.get(player.getUUID());
        if (shotTime != null && System.currentTimeMillis() - shotTime < 200) {

            if (bow.getItem() == PsiEXRegistry.PSI_BOW.get()){
                if (bow.isEmpty()) return;
                ISocketable sock = ISocketable.socketable(bow);
                if (sock == null ) return;
                int idx = 1;
                ItemStack bullet = sock.getBulletInSocket(idx);
                ISpellAcceptor acc = ISpellAcceptor.acceptor(bullet);
                if (acc == null || !ISpellAcceptor.hasSpell(bullet)) return;
                Spell spell = acc.getSpell();
                SpellContext spellContext = new SpellContext();
                spellContext.focalPoint = shooter;
                spellContext.setSpell(spell).setPlayer(player);
                int cost = spellContext.cspell.metadata.hashCode();
                spellContext.cspell.safeExecute(spellContext);
            }

            // 一度使ったら削除
            recentlyShotPlayers.remove(player.getUUID());
            recentlyShotBows.remove(player.getUUID());
        }
    }


}
