/*
 * This file is part of EverCrops: Farmer's Delight.
 * Copyright (c) 2026 Mark Gottschling (gottsch)
 *
 * EverCrops: Farmer's Delight is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverCrops: Farmer's Delight is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with EverCrops: Farmer's Delight.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.forge.evercrops.farmersdelight.core.event;

import mod.gottsch.forge.evercrops.core.persistence.CropCatchUp;
import mod.gottsch.forge.evercrops.core.persistence.CropRegistry;
import mod.gottsch.forge.evercrops.farmersdelight.EverCropsFD;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.block.BuddingBushBlock;
import vectorwing.farmersdelight.common.block.RiceBlock;
import vectorwing.farmersdelight.common.block.TomatoBlock;

/**
 * Game-bus event listeners for EverCrops: Farmer's Delight.
 *
 * @author Mark Gottschling on 2026-05-10
 */
@Mod.EventBusSubscriber(modid = EverCropsFD.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    /**
     * Register a CropState entry whenever a tracked FD crop block is placed.
     */
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel().isClientSide()) return;
        BlockState state = event.getPlacedBlock();
        if (!isTracked(state)) return;
        ServerLevel serverLevel = (ServerLevel) event.getLevel();
        BlockPos pos = event.getPos();
        CropRegistry.put(serverLevel, pos, CropCatchUp.createState(serverLevel, pos));
    }

    /**
     * Remove the CropState entry when a tracked FD crop block is broken.
     */
    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        if (event.getLevel().isClientSide()) return;
        BlockState state = event.getState();
        if (isTracked(state)) {
            CropRegistry.remove((ServerLevel) event.getLevel(), event.getPos());
        }
    }

    /**
     * FD blocks tracked by this mod. Mirrors the set of mixins in this mod.
     * CabbageBlock, OnionBlock, and RicePaniclesBlock are CropBlock subclasses and
     * are tracked by EverCrops's own ModEvents — no need to handle them here.
     */
    private static boolean isTracked(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof TomatoBlock
                && state.hasProperty(TomatoBlock.VINE_AGE)
                && !state.getValue(TomatoBlock.ROPELOGGED)) return true;
        if (block instanceof BuddingBushBlock
                && state.hasProperty(BuddingBushBlock.AGE)) return true;
        if (block instanceof RiceBlock
                && state.hasProperty(RiceBlock.AGE)) return true;
        return false;
    }
}
