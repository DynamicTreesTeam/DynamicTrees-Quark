package maxhyper.dtquark.worldgen;

import com.dtteam.dynamictrees.api.worldgen.GroundFinder;
import com.dtteam.dynamictrees.api.worldgen.LevelContext;
import com.dtteam.dynamictrees.tree.species.Species;
import com.dtteam.dynamictrees.utility.CoordUtils;
import com.dtteam.dynamictrees.worldgen.DynamicTreeGenerationContext;
import com.dtteam.dynamictrees.worldgen.SubterraneanGroundFinder;
import com.mojang.serialization.Codec;
import maxhyper.dtquark.DynamicTreesQuark;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.List;

// Places dtquark:azalea on cave floors in lush_caves, bypassing DT's BiomeDatabase
// populator (which would trigger Species.reset() and break bonemeal globally).
// Returns true on first successful placement; the placed feature rolls count() times.
public class LushCavesAzaleaFeature extends Feature<NoneFeatureConfiguration> {

    public static final String SPECIES_PATH = "azalea";
    public static final ResourceLocation SPECIES_ID =
            ResourceLocation.fromNamespaceAndPath(DynamicTreesQuark.MOD_ID, SPECIES_PATH);

    // Mirror DT 1.6 CaveRootedData default radius.
    private static final int TREE_RADIUS = 1;

    private static final GroundFinder GROUND_FINDER = new SubterraneanGroundFinder();

    public LushCavesAzaleaFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        final WorldGenLevel level = ctx.level();
        final BlockPos origin = ctx.origin();
        final RandomSource random = ctx.random();

        final Species species = Species.REGISTRY.get(SPECIES_ID);
        if (species == null || !species.isValid()) {
            return false;
        }

        // Skip if origin chunk is outside the worldgen region (avoids IllegalStateException).
        if (!level.hasChunk(origin.getX() >> 4, origin.getZ() >> 4)) {
            return false;
        }

        final LevelContext levelContext = LevelContext.create(level);

        // findGround can throw on an unavailable chunk; guard so worldgen never crashes.
        final List<BlockPos> groundPositions;
        try {
            groundPositions = GROUND_FINDER.findGround(level, origin, Heightmap.Types.MOTION_BLOCKING);
        } catch (IllegalStateException unavailable) {
            return false;
        }
        if (groundPositions.isEmpty()) {
            return false;
        }

        final Holder<Biome> biome = level.getBiome(origin);

        for (BlockPos groundPos : groundPositions) {
            // Re-check chunk availability per candidate to catch any cross-chunk drift.
            if (!level.hasChunk(groundPos.getX() >> 4, groundPos.getZ() >> 4)) {
                continue;
            }
            try {
                final BlockState soilState = level.getBlockState(groundPos);
                if (!species.isAcceptableSoilForWorldgen(level, groundPos, soilState)) {
                    continue;
                }

                final DynamicTreeGenerationContext genCtx = new DynamicTreeGenerationContext(
                        levelContext,
                        species,
                        origin,
                        groundPos.mutable(),
                        biome,
                        CoordUtils.getRandomDir(random),
                        TREE_RADIUS,
                        true
                );

                if (species.generate(genCtx)) {
                    return true;
                }
            } catch (IllegalStateException unavailable) {
                // Chunk vanished mid-iteration; skip and continue.
            }
        }

        return false;
    }
}
