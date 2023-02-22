package net.dxred.wonderworks.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab extends CreativeModeTab {
	public static final ModCreativeTab TAB_WONDERWORKS = new ModCreativeTab("wonderworks", new ItemStack(ModItems.EXPLORERS_NOTEBOOK.get()));

	private ItemStack icon;

	public ModCreativeTab(String label, ItemStack icon) {
		super(label);
		this.icon = icon;
	}

	@Override
	public ItemStack makeIcon() {
		return this.icon;
	}
}
