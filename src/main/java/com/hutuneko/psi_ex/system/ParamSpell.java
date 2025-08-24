// yourmod/api/spell/param/ParamSpell.java
package com.hutuneko.psi_ex.system;

import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.piece.PieceTrick;

/**
 * サブスペル（子スペル）を受け取るためのパラメーター。
 * このパラメーターに接続されたピースは
 * SpellPiece 型であることが要求されます。
 */
public class ParamSpell extends SpellParam {

    /**
     * @param name        パラメーター名（表示や国際化キーに使われます）
     * @param color       パラメーターの色（SpellParam.RED, GREEN, など）
     * @param canDisable  スペルの無効化スイッチを表示するか
     */
    public ParamSpell(String name, int color, boolean canDisable) {
        super(name, color, canDisable);
    }

    @Override
    protected Class<?> getRequiredType() {
        return PieceTrick.class;
    }
}

