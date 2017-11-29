package net.insane96mcp.naturalnetherportals.worldgen;

import java.util.Random;

import net.insane96mcp.naturalnetherportals.lib.Properties;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class NetherPortalGen implements IWorldGenerator{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (random.nextInt(Properties.Nether.chance) != 0)
			return;
		
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		BlockPos chunkPos = new BlockPos(blockX, 0, blockZ);
		
		int dimension = world.provider.getDimension();
		if (dimension != -1)
			return;
		
		int randX = random.nextInt(16);
		int randZ = random.nextInt(16);
		
		chunkPos = chunkPos.add(randX, 0, randZ);
		
		int y = GetGroundFromAbove(world, chunkPos);
		
		if (y < Properties.Nether.minY)
			return;

		chunkPos = chunkPos.add(0, y, 0);
		
		GeneratePortal(world, random, chunkPos);
	}
	
	private static float portalDecay = Properties.Nether.portalDecay / 100f;
	
	static boolean isFull = false;
	
	public static void GeneratePortal(World world, Random rand, BlockPos pos) {
		int width = rand.nextInt(Properties.Nether.maxWidth + 1 - Properties.Nether.minWidth) + Properties.Nether.minWidth;
		int height = rand.nextInt(Properties.Nether.maxHeight + 1 - Properties.Nether.minHeight) + Properties.Nether.minHeight;
		
		isFull = rand.nextFloat() < Properties.Nether.fullPortalChance / 100f;
		
		if (rand.nextBoolean()) {			
			for (int l = 0; l < width; l++) {
				TrySetBlock(l - width / 2, 0, 0, rand, world, pos);
				TrySetBlock(l - width / 2, height - 1, 0, rand, world, pos);
			}
			for (int h = 1; h < height - 1; h++) {
				TrySetBlock(-width / 2, h, 0, rand, world, pos);
				TrySetBlock(width / 2 - 1, h, 0, rand, world, pos);
			}
		}
		else {
			for (int l = 0; l < width; l++) {
				TrySetBlock(0, 0, l - width / 2, rand, world, pos);
				TrySetBlock(0, height - 1, l - width / 2, rand, world, pos);
			}
			for (int h = 1; h < height - 1; h++) {
				TrySetBlock(0, h, -(width / 2), rand, world, pos);
				TrySetBlock(0, h, width / 2 - 1, rand, world, pos);
			}
		}
		System.out.println(pos.getX() + " " + pos.getY() + " " + pos.getZ() + " isFull: " + isFull);
		
		if (isFull && rand.nextFloat() < Properties.Nether.litPortalChance / 100f)
			world.setBlockState(pos.add(0, 1, 0), Blocks.FIRE.getDefaultState());

		
		for(int h = 0; h < 3; h++) {
			TryPlaceFire(world, pos.add(0, h, 0), 4 - h);
			if (h != 0)
				TryPlaceFire(world, pos.add(0, -h, 0), 4 - h);
		}
	}
	
	private static void TrySetBlock(int x, int y, int z, Random rand, World world, BlockPos pos) {
		if (rand.nextFloat() < portalDecay || isFull)
			world.setBlockState(pos.add(x, y, z), Blocks.OBSIDIAN.getDefaultState());
	}
	
	private static int GetGroundFromAbove(World world, BlockPos pos)
	{
		int y = Properties.Nether.maxY;
		boolean foundGround = false;
		while(!foundGround && y-- >= Properties.Nether.minY)
		{
			Block blockAt = world.getBlockState(pos.add(0, y, 0)).getBlock();
			Block blockUp = world.getBlockState(pos.add(0, y + 1, 0)).getBlock();
			foundGround = (blockAt == Blocks.NETHERRACK || blockAt == Blocks.SOUL_SAND) && blockUp == Blocks.AIR;
		}

		return y;
	}
	
	public static void TryPlaceFire(World world, BlockPos pos, int radius) {
		while(radius > 0) {
			int radiusLoop = radius * 12;
			for(int i = 0; i < radiusLoop; i++){
				float angle = i * 360 / radiusLoop;
				BlockPos circlePos = pos.add(radius * Math.cos(angle) + .5f, 0, radius * Math.sin(angle) + .5f);
				Block upBlock = world.getBlockState(circlePos.up()).getBlock();
				if (upBlock == Blocks.AIR && world.rand.nextFloat() < Properties.Nether.fireChance / 100f / radius)
					world.setBlockState(circlePos.up(), Blocks.FIRE.getDefaultState());
			}
		
			radius--;
		}
	}
}
