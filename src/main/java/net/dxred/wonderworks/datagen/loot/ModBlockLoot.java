package net.dxred.wonderworks.datagen.loot;

import java.util.Set;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableSet;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.registry.ModBlocks;
import net.dxred.wonderworks.registry.ModItems;
import net.dxred.wonderworks.util.ModWoodType;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLoot extends BlockLoot {
	protected static final Set<Item> EXPLOSION_RESISTANT = Stream.of(ModBlocks.TEST_BLOCK.get()).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());
	private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05f, 0.0625f, 0.083333336f, 0.1f};

	@Override
	protected void addTables() {
		this.dropSelf(ModBlocks.TEST_BLOCK.get());
		this.dropSelf(ModBlocks.WORKBENCH.get());
		this.add(ModBlocks.BAG_OF_HOLDING.get(), BlockLoot::createShulkerBoxDrop);

		this.addWoodTypeDrops(WonderworksMod.GREATWOOD);
		this.addWoodTypeDrops(WonderworksMod.SILVERWOOD);
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ModBlocks.REGISTRY.getEntries().stream().map(RegistryObject::get)::iterator;
	}

	protected static <T extends FunctionUserBuilder<T>> T applyExplosionDecay(ItemLike item, FunctionUserBuilder<T> function) {
		return (T)(!EXPLOSION_RESISTANT.contains(item.asItem()) ? function.apply(ApplyExplosionDecay.explosionDecay()) : function.unwrap());
	}

	public void addWoodTypeDrops(ModWoodType pWoodType) {
		this.dropSelf(pWoodType.SAPLING.get());
		this.add(pWoodType.LEAVES.get(), (block) -> createLeavesDrops(block, pWoodType.SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		this.dropSelf(pWoodType.LOG.get());
		this.dropSelf(pWoodType.WOOD.get());
		this.dropSelf(pWoodType.STRIPPED_LOG.get());
		this.dropSelf(pWoodType.STRIPPED_WOOD.get());
		this.dropSelf(pWoodType.PLANKS.get());
		this.dropSelf(pWoodType.STAIRS.get());
		this.add(pWoodType.SLAB.get(), (block) -> createSlabItemTable(block));
		this.dropSelf(pWoodType.FENCE.get());
		this.dropSelf(pWoodType.FENCE_GATE.get());
		this.add(pWoodType.DOOR.get(), (block) -> createDoorTable(block));
		this.dropSelf(pWoodType.TRAPDOOR.get());
		this.dropSelf(pWoodType.PRESSURE_PLATE.get());
		this.dropSelf(pWoodType.BUTTON.get());
		this.dropOther(pWoodType.SIGN.get(), pWoodType.SIGN_ITEM.get());
		this.dropOther(pWoodType.WALL_SIGN.get(), pWoodType.SIGN_ITEM.get());
	}
}
