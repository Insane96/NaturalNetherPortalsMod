package net.insane96mcp.naturalnetherportals.worldgen;

import java.util.Random;

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
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		BlockPos chunkPos = new BlockPos(blockX, 0, blockZ);
		
		if (world.provider.getDimension() != -1)
			return;

		int randX = random.nextInt(16);
		int randZ = random.nextInt(16);
		
		int y = GetGroundFromAbove(world, chunkPos.add(randX, 0, randZ));
		
		if (y < /*Stats.minY*/32) 
			return;
		
		GeneratePortal(world, random, chunkPos.add(0, y, 0));
	}
	
	public static void GeneratePortal(World world, Random rand, BlockPos pos) {
		if (rand.nextBoolean()) {
			for (int l = 0; l < 4/*Stats.width*/; l++) {
				world.setBlockState(pos.add(l - 4/*Stats.width*/ / 2, 0, 0), Blocks.OBSIDIAN.getDefaultState());
				world.setBlockState(pos.add(l - 4/*Stats.width*/ / 2, 5/*stats.height*/ - 1, 0), Blocks.OBSIDIAN.getDefaultState());
			}
			for (int h = 0; h < 5/*Stats.height*/; h++) {
				world.setBlockState(pos.add(-(4/*Stats.width*/ / 2) + 1, h, 0), Blocks.OBSIDIAN.getDefaultState());
				world.setBlockState(pos.add(4/*Stats.width*/ / 2, h, 0), Blocks.OBSIDIAN.getDefaultState());
			}
		}
		else {
			for (int l = 0; l < 4/*Stats.width*/; l++) {
				world.setBlockState(pos.add(0, 0, l - 4/*Stats.width*/ / 2), Blocks.OBSIDIAN.getDefaultState());
				world.setBlockState(pos.add(0, 5/*stats.height*/ - 1, l - 4/*Stats.width*/ / 2), Blocks.OBSIDIAN.getDefaultState());
			}
			for (int h = 0; h < 5/*Stats.height*/; h++) {
				world.setBlockState(pos.add(0, h, -(4/*Stats.width*/ / 2) + 1), Blocks.OBSIDIAN.getDefaultState());
				world.setBlockState(pos.add(0, h, 4/*Stats.width*/ / 2), Blocks.OBSIDIAN.getDefaultState());
			}
			
		}
	}
	
	private static int GetGroundFromAbove(World world, BlockPos pos)
	{
		int y = /*Stats.maxY*/64;
		boolean foundGround = false;
		while(!foundGround && y-- >= 0)
		{
			Block blockAt = world.getBlockState(pos.add(0, y, 0)).getBlock();
			Block blockUp = world.getBlockState(pos.add(0, y + 1, 0)).getBlock();
			foundGround = (blockAt == Blocks.NETHERRACK || blockAt == Blocks.SOUL_SAND) && blockUp == Blocks.AIR;
		}

		return y;
	}

}
