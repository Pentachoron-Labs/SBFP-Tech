package sbfp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import sbfp.machines.ItemRedflux;
import sbfp.world.BlockOre;
import sbfp.world.ItemBlockOre;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = modsbfp.modid, name = modsbfp.shortname, version = modsbfp.version)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class modsbfp{

	// Logger
	public static final Logger logger = Logger.getLogger("Minecraft");
	// name constants
	public static final String modid = "sbfp";
	public static final String shortname = "SBFP Tech";
	public static final String version = "Aleph Null";
	
	public static final Map<String, String> names = new HashMap();

	// mechanics constants
	@Instance(modid)
	private static modsbfp instance;
	private static final Configuration config = new Configuration(new File("config/SBFP/SBFP.cfg"));
	@SidedProxy(clientSide = "sbfp.client.SBClientProxy", serverSide = "sbfp.SBCommonProxy")
	public static SBCommonProxy proxy;

	// blocks and items
	public static final BlockOre blockOre = new BlockOre(getBlockID("blockOreID",0x4c0));
	public static final ItemRedflux itemRedflux = new ItemRedflux(getItemID("itemRedfluxID",0x4c1));

	@Init
	public void init(FMLInitializationEvent event){
		GameRegistry.registerBlock(blockOre,ItemBlockOre.class,"blockOre");
		GameRegistry.registerItem(itemRedflux,"itemRedflux");
		//LanguageRegistry.instance().addStringLocalization("oreThorium","Thorium Ore");
		for(int i = 0; i<blockOre.names.length; i++){
			LanguageRegistry.addName(new ItemStack(blockOre.blockID,1,i),names.get(blockOre.names[i]));
			//change this.names[i] to whatever lang code
		}
		for(int i = 0; i<itemRedflux.names.length; i++){
			LanguageRegistry.addName(new ItemStack(itemRedflux.itemID,1,i),names.get(itemRedflux.names[i]));
			//change this.names[i] to whatever lang code
		}
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
	
	public void addDisplayName(String unLocal, String disp){
		this.names.put(unLocal, disp);
	}

	public static modsbfp getInstance(){
		return instance;
	}
}