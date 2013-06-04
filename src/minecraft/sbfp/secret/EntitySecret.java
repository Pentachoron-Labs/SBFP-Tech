package sbfp.secret;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntitySecret extends Entity{

	public EntitySecret(World w){
		super(w);
	}

	public EntitySecret(World w, double posX, double posY, double posZ){
		this(w);
		this.setPosition(posX,posY+this.yOffset,posZ);
	}

	@Override
	protected void entityInit(){}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound){}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound){}
}