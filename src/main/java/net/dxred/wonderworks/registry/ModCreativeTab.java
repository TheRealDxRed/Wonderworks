package net.dxred.wonderworks.registry;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.util.ModTranslationComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab extends CreativeModeTab {
	public static final ModCreativeTab TAB_WONDERWORKS = new ModCreativeTab();

	public ModCreativeTab() {
		super(WonderworksMod.MODID);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ModItems.EXPLORERS_NOTEBOOK.get());
	}

	@Override
	public Component getDisplayName() {
		return ModTranslationComponents.CREATIVE_TAB_NAME;
	}
}
