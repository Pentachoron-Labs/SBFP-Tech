package sbfp.world;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumWorldBlockLayer;
import sbfp.BlockSB;

public class BlockOre extends BlockSB{
        public static final IProperty TYPE = PropertyEnum.create("TYPE", EnumOreType.class);
        
	public BlockOre(String name){
		super(Material.rock,name);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.blockHardness = 3;
	}
        
        @Override
        public EnumWorldBlockLayer getBlockLayer(){
            return EnumWorldBlockLayer.SOLID;
        }
        @Override
        public boolean isOpaqueCube(){
            return true;
        }
        
        @Override
        public int getRenderType(){
            return 3;
        }
        
        @Override
        public BlockState createBlockState(){
            return new BlockState(this, new IProperty[]{TYPE});
        }
        
        @Override
        public IBlockState getStateFromMeta(int meta){
            for(EnumOreType ore : EnumOreType.values()){
                if(ore.getMeta() == meta){
                    return getDefaultState().withProperty(TYPE, ore);    
                }
            }
            return getDefaultState().withProperty(TYPE, EnumOreType.MONAZITE);
        }
        
        @Override
        public int getMetaFromState(IBlockState state){
            return ((EnumOreType) state.getValue(TYPE)).getMeta();
        }
        
        @Override
        public int damageDropped(IBlockState state){
            return getMetaFromState(state);
        }
        
        @Override
        public void getSubBlocks(Item item, CreativeTabs tab, List list){
            for(EnumOreType ore : EnumOreType.values()){
                list.add(new ItemStack(item, 1, ore.getMeta()));
            }
        }
}
