package sbfp.machines;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import sbfp.BlockSub;


public abstract class BlockMachine extends BlockSub{
	public Icon[][] icons;
	public BlockMachine(int id, Material material, String[] names){
		super(id,material,names);
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register){
		icons = new Icon[names.length][6];
		for(int i = 0; i<names.length; i++){
			for(int j = 0; j<6; j++){
				this.icons[i][j] = register.registerIcon("sbfp:"+this.names[i]+ForgeDirection.getOrientation(j).toString());
				//System.out.println("sbfp:"+this.names[i]+ForgeDirection.getOrientation(j).toString());
			}
		}
	}
	
}
