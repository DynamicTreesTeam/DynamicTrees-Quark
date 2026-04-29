package maxhyper.dtquark;

import com.dtteam.dynamictrees.block.fruit.Fruit;
import com.dtteam.dynamictrees.block.leaves.LeavesProperties;
import com.dtteam.dynamictrees.block.soil.SoilProperties;
import com.dtteam.dynamictrees.data.GatherDataHelper;
import com.dtteam.dynamictrees.event.RegistryEvent;
import com.dtteam.dynamictrees.event.TypeRegistryEvent;
import com.dtteam.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.dtteam.dynamictrees.api.worldgen.FeatureCanceller;
import com.dtteam.dynamictrees.tree.family.Family;
import com.dtteam.dynamictrees.tree.species.Species;
import com.dtteam.dynamictrees.worldgen.featurecancellation.MushroomFeatureCanceller;
import com.dtteam.dynamictreesplus.block.mushroom.CapProperties;
import maxhyper.dtquark.mushroom.GlowShroomCapProperties;
import maxhyper.dtquark.mushroom.GlowShroomSpecies;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class PlusRegistries {

    public static final FeatureCanceller MUSHROOM_CANCELLER = new MushroomFeatureCanceller<>(DynamicTreesQuark.location("mushroom"), null){
        @Override
        public boolean shouldCancel(ConfiguredFeature<?, ?> configuredFeature, BiomePropertySelectors.NormalFeatureCancellation featureCancellations) {
            final ResourceLocation featureName = BuiltInRegistries.FEATURE.getKey(configuredFeature.feature());
            if (featureName == null) {
                return false;
            }
            return featureCancellations.shouldCancelNamespace(featureName.getNamespace()) && featureName.getPath().equals("glow_shrooms");
        }
    };

    @SubscribeEvent
    public static void onFeatureCancellerRegistry(final RegistryEvent<FeatureCanceller> event) {
        if (event.isEntryOfType(FeatureCanceller.class)) {
            event.getRegistry().registerAll(MUSHROOM_CANCELLER);
        }
    }

    @SubscribeEvent
    public static void registerCapPropertiesType(final TypeRegistryEvent<CapProperties> event) {
        if (event.isEntryOfType(CapProperties.class)) {
            event.registerType(DynamicTreesQuark.location("glow_shroom"), GlowShroomCapProperties.TYPE);
        }
    }

    @SubscribeEvent
    public static void registerSpeciesType(final TypeRegistryEvent<Species> event) {
        if (event.isEntryOfType(Species.class)) {
            event.registerType(DynamicTreesQuark.location("glow_shroom"), GlowShroomSpecies.TYPE);
        }
    }

    public static void gatherAllData(GatherDataEvent event) {
        GatherDataHelper.gatherAllData(DynamicTreesQuark.MOD_ID, event,
                SoilProperties.REGISTRY,
                Family.REGISTRY,
                Species.REGISTRY,
                Fruit.REGISTRY,
                LeavesProperties.REGISTRY,
                CapProperties.REGISTRY);
    }


}
