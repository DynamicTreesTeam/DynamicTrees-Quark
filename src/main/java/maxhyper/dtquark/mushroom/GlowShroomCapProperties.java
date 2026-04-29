package maxhyper.dtquark.mushroom;

import com.dtteam.dynamictrees.api.registry.TypedRegistry;
import com.dtteam.dynamictreesplus.block.mushroom.CapProperties;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class GlowShroomCapProperties extends CapProperties {

    public static final TypedRegistry.EntryType<CapProperties> TYPE = TypedRegistry.newType(GlowShroomCapProperties::new);

    public GlowShroomCapProperties(ResourceLocation registryName) {
        super(registryName);
    }

    public ParticleOptions sporeParticleType(BlockState state, Level level, BlockPos pos, RandomSource random) {
        return ParticleTypes.END_ROD;
    }

    public Vec3 sporeParticleSpeed(BlockState state, Level level, BlockPos pos, RandomSource random) {
        return new Vec3(0, -0.05 - random.nextDouble() * 0.05, 0);
    }

    @Override
    public BlockBehaviour.Properties getDefaultBlockProperties() {
        return super.getDefaultBlockProperties().emissiveRendering((a,b,c)->true).hasPostProcess((a,b,c)->true);
    }
}
