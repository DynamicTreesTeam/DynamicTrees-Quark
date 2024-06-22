package maxhyper.dtquark.loot;

import com.ferreusveritas.dynamictrees.util.LazyValue;
import maxhyper.dtquark.DTQuarkConfig;
import maxhyper.dtquark.DynamicTreesQuark;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.violetmoon.zeta.event.play.loading.ZLootTableLoad;
import org.violetmoon.quark.content.world.module.AncientWoodModule;

@Mod.EventBusSubscriber(modid = DynamicTreesQuark.MOD_ID)
public class LootModification {

    private static final LazyValue<Item> ANCIENT_SEED = LazyValue.supplied(
            () -> ForgeRegistries.ITEMS.getValue(DynamicTreesQuark.location("ancient_seed"))
    );

    @SubscribeEvent
    public static void loadLootTable(LootTableLoadEvent event) {
        int weight = 0;
        if (event.getName().equals(BuiltInLootTables.ANCIENT_CITY)) {
            weight = DTQuarkConfig.ANCIENT_SEED_LOOT_WEIGHT.get();
        }

        if (weight > 0) {
            LootPoolEntryContainer entry = LootItem.lootTableItem(ANCIENT_SEED.get())
                    .setWeight(weight)
                    .setQuality(AncientWoodModule.ancientCityLootQuality)
                    .build();
            if (event instanceof ZLootTableLoad) {
                ZLootTableLoad zLootTableLoadEvent = (ZLootTableLoad) event;
                zLootTableLoadEvent.add(entry);
            }
        }
    }

}
