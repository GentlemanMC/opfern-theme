/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package me.zeroeightsix.kami.gui.rgui.util;

import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class BlockUtils {
    static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isEntitiesEmpty(BlockPos pos) {
        List entities = BlockUtils.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)).stream().filter(e -> !(e instanceof EntityItem)).filter(e -> !(e instanceof EntityXPOrb)).collect(Collectors.toList());
        return entities.isEmpty();
    }

    public static boolean placeBlockScaffold(BlockPos pos, boolean rotate) {
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if (!BlockUtils.canBeClicked(neighbor)) continue;
            Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
            if (rotate) {
                BlockUtils.faceVectorPacketInstant(hitVec);
            }
            BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtils.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            BlockUtils.processRightClickBlock(neighbor, side2, hitVec);
            BlockUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
            BlockUtils.mc.rightClickDelayTimer = 0;
            BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtils.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            return true;
        }
        return false;
    }

    private static PlayerControllerMP getPlayerController() {
        return BlockUtils.mc.playerController;
    }

    public static void processRightClickBlock(BlockPos pos, EnumFacing side, Vec3d hitVec) {
        BlockUtils.getPlayerController().processRightClickBlock(BlockUtils.mc.player, BlockUtils.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }

    public static IBlockState getState(BlockPos pos) {
        return BlockUtils.mc.world.getBlockState(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return BlockUtils.getState(pos).getBlock();
    }

    public static boolean canBeClicked(BlockPos pos) {
        return BlockUtils.getBlock(pos).canCollideCheck(BlockUtils.getState(pos), false);
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = BlockUtils.getNeededRotations2(vec);
        BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], BlockUtils.mc.player.onGround));
    }

    private static float[] getNeededRotations2(Vec3d vec) {
        Vec3d eyesPos = BlockUtils.getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{BlockUtils.mc.player.rotationYaw + MathHelper.wrapDegrees((float)(yaw - BlockUtils.mc.player.rotationYaw)), BlockUtils.mc.player.rotationPitch + MathHelper.wrapDegrees((float)(pitch - BlockUtils.mc.player.rotationPitch))};
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(BlockUtils.mc.player.posX, BlockUtils.mc.player.posY + (double)BlockUtils.mc.player.getEyeHeight(), BlockUtils.mc.player.posZ);
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(BlockUtils.getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return BlockUtils.getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
}

