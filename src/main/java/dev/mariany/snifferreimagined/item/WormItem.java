package dev.mariany.snifferreimagined.item;

import dev.mariany.snifferreimagined.block.SRBlocks;
import dev.mariany.snifferreimagined.config.SRConfigHandler;
import dev.mariany.snifferreimagined.sound.SRSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WormItem extends Item {
    public WormItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState blockState = world.getBlockState(pos);

        if (blockState.getBlock() instanceof FarmlandBlock && !blockState.isOf(SRBlocks.ENRICHED_FARMLAND)) {
            stack.decrementUnlessCreative(1, player);

            world.playSound(
                    null,
                    pos,
                    SRSoundEvents.ITEM_WORM_USE,
                    SoundCategory.BLOCKS,
                    1,
                    1
            );

            if (!world.isClient()) {
                if (world.getRandom().nextFloat() <= SRConfigHandler.getConfig().enrichedFarmlandConversionRate) {
                    BlockState newState = SRBlocks.ENRICHED_FARMLAND.getDefaultState();

                    for (Property property : blockState.getProperties()) {
                        newState = newState.withIfExists(property, blockState.get(property));
                    }

                    world.setBlockState(pos, newState, Block.NOTIFY_ALL);
                }
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
