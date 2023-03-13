package net.dxred.wonderworks.datagen;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.util.ModWoodType;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, WonderworksMod.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		registerModWoodType(WonderworksMod.GREATWOOD);
		registerModWoodType(WonderworksMod.SILVERWOOD);
	}

	private void registerBlockModel(RegistryObject<Block> pBlock) {
		String path = pBlock.getId().getPath();
		getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + path)));
	}

	private void registerModWoodType(ModWoodType pWoodType) {
		registerBlockModel(pWoodType.SAPLING);
		registerBlockModel(pWoodType.LEAVES);
		registerBlockModel(pWoodType.LOG);
		registerBlockModel(pWoodType.WOOD);
		registerBlockModel(pWoodType.STRIPPED_LOG);
		registerBlockModel(pWoodType.STRIPPED_WOOD);
		registerBlockModel(pWoodType.PLANKS);
		registerBlockModel(pWoodType.STAIRS);
		registerBlockModel(pWoodType.SLAB);
		registerBlockModel(pWoodType.FENCE);
		registerBlockModel(pWoodType.FENCE_GATE);
		basicItem(WonderworksMod.resourceLocation(pWoodType.BASENAME + "_door"));
		registerBlockModel(pWoodType.TRAPDOOR);
		registerBlockModel(pWoodType.PRESSURE_PLATE);
		registerBlockModel(pWoodType.BUTTON);
		basicItem(WonderworksMod.resourceLocation(pWoodType.BASENAME + "_sign"));
	}
}
