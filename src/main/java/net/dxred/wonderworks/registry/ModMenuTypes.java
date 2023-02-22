package net.dxred.wonderworks.registry;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.menu.GuidebookMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModMenuTypes {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, WonderworksMod.MODID);

	public static final RegistryObject<MenuType<GuidebookMenu>> GUIDEBOOK = register("explorers_notebook", GuidebookMenu::new);

	private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String pName, IContainerFactory<T> pFactory) {
		return REGISTRY.register(pName, () -> IForgeMenuType.create(pFactory));
	}
}
