package maxhyper.dtquark;

import com.dtteam.dynamictrees.api.registry.TypedRegistry;
import com.dtteam.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.dtteam.dynamictrees.block.leaves.LeavesProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.violetmoon.quark.content.world.module.BlossomTreesModule;

import javax.annotation.Nonnull;

public class BlossomLeavesProperties extends LeavesProperties {

    public static final TypedRegistry.EntryType<LeavesProperties> TYPE = TypedRegistry.newType(BlossomLeavesProperties::new);

    public BlossomLeavesProperties(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    @Nonnull
    protected DynamicLeavesBlock createDynamicLeaves(@Nonnull BlockBehaviour.Properties properties) {
        return new DynamicLeavesBlock(this, properties) {
            @Override
            @OnlyIn(Dist.CLIENT)
            public void animateTick(@NotNull BlockState stateIn, Level worldIn, BlockPos pos, @NotNull RandomSource rand) {
                if (BlossomTreesModule.dropLeafParticles && rand.nextInt(5) == 0 && worldIn.isEmptyBlock(pos.below())) {
                    double windStrength = 5.0 + Math.cos((double)worldIn.getGameTime() / 2000.0) * 2.0;
                    double windX = Math.cos((double)worldIn.getGameTime() / 1200.0) * windStrength;
                    double windZ = Math.sin((double)worldIn.getGameTime() / 1000.0) * windStrength;
                    worldIn.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, stateIn), (double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5, windX, -1.0, windZ);
                }
                super.animateTick(stateIn, worldIn, pos, rand);
            }
        };
    }

}
