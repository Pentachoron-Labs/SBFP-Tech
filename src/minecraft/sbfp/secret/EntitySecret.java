package sbfp.secret;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntitySecret extends Entity{

	public EntitySecret(World w){
		super(w);
		this.preventEntitySpawning = true;
		this.setSize(0.75f,0.6F);
		this.yOffset = this.height/2.0F;
	}

	@Override
	protected boolean canTriggerWalking(){
		return true;
	}

	@Override
	protected void entityInit(){
		this.dataWatcher.addObject(17,new Integer(0));
		this.dataWatcher.addObject(18,new Integer(1));
		this.dataWatcher.addObject(19,new Integer(0));
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity par1Entity){
		return par1Entity.boundingBox;
	}

	@Override
	public AxisAlignedBB getBoundingBox(){
		return this.boundingBox;
	}

	@Override
	public boolean canBePushed(){
		return true;
	}

	public EntitySecret(World w, double x, double y, double z){
		this(w);
		this.setPosition(x,y+this.yOffset,z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	/*
		@Override
		public double getMountedYOffset(){
			return this.height*0.0D-0.30000001192092896D;
		}

		@Override
		public boolean attackEntityFrom(DamageSource par1DamageSource, int par2){
			if(this.isEntityInvulnerable()){
				return false;
			}else if(!this.worldObj.isRemote&&!this.isDead){
				this.setForwardDirection(-this.getForwardDirection());
				this.setTimeSinceHit(10);
				this.setDamageTaken(this.getDamageTaken()+par2*10);
				this.setBeenAttacked();
				boolean flag = par1DamageSource.getEntity() instanceof EntityPlayer&&((EntityPlayer) par1DamageSource.getEntity()).capabilities.isCreativeMode;

				if(flag||this.getDamageTaken()>40){
					if(this.riddenByEntity!=null){
						this.riddenByEntity.mountEntity(this);
					}

					if(!flag){
						this.dropItemWithOffset(modsbfp.itemSecret.itemID,1,0.0F);
					}

					this.setDead();
				}

				return true;
			}else{
				return true;
			}
		}

		@Override
		public boolean canBeCollidedWith(){
			return !this.isDead;
		}

		@Override
		@SideOnly(Side.CLIENT)
		/**
		 * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
		 * posY, posZ, yaw, pitch
		 *
		public void setPositionAndRotation2(double posX, double posY, double posZ, float yaw, float pitch, int par9){
	//		if(this.field_70279_a){
				this.secretPosRotationIncrements = par9+5;
	//		}else{
	//			double dx = posX-this.posX;
	//			double dy = posY-this.posY;
	//			double dz = posZ-this.posZ;
	//			double d6 = dx*dx+dy*dy+dz*dz;
	//
	//			if(d6<=1.0D){
	//				return;
	//			}
	//
	//			this.secretPosRotationIncrements = 3;
	//		}

			this.secretX = posX;
			this.secretY = posY;
			this.secretZ = posZ;
			this.secretYaw = yaw;
			this.secretPitch = pitch;
			this.motionX = this.velocityX;
			this.motionY = this.velocityY;
			this.motionZ = this.velocityZ;
		}

		@Override
		@SideOnly(Side.CLIENT)
		/**
		 * Sets the velocity to the args. Args: x, y, z
		 *
		public void setVelocity(double par1, double par3, double par5){
			this.velocityX = this.motionX = par1;
			this.velocityY = this.motionY = par3;
			this.velocityZ = this.motionZ = par5;
		}

		@Override
		public void onUpdate(){
			super.onUpdate();

			if(this.getTimeSinceHit()>0){
				this.setTimeSinceHit(this.getTimeSinceHit()-1);
			}

			if(this.getDamageTaken()>0){
				this.setDamageTaken(this.getDamageTaken()-1);
			}

			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			byte b0 = 5;
			double d0 = 0.0D;

			for(int i = 0; i<b0; ++i){
				double d1 = this.boundingBox.minY+(this.boundingBox.maxY-this.boundingBox.minY)*(i+0)/b0-0.125D;
				double d2 = this.boundingBox.minY+(this.boundingBox.maxY-this.boundingBox.minY)*(i+1)/b0-0.125D;
				AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX,d1,this.boundingBox.minZ,this.boundingBox.maxX,d2,this.boundingBox.maxZ);

				if(this.worldObj.isAABBInMaterial(axisalignedbb,Material.water)){
					d0 += 1.0D/b0;
				}
			}

			double d3 = Math.sqrt(this.motionX*this.motionX+this.motionZ*this.motionZ);
			double d4;
			double d5;

			if(d3>0.26249999999999996D){
				d4 = Math.cos(this.rotationYaw*Math.PI/180.0D);
				d5 = Math.sin(this.rotationYaw*Math.PI/180.0D);

				for(int j = 0; j<1.0D+d3*60.0D; ++j){
					double d6 = this.rand.nextFloat()*2.0F-1.0F;
					double d7 = (this.rand.nextInt(2)*2-1)*0.7D;
					double d8;
					double d9;

					if(this.rand.nextBoolean()){
						d8 = this.posX-d4*d6*0.8D+d5*d7;
						d9 = this.posZ-d5*d6*0.8D-d4*d7;
						this.worldObj.spawnParticle("splash",d8,this.posY-0.125D,d9,this.motionX,this.motionY,this.motionZ);
					}else{
						d8 = this.posX+d4+d5*d6*0.7D;
						d9 = this.posZ+d5-d4*d6*0.7D;
						this.worldObj.spawnParticle("splash",d8,this.posY-0.125D,d9,this.motionX,this.motionY,this.motionZ);
					}
				}
			}

			double d10;
			double d11;

			if(this.worldObj.isRemote&&this.field_70279_a){
				if(this.secretPosRotationIncrements>0){
					d4 = this.posX+(this.secretX-this.posX)/this.secretPosRotationIncrements;
					d5 = this.posY+(this.secretY-this.posY)/this.secretPosRotationIncrements;
					d11 = this.posZ+(this.secretZ-this.posZ)/this.secretPosRotationIncrements;
					d10 = MathHelper.wrapAngleTo180_double(this.secretYaw-this.rotationYaw);
					this.rotationYaw = (float) (this.rotationYaw+d10/this.secretPosRotationIncrements);
					this.rotationPitch = (float) (this.rotationPitch+(this.secretPitch-this.rotationPitch)/this.secretPosRotationIncrements);
					--this.secretPosRotationIncrements;
					this.setPosition(d4,d5,d11);
					this.setRotation(this.rotationYaw,this.rotationPitch);
				}else{
					d4 = this.posX+this.motionX;
					d5 = this.posY+this.motionY;
					d11 = this.posZ+this.motionZ;
					this.setPosition(d4,d5,d11);

					if(this.onGround){
						this.motionX *= 0.5D;
						this.motionY *= 0.5D;
						this.motionZ *= 0.5D;
					}

					this.motionX *= 0.99;
					this.motionY *= 0.95;
					this.motionZ *= 0.99;
				}
			}else{
				if(d0<1.0D){
					d4 = d0*2.0D-1.0D;
					this.motionY += 0.04*d4;
				}else{
					if(this.motionY<0.0D){
						this.motionY /= 2;
					}

					this.motionY += 0.007;
				}

				if(this.riddenByEntity!=null){
					this.motionX += this.riddenByEntity.motionX*this.speedMultiplier;
					this.motionZ += this.riddenByEntity.motionZ*this.speedMultiplier;
				}

				d4 = Math.sqrt(this.motionX*this.motionX+this.motionZ*this.motionZ);

				if(d4>0.35D){
					d5 = 0.35D/d4;
					this.motionX *= d5;
					this.motionZ *= d5;
					d4 = 0.35D;
				}

				if(d4>d3&&this.speedMultiplier<0.35D){
					this.speedMultiplier += (0.35D-this.speedMultiplier)/35.0D;

					if(this.speedMultiplier>0.35D){
						this.speedMultiplier = 0.35D;
					}
				}else{
					this.speedMultiplier -= (this.speedMultiplier-0.07D)/35.0D;

					if(this.speedMultiplier<0.07D){
						this.speedMultiplier = 0.07D;
					}
				}

				if(this.onGround){
					this.motionX *= 0.5D;
					this.motionY *= 0.5D;
					this.motionZ *= 0.5D;
				}

				this.moveEntity(this.motionX,this.motionY,this.motionZ);

				if(this.isCollidedHorizontally&&d3>0.2D){
					if(!this.worldObj.isRemote&&!this.isDead){
						this.setDead();
						int k;

						for(k = 0; k<3; ++k){
							this.dropItemWithOffset(Block.planks.blockID,1,0.0F);
						}

						for(k = 0; k<2; ++k){
							this.dropItemWithOffset(Item.stick.itemID,1,0.0F);
						}
					}
				}else{
					this.motionX *= 0.9900000095367432D;
					this.motionY *= 0.949999988079071D;
					this.motionZ *= 0.9900000095367432D;
				}

				this.rotationPitch = 0.0F;
				d5 = this.rotationYaw;
				d11 = this.prevPosX-this.posX;
				d10 = this.prevPosZ-this.posZ;

				if(d11*d11+d10*d10>0.001D){
					d5 = (float) (Math.atan2(d10,d11)*180.0D/Math.PI);
				}

				double d12 = MathHelper.wrapAngleTo180_double(d5-this.rotationYaw);

				if(d12>20.0D){
					d12 = 20.0D;
				}

				if(d12<-20.0D){
					d12 = -20.0D;
				}

				this.rotationYaw = (float) (this.rotationYaw+d12);
				this.setRotation(this.rotationYaw,this.rotationPitch);

				if(!this.worldObj.isRemote){
					List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,this.boundingBox.expand(0.20000000298023224D,0.0D,0.20000000298023224D));
					int l;

					if(list!=null&&!list.isEmpty()){
						for(l = 0; l<list.size(); ++l){
							Entity entity = (Entity) list.get(l);

							if(entity!=this.riddenByEntity&&entity.canBePushed()&&entity instanceof EntitySecret){
								entity.applyEntityCollision(this);
							}
						}
					}

					for(l = 0; l<4; ++l){
						int i1 = MathHelper.floor_double(this.posX+(l%2-0.5D)*0.8D);
						int j1 = MathHelper.floor_double(this.posZ+(l/2-0.5D)*0.8D);

						for(int k1 = 0; k1<2; ++k1){
							int l1 = MathHelper.floor_double(this.posY)+k1;
							int i2 = this.worldObj.getBlockId(i1,l1,j1);

							if(i2==Block.snow.blockID){
								this.worldObj.setBlockToAir(i1,l1,j1);
							}else if(i2==Block.waterlily.blockID){
								this.worldObj.destroyBlock(i1,l1,j1,true);
							}
						}
					}

					if(this.riddenByEntity!=null&&this.riddenByEntity.isDead){
						this.riddenByEntity = null;
					}
				}
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
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer){
		if(this.riddenByEntity!=null&&this.riddenByEntity instanceof EntityPlayer&&this.riddenByEntity!=par1EntityPlayer){
			return true;
		}else{
			if(!this.worldObj.isRemote){
				par1EntityPlayer.mountEntity(this);
			}

			return true;
		}
	}

	/**
	 * Sets the damage taken from the last hit.
	 */
	public void setDamageTaken(int par1){
		this.dataWatcher.updateObject(19,Integer.valueOf(par1));
	}

	/**
	 * Gets the damage taken from the last hit.
	 */
	public int getDamageTaken(){
		return this.dataWatcher.getWatchableObjectInt(19);
	}

	/**
	 * Sets the time to count down from since the last time entity was hit.
	 */
	public void setTimeSinceHit(int par1){
		this.dataWatcher.updateObject(17,Integer.valueOf(par1));
	}

	/**
	 * Gets the time since the last hit.
	 */
	public int getTimeSinceHit(){
		return this.dataWatcher.getWatchableObjectInt(17);
	}

	/**
	 * Sets the forward direction of the entity.
	 */
	public void setForwardDirection(int par1){
		this.dataWatcher.updateObject(18,Integer.valueOf(par1));
	}

	/**
	 * Gets the forward direction of the entity.
	 */
	public int getForwardDirection(){
		return this.dataWatcher.getWatchableObjectInt(18);
	}
}