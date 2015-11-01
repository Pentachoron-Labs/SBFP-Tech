package sbfp.flux;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemFluxCell extends Item implements IFluxSourceItem, IFluxContainerItem {

    public ItemFluxCell(String name, int maxCharge){
        super();
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setMaxStackSize(1);
        this.setMaxDamage(maxCharge == 0 ? 50 : maxCharge);
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
    
    public int addFlux(ItemStack cell, int deltaC){
        if (deltaC < 0) return 0;
        NBTTagCompound data = cell.getTagCompound();
        if(data == null){
            data = new NBTTagCompound();
            cell.setTagCompound(data);
        }
        int currentCharge = cell.getMaxDamage() - cell.getItemDamage();
        int overflow = deltaC + currentCharge - cell.getMaxDamage();
        int finalCharge = overflow <= 0 ? deltaC+currentCharge : cell.getMaxDamage();
        data.setInteger("charge", finalCharge);
        cell.setItemDamage(cell.getMaxDamage() - finalCharge);
        return overflow < 0 ? 0 : overflow;
    }
    
    public int drainFlux(ItemStack cell, int amount){
        if(amount < 0) return 0;
        NBTTagCompound data = cell.getTagCompound();
        if(data == null){
            data = new NBTTagCompound();
            cell.setTagCompound(data);
        }
        int currentCharge = cell.getMaxDamage() - cell.getItemDamage();
        int overflow = amount - currentCharge;
        int finalCharge = overflow <= 0 ? currentCharge - amount : 0;
        data.setInteger("charge", finalCharge);
        cell.setItemDamage(cell.getMaxDamage() - finalCharge);
        return overflow < 0 ? 0 : overflow;
    }

    @Override
    public boolean isSubtypeValidFluxSource(int meta) {
        return true;
    }

    @Override
    public boolean isSubtypeValidFluxContainer(int meta) {
        return true;
    }

    @Override
    public boolean destroyOnDrain() {
        return false;
    }
    
    
}
