package dev.mariany.snifferreimagined.block;

import dev.mariany.snifferreimagined.config.SRConfigHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldEvents;

public class EnrichedFarmlandBlock extends FarmlandBlock {
    public EnrichedFarmlandBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);

        BlockPos abovePos = pos.up();
        BlockState aboveState = world.getBlockState(abovePos);

        if (aboveState.getBlock() instanceof Fertilizable fertilizable) {
            if (random.nextFloat() <= SRConfigHandler.getConfig().enrichedFarmlandGrowthRate) {
                if (fertilizable.isFertilizable(world, abovePos, aboveState)) {
                    fertilizable.grow(world, random, abovePos, aboveState);
                    world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, abovePos, 15);
                }
            }
        }
    }
}
