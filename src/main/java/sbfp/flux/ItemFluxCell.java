package sbfp.flux;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sbfp.modsbfp;

public class ItemFluxCell extends Item implements IFluxSourceItem, IFluxStorageItem {

    public ItemFluxCell(String name, int maxCharge){
        super();
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setMaxStackSize(1);
        this.setMaxDamage(maxCharge);
    }
    
    /**
     * Converts a flux value to a damage value...
     * @param stack The stack to set the damage for
     * @param flux Flux value
     * @return The damage to set
     */
    private static int fluxToDamage(ItemStack stack, int flux){
        return stack.getMaxDamage() - flux;
    }
    
    @Override
    public String getUnlocalizedName(ItemStack i){
        return "fluxCell";
    }
    
    @Override
    public boolean updateItemStackNBT(NBTTagCompound nbt)
    {
        if(!nbt.hasKey("charge")) nbt.setInteger("charge", 0);
        return false;
    }
    
    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[]{modsbfp.tabSBFP, this.getCreativeTab()};
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
    {
        ItemStack stack = new ItemStack(itemIn, 1);
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("charge",  stack.getMaxDamage());
        stack.setItemDamage(fluxToDamage(stack, stack.getMaxDamage()));
        subItems.add(stack);
        stack = new ItemStack(itemIn, 1);
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("charge", 0);
        subItems.add(stack);
        
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
    
    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return stack.getTagCompound() != null ? (stack.getTagCompound().hasKey("charge") && stack.getItemDamage() != 0): false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack cell, EntityPlayer playerIn, List tooltip, boolean advanced) 
    {
        if(!cell.hasTagCompound()){
            cell.setTagCompound(new NBTTagCompound());
            cell.getTagCompound().setInteger("charge", 0);
            cell.setItemDamage(fluxToDamage(cell, 0));
        }
        
        tooltip.add("Flux Level: "+cell.getTagCompound().getInteger("charge")+ "/" + cell.getMaxDamage());
    }
    
    @Override
    public int addFlux(ItemStack cell, int deltaF){
        if (deltaF < 0) return 0;
        if(!cell.hasTagCompound()){
            cell.setTagCompound(new NBTTagCompound());
            cell.getTagCompound().setInteger("charge", 0);
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
        if(!cell.hasTagCompound()){
            cell.setTagCompound(new NBTTagCompound());
            cell.getTagCompound().setInteger("charge", 0);
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
