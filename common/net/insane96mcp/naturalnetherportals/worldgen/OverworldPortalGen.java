package net.insane96mcp.naturalnetherportals.worldgen;

import java.util.Random;

import net.insane96mcp.naturalnetherportals.lib.Properties;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OverworldPortalGen implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (random.nextInt(Properties.Overworld.chance) != 0)
			return;
		
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		BlockPos chunkPos = new BlockPos(blockX, 0, blockZ);
		
		int dimension = world.provider.getDimension();
		if (dimension != 0)
			return;
		
		int randX = random.nextInt(16);
		int randZ = random.nextInt(16);
		
		chunkPos = chunkPos.add(randX, 0, randZ);
		
		int y = GetGroundFromAbove(world, chunkPos);
		
		if (y <= Properties.Overworld.minY)
			return;

		chunkPos = chunkPos.add(0, y, 0);
		
		GeneratePortal(world, random, chunkPos);
	}
	
	private static float portalDecay = Properties.Overworld.portalDecay / 100f;
	
	static boolean isFull = false;
	
	public static void GeneratePortal(World world, Random rand, BlockPos pos) {
		int width = rand.nextInt(Properties.Overworld.maxWidth + 1 - Properties.Overworld.minWidth) + Properties.Overworld.minWidth;
		int height = rand.nextInt(Properties.Overworld.maxHeight + 1 - Properties.Overworld.minHeight) + Properties.Overworld.minHeight;
		
		isFull = rand.nextFloat() < Properties.Overworld.fullPortalChance / 100f;

		if (rand.nextBoolean()) {			
			for (int l = 0; l < width; l++) {
				TrySetBlock(l - width / 2, 0, 0, rand, world, pos);
				TrySetBlock(l - width / 2, height - 1, 0, rand, world, pos);
			}
			for (int h = 1; h < height - 1; h++) {
				TrySetBlock(-width / 2, h, 0, rand, world, pos);
				TrySetBlock((int) ((width / 2f) - .5f), h, 0, rand, world, pos);
			}
		}
		else {
			for (int l = 0; l < width; l++) {
				TrySetBlock(0, 0, l - width / 2, rand, world, pos);
				TrySetBlock(0, height - 1, l - width / 2, rand, world, pos);
			}
			for (int h = 1; h < height - 1; h++) {
				TrySetBlock(0, h, -width / 2, rand, world, pos);
				TrySetBlock(0, h, (int) ((width / 2f) - .5f), rand, world, pos);
			}
		}
		
		if (isFull && rand.nextFloat() < Properties.Overworld.litPortalChance / 100f)
			world.setBlockState(pos.add(0, 1, 0), Blocks.FIRE.getDefaultState());
		
		for (int i = 0; i < 4; i++) {
			for(int h = 0; h < 3; h++) {
				NetherrackCircle(world, pos.add(0, h, 0), i + 1 - h);
				if (h != 0)
					NetherrackCircle(world, pos.add(0, -h, 0), i + 1 - h);
			}
		}
	}
	
	private static int GetGroundFromAbove(World world, BlockPos pos)
	{
		int y = Properties.Overworld.maxY;
		boolean foundGround = false;
		while(!foundGround && y-- >= Properties.Overworld.minY)
		{
			Block blockAt = world.getBlockState(pos.add(0, y, 0)).getBlock();
			Block blockUp = world.getBlockState(pos.add(0, y + 1, 0)).getBlock();
			foundGround = blockAt.isBlockNormalCube(blockAt.getDefaultState()) 
					&& blockAt != Blocks.LEAVES && blockAt != Blocks.LEAVES2
					&& !blockUp.isBlockNormalCube(blockUp.getDefaultState())
					&& blockUp != Blocks.WATER;
			
		}

		return y;
	}
	
	private static void TrySetBlock(int x, int y, int z, Random rand, World world, BlockPos pos) {
		if (rand.nextFloat() < portalDecay || isFull)
			world.setBlockState(pos.add(x, y, z), Blocks.OBSIDIAN.getDefaultState());
	}
	
	private static void NetherrackCircle(World world, BlockPos pos, int radius) {
		int radiusLoop = radius * 12;
		for(int i = 0; i < radiusLoop; i++){
			float angle = i * 360 / radiusLoop;
			BlockPos circlePos = pos.add(radius * Math.cos(angle) + .5f, 0, radius * Math.sin(angle) + .5f);
			Block actualBlock = world.getBlockState(circlePos).getBlock();
			
			float actualChance;
			if (radius - 1 <= 0)
				actualChance = 1.0f;
			else
				actualChance = Properties.Overworld.netherrackChance / 100f / (radius - 1);
			if (actualBlock.isBlockNormalCube(actualBlock.getDefaultState()) 
					&& actualBlock != Blocks.OBSIDIAN 
					&& world.rand.nextFloat() < actualChance) {
				if (actualBlock.getFlammability(world, circlePos, EnumFacing.random(world.rand)) > 0) {
					if (Properties.Overworld.burnBurnable)
						world.setBlockState(circlePos, Blocks.FIRE.getDefaultState());
					else
						world.setBlockToAir(circlePos);
				}
				else
					world.setBlockState(circlePos, Blocks.NETHERRACK.getDefaultState());
				
				if (world.getBlockState(circlePos.up()).equals(Blocks.AIR.getDefaultState()) && world.rand.nextFloat() < Properties.Overworld.fireChance / 100f)
					world.setBlockState(circlePos.up(), Blocks.FIRE.getDefaultState());
			}
		}
	}
}
