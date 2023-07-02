package maxhyper.dtquark;

import com.ferreusveritas.dynamictrees.api.GatherDataHelper;
import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.block.rooty.SoilProperties;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import vazkii.quark.base.module.config.type.CompoundBiomeConfig;
import vazkii.quark.content.world.config.BlossomTreeConfig;
import vazkii.quark.content.world.module.BlossomTreesModule;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DynamicTreesQuark.MOD_ID)
public class DynamicTreesQuark {
    public static final String MOD_ID = "dtquark";

    public DynamicTreesQuark() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);

        MinecraftForge.EVENT_BUS.register(this);

        RegistryHandler.setup(MOD_ID);
    }

    private void clientSetup(final FMLClientSetupEvent event) {

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        DTQuarkRegistries.setup();

        for (BlossomTreeConfig config : BlossomTreesModule.trees.values()) {
            config.biomeConfig = CompoundBiomeConfig.fromBiomeTags(false);
        }
    }

    private void gatherData(final GatherDataEvent event) {
        GatherDataHelper.gatherAllData(MOD_ID, event,
                SoilProperties.REGISTRY,
                Family.REGISTRY,
                Species.REGISTRY,
                LeavesProperties.REGISTRY);
    }

    public static ResourceLocation location(final String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
