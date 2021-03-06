package mchorse.blockbuster.recording.actions;

import io.netty.buffer.ByteBuf;
import mchorse.blockbuster.recording.data.Frame;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Parent of all recording actions
 *
 * This class holds additional information about player's actions performed during
 * recording. Supports abstraction and stuffz.
 */
public abstract class Action
{
    /**
     * Apply action on an actor (shoot arrow, mount entity, break block, etc.)
     *
     * Some action doesn't necessarily should have apply method (that's why this
     * method is empty)
     */
    public void apply(EntityLivingBase actor)
    {}

    public void applyWithForce(EntityLivingBase actor)
    {
        this.apply(actor);
    }

    public void changeOrigin(double rotation, double newX, double newY, double newZ, double firstX, double firstY, double firstZ)
    {}

    public void flip(String axis, double coordinate)
    {}

    public void copyActor(EntityLivingBase actor, EntityPlayer player, Frame frame)
    {
        player.width = actor.width;
        player.height = actor.height;
        player.eyeHeight = actor.getEyeHeight();
        player.setEntityBoundingBox(actor.getEntityBoundingBox());

        player.posX = actor.posX;
        player.posY = actor.posY;
        player.posZ = actor.posZ;
        player.rotationYaw = frame.yaw;
        player.rotationPitch = frame.pitch;
        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, actor.getHeldItemMainhand());
        player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, actor.getHeldItemOffhand());
    }

    /**
     * Persist action from byte buffer. Used for sending the action 
     * over the network.
     */
    public void fromBuf(ByteBuf buf)
    {}

    /**
     * Persist action to byte buffer. Used for sending the action over 
     * the network.
     */
    public void toBuf(ByteBuf buf)
    {}

    /**
     * Persist action from NBT tag. Used for loading from the disk.
     */
    public void fromNBT(NBTTagCompound tag)
    {}

    /**
     * Persist action to NBT tag. Used for saving to the disk.
     */
    public void toNBT(NBTTagCompound tag)
    {}

    /**
     * Whether this action is safe. Safe action means that it doesn't 
     * modify the world, at max, only its user. 
     */
    public boolean isSafe()
    {
        return false;
    }
}