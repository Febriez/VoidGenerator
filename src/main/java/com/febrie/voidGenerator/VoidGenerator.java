package com.febrie.voidGenerator;

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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public final class VoidGenerator extends JavaPlugin {

    @Contract("_, _ -> new")
    @Override
    public @NotNull ChunkGenerator getDefaultWorldGenerator(@Nullable String worldName, String id) {
        return new VoidChunkGenerator(!"nobedrock".equalsIgnoreCase(id));
    }

    private static final class VoidChunkGenerator extends ChunkGenerator {

        private static final VoidBiomeProvider BIOME_PROVIDER = new VoidBiomeProvider();

        private final boolean bedrock;

        public VoidChunkGenerator(boolean bedrock) {
            this.bedrock = bedrock;
        }

        @Override
        public void generateBedrock(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
            if (!bedrock) return;
            if (chunkX == 0 && chunkZ == 0) chunkData.setBlock(0, 63, 0, Material.BEDROCK);
        }

        @Override
        public int getBaseHeight(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull HeightMap heightMap) {
            return worldInfo.getMinHeight();
        }

        @Override
        public boolean canSpawn(@NotNull World world, int x, int z) {
            return true;
        }

        @Override
        public @NotNull Location getFixedSpawnLocation(@NotNull World world, @NotNull Random random) {
            return new Location(world, 0.5, 64, 0.5);
        }

        @Override
        public @NotNull BiomeProvider getDefaultBiomeProvider(@NotNull WorldInfo worldInfo) {
            return BIOME_PROVIDER;
        }

        @Override
        public @NotNull List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
            return List.of();
        }
    }

    private static final class VoidBiomeProvider extends BiomeProvider {

        private static final List<Biome> BIOMES = List.of(Biome.PLAINS);

        @Override
        public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
            return Biome.PLAINS;
        }

        @Override
        public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
            return BIOMES;
        }
    }
}
