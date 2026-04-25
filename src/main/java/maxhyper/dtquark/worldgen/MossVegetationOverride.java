package maxhyper.dtquark.worldgen;

import com.mojang.logging.LogUtils;
import maxhyper.dtquark.DTQuarkConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import org.slf4j.Logger;

import java.lang.reflect.Field;

// Swaps minecraft:moss_vegetation to spawn dtquark:azalea_sapling when
// DTQuarkConfig.REPLACE_VANILLA_AZALEA_WITH_SAPLING is true; no-op otherwise.
// Records are immutable, so we mutate the live Holder.Reference value field.
public final class MossVegetationOverride {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceKey<ConfiguredFeature<?, ?>> MOSS_VEGETATION_KEY =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    ResourceLocation.parse("minecraft:moss_vegetation")
            );
    private static final ResourceLocation DTQUARK_AZALEA_SAPLING_ID =
            ResourceLocation.fromNamespaceAndPath("dtquark", "azalea_sapling");

    // Vanilla weights: azalea 7 + flowering_azalea 4 = 11 (folded into sapling), then
    // moss_carpet 25, short_grass 50, tall_grass 10. Total preserved so bonemeal rate matches.
    private static final int SAPLING_WEIGHT = 11;
    private static final int MOSS_CARPET_WEIGHT = 25;
    private static final int SHORT_GRASS_WEIGHT = 50;
    private static final int TALL_GRASS_WEIGHT = 10;

    private static Field holderRefValueField;

    private MossVegetationOverride() {}

    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        if (!DTQuarkConfig.REPLACE_VANILLA_AZALEA_WITH_SAPLING.get()) {
            return;
        }

        Registry<ConfiguredFeature<?, ?>> registry = event.getServer()
                .registryAccess()
                .registryOrThrow(Registries.CONFIGURED_FEATURE);

        Holder<ConfiguredFeature<?, ?>> holder = registry.getHolder(MOSS_VEGETATION_KEY).orElse(null);
        if (!(holder instanceof Holder.Reference<ConfiguredFeature<?, ?>> reference)) {
            LOGGER.warn("[dtquark] minecraft:moss_vegetation holder is missing or not a Reference; skipping override");
            return;
        }

        BlockState saplingState = BuiltInRegistries.BLOCK.get(DTQUARK_AZALEA_SAPLING_ID).defaultBlockState();
        if (saplingState.isAir()) {
            LOGGER.warn("[dtquark] dtquark:azalea_sapling is not registered; skipping moss_vegetation override");
            return;
        }

        SimpleWeightedRandomList.Builder<BlockState> entries = SimpleWeightedRandomList.builder();
        entries.add(saplingState, SAPLING_WEIGHT);
        entries.add(Blocks.MOSS_CARPET.defaultBlockState(), MOSS_CARPET_WEIGHT);
        entries.add(Blocks.SHORT_GRASS.defaultBlockState(), SHORT_GRASS_WEIGHT);
        entries.add(
                Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER),
                TALL_GRASS_WEIGHT
        );

        ConfiguredFeature<?, ?> override = new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new WeightedStateProvider(entries.build()))
        );

        try {
            if (holderRefValueField == null) {
                holderRefValueField = Holder.Reference.class.getDeclaredField("value");
                holderRefValueField.setAccessible(true);
            }
            holderRefValueField.set(reference, override);
            LOGGER.info("[dtquark] minecraft:moss_vegetation overridden — bushes will spawn as dtquark:azalea_sapling");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error("[dtquark] Failed to override minecraft:moss_vegetation via reflection", e);
        }
    }
}
