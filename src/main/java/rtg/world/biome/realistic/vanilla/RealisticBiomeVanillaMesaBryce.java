package rtg.world.biome.realistic.vanilla;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import rtg.api.biome.BiomeConfig;
import rtg.util.BlockUtil;
import rtg.util.CellNoise;
import rtg.util.OpenSimplexNoise;
import rtg.world.biome.deco.DecoBoulder;
import rtg.world.biome.deco.DecoCactus;
import rtg.world.biome.deco.DecoDeadBush;
import rtg.world.biome.deco.DecoShrub;
import rtg.world.biome.deco.collection.DecoCollectionDesertRiver;
import rtg.world.gen.surface.vanilla.SurfaceVanillaMesaBryce;
import rtg.world.gen.terrain.TerrainBase;
import rtg.world.gen.terrain.vanilla.TerrainVanillaMesaBryce;

public class RealisticBiomeVanillaMesaBryce extends RealisticBiomeVanillaBase {

    public static Biome biome = Biomes.MUTATED_MESA;
    public static Biome river = Biomes.RIVER;

    public RealisticBiomeVanillaMesaBryce(BiomeConfig config) {

        super(config, biome, river,
            new TerrainVanillaMesaBryce(false, 55f, 120f, 60f, 40f, 69f),
            new SurfaceVanillaMesaBryce(config, BlockUtil.getStateSand(1), BlockUtil.getStateSand(1), 0)
        );

        this.waterSurfaceLakeChance = 0;
        this.lavaSurfaceLakeChance = 0;

        this.addDecoCollection(new DecoCollectionDesertRiver());

        DecoBoulder decoBoulder = new DecoBoulder();
        decoBoulder.boulderBlock = Blocks.COBBLESTONE.getDefaultState();
        decoBoulder.maxY = 83;
        this.addDeco(decoBoulder);

        DecoShrub decoShrub = new DecoShrub();
        decoShrub.loops = 3;
        decoShrub.maxY = 90;
        addDeco(decoShrub);

        DecoDeadBush decoDeadBush = new DecoDeadBush();
        decoDeadBush.maxY = 100;
        decoDeadBush.loops = 3;
        this.addDeco(decoDeadBush);

        DecoCactus decoCactus = new DecoCactus();
        decoCactus.soilBlock = BlockUtil.getStateSand(1);
        decoCactus.loops = 18;
        decoCactus.maxY = 100;
        this.addDeco(decoCactus);
    }

    @Override
    public TerrainBase initTerrain() {

        return new TerrainBase(69f) {

            @Override
            public float generateNoise(OpenSimplexNoise simplex, CellNoise cell, int x, int y, float border, float river) {

                return terrainBryce(x, y, simplex, river, 20f, border);
            }
        };
    }

    @Override
    public void rReplace(ChunkPrimer primer, int i, int j, int x, int y, int depth, World world, Random rand, OpenSimplexNoise simplex, CellNoise cell, float[] noise, float river, Biome[] base) {

        this.rReplaceRiverSurface(primer, i, j, x, y, depth, world, rand, simplex, cell, noise, river, base);
    }

    @Override
    public int getExtraGoldGenCount() {
        return 20;
    }

    @Override
    public Biome beachBiome() {
        return this.beachBiome(Biomes.BEACH);
    }
}
