package maxhyper.dtquark;

import com.dtteam.dynamictrees.event.TypeRegistryEvent;
import com.dtteam.dynamictrees.tree.species.Species;
import com.dtteam.dynamictreesplus.block.mushroom.CapProperties;
import maxhyper.dtquark.mushroom.GlowShroomCapProperties;
import maxhyper.dtquark.mushroom.GlowShroomSpecies;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;



public class DTQuarkPlusRegistries {

    @SubscribeEvent
    public static void registerCapPropertiesTypes(final TypeRegistryEvent<CapProperties> event) {
        if (event.isEntryOfType(CapProperties.class)) {
            event.registerType(ResourceLocation.fromNamespaceAndPath(DynamicTreesQuark.MOD_ID, "glow_shroom"), GlowShroomCapProperties.TYPE);
        }
    }

    @SubscribeEvent
    public static void registerSpeciesTypes(final TypeRegistryEvent<Species> event) {
        if (event.isEntryOfType(Species.class)) {
            event.registerType(ResourceLocation.fromNamespaceAndPath(DynamicTreesQuark.MOD_ID, "glow_shroom"), GlowShroomSpecies.TYPE);
        }
    }

}
