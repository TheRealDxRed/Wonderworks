package net.dxred.wonderworks.block.grower;

import javax.annotation.Nullable;

import net.dxred.wonderworks.data.worldgen.features.FeatureConfigs;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class GreatwoodTreeGrower extends AbstractTreeGrower {
	@Override
	@Nullable
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pLargeHive) {
		return pLargeHive ? FeatureConfigs.GREATWOOD_TREE_WITH_MORE_BEEHIVES_CONFIG : FeatureConfigs.GREATWOOD_TREE;
	}
}
