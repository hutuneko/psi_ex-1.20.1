
package com.hutuneko.psi_ex.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.piece.PieceTrick;
import vazkii.psi.api.spell.param.ParamAny;
import vazkii.psi.api.spell.param.ParamVector;

public class PieceTrick_copy extends PieceTrick {
    // ロガーを宣言
    private static final Logger LOGGER = LogManager.getLogger();

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

//        addParam(posParam = new ParamVector(SpellParam.GENERIC_NAME_VECTOR3,SpellParam.BLUE,false,false
//        ));

        addParam(xParam = new ParamVector(SpellParam.GENERIC_NAME_X,SpellParam.BLUE,false,false
        ));
        addParam(yParam = new ParamVector(SpellParam.GENERIC_NAME_Y,SpellParam.BLUE,false,false
        ));
        addParam(zParam = new ParamVector(SpellParam.GENERIC_NAME_Z,SpellParam.BLUE,false,false
        ));
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
        Vector3 vx = getParamValue(context, xParam);
        Vector3 vy = getParamValue(context, yParam);
        Vector3 vz = getParamValue(context, zParam);
        if (context.caster == null) {
            throw new SpellRuntimeException("Must be a player");
        }
        Player player = context.caster;
        Level world = player.level();
        Vector3 v = getParamValue(context, posParam);
        BlockPos target = new BlockPos(
                (int) Math.floor(v.x),
                (int) Math.floor(v.y),
                (int) Math.floor(v.z)
        );
        player.displayClientMessage(
                Component.literal("DEBUG: target = " + target),
                true
        );

        // --- デバッグ2: storageデータサイズ ---
        Object raw = getParamValue(context, storageParam);
        if (!(raw instanceof ListTag stored)) {
            player.displayClientMessage(
                    Component.literal("DEBUG: dataParam is not ListTag"),
                    true
            );
            throw new SpellRuntimeException("No storage data provided");
        }
        player.displayClientMessage(
                Component.literal("DEBUG: stored list size = " + stored.size()),
                true
        );

        // --- 該当エントリ検索 ---
        CompoundTag found = null;
        for (Tag t : stored) {
            if (!(t instanceof CompoundTag tag)) continue;
            BlockPos p = null;
            if (tag.contains("Pos")) {
                p = BlockPos.of(tag.getLong("Pos"));
            } else if (tag.contains("x") && tag.contains("y") && tag.contains("z")) {
                p = new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
            }
            if (p != null && p.equals(target)) {
                found = tag;
                break;
            }
        }
        if (found == null) {
            player.displayClientMessage(
                    Component.literal("DEBUG: no entry found at " + target),
                    true
            );
            throw new SpellRuntimeException("No stored data at " + target);
        }
        player.displayClientMessage(
                Component.literal("DEBUG: found entry keys = " + found.getAllKeys()),
                true
        );

        // --- ブロック状態復元 ---
        if (world.isClientSide) {
            // クライアントでは何もしない
            return null;
        }
        // サーバー側のワールドにキャスト
        ServerLevel serverWorld = (ServerLevel) world;

        // 例: デバッグログをサーバーコンソールに出力
        LOGGER.info("ストレージ内エントリ数 = {}, 配置先 = {}", stored.size(), target);

        // ブロック状態の復元
        if (found.contains("block_state", Tag.TAG_COMPOUND)) {
            CompoundTag stateTag = found.getCompound("block_state");
            BlockState state = NbtUtils.readBlockState(
                    (net.minecraft.core.HolderGetter<net.minecraft.world.level.block.Block>) serverWorld.registryAccess().registryOrThrow(Registries.BLOCK),
                    stateTag
            );
            LOGGER.info("配置ブロック = {} at {}", state.getBlock(), target);
            // setBlockAndUpdate でクライアントにも即時同期
            serverWorld.setBlockAndUpdate(target, state);
        }

        // タイルエンティティの復元
        if (found.contains("block_entity", Tag.TAG_COMPOUND)) {
            CompoundTag beTag = found.getCompound("block_entity");
            beTag.remove("x"); beTag.remove("y"); beTag.remove("z");
            BlockEntity be = serverWorld.getBlockEntity(target);
            if (be != null) {
                be.load(beTag);
                be.setChanged();
                LOGGER.info("タイルエンティティを復元: {}", be.getType());
            }
        }

        return null;
    }

}
