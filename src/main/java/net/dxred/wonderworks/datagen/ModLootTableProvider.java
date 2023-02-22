package net.dxred.wonderworks.datagen;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;

import net.dxred.wonderworks.datagen.loot.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class ModLootTableProvider extends LootTableProvider {
	public ModLootTableProvider(DataGenerator pGenerator) {
		super(pGenerator);
	}

	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
		return List.of(
			Pair.of(ModBlockLoot::new, LootContextParamSets.BLOCK)
			//Pair.of(ModEntityLoot::new, LootContextParamSets.ENTITY),
			//Pair.of(ModChestLoot::new, LootContextParamSets.CHEST)
		);
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
		map.forEach((location, lootTable) -> LootTables.validate(validationtracker, location, lootTable));
	}
}
