package net.dxred.wonderworks.registry;

import com.google.common.base.Supplier;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.block.BagOfHoldingBlock;
import net.dxred.wonderworks.block.WorkbenchBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, WonderworksMod.MODID);

	public static final RegistryObject<Block> TEST_BLOCK = register(
		"test_block",
		() -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)),
		ModCreativeTab.TAB_WONDERWORKS
	);

	public static final RegistryObject<Block> ELEMENTAL_ORE = register(
		"elemental_ore",
		() -> new Block(BlockBehaviour.Properties.of(Material.STONE)
			.strength(1.5f, 1.5f)
			.requiresCorrectToolForDrops()
		),
		ModCreativeTab.TAB_WONDERWORKS
	);

	public static final RegistryObject<Block> DEEPSLATE_ELEMENTAL_ORE = register(
		"deepslate_elemental_ore",
		() -> new Block(BlockBehaviour.Properties.copy(ModBlocks.ELEMENTAL_ORE.get())),
		ModCreativeTab.TAB_WONDERWORKS
	);

	public static final RegistryObject<Block> WORKBENCH = register(
		"workbench",
		() -> new WorkbenchBlock(BlockBehaviour.Properties.of(ModMaterials.WORKBENCH)
			.strength(1.0f, 1.0f)
			.noOcclusion()
		),
		ModCreativeTab.TAB_WONDERWORKS
	);

	//TODO BlockItem -> BagOfHoldingItem
	public static final RegistryObject<Block> BAG_OF_HOLDING = register(
		"bag_of_holding",
		() -> new BagOfHoldingBlock(BlockBehaviour.Properties.of(ModMaterials.BAG_OF_HOLDING)
			.strength(1.0f, 1.0f)
			.noOcclusion()
			.dynamicShape()
		),
		ModCreativeTab.TAB_WONDERWORKS
	);

	public static RegistryObject<Block> register(String name, Supplier<? extends Block> sup, CreativeModeTab tab) {
		final RegistryObject<Block> block = REGISTRY.register(name, sup);
		ModItems.REGISTRY.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
		return block;
	}

	public static RegistryObject<Block> registerWithoutItem(String name, Supplier<? extends Block> sup) {
		return REGISTRY.register(name, sup);
	}
}