package rtg.api.world.deco;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.minecraftforge.event.terraingen.TerrainGen;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.DEAD_BUSH;

import rtg.api.world.IRTGWorld;
import rtg.api.world.biome.IRealisticBiome;

/**
 * @author WhichOnesPink
 */
public class DecoDeadBush extends DecoBase {

    private float strengthFactor;
    private int chance;
    private int loops;

    public DecoDeadBush() {

        super();

        /**
         * Default values.
         * These can be overridden when configuring the Deco object in the realistic biome.
         */
        this.setStrengthFactor(0f); // The higher the value, the more there will be.
        this.setChance(1);
        this.setLoops(1);

        this.addDecoTypes(DecoType.DEAD_BUSH);
    }

    @Override
    public void initConfig() {
        this.config().addProperty(this.config().MAX_Y).set(255);
    }

    @Override
    public void generate(IRealisticBiome biome, IRTGWorld rtgWorld, Random rand, int worldX, int worldZ, float strength, float river, boolean hasPlacedVillageBlocks) {

        if (this.config().ALLOW.get()) {

            if (TerrainGen.decorate(rtgWorld.world(), rand, new BlockPos(worldX, 0, worldZ), DEAD_BUSH)) {

                WorldGenerator worldGenerator = new WorldGenDeadBush();

                int loopCount = this.loops;
                loopCount = (this.strengthFactor > 0f) ? (int) (this.strengthFactor * strength) : loopCount;
                for (int i = 0; i < loopCount; i++) {
                    int intX = worldX + rand.nextInt(16);// + 8;
                    int intY = rand.nextInt(this.config().MAX_Y.get());
                    int intZ = worldZ + rand.nextInt(16);// + 8;

                    if (intY <= this.config().MAX_Y.get() && rand.nextInt(this.chance) == 0) {
                        worldGenerator.generate(rtgWorld.world(), rand, new BlockPos(intX, intY, intZ));
                    }
                }
            }
        }
    }

    public float getStrengthFactor() {

        return strengthFactor;
    }

    public DecoDeadBush setStrengthFactor(float strengthFactor) {

        this.strengthFactor = strengthFactor;
        return this;
    }

    public int getChance() {

        return chance;
    }

    public DecoDeadBush setChance(int chance) {

        this.chance = chance;
        return this;
    }

    public int getLoops() {

        return loops;
    }

    public DecoDeadBush setLoops(int loops) {

        this.loops = loops;
        return this;
    }
}