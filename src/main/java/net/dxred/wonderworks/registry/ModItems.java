package net.dxred.wonderworks.registry;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.item.GuidebookItem;
import net.dxred.wonderworks.util.ModNames;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, WonderworksMod.MODID);

	public static final RegistryObject<Item> ELEMENTAL_CRYSTAL = REGISTRY.register(
		"elemental_crystal",
		() -> new Item(new Item.Properties().tab(ModCreativeTab.TAB_WONDERWORKS))
	);

	public static final RegistryObject<Item> EXPLORERS_NOTEBOOK = REGISTRY.register(
		ModNames.GUIDEBOOK,
		() -> new GuidebookItem(new Item.Properties().tab(ModCreativeTab.TAB_WONDERWORKS))
	);

	//TODO ignore todos until you have a plan
	//TODO item for bag of holding
}
