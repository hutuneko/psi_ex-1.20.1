package com.hutuneko.psi_ex.system.attribute;

import com.hutuneko.psi_ex.system.Net;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * スライダーGUI版：
 * - 各属性はスライダーで編集
 * - 合計が MAX_TOTAL を超えないよう変更側のみクランプ
 * - 値確定時（ドラッグ終了 or +/- キー操作確定）にサーバーへ送信
 */
public class AttributeEditorScreen extends Screen {

    /** 合計の上限（必要に応じて変更） */
    private static final double MAX_TOTAL = 1000;

    /** 個々の下限・上限（全項目共通。個別に変えたければ Row にフィールドを足してください） */
    private static final double MIN_PER = 0.0;
    private static final double MAX_PER = 1000;

    private static class Row {
        final ResourceLocation id;
        final Component label;
        double current; // 実値（MIN_PER..MAX_PER）
        Slider slider;

        Row(ResourceLocation id, double current) {
            this.id = id;
            this.label = Component.literal(id.toString());
            this.current = current;
        }
    }

    private final List<Row> rows = new ArrayList<>();

    /** S2C で初期値を受け取り、この画面を開く想定 */
    public AttributeEditorScreen(Map<ResourceLocation, Double> values) {
        super(Component.literal("Attribute Editor"));
        values.forEach((id, v) -> rows.add(new Row(id, clamp(v, MIN_PER, MAX_PER))));
    }

    @Override
    protected void init() {
        int y = 34;
        int left = this.width / 2 - 160;
        int sliderX = this.width / 2 - 30; // ラベルの右にスライダー
        int sliderW = 210;

        for (Row r : rows) {
            // 実値→[0..1]へ正規化
            double norm = denormalize(r.current);
            r.slider = new Slider(sliderX, y - 4, sliderW, 20, r, norm);
            addRenderableWidget(r.slider);
            y += 24;
        }

        // Close ボタン
        addRenderableWidget(Button.builder(Component.literal("Close"), b -> onClose())
                .bounds(this.width / 2 - 0, this.height - 28, 80, 20).build());
    }

    @Override
    public void render(@NotNull GuiGraphics g, int mx, int my, float pt) {
        renderBackground(g);
        g.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xFFFFFF);

        // 合計表示
        int left = this.width / 2 - 160;
        int sumY = 18;
        g.drawString(this.font, Component.literal(
                "Total: " + String.format(Locale.ROOT, "%.2f / %.2f", total(), MAX_TOTAL)
        ), left, sumY, 0xFFD080, false);

        // ラベル群
        int y = 36;
        for (Row r : rows) {
            // 左にラベル、右端に現在値の数値表示
            g.drawString(this.font, r.label, left, y, 0xAAAAAA, false);
            String val = String.format(Locale.ROOT, "%.2f", r.current);
            int valX = this.width / 2 + 190;
            g.drawString(this.font, Component.literal(val), valX, y, 0xFFFFFF, false);
            y += 24;
        }

        super.render(g, mx, my, pt);
    }

    @Override
    public boolean isPauseScreen() { return false; }

    /* ------------- 内部クラス：スライダー ------------- */

    private class Slider extends AbstractSliderButton {
        final Row row;

        /**
         * @param value 正規化済み [0..1]
         */
        Slider(int x, int y, int w, int h, Row row, double value) {
            super(x, y, w, h, Component.empty(), value);
            this.row = row;
            updateFromNormalized(value); // 表示文言更新
        }

        @Override
        protected void updateMessage() {
            // ここではボタン上に数値は描かず、右側に描画しているため空でOK
            setMessage(Component.empty());
        }

        @Override
        protected void applyValue() {
            // ユーザ操作で値が変わった時に呼ばれる
            // 1) 正規化→実値へ
            double proposed = normalize(this.value);

            // 2) 合計チェック（このスライダーだけをクランプ）
            double others = totalExcept(row);
            double allowed = clamp(MAX_TOTAL - others, MIN_PER, MAX_PER);
            double clamped = clamp(proposed, MIN_PER, allowed);

            // 3) 実値・表示の反映
            row.current = clamped;
            double backNorm = denormalize(clamped);
            if (Math.abs(backNorm - this.value) > 1e-9) {
                // 表示側を合わせる（はみ出しクランプした場合）
                this.value = backNorm;
            }
        }

        @Override
        public void onRelease(double mouseX, double mouseY) {
            super.onRelease(mouseX, mouseY);
            // 値確定 → サーバーへ送信
            Net.CHANNEL.sendToServer(new C2SSetAttribute(row.id, row.current));
        }

        /** 正規化→実値 */
        private double normalize(double v01) {
            return MIN_PER + v01 * (MAX_PER - MIN_PER);
        }
        /** 実値→正規化 */
        private double denormalize(double actual) {
            return (actual - MIN_PER) / (MAX_PER - MIN_PER);
        }
        /** 表示同期用（初期化時） */
        private void updateFromNormalized(double v01) {
            this.value = clamp(v01, 0, 1);
            this.row.current = normalize(this.value);
            updateMessage();
        }
    }

    /* ------------- 合計・補助 ------------- */

    private double total() {
        double t = 0;
        for (Row r : rows) t += r.current;
        return t;
    }

    private double totalExcept(Row except) {
        double t = 0;
        for (Row r : rows) if (r != except) t += r.current;
        return t;
    }

    private static double denormalize(double actual) {
        return (actual - MIN_PER) / (MAX_PER - MIN_PER);
    }

    private static double clamp(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }

    public void onServerConfirmed(ResourceLocation id, double value) {
        for (Row r : rows) {
            if (r.id.equals(id)) {
                r.current = value;
                if (r.slider != null) {
                    r.slider.updateFromNormalized((value - MIN_PER) / (MAX_PER - MIN_PER));
                }
            }
        }
    }
    @Override
    public void removed() {
        // まとめて確定送信（バッチにできるなら1パケットにするのがベスト）
        rows.forEach(r -> Net.CHANNEL.sendToServer(new C2SSetAttribute(r.id, r.current)));
        super.removed();
    }

}
