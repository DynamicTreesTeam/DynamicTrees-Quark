package maxhyper.dtquark;

import net.neoforged.neoforge.common.ModConfigSpec;

// Server-side config spec.
public final class DTQuarkConfig {

    public static final ModConfigSpec SERVER_SPEC;
    public static final ModConfigSpec.BooleanValue REPLACE_VANILLA_AZALEA_WITH_SAPLING;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("Worldgen options").push("worldgen");

        REPLACE_VANILLA_AZALEA_WITH_SAPLING = builder
                .comment(
                        "Replace vanilla azalea bushes from minecraft:moss_vegetation with dtquark:azalea_sapling.",
                        "Does not affect adult azaleas spawned in lush_caves via cave_rooted."
                )
                .define("replaceVanillaAzaleaWithSapling", false);

        builder.pop();

        SERVER_SPEC = builder.build();
    }

    private DTQuarkConfig() {}
}
