package sbfp.machines;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import sbfp.BlockSub;
import sbfp.modsbfp;
import sbfp.machines.processor.crusher.TileEntityCrusher;
import sbfp.machines.processor.solar.TileEntitySolarCharger;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachine extends BlockSub implements ITileEntityProvider{

	public Icon[][] icons;

	public BlockMachine(int id, String[] names){
		super(id,Material.iron,names);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register){
		icons = new Icon[names.length][6];
		for(int i = 0; i<names.length; i++){
			for(int j = 0; j<6; j++){
				this.icons[i][j] = register.registerIcon("sbfp:"+this.names[i]+ForgeDirection.getOrientation(j).toString());
				// System.out.println("sbfp:"+this.names[i]+ForgeDirection.getOrientation(j).toString());
			}
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta){
		this.dropEntireInventory(world,x,y,z,id,meta);
		super.breakBlock(world,x,y,z,id,meta);
	}

	/**
	 * Drops entire inventory of block
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param id block ID (seems unnecessary)
	 * @param metadata block meta
	 */
	public void dropEntireInventory(World world, int x, int y, int z, int id, int metadata){
		TileEntity tileEntity = world.getBlockTileEntity(x,y,z);
		if(tileEntity!=null){
			if(tileEntity instanceof IInventory){
				IInventory inventory = (IInventory) tileEntity;
				for(int i = 0; i<inventory.getSizeInventory(); ++i){
					ItemStack stack = inventory.getStackInSlot(i);
					if(stack!=null){
						Random random = new Random();
						float sx = random.nextFloat()*0.8F+0.1F;
						float sy = random.nextFloat()*0.8F+0.1F;
						float sz = random.nextFloat()*0.8F+0.1F;
						while(stack.stackSize>0){
							int qty = random.nextInt(21)+10;
							if(qty>stack.stackSize){
								qty = stack.stackSize;
							}
							stack.stackSize -= qty;
							EntityItem eitem = new EntityItem(world,x+sx,y+sy,z+sz,new ItemStack(stack.itemID,qty,stack.getItemDamage()));
							if(stack.hasTagCompound()){
								eitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
							}
							float displacement = 0.05F;
							eitem.motionX = (float) random.nextGaussian()*displacement;
							eitem.motionY = (float) random.nextGaussian()*displacement+0.2F;
							eitem.motionZ = (float) random.nextGaussian()*displacement;
							world.spawnEntityInWorld(eitem);
						}
					}
				}
			}
		}
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World w, int x, int y, int z, int metadata, int fortune){
		ArrayList<ItemStack> q = new ArrayList<ItemStack>();
		q.add(new ItemStack(this.blockID,1,metadata));
		return q;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta){
		return icons[meta][side];
		//TODO Make machines rotate
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ){
		if(!entityPlayer.isSneaking()){
			entityPlayer.openGui(modsbfp.getInstance(),-1,world,x,y,z);
			return true;
		}
		return false;

	}

	@Override
	public TileEntity createTileEntity(World w, int meta){
		switch(meta){
			case 0:
				return new TileEntitySolarCharger(); //Flux Infuser
			case 1:
				return new TileEntityCrusher(); //Crusher
		}
		FMLLog.warning("Tried to make a TileEntityProcessor but the metadata was %d. What gives?",meta);
		return null;
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world){
		return null;
	}
}