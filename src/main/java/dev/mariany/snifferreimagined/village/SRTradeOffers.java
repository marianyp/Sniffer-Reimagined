package dev.mariany.snifferreimagined.village;

import dev.mariany.snifferreimagined.SnifferReimagined;
import dev.mariany.snifferreimagined.config.SRConfigHandler;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffers;

import java.util.List;

public class SRTradeOffers {
    public static void register() {
        SnifferReimagined.LOGGER.info("Registering Villager Trades for " + SnifferReimagined.MOD_ID);
        SRTradeOffers.registerSnifferEgg();
    }

    private static void registerSnifferEgg() {
        if (SRConfigHandler.getConfig().wanderingTraderSellsSnifferEggs) {
            TradeOfferHelper.registerWanderingTraderOffers(builder -> builder
                    .addOffersToPool(
                            TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL,
                            List.of(
                                    new TradeOffers.SellItemFactory(
                                            Items.SNIFFER_EGG,
                                            8,
                                            1,
                                            3,
                                            1
                                    )
                            )
                    )
            );
        }
    }
}
