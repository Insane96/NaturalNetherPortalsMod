package net.insane96mcp.naturalnetherportals.lib;

public class Properties {
	
	public static void Init() {
		General.Init();
		Nether.Init();
		Overworld.Init();
	}
	
	public static class General{
		
		public static void Init() {
			
		}
	}
	
	public static class Nether{		
		public static int chance;
		
		public static int minY;
		public static int maxY;
		
		public static float portalDecay;

		public static int minWidth;
		public static int maxWidth;

		public static int minHeight;
		public static int maxHeight;
		
		public static float fireChance;
		
		public static float fullPortalChance;
		public static float litPortalChance;
		
		public static float minDistance;
		
		public static void Init() {
			Config.config.setCategoryComment("nether", "Configure here every setting for the portal spawn in the nether");
			
			chance = Config.LoadIntProperty("nether", "chance", "One in every x there's a chance that a portal will try to spawn in a chunk", 120);

			minY = Config.LoadIntProperty("nether", "min_y", "Minimum Height the Portal will try to spawn", 8);
			maxY = Config.LoadIntProperty("nether", "max_y", "Maximum Height the Portal will try to spawn", 96);
			
			portalDecay = Config.LoadFloatProperty("nether", "portal_decay", "Percentage of how many obsidian blocks will have a portal. 100 means that the portal will not be missing any block", 45.0f);

			minWidth = Config.LoadIntProperty("nether", "portal_width_min", "Minimum width of the portal (counting the obsidian frame too).", 4);
			maxWidth = Config.LoadIntProperty("nether", "portal_width_max", "Maximum width of the portal (counting the obsidian frame too).", 5);

			minHeight = Config.LoadIntProperty("nether", "portal_height_min", "Minimum height of the portal (counting the obsidian frame too).", 5);
			maxHeight = Config.LoadIntProperty("nether", "portal_height_max", "Maximum height of the portal (counting the obsidian frame too).", 6);
			
			fireChance = Config.LoadFloatProperty("nether", "fire_chance", "Chance for fire to be placed on netherrack around the portal", 30.0f);

			fullPortalChance = Config.LoadFloatProperty("nether", "portal_full_chance", "Chance for a portal to have the full obsidian frame around it", 25.0f);
			litPortalChance = Config.LoadFloatProperty("nether", "portal_lit_chance", "Chance for a full portal to be on", 100.0f);
		
			minDistance = Config.LoadFloatProperty("nether", "min_distance", "Minumum blocks distance between the portals spawning. This is here to avoid clusters of portals", 175);
		}
	}
	
	public static class Overworld{		
		public static int chance;
		
		public static int minY;
		public static int maxY;
		
		public static float portalDecay;

		public static int minWidth;
		public static int maxWidth;

		public static int minHeight;
		public static int maxHeight;
		
		public static boolean burnBurnable;
		public static float fireChance;
		
		public static float netherrackChance;
		
		public static float fullPortalChance;
		public static float litPortalChance;
		
		public static float minDistance;
		
		public static int worldSpawnDistance;
		
		public static void Init() {
			Config.config.setCategoryComment("overworld", "Configure here every setting for the portal spawn in the overworld");
			
			chance = Config.LoadIntProperty("overworld", "chance", "One in every x there's a chance that a portal will try to spawn in a chunk", 384);

			minY = Config.LoadIntProperty("overworld", "min_y", "Minimum Height the Portal will try to spawn", 64);
			maxY = Config.LoadIntProperty("overworld", "max_y", "Maximum Height the Portal will try to spawn", 196);
			
			portalDecay = Config.LoadFloatProperty("overworld", "portal_decay", "Percentage of how many obsidian blocks will have a portal. 100 means that the portal will not be missing any block", 45.0f);

			minWidth = Config.LoadIntProperty("overworld", "portal_width_min", "Minimum width of the portal (counting the obsidian frame too).", 4);
			maxWidth = Config.LoadIntProperty("overworld", "portal_width_max", "Maximum width of the portal (counting the obsidian frame too).", 4);

			minHeight = Config.LoadIntProperty("overworld", "portal_height_min", "Minimum height of the portal (counting the obsidian frame too).", 5);
			maxHeight = Config.LoadIntProperty("overworld", "portal_height_max", "Maximum height of the portal (counting the obsidian frame too).", 5);
			
			burnBurnable = Config.LoadBoolProperty("overworld", "burn_burnable", "If true, burnable blocks (e.g. leaves, logs, etc.) will be replaced by fire instad of air", false);
			fireChance = Config.LoadFloatProperty("overworld", "fire_chance", "Chance for fire to be placed on netherrack", 10.0f);
			
			netherrackChance = Config.LoadFloatProperty("overworld", "base_netherrack_chance", "Chance for a netherrack block to be placed around the portal.\nThe actual chance is based on distance from portal\nThe formula to calculate the chance based on distance is (netherrack_chance / distance)\n", 50.0f);

			fullPortalChance = Config.LoadFloatProperty("overworld", "portal_full_chance", "Chance for a portal to have the full obsidian frame around it", 25.0f);
			litPortalChance = Config.LoadFloatProperty("overworld", "portal_lit_chance", "Chance for a full portal to be on", 0.0f);
		
			minDistance = Config.LoadFloatProperty("overworld", "min_distance", "Minumum blocks distance between the portals spawning. This is here to avoid clusters of portals", 313);
		
			worldSpawnDistance = Config.LoadIntProperty("overworld", "world_spawn_distance", "Radius where no nether portals will spawn around the spawn of the world. Set to 0 to disable.", 384);
		}
	}
}
