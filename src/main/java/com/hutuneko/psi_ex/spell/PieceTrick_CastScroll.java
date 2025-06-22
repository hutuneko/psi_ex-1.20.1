package com.hutuneko.psi_ex.spell;

import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamAny;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceTrick;

public class PieceTrick_CastScroll extends PieceTrick {
    private ParamVector dirParam;
    private ParamAny    dataParam;

    public PieceTrick_CastScroll(vazkii.psi.api.spell.Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        addParam(dirParam = new ParamVector(SpellParam.GENERIC_NAME_VECTOR,SpellParam.RED,false,false
        ));
        addParam(dataParam = new ParamAny(
                "scrollData", 10101010, false
        ));
    }

    @Override
    public void addToMetadata(vazkii.psi.api.spell.SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 20);
        meta.addStat(EnumSpellStat.COST,   50);
    }

    @Override
    public EnumPieceType getPieceType() {
        return EnumPieceType.TRICK;
    }

    @Override
    public Class<?> getEvaluationType() {
        return Void.class;
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        Player player = context.caster;
        Level world = player.level();
        // サーバーサイドでのみ動かす
        if (world.isClientSide) {
            return null;
        }
        ServerLevel sWorld = (ServerLevel) world;

        // 1) 方向ベクトル
        Vector3 v = getParamValue(context, dirParam);
        Vec3 dir = new Vec3(v.x, v.y, v.z);

        // 2) Selector から来たスクロールスタック
        Object raw = getParamValue(context, dataParam);
        if (!(raw instanceof ItemStack scroll)) {
            throw new SpellRuntimeException("スクロールが渡されていません");
        }

        // 3) Iron のスクロールかチェック
        if (!ISpellContainer.isSpellContainer(scroll)) {
            throw new SpellRuntimeException("これは Iron のスクロールではありません");
        }
        ISpellContainer container = ISpellContainer.get(scroll);

        int count = container.getActiveSpellCount();
        if (count == 0) {
            throw new SpellRuntimeException("スクロールにスペルが入っていません");
        }
        ItemStack scrollCopy = scroll.copy();
        // 4) 各 SpellData を取り出して attemptInitiateCast
        for (int i = 0; i < count; i++) {
            SpellData data = container.getSpellAtIndex(i);
            AbstractSpell spell = data.getSpell();
            int level = data.getLevel();

            if (spell == null) {
                continue;
            }

            spell.attemptInitiateCast(
                    scrollCopy, level,
                    sWorld, player,
                    CastSource.SCROLL, true,
                    "right"
            );
        }

        return null;
    }
}
