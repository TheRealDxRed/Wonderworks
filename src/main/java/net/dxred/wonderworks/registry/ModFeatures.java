package net.dxred.wonderworks.registry;

import net.dxred.wonderworks.WonderworksMod;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, WonderworksMod.MODID);

	public static final RegistryObject<Feature<TreeConfiguration>> GREATWOOD_TREE = REGISTRY.register("greatwood_tree", () -> new TreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<TreeConfiguration>> SILVERWOOD_TREE = REGISTRY.register("silverwood_tree", () -> new TreeFeature(TreeConfiguration.CODEC));
}
