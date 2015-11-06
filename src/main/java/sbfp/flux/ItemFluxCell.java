package sbfp.flux;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFluxCell extends Item implements IFluxSourceItem, IFluxStorageItem {

    public ItemFluxCell(String name, int maxCharge){
        super();
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setMaxStackSize(1);
        this.setMaxDamage(maxCharge == 0 ? 50 : maxCharge);
    }
    
    private static int fluxToDamage(ItemStack stack, int flux){
        return stack.getMaxDamage() - flux;
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
    
    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return stack.getTagCompound() != null ? stack.getTagCompound().hasKey("charge") : false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack cell, EntityPlayer playerIn, List tooltip, boolean advanced) 
    {
        NBTTagCompound data = cell.getTagCompound();
        if(data == null){
            data = new NBTTagCompound();
            data.setInteger("charge", 0);
            cell.setTagCompound(data);
            cell.setItemDamage(fluxToDamage(cell, 0));
        }
        
        tooltip.add("Flux Level: "+cell.getTagCompound().getInteger("charge")+ "/" + cell.getMaxDamage());
    }
    
    @Override
    public int addFlux(ItemStack cell, int deltaF){
        if (deltaF < 0) return 0;
        NBTTagCompound data = cell.getTagCompound();
        if(data == null){
            data = new NBTTagCompound();
            data.setInteger("charge", 0);
            cell.setTagCompound(data);
            cell.setItemDamage(fluxToDamage(cell, 0));
        }
        int currentFlux = cell.getTagCompound().getInteger("charge");
        int overflow = deltaF + currentFlux - cell.getMaxDamage();
        int finalFlux = overflow <= 0 ? deltaF+currentFlux : cell.getMaxDamage();
        cell.getTagCompound().setInteger("charge", finalFlux);
        cell.setItemDamage(fluxToDamage(cell,finalFlux));
        return overflow < 0 ? 0 : overflow;
    }
    
    @Override
    public int drainFlux(ItemStack cell, int deltaF){
        if(deltaF < 0) return 0;
        NBTTagCompound data = cell.getTagCompound();
        if(data == null){
            data = new NBTTagCompound();
            data.setInteger("charge", 0);
            cell.setTagCompound(data);
            cell.setItemDamage(fluxToDamage(cell, 0));
        }
        int currentFlux = cell.getTagCompound().getInteger("charge");
        if (currentFlux == 0) return 0;
        int fluxDrained, finalFlux;
        if(currentFlux < deltaF){
            fluxDrained = currentFlux;
            finalFlux = 0;
        }else{
            fluxDrained = deltaF;
            finalFlux = currentFlux - deltaF;
        }
        cell.getTagCompound().setInteger("charge", finalFlux);
        cell.setItemDamage(fluxToDamage(cell, finalFlux));
        return fluxDrained;
    }

    @Override
    public boolean isSubtypeValidFluxSource(int meta) {
        return true;
    }

    @Override
    public boolean canSubtypeStoreFlux(int meta) {
        return true;
    }

    @Override
    public boolean destroyOnDrain() {
        return false;
    }

    @Override
    public boolean canStackAcceptFlux(ItemStack stack, int amount) {
        return stack.getMaxDamage() - stack.getItemDamage() >= amount;
    }
    
    
}
