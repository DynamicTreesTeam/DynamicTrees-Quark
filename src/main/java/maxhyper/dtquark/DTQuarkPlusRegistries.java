package maxhyper.dtquark;

import com.dtteam.dynamictrees.event.TypeRegistryEvent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;



public class DTQuarkPlusRegistries {

    @SubscribeEvent
    public static void registerCapPropertiesTypes(final TypeRegistryEvent<com.dtteam.dynamictreesplus.block.mushroom.CapProperties> event) {
        if (event.isEntryOfType(com.dtteam.dynamictreesplus.block.mushroom.CapProperties.class)) {
            event.registerType(ResourceLocation.fromNamespaceAndPath(DynamicTreesQuark.MOD_ID, "glow_shroom"), maxhyper.dtquark.mushroom.GlowShroomCapProperties.TYPE);
        }
    }

    @SubscribeEvent
    public static void registerSpeciesTypes(final TypeRegistryEvent<com.dtteam.dynamictrees.tree.species.Species> event) {
        if (event.isEntryOfType(com.dtteam.dynamictrees.tree.species.Species.class)) {
            event.registerType(ResourceLocation.fromNamespaceAndPath(DynamicTreesQuark.MOD_ID, "glow_shroom"), maxhyper.dtquark.mushroom.GlowShroomSpecies.TYPE);
        }
    }

}
