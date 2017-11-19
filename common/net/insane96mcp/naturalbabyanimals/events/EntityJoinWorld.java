package net.insane96mcp.naturalbabyanimals.events;

import java.util.Random;

import net.insane96mcp.naturalbabyanimals.lib.Properties;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityJoinWorld {	
	@SubscribeEvent
	public static void EntityJoinWorldEvent(EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();
	
		if (!(entity instanceof EntityAgeable)) 
			return;
		
		EntityAgeable animal = (EntityAgeable)entity;
		
		NBTTagCompound tags = entity.getEntityData();
		byte isAlreadyChecked = tags.getByte("naturalbabyanimals:check");
		
		if (isAlreadyChecked == 1)
			return;
		
		boolean affected = false;
		int index = 0;
		for (int i = 0; i < Properties.General.mobs_affected.length; i++) {
			if (EntityList.getKey(entity).toString().equals(Properties.General.mobs_affected[i])) {
				affected = true;
				index = i;
			}
		}
		if(!affected)
			return;

		tags.setByte("naturalbabyanimals:check", (byte)1);

		if (animal.getGrowingAge() < 0)
			return;
		
		if (event.getWorld().rand.nextFloat() < Properties.General.mobs_affected_chance[index]) {
			Random random = animal.getRNG();
			int age = random.nextInt(Properties.General.maxAgeTicks - Properties.General.minAgeTicks) + Properties.General.minAgeTicks;
			animal.setGrowingAge(-age);
		}
	}

}
