package maxhyper.dtquark;

import com.ferreusveritas.dynamictrees.api.cell.CellKit;
import com.ferreusveritas.dynamictrees.api.registry.RegistryEvent;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.util.CommonVoxelShapes;
import maxhyper.dtquark.cell.DTQuarkCellKits;
import maxhyper.dtquark.growthlogic.DTQuarkGrowthLogicKits;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTQuarkRegistries {

    public static final VoxelShape SHROOM_AGE0 = Shapes.create(0, 0, 0, 1, 0.75, 1);
    public static final VoxelShape MUSHROOM_CAP_SHORT_ROUND = Block.box(5D, 3D, 5D, 11D, 7D, 11D);
    public static final VoxelShape ROUND_SHORT_MUSHROOM = Shapes.or(CommonVoxelShapes.MUSHROOM_STEM, MUSHROOM_CAP_SHORT_ROUND);

    public static void setup() {
        CommonVoxelShapes.SHAPES.put(new ResourceLocation(DynamicTreesQuark.MOD_ID, "glow_shroom_age0").toString(), SHROOM_AGE0);
        CommonVoxelShapes.SHAPES.put(new ResourceLocation(DynamicTreesQuark.MOD_ID, "round_short_mushroom").toString(), ROUND_SHORT_MUSHROOM);
    }

    @SubscribeEvent
    public static void registerLeavesPropertiesTypes(final TypeRegistryEvent<LeavesProperties> event) {
        event.registerType(new ResourceLocation(DynamicTreesQuark.MOD_ID, "trumpet"), TrumpetLeafProperties.TYPE);
    }

    @SubscribeEvent
    public static void registerCellKits(RegistryEvent<CellKit> event) {
        DTQuarkCellKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerGrowthLogicKits(RegistryEvent<GrowthLogicKit> event) {
        DTQuarkGrowthLogicKits.register(event.getRegistry());
    }

}
