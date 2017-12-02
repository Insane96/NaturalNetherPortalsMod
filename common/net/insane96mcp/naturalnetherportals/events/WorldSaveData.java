package net.insane96mcp.naturalnetherportals.events;

import java.util.ArrayList;

import net.insane96mcp.naturalnetherportals.NaturalNetherPortals;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class WorldSaveData extends WorldSavedData {

	public WorldSaveData() {
		super(IDENTIFIER);
	}
	
	public WorldSaveData(String name) {
		super(name);
	}

	private ArrayList<BlockPos> overworldPortals = new ArrayList<BlockPos>();
	private ArrayList<BlockPos> netherPortals = new ArrayList<BlockPos>();
	
	public ArrayList<BlockPos> getOverworldPortals(){
		return overworldPortals;
	}
	
	public ArrayList<BlockPos> getNetherPortals(){
		return netherPortals;
	}
	
	public void addOverworldPortal(BlockPos pos) {
		if (overworldPortals.add(pos))
			markDirty();
		else
			NaturalNetherPortals.logger.warn("Failed to add portal position to overworld portals");
	}
	
	public void addNetherPortal(BlockPos pos) {
		if (netherPortals.add(pos))
			markDirty();
		else
			NaturalNetherPortals.logger.warn("Failed to add portal position to nether portals");
	}

	private static final String IDENTIFIER = "naturalnetherportals";
	
	public static WorldSaveData get(World world) {
		WorldSaveData data = (WorldSaveData)world.loadItemData(WorldSaveData.class, IDENTIFIER);
		if (data == null) {
			data = new WorldSaveData();
			world.setItemData(IDENTIFIER, data);
		}
		return data;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		readOverworldNBT(nbt);
		readNetherNBT(nbt);
	}
	
	private void readOverworldNBT(NBTTagCompound nbt) {
		NBTTagList tagList = nbt.getTagList("overworld_portals", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {
			overworldPortals = new ArrayList<BlockPos>();
			int x = tagList.getCompoundTagAt(i).getInteger("x");
			int y = tagList.getCompoundTagAt(i).getInteger("y");
			int z = tagList.getCompoundTagAt(i).getInteger("z");
			BlockPos pos = new BlockPos(x, y, z);
			overworldPortals.add(pos);
		}
	}
	
	private void readNetherNBT(NBTTagCompound nbt) {
		NBTTagList tagList = nbt.getTagList("nether_portals", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {
			netherPortals = new ArrayList<BlockPos>();
			int x = tagList.getCompoundTagAt(i).getInteger("x");
			int y = tagList.getCompoundTagAt(i).getInteger("y");
			int z = tagList.getCompoundTagAt(i).getInteger("z");
			BlockPos pos = new BlockPos(x, y, z);
			netherPortals.add(pos);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		writeOverworldNBT(nbt);
		return nbt;
	}
	
	private void writeOverworldNBT(NBTTagCompound nbt) {
		NBTTagCompound pos;
		NBTTagList tagList = nbt.getTagList("overworld_portals", Constants.NBT.TAG_COMPOUND);
		for (BlockPos blockPos : overworldPortals) {
			pos = new NBTTagCompound();
			pos.setInteger("x", blockPos.getX());
			pos.setInteger("y", blockPos.getY());
			pos.setInteger("z", blockPos.getZ());
			tagList.appendTag(pos);
		}
		nbt.setTag("overworld_portals", tagList);
	}
	
	private void writeNetherNBT(NBTTagCompound nbt) {
		NBTTagCompound pos;
		NBTTagList tagList = nbt.getTagList("nether_portals", Constants.NBT.TAG_COMPOUND);
		for (BlockPos blockPos : netherPortals) {
			pos = new NBTTagCompound();
			pos.setInteger("x", blockPos.getX());
			pos.setInteger("y", blockPos.getY());
			pos.setInteger("z", blockPos.getZ());
			tagList.appendTag(pos);
		}
		nbt.setTag("nether_portals", tagList);
	}
}