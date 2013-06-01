package sbfp;

import java.io.File;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import sbfp.chemistry.ItemDye;
import sbfp.machines.BlockMachines;
import sbfp.machines.ItemBlockMachines;
import sbfp.machines.ItemRedflux;
import sbfp.machines.tiles.TileSolarCharger;
import sbfp.world.BlockOre;
import sbfp.world.GeneratorOres;
import sbfp.world.ItemBlockOre;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = modsbfp.modid, name = modsbfp.shortname, version = modsbfp.version)
@NetworkMod(channels = {modsbfp.modid}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class modsbfp{

	// name constants
	public static final String modid = "sbfp"; //Channel, name, etc
	public static final String shortname = "SBFP Tech";
	public static final String version = "Aleph 0.58";
	
	//Directory Constants
	public static final String guiDirectory = "/mods/sbfp/textures/gui/";

	// mechanics constants
	@Instance(modid)
	private static modsbfp instance;
	private GeneratorOres wGen;
	private static final Configuration config = new Configuration(new File("config/SBFP/SBFP.cfg"));

	@SidedProxy(clientSide = "sbfp.client.SBClientProxy", serverSide = "sbfp.SBCommonProxy")
	public static SBCommonProxy proxy;

	//block and item name
	public static final String[][] redFluxNames = new String[][]{{"redFluxAmp","redFluxAbsorber","redFluxStablizer","chargedRedstone"},{"Redstone Flux Amplifier","Redstone Flux Absorber","Redstone Flux Stablilizer","Charged Redstone"}};
	public static final String[][] machineNames = new String[][]{{"solarCharger"},{"Sunlight Collector"}};
	public static final String[][] oreNames = new String[][]{{"oreThorium","oreFluorite","oreMoS2","oreRutile","oreCinnabar","oreLimonite","orePyrolusite"},{"Monazite Sand","Fluorite","Molybdenite","Rutile","Cinnabar","Limonite","Pyrolusite"}};
	public static final String[][] dyeNames = new String[][]{{"dyeTiO2","dyeVermillion","dyeOchre","dyeUltramarine","dyeMnO2","dyeGreen","dyePurple","dyeOrange","dyeGrey"},{"Titanium White","Vermillion","Ochre","Ultramarine","Manganese Black","Green Dye","Purple Dye","Orange Dye","Grey Dye"}};
	// blocks and items
	public static final BlockOre blockOre = new BlockOre(getBlockID("blockOreID",0x4c0));
	public static final BlockMachines blockMachines = new BlockMachines(getBlockID("blockMachinesID",0x4c3));
	public static final ItemRedflux itemRedflux = new ItemRedflux(getItemID("itemRedfluxID",0x4c1));
	public static final ItemDye itemDye = new ItemDye(getItemID("itemDyeID",0x4c2));

	@PreInit
	public void preInit(FMLPreInitializationEvent event){
		instance = this;
		wGen = new GeneratorOres();
	}

	@Init
	public void init(FMLInitializationEvent event){
		FMLLog.info("SHAZAP!!!");
		GameRegistry.registerBlock(blockOre,ItemBlockOre.class,"blockOre");
		GameRegistry.registerBlock(blockMachines, ItemBlockMachines.class, "blockMachines");
		GameRegistry.registerTileEntity(TileSolarCharger.class,"sunlightCollector");
		GameRegistry.registerItem(itemRedflux,"itemRedflux");
		//TODO: multilingual support
		for(int i = 0; i<blockOre.names.length; i++){
			LanguageRegistry.addName(new ItemStack(blockOre.blockID,1,i),oreNames[1][i]);
		}
		for(int i = 0; i<itemRedflux.names.length; i++){
			LanguageRegistry.addName(new ItemStack(itemRedflux.itemID,1,i),redFluxNames[1][i]);
		}
		for(int i = 0; i<blockMachines.names.length; i++){
			LanguageRegistry.addName(new ItemStack(blockMachines.blockID,1,i),machineNames[1][i]);
		}
		for(int i = 0; i<itemDye.names.length; i++){
			LanguageRegistry.addName(new ItemStack(itemDye.itemID,1,i),dyeNames[1][i]);
		}
		this.addRecipes();
		GameRegistry.registerWorldGenerator(this.wGen);
		NetworkRegistry.instance().registerGuiHandler(this, this.proxy);
	}

	private void addRecipes(){
		GameRegistry.addShapelessRecipe(new ItemStack(itemDye,2,5),new ItemStack(itemDye,1,2),new ItemStack(itemDye,1,3));
		GameRegistry.addShapelessRecipe(new ItemStack(itemDye,2,6),new ItemStack(itemDye,1,1),new ItemStack(itemDye,1,3));
		GameRegistry.addShapelessRecipe(new ItemStack(itemDye,2,7),new ItemStack(itemDye,1,1),new ItemStack(itemDye,1,2));
		GameRegistry.addShapelessRecipe(new ItemStack(itemDye,2,8),new ItemStack(itemDye,1,0),new ItemStack(itemDye,1,4));
	}

	private static int getBlockID(String name, int defaultid){
		config.load();
		Property q = config.get(Configuration.CATEGORY_BLOCK,name,defaultid);
		return q.getInt(defaultid);
	}

	private static int getItemID(String name, int defaultid){
		config.load();
		Property q = config.get(Configuration.CATEGORY_ITEM,name,defaultid);
		return q.getInt(defaultid);
	}

	public static modsbfp getInstance(){
		return instance;
	}
}