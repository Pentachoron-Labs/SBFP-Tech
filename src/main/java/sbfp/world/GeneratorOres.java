package sbfp.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraftforge.fml.common.IWorldGenerator;
import sbfp.modsbfp;

public final class GeneratorOres implements IWorldGenerator {

    public static final int GENERATOR_WEIGHT = 100; //I dunno what this does, so it's 100 

    @Override
    public void generate(Random r, int cx, int cz, World world, IChunkProvider cg, IChunkProvider cp) {
        // FMLLog.finest("Chunk (%d,%d)",cx,cz);
        cx <<= 4;
        cz <<= 4;
        BlockPos pos;
        if (cg instanceof ChunkProviderGenerate) {
            for (int x = cx; x < cx + 16; x++) {
                for (int z = cz; z < cz + 16; z++) {
                    for (int y = 0; y < 40; y++) {
                        pos = new BlockPos(x, y, z);
                        if (getBlock(world, pos) == Blocks.stone) {
                            if (getBlock(world, x, y + 1, z) == Blocks.water && r.nextInt(32) == 0) {
                                world.setBlockState(pos, modsbfp.blockOre.getStateFromMeta(OreTypes.MONAZITE.getMeta()), 2); // Monazite—pools
                            } else if (r.nextInt(1536) == 0) {
                                world.setBlockState(pos, modsbfp.blockOre.getStateFromMeta(OreTypes.MONAZITE.getMeta()), 2); // Monazite—other
                            } else if (r.nextInt(2560) == 0) {
                                new LargeVeinGenerator(x, y, z, world, r, OreTypes.FLUORITE.getMeta(), 1).generate(); // Fluorite
                            } else if (r.nextInt(1536) == 0) {
                                world.setBlockState(pos, modsbfp.blockOre.getStateFromMeta(OreTypes.MOLYBDENITE.getMeta()), 2); // MoS₂
                                if (r.nextBoolean()) {
                                    int q = r.nextInt(3);
                                    world.setBlockState(new BlockPos(x + (q == 0 ? 1 : 0), y + (q == 1 ? 1 : 0), z + (q == 2 ? 1 : 0)), modsbfp.blockOre.getStateFromMeta(OreTypes.MOLYBDENITE.getMeta()), 2);
                                }
                            } else if (r.nextInt(10240) == 0) {
                                new LargeVeinGenerator(x, y, z, world, r, OreTypes.RUTILE.getMeta(), 2).generate(); // Rutile
                            } else if (r.nextInt(128) == 0 && bordersLava(new BlockPos(x, y, z), world)) {
                                new LargeVeinGenerator(x, y, z, world, r, OreTypes.CINNABAR.getMeta(), 1.5).generate(); // Cinnabar—lava
                                // pools
                            } else if (r.nextInt(2560) == 0) {
                                new LargeVeinGenerator(x, y, z, world, r, OreTypes.CINNABAR.getMeta(), 1.5).generate(); // Cinnabar—other
                            } else if (r.nextInt(5120) == 0) {
                                new LargeVeinGenerator(x, y, z, world, r, OreTypes.ARSENOPYRITE.getMeta(), 1.5).generate(); // Arsenopyrite
                            }
                        }
                    }
                    for (int y = 40; y < 60; y++) {
                        if (getBlock(world, x, y, z) == Blocks.stone) {
                            if (r.nextInt(5120) == 0) {
                                new LargeVeinGenerator(x, y, z, world, r, OreTypes.LIMONITE.getMeta(), 1, true).generate(); // Limonite
                            } else if (r.nextInt(5120) == 0) {
                                new LargeVeinGenerator(x, y, z, world, r, OreTypes.PYROLUSITE.getMeta(), 1, true).generate(); // Pyrolusite
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean bordersLava(BlockPos pos, World world) {
        return (getBlock(world, pos.up()) == Blocks.lava) || (getBlock(world, pos.down()) == Blocks.lava) || (getBlock(world, pos.down(2)) == Blocks.lava) 
                || (getBlock(world, pos.east()) == Blocks.lava) || (getBlock(world, pos.west()) == Blocks.lava)
                || (getBlock(world, pos.north()) == Blocks.lava) || (getBlock(world, pos.south()) == Blocks.lava);
    }

    protected static Block getBlock(World w, BlockPos pos) {
        return w.getBlockState(pos).getBlock();
    }

    protected static Block getBlock(World w, int x, int y, int z) {
        return getBlock(w, new BlockPos(x, y, z));
    }
}
