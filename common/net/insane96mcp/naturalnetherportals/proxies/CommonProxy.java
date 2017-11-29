package net.insane96mcp.naturalnetherportals.proxies;

import net.insane96mcp.naturalnetherportals.lib.Config;
import net.insane96mcp.naturalnetherportals.lib.Properties;
import net.insane96mcp.naturalnetherportals.worldgen.NetherPortalGen;
import net.insane96mcp.naturalnetherportals.worldgen.OverworldPortalGen;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void PreInit(FMLPreInitializationEvent event) {
		Config.config = new Configuration(event.getSuggestedConfigurationFile());
		Config.SyncConfig();
		Properties.Init();
		
	}
	
	public void Init(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(new NetherPortalGen(), 1);
		GameRegistry.registerWorldGenerator(new OverworldPortalGen(), 1);
	}
	
	public void PostInit(FMLPostInitializationEvent event) {
		Config.SaveConfig();
	}
}
