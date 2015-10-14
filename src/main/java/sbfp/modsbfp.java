package sbfp;

import java.io.File;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import sbfp.chemistry.ItemDye;
import sbfp.machines.BlockMachine;
import sbfp.machines.ItemBlockMachine;
import sbfp.machines.ItemRedflux;
import sbfp.machines.processor.ProcessorRecipeManager;
import sbfp.machines.processor.crusher.TileEntityCrusher;
import sbfp.machines.processor.solar.TileEntitySolarCharger;
import sbfp.world.BlockOre;
import sbfp.world.GeneratorOres;
import sbfp.world.ItemBlockOre;

@Mod(modid = modsbfp.modid, name = modsbfp.shortname, version = modsbfp.version)
public class modsbfp{

	// name constants
	public static final String modid = "sbfp"; // Channel, name, etc
	public static final String shortname = "SBFP Tech";

	public static final ItemStack recipePlaceholder = new ItemStack(Block.getBlockFromName("minecraft:stone"),1);
	public static final String version = "Aleph 2";

	// data constants
	public static final String guiDirectory = "/mods/sbfp/textures/gui/";
	public static final String entityDirectory = "/mods/sbfp/textures/entity/";
        
	// mechanics constants
	@Instance(modid)
	private static modsbfp instance;
	private final GeneratorOres wGen = new GeneratorOres();
	private static final Configuration config = new Configuration(new File("config/SBFP/SBFP.cfg"));
	private static final HashMap<String,HashMap<String,String>> lang = new HashMap<String,HashMap<String,String>>();

	@SidedProxy(clientSide = "sbfp.client.SBClientProxy", serverSide = "sbfp.SBCommonProxy")
	public static SBCommonProxy proxy;

	// blocks and items (sort by ID please)
	public static final BlockOre blockOre = new BlockOre(getBlockID("blockOreID",0x4c0),new String[]{"oreMonazite","oreFluorite","oreMolybdenite","oreRutile","oreCinnabar","oreLimonite","orePyrolusite","oreArsenopyrite"});
	public static final BlockMachine blockMachine = new BlockMachine(getBlockID("blockMachinesID",0x4c1),new String[]{"solarCharger","crusher"});

	public static final ItemRedflux itemRedflux = new ItemRedflux(getItemID("itemRedfluxID",0x4c00),new String[]{"redFluxAmp","redFluxAbsorber","redFluxStabilizer","chargedRedstone"});
	public static final ItemDye itemDye = new ItemDye(getItemID("itemDyeID",0x4c01),new String[]{"dyeTiO2","dyeVermillion","dyeOchre","dyeUltramarine","dyeMnO2","dyeGreen","dyePurple","dyeOrange","dyeGrey"});
//	public static final ItemTractor itemTractor = new ItemTractor(getItemID("itemTractorID",0x4c02),"itemTractor");

	public static final ProcessorRecipeManager<TileEntitySolarCharger> prmSolar = new ProcessorRecipeManager<TileEntitySolarCharger>();
	public static final ProcessorRecipeManager<TileEntityCrusher> prmCrusher = new ProcessorRecipeManager<TileEntityCrusher>();

	//For setting harvest levels of various blocks.
	public enum HarvestLevels{
		WOOD, STONE, IRON, DIAMOND
	}

        public static CreativeTabs tabSBFP = new CreativeTabs("SBFP") {

            @Override
            @SideOnly(Side.CLIENT)
            public Item getTabIconItem(){
             ItemStack iStack = new ItemStack(Blocks.wool);
             return iStack.getItem();
            }
        };
        
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		instance = this;
	}

	@EventHandler
	public void init(FMLInitializationEvent event){
		GameRegistry.registerBlock(blockOre,ItemBlockOre.class,"blockOre");
		GameRegistry.registerBlock(blockMachine,ItemBlockMachine.class,"blockMachines");

		GameRegistry.registerTileEntity(TileEntitySolarCharger.class,"sunlightCollector");
		GameRegistry.registerTileEntity(TileEntityCrusher.class,"crusher");

		GameRegistry.registerItem(itemRedflux,"itemRedflux");
//		GameRegistry.registerItem(itemTractor,"itemTractor");

//		MinecraftForge.setBlockHarvestLevel(blockOre,0,"pickaxe",HarvestLevels.IRON.ordinal());
//		MinecraftForge.setBlockHarvestLevel(blockOre,1,"pickaxe",HarvestLevels.STONE.ordinal());
//		MinecraftForge.setBlockHarvestLevel(blockOre,2,"pickaxe",HarvestLevels.DIAMOND.ordinal());
//		MinecraftForge.setBlockHarvestLevel(blockOre,3,"pickaxe",HarvestLevels.STONE.ordinal());
//		MinecraftForge.setBlockHarvestLevel(blockMachine,"pickaxe",HarvestLevels.IRON.ordinal());

//		EntityRegistry.registerModEntity(EntityTractor.class,"entityTractor",0,this,256,1,true);
//		GameRegistry.registerWorldGenerator(this.wGen);
		//NetworkRegistry.instance().registerGuiHandler(this,modsbfp.proxy);
		for(int i = 0; i<blockOre.names.length; i++){
			OreDictionary.registerOre(blockOre.names[i],new ItemStack(blockOre,1,i));
		}
		this.addRecipes();
		//modsbfp.instance.loadLang();
		
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

	private void addRecipes(){
		GameRegistry.addShapelessRecipe(new ItemStack(itemDye,2,5),new ItemStack(itemDye,1,2),new ItemStack(itemDye,1,3));
		GameRegistry.addShapelessRecipe(new ItemStack(itemDye,2,6),new ItemStack(itemDye,1,1),new ItemStack(itemDye,1,3));
		GameRegistry.addShapelessRecipe(new ItemStack(itemDye,2,7),new ItemStack(itemDye,1,1),new ItemStack(itemDye,1,2));
		GameRegistry.addShapelessRecipe(new ItemStack(itemDye,2,8),new ItemStack(itemDye,1,0),new ItemStack(itemDye,1,4));

		//Redflux Amplifier
//		GameRegistry.addRecipe(new ItemStack(itemRedflux,1,0),new Object[]{"IGI","GRG","IGI",'I',Item.ingotIron,'G',Item.ingotGold,'R',Item.redstone});
		//Redflux Absorber
//		GameRegistry.addRecipe(new ItemStack(itemRedflux,1,1),new Object[]{"GgG","gRg","GgG",'G',Block.glass,'g',Item.ingotGold,'R',Item.redstoneRepeater});
		//Stabilizer
//		GameRegistry.addRecipe(new ItemStack(itemRedflux,1,2),new Object[]{"RIR","GAG","IrI",'A',new ItemStack(itemRedflux,1,1),'R',Item.redstone,'r',Item.redstoneRepeater,'G',Item.ingotGold,'I',Item.ingotIron});
		//Infuser
//		GameRegistry.addRecipe(new ItemStack(blockMachine,1,0),new Object[]{"GGG","IAI","IRI",'G',Block.glass,'I',Item.ingotIron,'R',Item.redstone,'A',new ItemStack(itemRedflux,1,0)});
		//Crusher
//		GameRegistry.addRecipe(new ItemStack(blockMachine,1,1),new Object[]{" I ","PAP","RaR",'I',Block.blockIron,'P',Block.pistonBase,'A',new ItemStack(itemRedflux,1,1),'a',Block.anvil,'R',Item.redstone});

//		prmSolar.addRecipe(new RecipeSolar(new ItemStack(Item.redstone,1),new ItemStack(itemRedflux,1,3),45*20));
//		prmCrusher.addRecipe(new RecipeCrusher(new ItemStack(Block.stone,1),new ItemStack(Block.cobblestone,1),15*20));
//		prmCrusher.addRecipe(new RecipeCrusher(new ItemStack(Block.cobblestone,1),new ItemStack(Block.gravel,1),15*20));
//		prmCrusher.addRecipe(new RecipeCrusher(new ItemStack(Block.gravel,1),new ItemStack(Block.sand,1),8*20));
//		prmCrusher.addRecipe(new RecipeCrusher(new ItemStack(blockOre,1,6),new ItemStack(itemDye,1,4),new ItemStack(Block.gravel,1),0.4F,10*20));
//		prmCrusher.addRecipe(new RecipeCrusher(new ItemStack(blockOre,1,3),new ItemStack(itemDye,1,0),new ItemStack(Block.gravel,1),0.4F,10*20));
//		prmCrusher.addRecipe(new RecipeCrusher(new ItemStack(blockOre,1,5),new ItemStack(itemDye,1,2),new ItemStack(Block.gravel,1),0.4F,10*20));
	}

	private static int getBlockID(String name, int defaultid){
		config.load();
		Property q = config.get(Configuration.CATEGORY_GENERAL,name,defaultid);
		return q.getInt(defaultid);
	}

	private static int getItemID(String name, int defaultid){
		config.load();
		Property q = config.get(Configuration.CATEGORY_GENERAL,name,defaultid);
		return q.getInt(defaultid);
	}

	public static modsbfp getInstance(){
		return instance;
	}
	
	
}