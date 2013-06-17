package sbfp.flux;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sbfp.BlockSub;

public class BlockFluxwire extends BlockSub implements ITileEntityProvider{

	public BlockFluxwire(int id, String[] names){
		super(id,Material.circuits,names);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public TileEntity createNewTileEntity(World world){
		return null;
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public boolean renderAsNormalBlock(){
		return false;
	}

	@Override
	public int getRenderType(){
		return 5;
	}

	@Override
	public boolean canPlaceBlockAt(World w, int x, int y, int z){
		return w.doesBlockHaveSolidTopSurface(x,y-1,z);
	}
}