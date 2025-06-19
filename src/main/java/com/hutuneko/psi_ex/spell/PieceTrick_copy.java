
package com.hutuneko.psi_ex.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.piece.PieceTrick;
import vazkii.psi.api.spell.param.ParamAny;
import vazkii.psi.api.spell.param.ParamVector;

public class PieceTrick_copy extends PieceTrick {
    // --- パラメータ定義 ---
    private ParamVector posParam;
    private ParamVector xParam;
    private ParamVector yParam;
    private ParamVector zParam;
    private ParamAny    storageParam;   // ItemStorage に保存された ListTag

    public PieceTrick_copy(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {

        addParam(posParam = new ParamVector(SpellParam.GENERIC_NAME_VECTOR3,SpellParam.BLUE,false,false
        ));
//        addParam(xParam = new ParamVector(SpellParam.GENERIC_NAME_X,SpellParam.BLUE,false,false
//        ));
//        addParam(yParam = new ParamVector(SpellParam.GENERIC_NAME_Y,SpellParam.BLUE,false,false
//        ));
//        addParam(zParam = new ParamVector(SpellParam.GENERIC_NAME_Z,SpellParam.BLUE,false,false
//        ));
        // ② ItemStorage から取得する NBT リスト
        addParam(storageParam = new ParamAny(SpellParam.GENERIC_NAME_LIST,
                1,false
        ));
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 50);
        meta.addStat(EnumSpellStat.COST,    100);
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
        // --- 1. 座標取得・丸め ---
        Vector3 vec = getParamValue(context, posParam);
        BlockPos target = new BlockPos(
                (int) Math.floor(vec.x),
                (int) Math.floor(vec.y),
                (int) Math.floor(vec.z)
        );

        // --- 2. 保存データ取得 & キャスト ---
        Object raw = getParamValue(context, storageParam);
        if (!(raw instanceof ListTag)) {
            throw new SpellRuntimeException("No stored data");
        }
        ListTag stored = (ListTag) raw;

        // --- 3. 該当 NBT タグをリストから検索 ---
        CompoundTag found = null;
        for (Tag entry : stored) {
            if (!(entry instanceof CompoundTag tag)) continue;
            BlockPos p;
            if (tag.contains("x") && tag.contains("y") && tag.contains("z")) {
                p = new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
            } else if (tag.contains("Pos")) {
                p = BlockPos.of(tag.getLong("Pos"));
            } else continue;
            if (p.equals(target)) {
                found = tag;
                break;
            }
        }
        if (found == null) {
            throw new SpellRuntimeException("No stored data at " + target);
        }

        Level world = context.caster.level();

        // --- 4. ブロック状態の復元 ---
        if (found.contains("block_state", Tag.TAG_COMPOUND)) {
            CompoundTag stateTag = found.getCompound("block_state");
            // 1.20+ では HolderGetter<Block> を渡す必要がある
            BlockState state = NbtUtils.readBlockState(
                    (net.minecraft.core.HolderGetter<net.minecraft.world.level.block.Block>) world.registryAccess().registryOrThrow(Registries.BLOCK),
                    stateTag
            );
            world.setBlock(target, state, 3);
        }

        // --- 5. タイルエンティティの復元 ---
        if (found.contains("id", Tag.TAG_STRING)) {
            CompoundTag beTag = found.copy();
            beTag.remove("x");
            beTag.remove("y");
            beTag.remove("z");
            BlockEntity be = world.getBlockEntity(target);
            if (be != null) {
                be.load(beTag);
                be.setChanged();
            }
        }

        // --- 6. 完了メッセージ ---
        context.caster.displayClientMessage(
                Component.literal("Placed block at " + target),
                false
        );

        return null;
    }
}
