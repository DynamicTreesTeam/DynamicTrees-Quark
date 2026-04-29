package maxhyper.dtquark;

import com.dtteam.dynamictrees.api.cell.CellKit;
import com.dtteam.dynamictrees.api.registry.Registry;
import com.dtteam.dynamictrees.event.RegistryEvent;
import com.dtteam.dynamictrees.event.TypeRegistryEvent;
import com.dtteam.dynamictrees.block.leaves.LeavesProperties;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.block.CommonVoxelShapes;
import maxhyper.dtquark.cell.DTQuarkCellKits;
import maxhyper.dtquark.growthlogic.DTQuarkGrowthLogicKits;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DTQuarkRegistries {

    public static final VoxelShape SHROOM_AGE0 = Shapes.create(0, 0, 0, 1, 0.75, 1);
    public static final VoxelShape MUSHROOM_CAP_SHORT_ROUND = Block.box(5D, 3D, 5D, 11D, 7D, 11D);
    public static final VoxelShape ROUND_SHORT_MUSHROOM = Shapes.or(CommonVoxelShapes.SAPLING_TRUNK, MUSHROOM_CAP_SHORT_ROUND);

    public static void setup() {
        CommonVoxelShapes.SHAPES.put(ResourceLocation.fromNamespaceAndPath(DynamicTreesQuark.MOD_ID, "glow_shroom_age0").toString(), SHROOM_AGE0);
        CommonVoxelShapes.SHAPES.put(ResourceLocation.fromNamespaceAndPath(DynamicTreesQuark.MOD_ID, "round_short_mushroom").toString(), ROUND_SHORT_MUSHROOM);
    }

    @SubscribeEvent
    public static void registerLeavesPropertiesTypes(final TypeRegistryEvent<LeavesProperties> event) {
        if (event.isEntryOfType(LeavesProperties.class)) {
            event.registerType(ResourceLocation.fromNamespaceAndPath(DynamicTreesQuark.MOD_ID, "blossom"), BlossomLeavesProperties.TYPE);
        }
    }


    @SubscribeEvent
    public static void registerCellKits(final RegistryEvent<CellKit> event) {
        if (event.isEntryOfType(CellKit.class)) {
            DTQuarkCellKits.register(event.getRegistry());
        }
    }

    @SubscribeEvent
    public static void registerGrowthLogicKits(final RegistryEvent<GrowthLogicKit> event) {
        if (event.isEntryOfType(GrowthLogicKit.class)) {
            DTQuarkGrowthLogicKits.register(event.getRegistry());
        }
    }

}
