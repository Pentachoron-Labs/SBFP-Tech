package sbfp.tractor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityTractor extends Entity{

	private static final double speed = 0.1;
	private static final double modelmaxX = 7/16., modelmaxY = 10/16., modelmaxZ = 10/16.;
	private static final double modelminX = -34/16., modelminY = -2/16., modelminZ = -8/16.;
	protected double tractorX, tractorY, tractorZ, tractorYaw, tractorPitch;
	@SideOnly(Side.CLIENT)
	protected double velX, velY, velZ;

	public EntityTractor(World w){
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
		this.boundingBox.setBounds(x+modelminX,y+modelminY,z+modelminZ,x+modelmaxX,y+modelmaxY,z+modelmaxZ);
		FMLLog.info("%s %f %f %f",this.boundingBox,posX,posY,posZ);
	}

	public EntityTractor(World w, double x, double y, double z){
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
		this.motionY -= 1/32.;
		this.moveEntity(this.motionX,this.motionY,this.motionZ);
		if(this.riddenByEntity!=null){
			if(Keyboard.isKeyDown(Keyboard.KEY_W)){
				this.motionX = Math.cos(this.rotationYaw)*speed;
				this.motionZ = Math.sin(this.rotationYaw)*speed;
			}else{
				this.motionX = 0;
				this.motionZ = 0;
			}
			float dyaw = 0;
			if(Keyboard.isKeyDown(Keyboard.KEY_A)){
				dyaw = 1;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				dyaw = -1;
			}
			this.setRotation(this.rotationPitch,this.rotationYaw+dyaw);
		}
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