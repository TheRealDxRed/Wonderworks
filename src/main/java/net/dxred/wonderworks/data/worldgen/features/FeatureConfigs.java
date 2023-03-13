package net.dxred.wonderworks.data.worldgen.features;

import java.util.List;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.registry.ModFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class FeatureConfigs {
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> GREATWOOD_TREE = FeatureUtils.register(
		WonderworksMod.MODID + ":greatwood_tree", 
		ModFeatures.GREATWOOD_TREE.get(), 
		createGreatwoodTree().build()
	);

	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> GREATWOOD_TREE_WITH_MORE_BEEHIVES_CONFIG = FeatureUtils.register(
		WonderworksMod.MODID + ":greatwood_tree_with_bees",
		ModFeatures.GREATWOOD_TREE.get(),
		createGreatwoodTree().decorators(List.of(new BeehiveDecorator(0.05f))).build()
	);

	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SILVERWOOD_TREE = FeatureUtils.register(
		WonderworksMod.MODID + ":silverwood_tree",
		ModFeatures.SILVERWOOD_TREE.get(),
		createSilverwoodTree().build()
	);

	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SILVERWOOD_TREE_WITH_MORE_BEEHIVES_CONFIG = FeatureUtils.register(
		WonderworksMod.MODID + ":silverwood_tree_with_bees",
		ModFeatures.SILVERWOOD_TREE.get(),
		createSilverwoodTree().decorators(List.of(new BeehiveDecorator(0.05f))).build()
	);
		
	private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(BlockState pTrunkState, BlockState pFoliageState, int pBaseHeight, int pHeightRandA, int pHeightRandB, int p6) {
		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(pTrunkState),
			new StraightTrunkPlacer(pBaseHeight, pHeightRandA, pHeightRandB),
			BlockStateProvider.simple(pFoliageState),
			new BlobFoliagePlacer(ConstantInt.of(p6),
			ConstantInt.ZERO, 3),
			new TwoLayersFeatureSize(1, 0, 1)
		);
	}

	private static TreeConfiguration.TreeConfigurationBuilder createGreatwoodTree() {
		return createStraightBlobTree(
			WonderworksMod.GREATWOOD.LOG.get().defaultBlockState(),
			WonderworksMod.GREATWOOD.LEAVES.get().defaultBlockState(),
			7,
			2,
			0,
			2
		);
	}

	private static TreeConfiguration.TreeConfigurationBuilder createSilverwoodTree() {
		return createStraightBlobTree(
			WonderworksMod.SILVERWOOD.LOG.get().defaultBlockState(),
			WonderworksMod.SILVERWOOD.LEAVES.get().defaultBlockState(),
			7,
			2,
			0,
			2
		);
	}
}
