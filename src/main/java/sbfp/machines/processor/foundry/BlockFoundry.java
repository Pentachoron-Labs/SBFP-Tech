package sbfp.machines.processor.foundry;

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
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import sbfp.BlockSB;
import sbfp.modsbfp;

public class BlockFoundry extends BlockSB implements ITileEntityProvider {

    public static final IProperty FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final IProperty STATE = PropertyEnum.create("state", FoundryStates.class);

    public BlockFoundry(String name) {
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
        if (tileEntity != null && tileEntity instanceof IInventory) {
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
        FoundryStates state = FoundryStates.stateFromMeta(meta);
        EnumFacing direction = (placer == null) ? EnumFacing.NORTH : EnumFacing.fromAngle(placer.rotationYaw);

        return this.getDefaultState().withProperty(STATE, state).withProperty(FACING, direction);
    }

    @Override
    public TileEntity createNewTileEntity(World w, int m) {
        return new TileEntityFoundry();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public BlockState createBlockState() {
        return new BlockState(this, new IProperty[]{STATE, FACING});
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        boolean isValidFoundry = checkValidFoundry((EnumFacing) state.getValue(FACING), worldIn, pos);

        return state.withProperty(STATE, FoundryStates.stateFromMeta(isValidFoundry ? 1 : 0));
    }

    //Metadata of format A1A2B1B2 where bits A are the facing direction and bits b are the state of the foundry
    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing direction = EnumFacing.getHorizontal(meta >> 2);
        int stateBits = meta & 0x03;
        FoundryStates foundryState = FoundryStates.stateFromMeta(stateBits);
        return this.getDefaultState().withProperty(STATE, foundryState).withProperty(FACING, direction);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing direction = (EnumFacing) state.getValue(FACING);
        FoundryStates fState = (FoundryStates) state.getValue(STATE);
        int stateBits = fState.getMeta();
        int directionBits = direction.getHorizontalIndex() << 2;

        return directionBits | stateBits;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return ((FoundryStates) state.getValue(STATE)).getMeta();
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, FoundryStates.DISCONNECTED.getMeta()));
    }

    private boolean checkValidFoundry(EnumFacing direction, IBlockAccess worldIn, BlockPos pos) {
        BlockPos posLL;
        switch (direction) {
            case NORTH:
                posLL = pos.west().down();
                break;
            case SOUTH:
                posLL = pos.east().down();
                break;
            case EAST:
                posLL = pos.north().down();
                break;
            case WEST:
                posLL = pos.south().down();
                break;
            default:
                posLL = pos.west().down();
        }
        boolean invalid;
        BlockPos check;
        for(int dx = 0; dx < 3; dx++){
            for(int dy = 0; dy < 3; dy++){
                for(int dz = 0; dz < 3; dz++){
                    check = posLL.offset(direction, dx).offset(direction.rotateY(), dz).up(dy);
                    invalid = !(worldIn.getBlockState(check).getBlock() == Blocks.iron_block || (check.equals(pos.offset(direction)) && worldIn.getBlockState(check).getBlock() == Blocks.air));
                    if(check.equals(pos)) invalid = false;
                    //FMLLog.info("Checked Position %d, %d, %d, value was %b", check.getX(), check.getY(), check.getZ(), invalid);
                    if(invalid) return false;
                }
            }
        }
        return true;
    }

    public static enum FoundryStates implements IStringSerializable {

        DISCONNECTED("disconnected", 0), CONNECTED("connected", 1);
        private final String name;
        private final int meta;
        private final String modelName;

        private FoundryStates(String name, int metadata) {
            this.name = name;
            this.meta = metadata;
            this.modelName = "sbfp:blockFoundry" + name.substring(0, 1).toUpperCase() + name.substring(1);
        }

        @Override
        public String getName() {
            return this.name;
        }

        public int getMeta() {
            return this.meta;
        }

        @Override
        public String toString() {
            return this.getName();
        }

        public String getModelResourceName() {
            return this.modelName;
        }

        private static final FoundryStates[] STATES_BY_META = new FoundryStates[FoundryStates.values().length];

        static {
            for (FoundryStates foundry : FoundryStates.values()) {
                STATES_BY_META[foundry.getMeta()] = foundry;
            }
        }

        public static FoundryStates stateFromMeta(int meta) {
            if (meta >= STATES_BY_META.length || meta < 0) {
                meta = 0;
            }
            return STATES_BY_META[meta];
        }

    }

}
