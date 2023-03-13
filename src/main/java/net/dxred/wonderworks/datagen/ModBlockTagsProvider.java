package net.dxred.wonderworks.datagen;

import org.jetbrains.annotations.Nullable;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.util.ModWoodType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {
	public ModBlockTagsProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
		super(pGenerator, WonderworksMod.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		createModWoodTypeTags(WonderworksMod.GREATWOOD);
		createModWoodTypeTags(WonderworksMod.SILVERWOOD);
	}

	private void createModWoodTypeTags(ModWoodType pWoodType) {
		this.tag(BlockTags.SAPLINGS).add(pWoodType.SAPLING.get());
		this.tag(BlockTags.LEAVES).add(pWoodType.LEAVES.get());
		this.tag(BlockTags.LOGS)
			.add(pWoodType.LOG.get())
			.add(pWoodType.WOOD.get())
			.add(pWoodType.STRIPPED_LOG.get())
			.add(pWoodType.STRIPPED_WOOD.get());
		this.tag(BlockTags.PLANKS).add(pWoodType.PLANKS.get());
		this.tag(BlockTags.STAIRS).add(pWoodType.STAIRS.get());
		this.tag(BlockTags.SLABS).add(pWoodType.SLAB.get());
		this.tag(BlockTags.FENCES).add(pWoodType.FENCE.get());
		this.tag(BlockTags.FENCE_GATES).add(pWoodType.FENCE_GATE.get());
		this.tag(BlockTags.DOORS).add(pWoodType.DOOR.get());
		this.tag(BlockTags.TRAPDOORS).add(pWoodType.TRAPDOOR.get());
		this.tag(BlockTags.PRESSURE_PLATES).add(pWoodType.PRESSURE_PLATE.get());
		this.tag(BlockTags.BUTTONS).add(pWoodType.BUTTON.get());
		//this.tag(BlockTags.SIGNS)
		//	.add(pWoodType.SIGN.get())
		//	.add(pWoodType.WALL_SIGN.get());
		//this.tag(BlockTags.STANDING_SIGNS).add(pWoodType.SIGN.get());
		//this.tag(BlockTags.WALL_SIGNS).add(pWoodType.WALL_SIGN.get());
	}
}
