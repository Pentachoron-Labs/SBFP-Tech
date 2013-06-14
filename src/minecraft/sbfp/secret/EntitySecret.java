package sbfp.secret;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntitySecret extends Entity{

	private static final double speed = 0.1;
	protected double tractorX, tractorY, tractorZ, tractorYaw, tractorPitch;
	@SideOnly(Side.CLIENT)
	protected double velX, velY, velZ;

	public EntitySecret(World w){
		super(w);
		this.preventEntitySpawning = true;
		this.entityCollisionReduction = 1;
		this.yOffset = this.height/2.0F;
	}

	@Override
	protected boolean canTriggerWalking(){
		return true;
	}
	
	@Override
	public boolean canBePushed(){
		FMLLog.info("%s",this.boundingBox);
		return false;
	}

	@Override
	protected void entityInit(){}

	@Override
	public AxisAlignedBB getCollisionBox(Entity e){
		return e.boundingBox;
	}

	@Override
	public AxisAlignedBB getBoundingBox(){
		return this.boundingBox;
	}

	@Override
	public void setPosition(double x, double y, double z){
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.boundingBox.setBounds(x-0.375,y,z-0.75,x+0.375,y+1.125,z+0.75);
	}

	public EntitySecret(World w, double x, double y, double z){
		this(w);
		this.setPosition(x,y+this.yOffset,z);
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	@Override
	public double getMountedYOffset(){
		return 0.3;
	}

	@Override
	public boolean attackEntityFrom(DamageSource ds, int damage){
		if(ds.damageType=="lava"||ds.damageType=="outOfWorld"||ds.damageType=="generic"){
			this.setDead();
			return true;
		}
		return false;
	}

	@Override
	public boolean canBeCollidedWith(){
		return !this.isDead;
	}

	@Override
	public void onUpdate(){
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.04;
		this.moveEntity(this.motionX,this.motionY,this.motionZ);
		this.motionX *= 0.98;
		this.motionY *= 0.98;
		this.motionZ *= 0.98;
		if(this.onGround){
			this.motionX *= 0.7;
			this.motionZ *= 0.7;
			this.motionY *= -0.5;
		}
		//
		// super.onUpdate();
		// if(!this.worldObj.isRemote && (this.riddenByEntity!=null)){
		// float dyaw = 0;
		// if(Keyboard.isKeyDown(Keyboard.KEY_W)){
		// this.motionX = Math.cos(this.rotationYaw*speed);
		// this.motionZ = Math.sin(this.rotationYaw*speed);
		// }else{
		// this.motionX = 0;
		// this.motionZ = 0;
		// }
		// if(Keyboard.isKeyDown(Keyboard.KEY_A)){
		// dyaw = 5;
		// }
		// if(Keyboard.isKeyDown(Keyboard.KEY_D)){
		// dyaw = -5;
		// }
		// this.setRotation(this.rotationPitch, this.rotationYaw+dyaw);
		// }
		// if(!this.worldObj.isRemote){
		// this.motionY -= 0.04;
		// }
		// this.moveEntity(this.motionX,this.motionY,this.motionZ);
		// // List list =
		// this.worldObj.getEntitiesWithinAABBExcludingEntity(this,this.boundingBox.expand(0.2,0,0.2));
		// // if(list!=null&&!list.isEmpty()){
		// // for(int i = 0; i<list.size(); ++i){
		// // Entity entity = (Entity) list.get(i);
		// // if(entity!=this.riddenByEntity&&entity.canBePushed()&&!(entity
		// instanceof EntitySecret)){
		// // entity.applyEntityCollision(this);
		// // }
		// // }
		// // }
		// for(int i = 0; i<4; ++i){
		// int blockX = MathHelper.floor_double(this.posX+(i%2-0.5D)*0.8D);
		// int blockZ = MathHelper.floor_double(this.posZ+(i/2-0.5D)*0.8D);
		// for(int j = 0; j<2; ++j){
		// int blockY = MathHelper.floor_double(this.posY)+j;
		// int id = this.worldObj.getBlockId(blockX,blockY,blockZ);
		// if(id==Block.snow.blockID){
		// this.worldObj.setBlockToAir(blockX,blockY,blockZ);
		// }else if(id==Block.waterlily.blockID){
		// this.worldObj.destroyBlock(blockX,blockY,blockZ,true);
		// }
		// }
		// }
		// if(this.riddenByEntity!=null&&this.riddenByEntity.isDead){
		// this.riddenByEntity = null;
		// }
	}

	@Override
	public void updateRiderPosition(){
		if(this.riddenByEntity!=null){
			double d0 = Math.cos(this.rotationYaw*Math.PI/180.0D)*0.4D;
			double d1 = Math.sin(this.rotationYaw*Math.PI/180.0D)*0.4D;
			this.riddenByEntity.setPosition(this.posX+d0,this.posY+this.getMountedYOffset()+this.riddenByEntity.getYOffset(),this.posZ+d1);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound){}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound){}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize(){
		return 0.0F;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer){
		if(this.riddenByEntity!=null&&this.riddenByEntity instanceof EntityPlayer&&this.riddenByEntity!=par1EntityPlayer) return true;
		else{
			if(!this.worldObj.isRemote){
				par1EntityPlayer.mountEntity(this);
			}
			return true;
		}
	}

	@Override
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int par9){
		this.tractorX = x;
		this.tractorY = y;
		this.tractorZ = z;
		this.tractorYaw = yaw;
		this.tractorPitch = pitch;
		this.motionX = this.velX;
		this.motionY = this.velY;
		this.motionZ = this.velZ;
	}

	@Override
	public void setVelocity(double dx, double dy, double dz){
		this.velX = this.motionX = dx;
		this.velY = this.motionY = dy;
		this.velZ = this.motionZ = dz;
	}
}