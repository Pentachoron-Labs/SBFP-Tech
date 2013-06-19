package sbfp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import sbfp.chemistry.ItemDye;
import sbfp.machines.BlockMachine;
import sbfp.machines.ItemBlockMachine;
import sbfp.machines.ItemRedflux;
import sbfp.machines.solar.TileEntitySolarCharger;
import sbfp.recipes.CrusherOutput;
import sbfp.recipes.ProcessorRecipeManager;
import sbfp.tractor.EntityTractor;
import sbfp.tractor.ItemTractor;
import sbfp.world.BlockMethane;
import sbfp.world.BlockOre;
import sbfp.world.GeneratorMe;
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
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = modsbfp.modid, name = modsbfp.shortname, version = modsbfp.version)
@NetworkMod(channels = {modsbfp.modid}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class modsbfp{

	// name constants
	public static final String modid = "sbfp"; // Channel, name, etc
	public static final String shortname = "SBFP Tech";

	public static final ItemStack recipePlaceholder = new ItemStack(Block.stone,1);
	public static final String version = "Aleph 1";

	// data constants
	public static final String guiDirectory = "/mods/sbfp/textures/gui/";
	public static final String textureDirectory = "/mods/sbfp/textures/entity/";

	// mechanics constants
	@Instance(modid)
	private static modsbfp instance;
	private final GeneratorOres wgenOres = new GeneratorOres();
	private final GeneratorMe wgenMe = new GeneratorMe();
	private static final Configuration config = new Configuration(new File("config/SBFP/SBFP.cfg"));
	private static final HashMap<String,HashMap<String,String>> lang = new HashMap<String,HashMap<String,String>>();

	@SidedProxy(clientSide = "sbfp.client.SBClientProxy", serverSide = "sbfp.SBCommonProxy")
	public static SBCommonProxy proxy;

	// blocks and items (sort by ID please)
	public static final BlockOre blockOre = new BlockOre(getBlockID("blockOreID",0x4c0),new String[]{"oreMonazite","oreFluorite","oreMolybdenite","oreRutile","oreCinnabar","oreLimonite","orePyrolusite","oreArsenopyrite"});
	public static final BlockMachine blockMachine = new BlockMachine(getBlockID("blockMachinesID",0x4c1),new String[]{"solarCharger", "crusher"});
	public static final BlockMethane blockMethane = new BlockMethane(getBlockID("blockMethaneID",0x4c2),new String[]{"methaneStill"});

	public static final ItemRedflux itemRedflux = new ItemRedflux(getItemID("itemRedfluxID",0x4c00),new String[]{"redFluxAmp","redFluxAbsorber","redFluxStabilizer","chargedRedstone"});
	public static final ItemDye itemDye = new ItemDye(getItemID("itemDyeID",0x4c01),new String[]{"dyeTiO2","dyeVermillion","dyeOchre","dyeUltramarine","dyeMnO2","dyeGreen","dyePurple","dyeOrange","dyeGrey"});
	public static final ItemTractor itemTractor = new ItemTractor(getItemID("itemSecretID",0x4c02),"itemSecret");

	@PreInit
	public void preInit(FMLPreInitializationEvent event){
		instance = this;
	}

	@Init
	public void init(FMLInitializationEvent event){
		FMLLog.info("SHAZAP!!!");
		GameRegistry.registerBlock(blockOre,ItemBlockOre.class,"blockOre");
		GameRegistry.registerBlock(blockMachine,ItemBlockMachine.class,"blockMachines");
		GameRegistry.registerTileEntity(TileEntitySolarCharger.class,"sunlightCollector");
		GameRegistry.registerItem(itemRedflux,"itemRedflux");
		GameRegistry.registerItem(itemTractor,"itemSecret");
		EntityRegistry.registerModEntity(EntityTractor.class,"entitySecret",0,this,256,1,true);
		GameRegistry.registerWorldGenerator(this.wgenOres);
		GameRegistry.registerWorldGenerator(this.wgenMe);
		NetworkRegistry.instance().registerGuiHandler(this,modsbfp.proxy);
		for(int i = 0; i<blockOre.names.length; i++){
			OreDictionary.registerOre(blockOre.names[i],new ItemStack(blockOre.blockID,1,i));
		}
		new ProcessorRecipeManager().initialize();
		this.addRecipes();
		modsbfp.instance.loadLang();
		proxy.init();
	}

	private void loadLang(){
		for(String i:(Set<String>) StringTranslate.getInstance().getLanguageList().keySet()){
			lang.put(i,new HashMap<String,String>());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(modsbfp.class.getResourceAsStream("/mods/sbfp/lang/sbfp.lang")));
		try{
			String q, l = null;
			while((q = br.readLine())!=null){
				if(q.equals("")||q.charAt(0)=='#'){
					continue;
				}
				if(q.contains("=")){
					String key = q.substring(0,q.indexOf("="));
					String value = q.substring(q.indexOf("=")+1);
					LanguageRegistry.instance().addStringLocalization(key,l,value);
				}else{
					l = q;
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void addRecipes(){
		GameRegistry.addShapelessRecipe(new ItemStack(itemDye,2,5),new ItemStack(itemDye,1,2),new ItemStack(itemDye,1,3));
		GameRegistry.addShapelessRecipe(new ItemStack(itemDye,2,6),new ItemStack(itemDye,1,1),new ItemStack(itemDye,1,3));
		GameRegistry.addShapelessRecipe(new ItemStack(itemDye,2,7),new ItemStack(itemDye,1,1),new ItemStack(itemDye,1,2));
		GameRegistry.addShapelessRecipe(new ItemStack(itemDye,2,8),new ItemStack(itemDye,1,0),new ItemStack(itemDye,1,4));
		
		ProcessorRecipeManager.instance.addRecipe(new ItemStack(Block.stone, 1), new CrusherOutput(new ItemStack(Block.cobblestone, 1)));
		ProcessorRecipeManager.instance.addRecipe(new ItemStack(Block.cobblestone, 1), new CrusherOutput(new ItemStack(Block.gravel, 1)));
		ProcessorRecipeManager.instance.addRecipe(new ItemStack(Block.gravel, 1), new CrusherOutput(new ItemStack(Block.sand, 1)));
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