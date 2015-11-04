package sbfp;

import java.io.File;
import java.util.HashMap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import sbfp.chemistry.DyeTypes;
import sbfp.chemistry.ItemDye;
import sbfp.machines.BlockMachine;
import sbfp.flux.FluxDeviceTypes;
import sbfp.flux.ItemFluxCell;
import sbfp.machines.ItemBlockMachine;
import sbfp.flux.ItemRedFluxDevice;
import sbfp.machines.MachineTypes;
import sbfp.machines.processor.MaterialProcessRegistry;
import sbfp.machines.processor.crusher.CrusherProcess;
import sbfp.machines.processor.crusher.TileEntityCrusher;
import sbfp.machines.processor.foundry.BlockFoundry;
import sbfp.machines.processor.foundry.ItemBlockFoundry;
import sbfp.machines.processor.foundry.TileEntityFoundry;
import sbfp.machines.processor.solar.SolarInfusionProcess;
import sbfp.machines.processor.solar.TileEntitySolarCharger;
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
    public static SBCommonProxy proxy;

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

    //For setting harvest levels of various blocks.
    public enum HarvestLevels {

        WOOD, STONE, IRON, DIAMOND
    }

    public static CreativeTabs tabSBFP = new CreativeTabs("SBFP") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            ItemStack iStack = new ItemStack(GameRegistry.findItem("sbfp", "blockMachine"), 1, 1);
            return iStack.getItem();
        }
    };

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

//	private void loadLang(){
//		for(String i:(Set<String>) StringTranslate.getInstance().getLanguageList().keySet()){
//			lang.put(i,new HashMap<String,String>());
//		}
//		BufferedReader br = new BufferedReader(new InputStreamReader(modsbfp.class.getResourceAsStream("/mods/sbfp/lang/sbfp.lang")));
//		try{
//			String q, l = null;
//			while((q = br.readLine())!=null){
//				if(q.equals("")||q.charAt(0)=='#'){
//					continue;
//				}
//				if(q.contains("=")){
//					String key = q.substring(0,q.indexOf("="));
//					String value = q.substring(q.indexOf("=")+1);
//					LanguageRegistry.instance().addStringLocalization(key,l,value);
//				}else{
//					l = q;
//				}
//			}
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//	}
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
        //
        GameRegistry.addRecipe(new ItemStack(itemLowFluxCell, 1), new Object[]{" I ", "IAI", "IRI", 'I', Items.iron_ingot, 'A', new ItemStack(itemFluxDevice, 1, FluxDeviceTypes.ABSORBER.getMeta()), 'R', Items.redstone});
    }

    private static int getBlockID(String name, int defaultid) {
        config.load();
        Property q = config.get(Configuration.CATEGORY_GENERAL, name, defaultid);
        return q.getInt(defaultid);
    }

    private static int getItemID(String name, int defaultid) {
        config.load();
        Property q = config.get(Configuration.CATEGORY_GENERAL, name, defaultid);
        return q.getInt(defaultid);
    }

    public static modsbfp getInstance() {
        return instance;
    }

}
