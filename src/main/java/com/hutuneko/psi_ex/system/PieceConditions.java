package com.hutuneko.psi_ex.system;

import net.minecraft.network.chat.Component;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellPiece;

import java.util.List;
import java.util.Objects;

/** 条件を合成するための補助クラス */
public final class PieceConditions {

    private PieceConditions() {}

    /** a AND b */
    public static PieceCondition and(PieceCondition a, PieceCondition b) {
        Objects.requireNonNull(a); Objects.requireNonNull(b);
        return (ctx, piece) -> a.test(ctx, piece) && b.test(ctx, piece);
    }

    /** a OR b */
    public static PieceCondition or(PieceCondition a, PieceCondition b) {
        Objects.requireNonNull(a); Objects.requireNonNull(b);
        return (ctx, piece) -> a.test(ctx, piece) || b.test(ctx, piece);
    }

    /** NOT a */
    public static PieceCondition not(PieceCondition a) {
        Objects.requireNonNull(a);
        return (ctx, piece) -> !a.test(ctx, piece);
    }

    /** すべて満たす（空なら true） */
    public static PieceCondition all(List<? extends PieceCondition> list) {
        return (ctx, piece) -> {
            if (list == null || list.isEmpty()) return true;
            for (PieceCondition c : list) if (!c.test(ctx, piece)) return false;
            return true;
        };
    }

    /** いずれか満たす（空なら false） */
    public static PieceCondition any(List<? extends PieceCondition> list) {
        return (ctx, piece) -> {
            if (list == null || list.isEmpty()) return false;
            for (PieceCondition c : list) if (c.test(ctx, piece)) return true;
            return false;
        };
    }

    /**
     * 失敗時メッセージを後付け（ベースをそのままラップ）
     * null メッセージならベースをそのまま返す
     */
    public static PieceCondition withMessage(PieceCondition base, Component failMessage) {
        if (failMessage == null) return base;
        return new PieceCondition() {
            @Override public boolean test(SpellContext ctx, SpellPiece piece) { return base.test(ctx, piece); }
            @Override public Component failMessage() { return failMessage; }
        };
    }
}
