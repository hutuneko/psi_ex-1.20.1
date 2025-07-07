package com.hutuneko.psi_ex.system;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;

public class ParamCompoundTag extends SpellParam {
    public static final ResourceLocation ID = new ResourceLocation("psi_ex", "compound_tag");
    public static final ParamCompoundTag TAG_PARAM = new ParamCompoundTag("psi_ex:compound_tag");

    public ParamCompoundTag(String name) {
        super(name, SpellParam.CYAN, false);
    }

    @Override
    protected Class<?> getRequiredType() {
        return CompoundTag.class;
    }

    public CompoundTag read(FriendlyByteBuf buf) {
        return buf.readNbt();
    }

    public void write(FriendlyByteBuf buf, CompoundTag value) {
        buf.writeNbt(value);
    }

    public CompoundTag read(JsonObject json) throws SpellRuntimeException {
        try {
            return TagParser.parseTag(json.get("value").getAsString());
        } catch (CommandSyntaxException e) {
            throw new SpellRuntimeException("SNBT のパースに失敗: " + e.getMessage());
        }
    }

    public JsonElement write(CompoundTag value) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", ID.toString());
        obj.addProperty("value", value.toString());
        return obj;
    }
}
