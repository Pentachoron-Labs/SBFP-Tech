package sbfp.world;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import sbfp.BlockSub;

public class BlockOre extends BlockSub{
        public static final IProperty TYPE = PropertyEnum.create("TYPE", EnumOreType.class);
        
	public BlockOre(int id, String[] names){
		super(id,Material.rock,names);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.blockHardness = 3;
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
            return getDefaultState().withProperty(TYPE, 0);
        }
        
        @Override
        public int getMetaFromState(IBlockState state){
            return ((EnumOreType) state.getValue(TYPE)).getMeta();
        }
        
        @Override
        public int damageDropped(IBlockState state){
            return getMetaFromState(state);
        }
}
