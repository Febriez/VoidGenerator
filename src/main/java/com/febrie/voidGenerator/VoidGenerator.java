package com.febrie.voidGenerator;

import java.util.List;
import java.util.Random;
import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class VoidGenerator extends JavaPlugin {

    private static final VoidChunkGenerator GENERATOR = new VoidChunkGenerator();

    @Override
    public @NotNull ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
        return GENERATOR;
    }

    private static final class VoidChunkGenerator extends ChunkGenerator {

        private static final VoidBiomeProvider BIOME_PROVIDER = new VoidBiomeProvider();

        // --- Generation phases ---

        @Override
        public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random,
                int chunkX, int chunkZ, @NotNull ChunkData chunkData) {}

        @Override
        public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random,
                int chunkX, int chunkZ, @NotNull ChunkData chunkData) {}

        @Override
        public void generateBedrock(@NotNull WorldInfo worldInfo, @NotNull Random random,
                int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
            if (chunkX == 0 && chunkZ == 0) {
                chunkData.setBlock(0, 63, 0, Material.BEDROCK);
            }
        }

        @Override
        public void generateCaves(@NotNull WorldInfo worldInfo, @NotNull Random random,
                int chunkX, int chunkZ, @NotNull ChunkData chunkData) {}

        // --- Vanilla generation toggles: all disabled ---

        @Override public boolean shouldGenerateNoise() { return false; }
        @Override public boolean shouldGenerateSurface() { return false; }
        @Override public boolean shouldGenerateCaves() { return false; }
        @Override public boolean shouldGenerateDecorations() { return false; }
        @Override public boolean shouldGenerateMobs() { return false; }
        @Override public boolean shouldGenerateStructures() { return false; }

        // --- Heightmap: short-circuit to minimum ---

        @Override
        public int getBaseHeight(@NotNull WorldInfo worldInfo, @NotNull Random random,
                int x, int z, @NotNull HeightMap heightMap) {
            return worldInfo.getMinHeight();
        }

        // --- Spawn handling ---

        @Override
        public boolean canSpawn(@NotNull World world, int x, int z) { return true; }

        @Override
        public @NotNull Location getFixedSpawnLocation(@NotNull World world, @NotNull Random random) {
            return new Location(world, 0.5, 64, 0.5);
        }

        // --- Biome provider ---

        @Override
        public @NotNull BiomeProvider getDefaultBiomeProvider(@NotNull WorldInfo worldInfo) {
            return BIOME_PROVIDER;
        }

        // --- Populators: empty, no allocation ---

        @Override
        public @NotNull List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
            return List.of();
        }
    }

    private static final class VoidBiomeProvider extends BiomeProvider {

        private static final List<Biome> BIOMES = List.of(Biome.THE_VOID);

        @Override
        public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
            return Biome.THE_VOID;
        }

        @Override
        public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
            return BIOMES;
        }
    }
}
