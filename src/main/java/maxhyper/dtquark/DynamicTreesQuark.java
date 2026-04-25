package maxhyper.dtquark;

import com.dtteam.dynamictrees.data.GatherDataHelper;
import com.dtteam.dynamictrees.api.registry.RegistryHandler;
import com.dtteam.dynamictrees.block.leaves.LeavesProperties;
import com.dtteam.dynamictrees.block.soil.SoilProperties;
import com.dtteam.dynamictrees.block.fruit.Fruit;
import com.dtteam.dynamictrees.tree.family.Family;
import com.dtteam.dynamictrees.tree.species.Species;
import com.dtteam.dynamictreesplus.block.mushroom.CapProperties;
import com.dtteam.dynamictrees.registry.NeoForgeRegistryHandler;
import maxhyper.dtquark.loot.LootModifiers;
import maxhyper.dtquark.worldgen.DTQuarkFeatures;
import maxhyper.dtquark.worldgen.MossVegetationOverride;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.violetmoon.zeta.config.type.CompoundBiomeConfig;
import org.violetmoon.quark.content.world.module.BlossomTreesModule;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DynamicTreesQuark.MOD_ID)
public class DynamicTreesQuark {
    public static final String MOD_ID = "dtquark";

    public DynamicTreesQuark(IEventBus modEventBus, ModContainer container) {
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);

        if (ModList.get().isLoaded("dynamictreesplus")){
            modEventBus.register(DTQuarkPlusRegistries.class);
        }


        LootModifiers.register(modEventBus);
        DTQuarkFeatures.register(modEventBus);

        NeoForgeRegistryHandler.setup(MOD_ID, modEventBus);
        DTQuarkRegistries.setup();

        // Server config + moss_vegetation override listener.
        container.registerConfig(ModConfig.Type.SERVER, DTQuarkConfig.SERVER_SPEC);
        NeoForge.EVENT_BUS.register(MossVegetationOverride.class);
    }

    private void clientSetup(final FMLClientSetupEvent event) {

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Disable Quark's native blossom spawning so dtquark trees don't compete.
        for (BlossomTreesModule.BlossomTree tree : BlossomTreesModule.blossomTrees) {
            tree.quarkConfig.biomeConfig = CompoundBiomeConfig.fromBiomeTags(false);
        }
    }
    

    private void gatherData(final GatherDataEvent event) {
        if (ModList.get().isLoaded("dynamictreesplus")) {
            GatherDataHelper.gatherAllData(MOD_ID, event,
                    SoilProperties.REGISTRY,
                    Family.REGISTRY,
                    Species.REGISTRY,
                    Fruit.REGISTRY,
                    LeavesProperties.REGISTRY,
                    CapProperties.REGISTRY);
        } else {
            GatherDataHelper.gatherAllData(MOD_ID, event,
                    SoilProperties.REGISTRY,
                    Family.REGISTRY,
                    Species.REGISTRY,
                    Fruit.REGISTRY,
                    LeavesProperties.REGISTRY);
        }
    }

    public static ResourceLocation location(final String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}
