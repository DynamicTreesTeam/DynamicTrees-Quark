package maxhyper.dtquark;

import com.ferreusveritas.dynamictreesplus.block.mushroom.CapProperties;
import com.ferreusveritas.dynamictreesplus.block.mushroom.DynamicCapBlock;
import com.ferreusveritas.dynamictreesplus.block.mushroom.DynamicCapCenterBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class GlowShroomCapProperties extends CapProperties {

    public GlowShroomCapProperties(ResourceLocation registryName) {
        super(registryName);
    }

    protected DynamicCapBlock createDynamicCap(final BlockBehaviour.Properties properties) {
        return new DynamicCapBlock(this, properties) {

        };
    }

    protected DynamicCapCenterBlock createDynamicCapCenter(final BlockBehaviour.Properties properties) {
        return new DynamicCapCenterBlock(this, properties) {

        };
    }

}
