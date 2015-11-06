package sbfp.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import sbfp.BlockSB;
import sbfp.machines.crusher.TileEntityCrusher;
import sbfp.machines.solar.TileEntitySolarCharger;
import sbfp.modsbfp;

public class BlockMachine extends BlockSB implements ITileEntityProvider {

    public static final IProperty TYPE = PropertyEnum.create("type", MachineTypes.class);
    public static final IProperty FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockMachine(String name) {
        super(Material.iron, name);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        this.dropEntireInventory(world, pos, 0, getMetaFromState(state));
        super.breakBlock(world, pos, state);
    }

    /**
     * Drops entire inventory of block
     *
     * @param world
     * @param pos block position
     * @param id block ID (seems unnecessary)
     * @param metadata block meta
     */
    public void dropEntireInventory(World world, BlockPos pos, int id, int metadata) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity != null) {
            if (tileEntity instanceof IInventory) {
                IInventory inventory = (IInventory) tileEntity;
                for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                    ItemStack stack = inventory.getStackInSlot(i);
                    if (stack != null) {
                        Random random = new Random();
                        float sx = random.nextFloat() * 0.8F + 0.1F;
                        float sy = random.nextFloat() * 0.8F + 0.1F;
                        float sz = random.nextFloat() * 0.8F + 0.1F;
                        while (stack.stackSize > 0) {
                            int qty = random.nextInt(21) + 10;
                            if (qty > stack.stackSize) {
                                qty = stack.stackSize;
                            }
                            stack.stackSize -= qty;
                            EntityItem eitem = new EntityItem(world, x + sx, y + sy, z + sz, new ItemStack(stack.getItem(), qty, stack.getItemDamage()));
                            if (stack.hasTagCompound()) {
                                eitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
                            }
                            float displacement = 0.05F;
                            eitem.motionX = (float) random.nextGaussian() * displacement;
                            eitem.motionY = (float) random.nextGaussian() * displacement + 0.2F;
                            eitem.motionZ = (float) random.nextGaussian() * displacement;
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }
        if (!playerIn.isSneaking()) {
            playerIn.openGui(modsbfp.getInstance(), -1, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;

    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing blockFaceClickedOn, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        MachineTypes machine = MachineTypes.typeFromMeta(meta);
        EnumFacing direction = (placer == null) ? EnumFacing.NORTH : EnumFacing.fromAngle(placer.rotationYaw);

        return this.getDefaultState().withProperty(TYPE, machine).withProperty(FACING, direction);
    }

    @Override
    public TileEntity createNewTileEntity(World w, int m) {
        int meta = m & 0x03;
        switch (meta) {
            case 1:
                TileEntitySolarCharger solar = new TileEntitySolarCharger(); //Flux Infuser
                solar.activate();
                return solar;
            case 0:
                TileEntityCrusher crusher = new TileEntityCrusher(); //Flux Infuser
                crusher.activate();
                return crusher;
        }
        FMLLog.info("Tried to make a TileEntityProcessor but the metadata was %d. What gives?", meta);
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side){
        IBlockState state = worldIn.getBlockState(pos);
        return state.getBlock().getMetaFromState(state) == MachineTypes.SOLARCHARGER.getMeta() ? true : super.shouldSideBeRendered(worldIn, pos, side);
    }
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public BlockState createBlockState() {
        return new BlockState(this, new IProperty[]{TYPE, FACING});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing direction = EnumFacing.getHorizontal(meta >> 2);
        int machineBits = meta & 0x03;
        MachineTypes machine = MachineTypes.typeFromMeta(machineBits);
        return this.getDefaultState().withProperty(TYPE, machine).withProperty(FACING, direction);

    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing direction = (EnumFacing) state.getValue(FACING);
        MachineTypes machine = (MachineTypes) state.getValue(TYPE);
        int machineBits = machine.getMeta();
        int directionBits = direction.getHorizontalIndex() << 2;

        return directionBits | machineBits;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return ((MachineTypes) state.getValue(TYPE)).getMeta();
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (MachineTypes machine : MachineTypes.values()) {
            list.add(new ItemStack(item, 1, machine.getMeta()));
        }
    }
}
