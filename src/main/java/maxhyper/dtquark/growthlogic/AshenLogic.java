package maxhyper.dtquark.growthlogic;

import com.ferreusveritas.dynamictrees.api.configuration.ConfigurationProperty;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKitConfiguration;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionManipulationContext;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionSelectionContext;
import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class AshenLogic extends GrowthLogicKit {

    public static final ConfigurationProperty<Integer> MIN_BRANCH_GAP = ConfigurationProperty.integer("min_branch_gap");
    public static final ConfigurationProperty<Integer> MAX_BRANCH_GAP = ConfigurationProperty.integer("max_branch_gap");

    public AshenLogic(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected GrowthLogicKitConfiguration createDefaultConfiguration() {
        return super.createDefaultConfiguration()
                .with(MIN_BRANCH_GAP, 3)
                .with(MAX_BRANCH_GAP, 5);
    }

    @Override
    protected void registerProperties() {
        register(MIN_BRANCH_GAP, MAX_BRANCH_GAP);
    }

    @Override
    public Direction selectNewDirection(GrowthLogicKitConfiguration configuration, DirectionSelectionContext context) {
        Direction newDir = super.selectNewDirection(configuration, context);
        GrowSignal signal = context.signal();
        if (signal.isInTrunk() && newDir != Direction.UP) {
            signal.energy = Math.min(3, signal.energy);
        }
        return newDir;
    }

    @Override
    public int[] populateDirectionProbabilityMap(GrowthLogicKitConfiguration configuration, DirectionManipulationContext context) {
        int[] probMap = super.populateDirectionProbabilityMap(configuration, context);
        GrowSignal signal = context.signal();
        Direction travelDir = signal.dir;

        if (signal.isInTrunk()) {
            // Vertical gap between branches, unique for each tree in range [2, 5]
            int gap = configuration.get(MIN_BRANCH_GAP) + (CoordUtils.coordHashCode(signal.rootPos, 0) % (configuration.get(MAX_BRANCH_GAP) + 1));
            // Disable/enable horizontal movement if we're not currently in a gap
            probMap[2] = probMap[3] = probMap[4] = probMap[5] = signal.energy % gap == 0 ? 3 : 0;
        } else {
            // Disable all directions except direction of travel if branched out of trunk
            probMap[1] = probMap[2] = probMap[3] = probMap[4] = probMap[5] = 0;
            probMap[travelDir.ordinal()] = 1;
        }

        // Down always disabled for ashen trees
        probMap[Direction.DOWN.get3DDataValue()] = 0;
        // Direction we came from always disabled
        probMap[travelDir.getOpposite().ordinal()] = 0;

        return probMap;
    }
}
