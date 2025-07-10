package com.febrie.voidGenerator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Random;

public final class VoidGenerator extends JavaPlugin {

    @Override
    public @NotNull ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
        return new VoidChunkGenerator();
    }

    private static class VoidChunkGenerator extends ChunkGenerator {
        @Override
        public void generateBedrock(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
            if (chunkX == 0 && chunkZ == 0) chunkData.setBlock(0, 63, 0, Material.BEDROCK);
        }

        @Override
        public boolean canSpawn(@NotNull World world, int x, int z) {
            return true;
        }

        @Override
        public @Nullable Location getFixedSpawnLocation(@NotNull World world, @NotNull Random random) {
            return new Location(world, 0, 64, 0);
        }

        @Override
        public @Nullable BiomeProvider getDefaultBiomeProvider(@NotNull WorldInfo worldInfo) {
            return new VoidBiomeProvider();
        }
    }

    private static class VoidBiomeProvider extends BiomeProvider {

        public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
            return Biome.PLAINS;
        }

        @Contract(value = "_ -> new", pure = true)
        public @NotNull @Unmodifiable List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
            return List.of(Biome.PLAINS);
        }
    }

}
