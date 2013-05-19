package sbfp;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import sbfp.world.BlockOre;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = sbfp.modid, name = sbfp.shortname, version = sbfp.version)
@NetworkMod(clientSideRequired=true,serverSideRequired=false)
public class sbfp{

	// name constants
	public static final String modid = "sbfp";
	public static final String shortname = "SBFP Tech";
	public static final String version = "Aleph Null";

	// mechanics constants
	@Instance(modid)
	private static sbfp instance;
	private static final Configuration config = new Configuration(new File("config/SBFP/SBFP.cfg"));
	@SidedProxy(clientSide = "sbfp.client.SBClientProxy", serverSide = "sbfp.SBCommonProxy")
	public static SBCommonProxy proxy;

	// blocks and items
	public static final BlockOre blockThoriumOre = new BlockOre(0,getBlockID("blockThoriumOre",0x4c0));
	public static final BlockOre blockFluoriteOre = new BlockOre(1,getBlockID("blockFluoriteOre",0x4c1));
	public static final BlockOre blockMolybdeniteOre = new BlockOre(2,getBlockID("blockMolybdeniteOre",0x4c2));

	@Init
	public void init(FMLInitializationEvent event){
		GameRegistry.registerBlock(blockThoriumOre,"blockThoriumOre");
		LanguageRegistry.addName(blockThoriumOre, "Thorium Ore");
		GameRegistry.registerBlock(blockFluoriteOre,"blockFluoriteOre");
		LanguageRegistry.addName(blockFluoriteOre, "Fluorite Ore");
		GameRegistry.registerBlock(blockMolybdeniteOre,"blockMolybdeniteOre");
		LanguageRegistry.addName(blockMolybdeniteOre, "Molybdenite Ore");
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
}