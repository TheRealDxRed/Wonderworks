package net.dxred.wonderworks.item;

import java.util.List;
import java.util.Map;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.aspect.Aspect;
import net.dxred.wonderworks.aspect.AspectStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

/**
 * Helper class for registering & storing item aspect components
 */
public final class ItemAspects {
	public static final AspectStack UNREGISTERED = new AspectStack(Aspect.VOID, -1);
	private static Map<String, List<AspectStack>> map = Map.of();

	public static void genMap() {
		// vanilla/ww aspects
		map = Map.of(
			Items.GLOWSTONE_DUST.getDescriptionId(), List.of(new AspectStack(Aspect.LIGHT, 5))
		);

		// generalized tag aspects (#forge:ingots, #minecraft:wood, etc.)
		//TODO: learn how to use tags
	}

	/**
	 * Registers an item's aspect components, does nothing if the item is already registered.
	 */
	public static List<AspectStack> register(Item pItem, List<AspectStack> pAspects) {
		String key = pItem.getDescriptionId();

		if (map.containsKey(key)) {
			WonderworksMod.LOGGER.info("Tried to register item aspects twice: {}, skipping...", key);
			return List.of(UNREGISTERED);
		}

		map.put(key, pAspects);
		return map.get(key);
	}

	// i realize this function is basically a copy of `register`... i don't care, it's staying
	public static List<AspectStack> get(String pKey) {
		if (!map.containsKey(pKey)) {
			WonderworksMod.LOGGER.info("Tried to get nonexistent aspect list: {}, creating empty list...", pKey);
			List<AspectStack> list = List.of(UNREGISTERED);
			return list;
		}

		return map.get(pKey);
	}

	public static List<AspectStack> get(Item pItem) {
		return get(pItem.getDescriptionId());
	}
}
