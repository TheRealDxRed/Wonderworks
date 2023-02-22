package net.dxred.wonderworks.datagen.loot;

import java.util.Set;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableSet;

import net.dxred.wonderworks.registry.ModBlocks;
import net.dxred.wonderworks.registry.ModItems;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLoot extends BlockLoot {
	protected static final Set<Item> EXPLOSION_RESISTANT = Stream.of(ModBlocks.TEST_BLOCK.get()).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());

	@Override
	protected void addTables() {
		this.dropSelf(ModBlocks.TEST_BLOCK.get());
		this.add(ModBlocks.ELEMENTAL_ORE.get(), (block) -> createElementalOreDrops(ModBlocks.ELEMENTAL_ORE.get()));
		this.add(ModBlocks.DEEPSLATE_ELEMENTAL_ORE.get(), (block) -> createElementalOreDrops(ModBlocks.DEEPSLATE_ELEMENTAL_ORE.get()));
		this.dropSelf(ModBlocks.WORKBENCH.get());
		this.add(ModBlocks.BAG_OF_HOLDING.get(), BlockLoot::createShulkerBoxDrop);
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ModBlocks.REGISTRY.getEntries().stream().map(RegistryObject::get)::iterator;
	}

	protected static <T extends FunctionUserBuilder<T>> T applyExplosionDecay(ItemLike item, FunctionUserBuilder<T> function) {
		return (T)(!EXPLOSION_RESISTANT.contains(item.asItem()) ? function.apply(ApplyExplosionDecay.explosionDecay()) : function.unwrap());
	}

	protected static LootTable.Builder createElementalOreDrops(Block block) {
		return BlockLoot.createSilkTouchDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(ModItems.ELEMENTAL_CRYSTAL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
	}
}
