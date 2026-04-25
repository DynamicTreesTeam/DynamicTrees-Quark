package maxhyper.dtquark.data;

import com.dtteam.dynamictrees.data.provider.DTBlockTagsProvider;
import com.dtteam.dynamictrees.data.tags.DTBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DTQuarkBlockTagsProvider extends DTBlockTagsProvider {

    public DTQuarkBlockTagsProvider(PackOutput output, String modid, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper fileHelper) {
        super(output, modid, lookupProvider, fileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        super.addTags(provider);

        // Add branches to axe mineable tag
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(DTBlockTags.BRANCHES)
                .addTag(DTBlockTags.STRIPPED_BRANCHES)
                .addTag(DTBlockTags.FUNGUS_BRANCHES)
                .addTag(DTBlockTags.STRIPPED_FUNGUS_BRANCHES);

        // Ensure branches are considered logs (if not already done by base mod logic)
        this.tag(BlockTags.LOGS)
                .addTag(DTBlockTags.BRANCHES)
                .addTag(DTBlockTags.FUNGUS_BRANCHES);

        this.tag(BlockTags.LOGS_THAT_BURN)
                .addTag(DTBlockTags.BRANCHES_THAT_BURN)
                .addTag(DTBlockTags.STRIPPED_BRANCHES_THAT_BURN);
    }
}
