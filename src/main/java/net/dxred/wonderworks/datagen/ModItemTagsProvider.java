package net.dxred.wonderworks.datagen;

import org.jetbrains.annotations.Nullable;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.util.ModWoodType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {
	public ModItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(pGenerator, pBlockTagsProvider, WonderworksMod.MODID, existingFileHelper);
	}
	
	@Override
	protected void addTags() {
		createModWoodTypeTags(WonderworksMod.GREATWOOD);
		createModWoodTypeTags(WonderworksMod.SILVERWOOD);
		super.addTags();
	}

	private void createModWoodTypeTags(ModWoodType pWoodType) {
		this.tag(ItemTags.SIGNS).add(pWoodType.SIGN_ITEM.get());
	}
}
