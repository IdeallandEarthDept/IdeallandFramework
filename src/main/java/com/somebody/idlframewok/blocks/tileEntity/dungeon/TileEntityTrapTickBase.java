package com.somebody.idlframewok.blocks.tileEntity.dungeon;

import com.somebody.idlframewok.blocks.blockDungeon.traps.BlockDungeonCustomizedTrap;
import com.somebody.idlframewok.blocks.tileEntity.TileEntityBase;
import com.somebody.idlframewok.init.InitBiome;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.MessageDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.enumGroup.EnumTrapArgTypes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class TileEntityTrapTickBase extends TileEntityBase implements ITickable {

	public AxisAlignedBB aabb;
	public boolean isReady = false;
	public SoundEvent onLoadSound = SoundEvents.BLOCK_NOTE_CHIME;
	public SoundEvent activeSound = SoundEvents.BLOCK_FIRE_EXTINGUISH;
	public EnumParticleTypes particleType = EnumParticleTypes.FLAME;

	int periodTickCount = TICK_PER_SECOND;
	int activeTickCount = 10;
	int postponeTick = 0;
	float damageRatio = 0.1f;

	boolean isInSPBiome = false;

    public Vec3d getPosInFloat()
	{
		BlockPos myPos = this.pos;
		Vec3d posInFloat = new Vec3d(myPos.getX() + 0.5, myPos.getY() + 0.5, myPos.getZ() + 0.5);
		return posInFloat;
	}

	public void init()
	{
		super.init();
		if (!world.isRemote)
		{
			//init Success
			playSoundHere();
			isInSPBiome = world.getBiome(pos) == InitBiome.BIOME_FIRE_TRAP_TOWER;
		}
	}

	public void playSoundHere()
	{
		world.playSound(null, this.pos, onLoadSound, SoundCategory.BLOCKS, 0.1F, 1f);
	}

	public void createParticles()
	{
		if (world.isRemote || isReady)
		{
			Random random = new Random();
			Vec3d myPos = getPosInFloat();
			float range = 1f;
			float x = (random.nextFloat() - 0.5f) * range;
			float z = (random.nextFloat() - 0.5f) * range;
			float vFactor = 0.3f;

			world.spawnParticle(particleType, myPos.x + x, myPos.y + 0.5f, myPos.z + z, 0, ModConfig.DEBUG_CONF.TRAP_PARTICLE_SPEED, 0);
		}
	}

	boolean lastActive = false;

	@Override
	public void update() {
		if (ModConfig.PerformanceConf.ENABLE_TIMING_TRAPS)
//				&& !isInSPBiome)//too laggy. disable until fix
		{
			if (!world.isRemote) {
				if (EntityUtil.getEntitiesWithinAABB(world, EntityLivingBase.class, pos, 8f, EntityUtil.ALL_ALIVE).size() > 0) {
					boolean activeNow = isActiveNow();
					if (activeNow != lastActive) {
						//Idealland.Log("tick = %d",world.getTotalWorldTime());
						onChangeActive(activeNow);
					}

					lastActive = activeNow;
				}

			} else {
				if (getBlockMetadata() > 0) {
					createParticles();
				}
			}
		}
	}

	public void onChangeActive(boolean newState)
	{
		BlockDungeonCustomizedTrap.setState(newState, world, pos);
		if (newState)
		{
			playsoundhere(activeSound);
		}
	}

	public boolean isActiveBegin()
	{
		if (periodTickCount == 0)
		{
			return false;
		}

		return (world.getTotalWorldTime()+postponeTick) % periodTickCount == 0;
	}

	public boolean isActiveNow()
	{
		if (periodTickCount == 0)
		{
			return false;
		}

		return (world.getTotalWorldTime()+postponeTick) % periodTickCount < activeTickCount;
	}

	//Writing this into the lang won't work for arguments.
	public static final String PERIOD_PREFIX = "§3";
	public static final String ACTIVE_PREFIX = "§a";
	public static final String PHASE_PREFIX = "§d";
	public static final String DAMAGE_PREFIX = "§c";

	//info
	public void introToPlayer(EntityPlayer player)
	{
		CommonFunctions.SafeSendMsgToPlayer(player, MessageDef.MSG_TRAP_INFO,
				PERIOD_PREFIX+periodTickCount,
				ACTIVE_PREFIX+activeTickCount,
				PHASE_PREFIX+postponeTick,
				DAMAGE_PREFIX+(int)(damageRatio*10000));
	}

	//shift mechanics
	public void setArg(EnumTrapArgTypes argType, int value)
	{
		switch (argType)
		{
			case ACTIVE_TICKS:
				setActiveTickCount(value);
				break;
			case PERIOD:
				setPeriodTickCount(value);
				break;
			case PHASE:
				setPostponeTick(value);
				break;
			case DAMAGE:
				setDamageRatio(value * 0.0001f);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + argType);
		}
	}

	public void addArg(EnumTrapArgTypes argType, int value)
	{
		setArg(argType, value + getArg(argType));
	}

	public int getArg(EnumTrapArgTypes argType)
	{
		switch (argType)
		{

			case ACTIVE_TICKS:
				return getActiveTickCount();
			case PERIOD:
				return getPeriodTickCount();
			case PHASE:
				return getPostponeTick();
			case DAMAGE:
				return (int) (getDamageRatio() * 10000);
			default:
				return 0;
		}
	}

	//setters
	public void setActiveTickCount(int activeTickCount) {
		this.activeTickCount = activeTickCount >= 1 ? activeTickCount : 1;
		//syncToTrackingClients();
	}

	public void setPeriodTickCount(int periodTickCount) {
		this.periodTickCount = periodTickCount >= 2 ? periodTickCount : 2;
		//syncToTrackingClients();
	}

	public void setPostponeTick(int postponeTick) {
		this.postponeTick = postponeTick >= 0 ? postponeTick : postponeTick % periodTickCount;
		//syncToTrackingClients();
	}

	public int getPeriodTickCount() {
		return periodTickCount;
	}

	public int getActiveTickCount() {
		return activeTickCount;
	}

	public int getPostponeTick() {
		return postponeTick;
	}

	public float getDamageRatio() {
		return damageRatio;
	}

	public void setDamageRatio(float damageRatio) {
		this.damageRatio = damageRatio;
		//syncToTrackingClients();
	}

    //nbt saving
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        periodTickCount = compound.getInteger(IDLNBTDef.PERIOD_TICK_COUNT);
        activeTickCount = compound.getInteger(IDLNBTDef.ACTIVE_TICK_COUNT);
        postponeTick = compound.getInteger(IDLNBTDef.POSTPONE_TICK);
        damageRatio = compound.getFloat(IDLNBTDef.DAMAGE_RATIO);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger(IDLNBTDef.PERIOD_TICK_COUNT, periodTickCount);
        compound.setInteger(IDLNBTDef.ACTIVE_TICK_COUNT, activeTickCount);
        compound.setInteger(IDLNBTDef.POSTPONE_TICK, postponeTick);
        compound.setFloat(IDLNBTDef.DAMAGE_RATIO, damageRatio);

        return compound;
    }

//    @Nullable
//    public SPacketUpdateTileEntity getUpdatePacket()
//    {
//        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
//    }
//
//    public NBTTagCompound getUpdateTag()
//    {
//        return this.writeToNBT(new NBTTagCompound());
//    }
//
//    //Harbinger code
//    public void syncToTrackingClients() {
//        if (!this.world.isRemote) {
//            SPacketUpdateTileEntity packet = this.getUpdatePacket();
//            PlayerChunkMapEntry trackingEntry = ((WorldServer)this.world).getPlayerChunkMap().getEntry(this.pos.getX() >> 4, this.pos.getZ() >> 4);
//            if (trackingEntry != null) {
//                for (EntityPlayerMP player : trackingEntry.getWatchingPlayers()) {
//                    player.connectionsMicro.sendPacket(packet);
//                }
//            }
//        }
//    }
//
//    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
//    {
//    	super.onDataPacket(net, pkt);
//    	this.readFromNBT(pkt.getNbtCompound());
//    }
//
//    /**
//     * Called when the chunk's TE update tag, gotten from {@link #getUpdateTag()}, is received on the client.
//     * <p>
//     * Used to handle this tag in a special way. By default this simply calls {@link #readFromNBT(NBTTagCompound)}.
//     *
//     * @param tag The {@link NBTTagCompound} sent from {@link #getUpdateTag()}
//     */
//    public void handleUpdateTag(NBTTagCompound tag)
//    {
//        this.readFromNBT(tag);
//    }

}
