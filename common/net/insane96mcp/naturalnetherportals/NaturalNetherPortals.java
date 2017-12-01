package net.insane96mcp.naturalnetherportals;

import net.insane96mcp.naturalnetherportals.proxies.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = NaturalNetherPortals.MOD_ID, name = NaturalNetherPortals.MOD_NAME, version = NaturalNetherPortals.VERSION, acceptableRemoteVersions = "*", acceptedMinecraftVersions = NaturalNetherPortals.MINECRAFT_VERSIONS)
public class NaturalNetherPortals {
	
	public static final String MOD_ID = "naturalnetherportals";
	public static final String MOD_NAME = "Natural Nether Portals";
	public static final String VERSION = "1.0.2";
	public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ":";
	public static final String MINECRAFT_VERSIONS = "[1.12,1.12.3]";
	
	@Instance(MOD_ID)
	public static NaturalNetherPortals instance;
	
	@SidedProxy(clientSide = "net.insane96mcp.naturalnetherportals.proxies.ClientProxy", serverSide = "net.insane96mcp.naturalnetherportals.proxies.ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		proxy.PreInit(event);
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) {
		proxy.Init(event);
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event) {
		proxy.PostInit(event);
	}
}
