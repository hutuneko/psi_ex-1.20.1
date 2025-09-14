// PsiPieceConditionsProvider.java
package com.hutuneko.psi_ex.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PsiPieceConditionsProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final PackOutput.PathProvider pathProvider;
    private final String modid;

    // 1エントリ = 1ファイルに対応（ファイル名 → JSONオブジェクト）
    private final Map<String, JsonObject> entries = new LinkedHashMap<>();

    public PsiPieceConditionsProvider(PackOutput output, String modid) {
        this.modid = modid;
        this.pathProvider = output.createPathProvider(
                PackOutput.Target.DATA_PACK, "psi_piece_conditions");
    }

    // ---------- 使いやすい追加用ヘルパ ----------
    public PsiPieceConditionsProvider pieceAttributeAtLeast(
            ResourceLocation piece, ResourceLocation attribute,
            double value, String mode, String messageKey, String fileName
    ) {
        JsonObject root = new JsonObject();
        root.addProperty("piece", piece.toString());

        JsonObject cond = new JsonObject();
        cond.addProperty("type", "attribute_at_least");
        cond.addProperty("attribute", attribute.toString());
        cond.addProperty("value", value);
        cond.addProperty("mode", mode); // "BASE" or "TOTAL"
        root.add("cond", cond);

        if (messageKey != null && !messageKey.isEmpty()) {
            root.addProperty("message", messageKey);
        }

        entries.put(fileName, root);
        return this;
    }

    /** 自由に JSON を渡したい時のフック */
    public PsiPieceConditionsProvider raw(String fileName, JsonObject json) {
        entries.put(fileName, json);
        return this;
    }
    // ---------------------------------------------

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (Map.Entry<String, JsonObject> e : entries.entrySet()) {
            Path path = pathProvider.json(new ResourceLocation(modid, e.getKey()));
            futures.add(DataProvider.saveStable(cache, e.getValue(), path));
        }

        // 1件もなくても null を返さない
        return futures.isEmpty()
                ? CompletableFuture.completedFuture(null)
                : CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }


    @Override
    public @NotNull String getName() {
        return "Psi Piece Conditions (" + modid + ")";
    }
}
