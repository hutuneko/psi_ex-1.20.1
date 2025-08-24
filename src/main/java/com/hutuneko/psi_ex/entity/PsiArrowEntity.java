package com.hutuneko.psi_ex.entity;

import com.hutuneko.psi_ex.compat.PsiEXRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayerFactory;
import org.jetbrains.annotations.NotNull;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.common.item.ItemSpellDrive;

public class PsiArrowEntity extends AbstractArrow {
    private ItemStack stack = ItemStack.EMPTY;
    private boolean triggered = false;
    public void setStack(ItemStack s) { this.stack = s.copy(); }
    public ItemStack getStack() { return stack; }
    public PsiArrowEntity(EntityType<? extends PsiArrowEntity> type, Level level) {
        super(type, level);
        this.pickup = Pickup.ALLOWED;
    }
    public PsiArrowEntity(Level level, LivingEntity shooter ,ItemStack stack) {
        super(PsiEXRegistry.PSI_ARROW_ENTITY.get(), shooter, level);
        this.pickup = Pickup.ALLOWED;
    }

    @Override
    protected void onHit(@NotNull HitResult hit) {
        super.onHit(hit);
        if (!level().isClientSide && !triggered) {
            Vec3 p = hit.getLocation();
            Spell spell = ItemSpellDrive.getSpell(stack);
            runPsiAt(p, spell, this.level());
            triggered = true;
        }
    }

    private void runPsiAt(Vec3 pos, Spell spell, Level level) {
        if (!(getOwner() instanceof ServerPlayer caster)) return;

        if (spell != null) {
            caster = FakePlayerFactory.getMinecraft((ServerLevel) level);
            double ox = pos.x, oy = pos.y, oz = pos.z;
            caster.setPos(ox, oy, oz);
            SpellContext ctx = new SpellContext()
                    .setPlayer(caster)
                    .setSpell(spell);
            ctx.cspell.safeExecute(ctx);
        }
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(PsiEXRegistry.PSI_ARROW.get());
    }
}
