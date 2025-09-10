package com.hutuneko.psi_ex.system;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.compat.PsiEXRegistry;
import com.hutuneko.psi_ex.item.PsiSpellBook;
import com.hutuneko.psi_ex.system.capability.PsionProvider;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import moffy.ticex.modules.general.TicEXRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.SlotResult;
import vazkii.psi.api.cad.ISocketable;
import vazkii.psi.api.spell.ISpellAcceptor;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = PsiEX.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class SpellDamageListeners {
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
        System.out.println(spellbook);
        if (spellbook.isEmpty()) return;
        ISocketable sock = ISocketable.socketable(spellbook);
        if (sock == null ) return;
        int idx = PsiSpellBook.getIndex(spellbook);
        ItemStack bullet = sock.getBulletInSocket(idx);
        ISpellAcceptor acc = ISpellAcceptor.acceptor(bullet);
        if (acc == null || !ISpellAcceptor.hasSpell(bullet)) return;
        Spell spell = acc.getSpell();
        Entity entity = e.getEntity();
        System.out.println(entity);
        SpellContext spellContext = new SpellContext()
                .setSpell(spell)
                .setPlayer(p)
                .setFocalPoint(entity);
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
}
