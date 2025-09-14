package com.hutuneko.psi_ex.system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellPiece;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// PsiPieceConditionReloadListener.java
public final class PsiPieceConditionReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setLenient().create();

    public PsiPieceConditionReloadListener() {
        super(GSON, "psi_piece_conditions"); // data/<modid>/psi_piece_conditions/*.json
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager mgr, @NotNull ProfilerFiller profiler) {
        // いったん全クリア
        PieceConditionRegistry.clear();

        map.forEach((resId, json) -> {
            try {
                JsonObject o = json.getAsJsonObject();

                ResourceLocation pieceId = new ResourceLocation(o.get("piece").getAsString());
                Component failMsg = null;
                if (o.has("message")) {
                    String m = o.get("message").getAsString();
                    failMsg = m.contains(".") ? Component.translatable(m) : Component.literal(m);
                }

                PieceCondition cond = parseNode(o, failMsg);
                if (cond != null) {
                    PieceConditionRegistry.register(pieceId, cond);
                }
            } catch (Exception e) {
                // ログ出力
            }
        });
    }

    // cond / all / any のいずれかを受け取って PieceCondition に組む
    @Nullable
    private PieceCondition parseNode(JsonObject o, @Nullable Component msg) {
        if (o.has("cond")) return wrap(parseLeaf(o.get("cond").getAsJsonObject()), msg);
        PieceCondition and = null, or = null;

        if (o.has("all")) {
            List<PieceCondition> list = new ArrayList<>();
            for (JsonElement el : o.getAsJsonArray("all")) list.add(parseLeaf(el.getAsJsonObject()));
            and = list.stream().reduce(PieceConditions::and).orElse(null);
        }
        if (o.has("any")) {
            List<PieceCondition> list = new ArrayList<>();
            for (JsonElement el : o.getAsJsonArray("any")) list.add(parseLeaf(el.getAsJsonObject()));
            or = list.stream().reduce(PieceConditions::or).orElse(null);
        }
        PieceCondition base;
        if (and != null && or != null) base = PieceConditions.and(and, or);
        else base = (and != null ? and : or);

        return wrap(base, msg);
    }

    // 失敗メッセージを後付け
    private PieceCondition wrap(@Nullable PieceCondition core, @Nullable Component msg) {
        if (core == null) return null;
        if (msg == null) return core;
        return new PieceCondition() {
            @Override public boolean test(SpellContext ctx, SpellPiece piece) { return core.test(ctx, piece); }
            @Override public Component failMessage() { return msg; }
        };
    }

    private PieceCondition parseLeaf(JsonObject leaf) {
        String type = leaf.get("type").getAsString();

        switch (type) {
            case "attribute_at_least": {
                ResourceLocation attr = new ResourceLocation(leaf.get("attribute").getAsString());
                double v = leaf.get("value").getAsDouble();
                var mode = AttributeValueCondition.Mode.valueOf(leaf.get("mode").getAsString());
                return new AttributeValueCondition(attr, v, mode, true, null);
            }
            case "attribute_at_most": {
                ResourceLocation attr = new ResourceLocation(leaf.get("attribute").getAsString());
                double v = leaf.get("value").getAsDouble();
                var mode = AttributeValueCondition.Mode.valueOf(leaf.get("mode").getAsString());
                return new AttributeValueCondition(attr, v, mode, false, null);
            }
            case "attribute_between": {
                ResourceLocation attr = new ResourceLocation(leaf.get("attribute").getAsString());
                double min = leaf.get("min").getAsDouble();
                double max = leaf.get("max").getAsDouble();
                var mode = AttributeValueCondition.Mode.valueOf(leaf.get("mode").getAsString());
                return PieceConditions.and(
                        new AttributeValueCondition(attr, min, mode, true, null),
                        new AttributeValueCondition(attr, max, mode, false, null)
                );
            }
            case "dimension_is": {
                ResourceLocation dim = new ResourceLocation(leaf.get("value").getAsString());
                return (ctx, p) -> ctx != null && ctx.caster != null && ctx.caster.level().dimension().location().equals(dim);
            }
            case "biome_tag": {
                ResourceLocation tagId = new ResourceLocation(leaf.get("tag").getAsString());
                TagKey<Biome> key = TagKey.create(Registries.BIOME, tagId);
                return (ctx, p) -> {
                    if (ctx == null || ctx.caster == null) return false;
                    Holder<Biome> b = ctx.caster.level().getBiome(ctx.caster.blockPosition());
                    return b.is(key);
                };
            }
            case "time_range": {
                int from = leaf.get("from").getAsInt();
                int to   = leaf.get("to").getAsInt();
                return (ctx, p) -> {
                    if (ctx == null || ctx.caster == null) return false;
                    long t = ctx.caster.level().getDayTime() % 24000L;
                    return (from <= to) ? (t >= from && t <= to) : (t >= from || t <= to);
                };
            }
            // 必要に応じて他タイプを追加
            default:
                return (ctx, p) -> true; // 未知タイプは無視（または false で弾く運用でもOK）
        }
    }
}
