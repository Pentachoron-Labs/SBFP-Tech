package sbfp.secret;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntitySecret extends Entity{

	public static final String name = "secret";

	public EntitySecret(World w){
		super(w);
	}

	@Override
	protected void entityInit(){
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound){
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound){
	}
}