package sbfp.machines;

import java.util.List;
import java.util.Random;


import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import sbfp.BlockSB;
import sbfp.machines.processor.crusher.TileEntityCrusher;
import sbfp.machines.processor.solar.TileEntitySolarCharger;

public class BlockMachine extends BlockSB implements ITileEntityProvider{

        public static final IProperty TYPE = PropertyEnum.create("TYPE", EnumMachineType.class);
        
	public BlockMachine(String name){
		super(Material.iron,name);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state){
		this.dropEntireInventory(world,pos,0,getMetaFromState(state));
		super.breakBlock(world,pos, state);
	}

	/**
	 * Drops entire inventory of block
	 * @param world
	 * @param pos block position
	 * @param id block ID (seems unnecessary)
	 * @param metadata block meta
	 */
	public void dropEntireInventory(World world, BlockPos pos, int id, int metadata){
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		TileEntity tileEntity = world.getTileEntity(pos);
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
							EntityItem eitem = new EntityItem(world,x+sx,y+sy,z+sz,new ItemStack(stack.getItem(),qty,stack.getItemDamage()));
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

//	@Override
//	@SideOnly(Side.CLIENT)
//	public Icon getIcon(int side, int meta){
//		return icons[meta][side];
//		//TODO Make machines rotate
//	}

        @Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ){
//		if(!playerIn.isSneaking()){
//			playerIn.openGui(modsbfp.getInstance(),-1,worldIn,pos.getX(), pos.getY(), pos.getZ());
//			return true;
//		}
		return false;

	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta){
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
        public BlockState createBlockState(){
            return new BlockState(this, new IProperty[]{TYPE});
        }
        
        @Override
        public IBlockState getStateFromMeta(int meta){
            for(EnumMachineType ore : EnumMachineType.values()){
                if(ore.getMeta() == meta){
                    return getDefaultState().withProperty(TYPE, ore);    
                }
            }
            return getDefaultState().withProperty(TYPE, 0);
        }
        
        @Override
        public int getMetaFromState(IBlockState state){
            return ((EnumMachineType) state.getValue(TYPE)).getMeta();
        }
        
        @Override
        public int damageDropped(IBlockState state){
            return getMetaFromState(state);
        }
        
        @Override
        public void getSubBlocks(Item item, CreativeTabs tab, List list){
            for(EnumMachineType ore : EnumMachineType.values()){
                list.add(new ItemStack(item, 1, ore.getMeta()));
            }
        }
}