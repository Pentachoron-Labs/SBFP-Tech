package sbfp;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import sbfp.chemistry.DyeTypes;
import sbfp.chemistry.ItemDye;
import sbfp.machines.BlockMachine;
import sbfp.flux.FluxDeviceTypes;
import sbfp.flux.ItemFluxCell;
import sbfp.machines.ItemBlockMachine;
import sbfp.flux.ItemRedFluxDevice;
import sbfp.machines.MachineTypes;
import sbfp.machines.MaterialProcessRegistry;
import sbfp.machines.crusher.CrusherProcess;
import sbfp.machines.crusher.TileEntityCrusher;
import sbfp.machines.foundry.BlockFoundry;
import sbfp.machines.foundry.FoundryProcess;
import sbfp.machines.foundry.ItemBlockFoundry;
import sbfp.machines.foundry.TileEntityFoundry;
import sbfp.machines.solar.SolarInfusionProcess;
import sbfp.machines.solar.TileEntitySolarCharger;
import sbfp.world.BlockOre;
import sbfp.world.OreTypes;
import sbfp.world.GeneratorOres;
import sbfp.world.ItemBlockOre;

@Mod(modid = modsbfp.modid, name = modsbfp.shortname, version = modsbfp.version)
public class modsbfp {

    // name constants
    public static final String modid = "sbfp"; // Channel, name, etc
    public static final String shortname = "SBFP Tech";

    public static final ItemStack recipePlaceholder = new ItemStack(Blocks.stone, 1);
    public static final String version = "Aleph 2";

    // data constants
    public static final String guiDirectory = "/mods/sbfp/textures/gui/";
    public static final String entityDirectory = "/mods/sbfp/textures/entity/";

    // mechanics constants
    @Instance(modid)
    private static modsbfp instance;

    private final GeneratorOres wGen = new GeneratorOres();

    private static final Configuration config = new Configuration(new File("config/SBFP/SBFP.cfg"));

    private static final HashMap<String, HashMap<String, String>> lang = new HashMap<String, HashMap<String, String>>();

    @SidedProxy(clientSide = "sbfp.client.SBClientProxy", serverSide = "sbfp.SBCommonProxy")
    public static SBProxy proxy;

    // blocks and items
    public static final BlockOre blockOre = new BlockOre("blockOre");
    public static final BlockMachine blockMachine = new BlockMachine("blockMachine");
    public static final BlockFoundry blockFoundry = new BlockFoundry("blockFoundry");

    public static final ItemRedFluxDevice itemFluxDevice = new ItemRedFluxDevice("itemFluxDevice");
    public static final ItemDye itemDye = new ItemDye("itemDye");
    public static final ItemFluxCell itemLowFluxCell = new ItemFluxCell("itemLowFluxCell", 100);

//	public static final ItemTractor itemTractor = new ItemTractor(getItemID("itemTractorID",0x4c02),"itemTractor");
    public static final MaterialProcessRegistry<CrusherProcess> crushingRegistry = new MaterialProcessRegistry<CrusherProcess>();
    public static final MaterialProcessRegistry<SolarInfusionProcess> solarInfusionRegistry = new MaterialProcessRegistry<SolarInfusionProcess>();
    public static final MaterialProcessRegistry<FoundryProcess> foundrySmeltingRegistry = new MaterialProcessRegistry<FoundryProcess>();

    //For setting harvest levels of various blocks.
    public enum HarvestLevels {

        WOOD, STONE, IRON, DIAMOND
    }

    public static final CreativeTabs tabSBFP = new TabSBFP();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        instance = this;

        //register blocks, items, and tileEntities
        GameRegistry.registerBlock(blockOre, ItemBlockOre.class, "blockOre");
        GameRegistry.registerBlock(blockMachine, ItemBlockMachine.class, "blockMachine");
        GameRegistry.registerBlock(blockFoundry, ItemBlockFoundry.class, "blockFoundry");

        GameRegistry.registerItem(itemDye, "itemDye");
        GameRegistry.registerItem(itemFluxDevice, "itemFluxDevice");
        GameRegistry.registerItem(itemLowFluxCell, "itemLowFluxCell");

        GameRegistry.registerTileEntity(TileEntitySolarCharger.class, "sunlightCollector");
        GameRegistry.registerTileEntity(TileEntityCrusher.class, "crusher");
        GameRegistry.registerTileEntity(TileEntityFoundry.class, "foundry");

        NetworkRegistry.INSTANCE.registerGuiHandler(modsbfp.getInstance(), proxy);
        //Do the client only/server only stuff
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        GameRegistry.registerWorldGenerator(this.wGen, GeneratorOres.GENERATOR_WEIGHT);
        //NetworkRegistry.instance().registerGuiHandler(this,modsbfp.proxy);

        for (OreTypes ore : OreTypes.values()) {
            OreDictionary.registerOre(ore.getName(), new ItemStack(blockOre, 1, ore.getMeta()));
        }

        this.addRecipes();

        proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent post){
        this.fillFoundryRegistry();
    }
    
    private void addRecipes() {
//        GameRegistry.addShapelessRecipe(new ItemStack(itemDye, 2, 5), new ItemStack(itemDye, 1, 2), new ItemStack(itemDye, 1, 3));
//        GameRegistry.addShapelessRecipe(new ItemStack(itemDye, 2, 6), new ItemStack(itemDye, 1, 1), new ItemStack(itemDye, 1, 3));
//        GameRegistry.addShapelessRecipe(new ItemStack(itemDye, 2, 7), new ItemStack(itemDye, 1, 1), new ItemStack(itemDye, 1, 2));
//        GameRegistry.addShapelessRecipe(new ItemStack(itemDye, 2, 8), new ItemStack(itemDye, 1, 0), new ItemStack(itemDye, 1, 4));
        solarInfusionRegistry.addProcess(new SolarInfusionProcess("chargedRedstone", new ItemStack(Items.redstone, 1), new ItemStack(itemFluxDevice, 1, FluxDeviceTypes.CHARGEDREDSTONE.getMeta()), 40));
        crushingRegistry.addProcess(new CrusherProcess("stone_to_cobblestone", new ItemStack(Blocks.cobblestone, 1), new ItemStack(Blocks.cobblestone, 1), 15 * 20, 20));
        crushingRegistry.addProcess(new CrusherProcess("cobblestone_to_gravel", new ItemStack(Blocks.cobblestone, 1), new ItemStack(Blocks.gravel, 1), 15 * 20, 15));
        crushingRegistry.addProcess(new CrusherProcess("gravel_to_sand", new ItemStack(Blocks.gravel, 1), new ItemStack(Blocks.sand, 1), 5 * 20, 5));
        crushingRegistry.addProcess(new CrusherProcess("pyrolusite_to_manganeseBlack", new ItemStack(blockOre, 1, OreTypes.PYROLUSITE.getMeta()), new ItemStack(itemDye, 1, DyeTypes.MNO2.getMeta()), new ItemStack(Blocks.gravel, 1), 0.4F, 10 * 20, 10));
        crushingRegistry.addProcess(new CrusherProcess("rutile_to_titaniumWhite", new ItemStack(blockOre, 1, OreTypes.RUTILE.getMeta()), new ItemStack(itemDye, 1, DyeTypes.TIO2.getMeta()), new ItemStack(Blocks.gravel, 1), 0.4F, 10 * 20, 10));
        crushingRegistry.addProcess(new CrusherProcess("limonite_to_ochre", new ItemStack(blockOre, 1, OreTypes.LIMONITE.getMeta()), new ItemStack(itemDye, 1, DyeTypes.OCHRE.getMeta()), new ItemStack(Blocks.gravel, 1), 0.4F, 10 * 20, 10));
        //Redflux Amplifier
        GameRegistry.addRecipe(new ItemStack(itemFluxDevice, 1, FluxDeviceTypes.AMPLIFIER.getMeta()), new Object[]{"IGI", "GRG", "IGI", 'I', Items.iron_ingot, 'G', Items.gold_ingot, 'R', Items.redstone});
        //Redflux Absorber
        GameRegistry.addRecipe(new ItemStack(itemFluxDevice, 1, FluxDeviceTypes.ABSORBER.getMeta()), new Object[]{"GgG", "gRg", "GgG", 'G', Blocks.glass, 'g', Items.gold_ingot, 'R', Items.repeater});
        //Stabilizer
        GameRegistry.addRecipe(new ItemStack(itemFluxDevice, 1, FluxDeviceTypes.STABILIZER.getMeta()), new Object[]{"RIR", "GAG", "IrI", 'A', new ItemStack(itemFluxDevice, 1, FluxDeviceTypes.ABSORBER.getMeta()), 'R', Items.redstone, 'r', Items.repeater, 'G', Items.gold_ingot, 'I', Items.iron_ingot});
        //Infuser
        GameRegistry.addRecipe(new ItemStack(blockMachine, 1, MachineTypes.SOLARCHARGER.getMeta()), new Object[]{"GGG", "IAI", "IRI", 'G', Blocks.glass, 'I', Items.iron_ingot, 'R', Items.redstone, 'A', new ItemStack(itemFluxDevice, 1, FluxDeviceTypes.AMPLIFIER.getMeta())});
        //Crusher
        GameRegistry.addRecipe(new ItemStack(blockMachine, 1, MachineTypes.CRUSHER.getMeta()), new Object[]{" I ", "PAP", "RaR", 'I', Blocks.iron_block, 'P', Blocks.piston, 'A', new ItemStack(itemFluxDevice, 1, FluxDeviceTypes.ABSORBER.getMeta()), 'a', Blocks.anvil, 'R', Items.redstone});
        //Empty Flux Cell -- Capacity 100
        GameRegistry.addRecipe(addNBTInt(addNBTInt(new ItemStack(itemLowFluxCell, 1), "fluxLevel", 0), "fluxCapacity", 100), 
                new Object[]{" I ", "IAI", "IRI", 'I', Items.iron_ingot, 'A', new ItemStack(itemFluxDevice, 1, FluxDeviceTypes.ABSORBER.getMeta()), 'R', Items.redstone});
        //Partially Charged Flux Cell -- Capacity 100
        ItemStack stack = new ItemStack(itemLowFluxCell, 1);
        addNBTInt(stack, "fluxLevel", 10);
        addNBTInt(stack, "fluxCapacity", 100);
        stack.setItemDamage(90);
        GameRegistry.addRecipe(stack, new Object[]{" I ", "IAI", "IRI", 'I', Items.iron_ingot, 'A', new ItemStack(itemFluxDevice, 1, FluxDeviceTypes.ABSORBER.getMeta()), 'R', new ItemStack(itemFluxDevice, 1, FluxDeviceTypes.CHARGEDREDSTONE.getMeta())});
        
    }
    
    private ItemStack addNBTInt(ItemStack stack, String tagName, int tagValue){
        stack.getSubCompound("sbfp", true).setInteger(tagName, tagValue);
        return stack;
    }
    
    private ItemStack addNBTString(ItemStack stack, String tagName, String tagValue){
        stack.getSubCompound("sbfp", true).setString(tagName, tagValue);
        return stack;
    }
    
    private void fillFoundryRegistry(){
        Set recipeSet = FurnaceRecipes.instance().getSmeltingList().entrySet();
        for(Object o : recipeSet){
            Entry<ItemStack, ItemStack> recipe = (Entry<ItemStack, ItemStack>) o;
            FMLLog.info(recipe.getKey().getDisplayName() + " to " + recipe.getValue().getDisplayName());
            foundrySmeltingRegistry.addProcess(new FoundryProcess(recipe));
        } 
    }

    public static modsbfp getInstance() {
        return instance;
    }

}
