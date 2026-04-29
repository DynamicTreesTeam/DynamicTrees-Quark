package maxhyper.dtquark.growthlogic;

import com.dtteam.dynamictrees.api.configuration.ConfigurationProperty;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKitConfiguration;
import com.dtteam.dynamictrees.systems.growthlogic.context.DirectionManipulationContext;
import com.dtteam.dynamictrees.systems.growthlogic.context.DirectionSelectionContext;
import com.dtteam.dynamictrees.systems.growthlogic.context.PositionalSpeciesContext;
import com.dtteam.dynamictrees.systems.GrowSignal;
import com.dtteam.dynamictrees.utility.CoordUtils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class AncientLogic extends GrowthLogicKit {

    public static final ConfigurationProperty<Integer> MIN_BRANCH_GAP = ConfigurationProperty.integer("min_branch_gap");
    public static final ConfigurationProperty<Integer> MAX_BRANCH_GAP = ConfigurationProperty.integer("max_branch_gap");
    public static final ConfigurationProperty<Integer> BRANCH_HEIGHT_VARIATION =
            ConfigurationProperty.integer("branch_height_variation");
    public AncientLogic(ResourceLocation registryName) {
        super(registryName);
    }
    public static final ConfigurationProperty<Integer> SIDE_BRANCH_ENERGY = ConfigurationProperty.integer("side_branch_energy");

    @Override
    protected GrowthLogicKitConfiguration createDefaultConfiguration() {
        return super.createDefaultConfiguration()
                .with(MIN_BRANCH_GAP, 4)
                .with(MAX_BRANCH_GAP, 6)
                .with(BRANCH_HEIGHT_VARIATION, 3)
                .with(HEIGHT_VARIATION, 10)
                .with(SIDE_BRANCH_ENERGY, 4);
    }

    @Override
    protected void registerProperties() {
        register(MIN_BRANCH_GAP, MAX_BRANCH_GAP, BRANCH_HEIGHT_VARIATION, GrowthLogicKit.HEIGHT_VARIATION, SIDE_BRANCH_ENERGY);
    }

    @Override
    public Direction selectNewDirection(GrowthLogicKitConfiguration configuration, DirectionSelectionContext context) {
        Direction newDir = super.selectNewDirection(configuration, context);
        GrowSignal signal = context.signal();
        if (signal.isInTrunk() && newDir != Direction.UP) {
            signal.energy = Math.min(configuration.get(SIDE_BRANCH_ENERGY), signal.energy);
        }
        return newDir;
    }

    @Override
    public int[] populateDirectionProbabilityMap(GrowthLogicKitConfiguration configuration, DirectionManipulationContext context) {
        int[] probMap = super.populateDirectionProbabilityMap(configuration, context);
        GrowSignal signal = context.signal();
        Direction travelDir = signal.dir;

        if (signal.isInTrunk()) {
            // Vertical gap between branches, unique for each tree in range
            int minGap = configuration.get(MIN_BRANCH_GAP);
            int maxGap = configuration.get(MAX_BRANCH_GAP);
            int gap = minGap + (CoordUtils.coordHashCode(signal.rootPos, 0) % (maxGap - minGap + 1));
            // Disable/enable horizontal movement if we're not currently in a gap
            probMap[2] = probMap[3] = probMap[4] = probMap[5] = ((int)signal.energy) % gap == 0 ? 3 : 0;
        } else {
            probMap[0] = probMap[1] = 0;
            int deltaXAbs = Math.abs(signal.delta.getX());
            int deltaZAbs = Math.abs(signal.delta.getZ());
            //Disable going straight when we are just turning out so the other branches don't get blocked
            if (deltaXAbs == 1 ^ deltaZAbs == 1){ // ^ is XOR
                probMap[2] = probMap[3] = probMap[4] = probMap[5] = 0;
                probMap[travelDir.ordinal()] = 2;
            }
            //Disable forward after a bit so the canopy isnt too pointy
//            if (deltaXAbs == 2 ^ deltaZAbs == 2){
//                probMap[travelDir.ordinal()] = 0;
//            }
        }

        // Down always disabled for ancient trees
        probMap[Direction.DOWN.get3DDataValue()] = 0;
        // Direction we came from always disabled
        probMap[travelDir.getOpposite().ordinal()] = 0;

        return probMap;
    }

    @Override
    public float getEnergy(GrowthLogicKitConfiguration configuration, PositionalSpeciesContext context) {
        return super.getEnergy(configuration, context) * context.species().biomeSuitability(context.level(), context.pos())
                + getHashVariation(configuration, context); // Vary the height energy by a psuedorandom hash function
    }

    @Override
    public int getLowestBranchHeight(GrowthLogicKitConfiguration configuration, PositionalSpeciesContext context) {
        return (int)(super.getLowestBranchHeight(configuration, context) +
                getHashVariation(configuration, context) % configuration.get(BRANCH_HEIGHT_VARIATION));
    }

    protected float getHashVariation (GrowthLogicKitConfiguration configuration, PositionalSpeciesContext context){
        long day = context.level().getGameTime() / 24000L;
        int month = (int) day / 30;//Change the hashs every in-game month

        return  (CoordUtils.coordHashCode(context.pos().above(month), 2) % configuration.get(GrowthLogicKit.HEIGHT_VARIATION));
    }
}
