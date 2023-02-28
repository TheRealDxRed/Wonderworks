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
public final class ItemAspect {
	private static final AspectStack UNREGISTERED = new AspectStack(Aspect.VOID, 1);
	private static Map<String, List<AspectStack>> map = Map.of(
		Items.GLOWSTONE_DUST.getDescriptionId(), List.of(new AspectStack(Aspect.LIGHT, 5)),
		Items.REDSTONE.getDescriptionId(), List.of(new AspectStack(Aspect.ENERGY, 5)),
		Items.BEDROCK.getDescriptionId(), Aspect.getAllAspects(10)
	);

	/**
	 * Registers an item's aspect components, does nothing if the item is already registered.
	 * Fuck this function, it isn't even used!
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
