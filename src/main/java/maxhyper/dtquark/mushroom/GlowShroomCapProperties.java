package maxhyper.dtquark.mushroom;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictreesplus.block.mushroom.CapProperties;
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

    public ParticleOptions sporeParticleType (BlockState pState, Level pLevel, BlockPos pPos, Random pRand){
        return ParticleTypes.END_ROD;
    }
    public Vec3 sporeParticleSpeed (BlockState pState, Level pLevel, BlockPos pPos, Random pRand){
        return new Vec3(0, -0.05 - Math.random() * 0.05, 0);
    }

}
