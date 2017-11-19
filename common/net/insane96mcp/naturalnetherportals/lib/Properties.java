package net.insane96mcp.naturalnetherportals.lib;

public class Properties {
	
	public static void Init() {
		General.Init();
	}
	
	public static class General{
		public static int minAgeTicks;
		public static int maxAgeTicks;
		
		public static String[] mobs_chance;
		public static String[] mobs_affected;
		public static float[] mobs_affected_chance;
		
		private static String[] mobs_chance_default = new String[] {
			"minecraft:chicken,50.0",
			"minecraft:pig,50.0",
			"minecraft:cow,50.0",
			"minecraft:sheep,50.0",
			"minecraft:mooshroom,50.0",
			"minecraft:villager,25.0"
		};
		
		public static void Init() {
			minAgeTicks = Config.LoadIntProperty("general", "min_age_ticks", "Minium random value to set the animals' age", 6000);
			maxAgeTicks = Config.LoadIntProperty("general", "max_age_ticks", "Maximum random value to set the animals' age\n1200 is one minute to grow up. 24000 is 20 minutes\n", 24000);
			
			mobs_chance = Config.LoadStringListProperty("general", "mobs_chance", "List of mobs that can spawn as baby and chance for them to become baby\nThe format is modid:entityname,percentageChance (e.g. babymobs:zombiechicken,50.0). Get to a new line to add more mobs\n", mobs_chance_default);

			mobs_affected = new String[mobs_chance.length];
			mobs_affected_chance = new float[mobs_chance.length];
			
			for (int i = 0; i < mobs_chance.length; i++) {
				String[] affected_and_chance = mobs_chance[i].split(",");
				mobs_affected[i] = affected_and_chance[0];
				mobs_affected_chance[i] = Float.parseFloat(affected_and_chance[1]) / 100f;
			}
		}
	}
}
