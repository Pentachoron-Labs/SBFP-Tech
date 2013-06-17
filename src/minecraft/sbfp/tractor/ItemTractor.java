package sbfp.tractor;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import sbfp.ItemSB;
import cpw.mods.fml.common.FMLLog;

public class ItemTractor extends ItemSB{

	public ItemTractor(int id, String name){
		super(id,name);
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabTransport);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer p){
		float pitch = p.rotationPitch;
		float yaw = p.rotationYaw;
		double x = p.posX;
		double y = p.posY+1.62-p.yOffset;
		double z = p.posZ;
		Vec3 v = w.getWorldVec3Pool().getVecFromPool(x,y,z);
		float f3 = MathHelper.cos((float) (-yaw*0.017453292F-Math.PI));
		float f4 = MathHelper.sin((float) (-yaw*0.017453292F-Math.PI));
		float f5 = -MathHelper.cos(-pitch*0.017453292F);
		float f6 = MathHelper.sin(-pitch*0.017453292F);
		float f7 = f4*f5;
		float f8 = f3*f5;
		double d3 = 5.0D;
		Vec3 vec31 = v.addVector(f7*d3,f6*d3,f8*d3);
		MovingObjectPosition mop = w.rayTraceBlocks_do(v,vec31,true);
		if(mop==null) return stack;
		Vec3 vec32 = p.getLook(1);
		float f9 = 1.0F;
		List list = w.getEntitiesWithinAABBExcludingEntity(p,p.boundingBox.addCoord(vec32.xCoord*d3,vec32.yCoord*d3,vec32.zCoord*d3).expand(f9,f9,f9));
		for(int i = 0; i<list.size(); ++i){
			Entity entity = (Entity) list.get(i);
			if(entity.canBeCollidedWith()){
				float f10 = entity.getCollisionBorderSize();
				AxisAlignedBB axisalignedbb = entity.boundingBox.expand(f10,f10,f10);
				if(axisalignedbb.isVecInside(v)) return stack;
			}
		}
		if(mop.typeOfHit==EnumMovingObjectType.TILE){
			int i = mop.blockX;
			int j = mop.blockY;
			int k = mop.blockZ;
			if(w.getBlockId(i,j,k)==Block.snow.blockID){
				--j;
			}
			EntityTractor entitysecret = new EntityTractor(w,i+0.5F,j+1.0F,k+0.5F);
			entitysecret.rotationYaw = ((MathHelper.floor_double(p.rotationYaw*4.0F/360.0F+0.5D)&3)-1)*90;
			if(!w.getCollidingBoundingBoxes(entitysecret,entitysecret.boundingBox.expand(-0.1D,-0.1D,-0.1D)).isEmpty()) return stack;
			if(!w.isRemote){
				FMLLog.info("Sha-POW!");
				w.spawnEntityInWorld(entitysecret);
			}
			if(!p.capabilities.isCreativeMode){
				--stack.stackSize;
			}
		}
		return stack;
	}
}