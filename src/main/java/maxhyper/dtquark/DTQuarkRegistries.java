package maxhyper.dtquark;

import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.ferreusveritas.dynamictrees.api.worldgen.FeatureCanceller;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.util.CommonVoxelShapes;
import com.ferreusveritas.dynamictrees.worldgen.featurecancellation.MushroomFeatureCanceller;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTQuarkRegistries {

    public static final VoxelShape SHROOM_AGE0 = Shapes.create(0, 0, 0, 1, 0.75, 1);
    public static final VoxelShape MUSHROOM_CAP_SHORT_ROUND = Block.box(5D, 3D, 5D, 11D, 7D, 11D);
    public static final VoxelShape ROUND_SHORT_MUSHROOM = Shapes.or(CommonVoxelShapes.MUSHROOM_STEM, MUSHROOM_CAP_SHORT_ROUND);

    @SubscribeEvent
    public static void registerLeavesPropertiesTypes(final TypeRegistryEvent<LeavesProperties> event) {
        event.registerType(new ResourceLocation(DynamicTreesQuark.MOD_ID, "blossom"), BlossomLeavesProperties.TYPE);
    }

    public static void setup() {
        CommonVoxelShapes.SHAPES.put(DynamicTreesQuark.MOD_ID + ":glow_shroom_age0", SHROOM_AGE0);
        CommonVoxelShapes.SHAPES.put(DynamicTreesQuark.MOD_ID + ":round_short_mushroom", ROUND_SHORT_MUSHROOM);
    }

    public static final FeatureCanceller MUSHROOM_CANCELLER = new MushroomFeatureCanceller<>(DynamicTreesQuark.location("mushroom"), HugeMushroomFeatureConfiguration.class) {
        @Override
        public boolean shouldCancel(ConfiguredFeature<?, ?> configuredFeature, @Nonnull BiomePropertySelectors.NormalFeatureCancellation featureCancellations) {
            final ResourceLocation featureName = ForgeRegistries.FEATURES.getKey(configuredFeature.feature());

            if (featureName == null) return false;

            return featureName.getNamespace().equals("quark") && featureName.getPath().equals("glow_shrooms");
        }
    };

    @SubscribeEvent
    public static void onFeatureCancellerRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<FeatureCanceller> event) {

        event.getRegistry().registerAll(MUSHROOM_CANCELLER);
    }

}
