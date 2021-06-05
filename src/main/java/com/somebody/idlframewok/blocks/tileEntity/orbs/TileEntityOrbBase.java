package com.somebody.idlframewok.blocks.tileEntity.orbs;

import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;

import java.util.Random;

public class TileEntityOrbBase extends TileEntity implements ITickable {

	public AxisAlignedBB aabb;
	private int range = 7;
	public boolean isReady = false;
	public SoundEvent onLoadSound = SoundEvents.BLOCK_NOTE_CHIME;
	public EnumParticleTypes particleType = EnumParticleTypes.CRIT_MAGIC;

	public void SetRange(int newRange){
		range = newRange;
		Vec3d posInFloat = GetPosInFloat();
		//IdlFramework.Log(String.format("SetRange:(%s,%s,%s) +- %s", posInFloat.x, posInFloat.y, posInFloat.z, newRange));
		aabb = new AxisAlignedBB(posInFloat.x - range, posInFloat.y - range, posInFloat.z - range, posInFloat.x + range, posInFloat.y + range, posInFloat.z + range);

	}

	public int getRange()
	{
		return range;
	}

	public void onLoad()
    {
		Init();
    }

	@Override
	public void invalidate() {
		MinecraftForge.EVENT_BUS.unregister(this);
		super.invalidate();
	}

    public Vec3d GetPosInFloat()
	{
		BlockPos myPos = this.pos;
		Vec3d posInFloat = new Vec3d(myPos.getX() + 0.5, myPos.getY() + 0.5, myPos.getZ() + 0.5);
		return posInFloat;
	}

	void Init()
	{
		MinecraftForge.EVENT_BUS.register(this);
		isReady = true;
		SetRange(range);
		if (!world.isRemote)
		{
			//Init Success
			PlaySoundHere();
		}
	}

	public void PlaySoundHere()
	{
		world.playSound(null, this.pos, onLoadSound, SoundCategory.BLOCKS, 0.3F, 1f);
	}

	public void CreateParticles()
	{
		if (world.isRemote && isReady)
		{
			Random random = new Random();
			Vec3d myPos = GetPosInFloat();
			float range = 2f;
			float x = (random.nextFloat() - 0.5f) * range;
			float y = (random.nextFloat() - 0.5f) * range;
			float z = (random.nextFloat() - 0.5f) * range;
			float vFactor = -1f;

			world.spawnParticle(particleType, myPos.x + x, myPos.y + y, myPos.z + z, x * vFactor, y * vFactor, z * vFactor);
		}
	}

	@Override
	public void update() {
		//create particles

		CreateParticles();
	}

	//failed attempt
//	@SubscribeEvent
//	public void onWorldRenderLast(RenderWorldLastEvent event) {
//		GlStateManager.pushMatrix();
//		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
//		GlStateManager.disableDepth();
//		GlStateManager.disableTexture2D();
//		GlStateManager.enableBlend();
//
//		//Minecraft.getMinecraft().getRenderManager().setRenderPosition(pos.getX(), pos.getY(), pos.getZ());
//
//		//Add drawing here
//		int color =  Color.HSBtoRGB(0F, 0.6F, 1F);
//		Vec3d posInFloat = GetPosInFloat();
//
//        GlStateManager.scale(1F, 1F, 1F);
//
//        GlStateManager.glLineWidth(1f);
//
//		//draw a cube
//		//for ()
//		DrawLine(posInFloat.x - range, posInFloat.y, posInFloat.z - range,
//				posInFloat.x + range, posInFloat.y, posInFloat.z + range);
//
//		DrawLine(posInFloat.x - range, posInFloat.y, posInFloat.z - range,
//				posInFloat.x + range, posInFloat.y, posInFloat.z - range);
//
//		DrawLine(posInFloat.x - range, posInFloat.y, posInFloat.z - range,
//				posInFloat.x - range, posInFloat.y, posInFloat.z + range);
//
//		DrawLine(posInFloat.x - range, posInFloat.y, posInFloat.z - range,
//				posInFloat.x - range, posInFloat.y, posInFloat.z - range);
//
//		DrawLine(posInFloat.x + range, posInFloat.y, posInFloat.z + range,
//				posInFloat.x + range, posInFloat.y, posInFloat.z - range);
//
//		DrawLine(posInFloat.x + range, posInFloat.y, posInFloat.z - range,
//				posInFloat.x - range, posInFloat.y, posInFloat.z + range);
//
//		GlStateManager.enableDepth();
//		GlStateManager.enableTexture2D();
//		GlStateManager.disableBlend();
//		GL11.glPopAttrib();
//		GlStateManager.popMatrix();
//	}
//
//	public static void DrawLine(double x1, double y1, double z1, double x2, double y2, double z2){
//		Tessellator tessellator = Tessellator.getInstance();
//		tessellator.getBuffer().begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
//		tessellator.getBuffer().pos(x1, y1, z1).endVertex();
//		tessellator.getBuffer().pos(x2, y2, z2).endVertex();
//		tessellator.draw();
//	}
}
