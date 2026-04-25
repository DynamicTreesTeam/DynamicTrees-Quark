package maxhyper.dtquark.worldgen;

import maxhyper.dtquark.DynamicTreesQuark;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

// Registers dtquark's custom worldgen features on the mod event bus.
public final class DTQuarkFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(BuiltInRegistries.FEATURE, DynamicTreesQuark.MOD_ID);

    public static final DeferredHolder<Feature<?>, LushCavesAzaleaFeature> LUSH_CAVES_AZALEA =
            FEATURES.register(
                    "lush_caves_azalea",
                    () -> new LushCavesAzaleaFeature(NoneFeatureConfiguration.CODEC)
            );

    private DTQuarkFeatures() {}

    public static void register(IEventBus modEventBus) {
        FEATURES.register(modEventBus);
    }
}
