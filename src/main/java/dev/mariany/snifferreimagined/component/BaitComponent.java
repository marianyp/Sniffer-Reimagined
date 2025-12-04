package dev.mariany.snifferreimagined.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public class BaitComponent implements TooltipAppender {
    public static final Codec<BaitComponent> CODEC = ItemStack.CODEC
            .xmap(BaitComponent::new, baitComponent -> baitComponent.stack);

    public static final PacketCodec<RegistryByteBuf, BaitComponent> PACKET_CODEC = ItemStack.OPTIONAL_PACKET_CODEC
            .xmap(BaitComponent::new, baitComponent -> baitComponent.stack);

    private final ItemStack stack;
    private final int hashCode;

    public BaitComponent(ItemStack stack) {
        this.stack = stack;
        this.hashCode = stack.hashCode();
    }

    public ItemStack getStack() {
        return this.stack;
    }

    @Override
    public void appendTooltip(
            Item.TooltipContext context,
            Consumer<Text> textConsumer,
            TooltipType type,
            ComponentsAccess components
    ) {
        int fishingSecondsReduction = this.stack.getOrDefault(SRComponents.FISHING_SECONDS_REDUCTION, 0);
        String sign = fishingSecondsReduction > 0 ? "-" : "+";
        String value = sign + fishingSecondsReduction;

        textConsumer.accept(Text.empty());
        textConsumer.accept(Text.translatable("item.modifiers.bait").formatted(Formatting.GRAY));
        textConsumer.accept(Text.translatable("item.modifiers.bait_value", value).formatted(Formatting.BLUE));
    }

    public boolean equals(Object object) {
        return this == object ||
                object instanceof BaitComponent baitComponent && ItemStack.areEqual(this.stack, baitComponent.stack);
    }

    public int hashCode() {
        return this.hashCode;
    }
}
