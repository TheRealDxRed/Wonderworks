package net.dxred.wonderworks.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.aspect.Aspect;
import net.dxred.wonderworks.aspect.AspectStack;
import net.dxred.wonderworks.item.ItemAspects;
import net.dxred.wonderworks.util.ModNames;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class AspectDataManager extends SimpleJsonResourceReloadListener {
	public static final String FOLDER = ModNames.ASPECT_DATA_FOLDER;
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	private static final AspectDataManager instance = new AspectDataManager();

	private Map<String, List<AspectStack>> aspects;
	private boolean loaded;

	public AspectDataManager() {
		super(GSON, FOLDER);
	}

	public static AspectDataManager get() {
		return instance;
	}

	public boolean isLoaded() {
		return this.loaded;
	}

	public Map<String, List<AspectStack>> getAspects() {
		return this.aspects;
	}

	public List<AspectStack> getAspects(String pItemDescriptionID) {
		return this.aspects.get(pItemDescriptionID);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> pContent, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
		Set<ResourceLocation> itemid_set = ForgeRegistries.ITEMS.getKeys();

		for (ResourceLocation itemid : itemid_set) {
			if (ForgeRegistries.ITEMS.containsKey(itemid) && pContent.containsKey(itemid)) {
				Item item = ForgeRegistries.ITEMS.getValue(itemid);
				List<AspectStack> aspects = List.of(ItemAspects.UNREGISTERED);
				Aspect aspect = null;
				int count = -1;

				JsonElement jsonElement = pContent.get(itemid);

				if (jsonElement.isJsonArray()) {
					JsonArray jsonArray = jsonElement.getAsJsonArray();

					for (int i = 0; i < jsonArray.size(); i++) {
						if (jsonArray.get(i).isJsonObject()) {
							JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

							JsonElement aspect_name = jsonObject.get("id");
							aspect = (jsonObject.isJsonPrimitive()) ? Aspect.aspects.get(aspect_name.getAsString()) : null;

							JsonElement aspect_count = jsonObject.get("count");
							count = (jsonObject.isJsonPrimitive()) ? aspect_count.getAsInt() : -1;
						}
					}

					if (aspect != null && count != -1) aspects.add(new AspectStack(aspect, count));
				} else {
					WonderworksMod.LOGGER.error("Unable to load aspect list for item {}, is the root object an array?", itemid.toString());
					return;
				}

				ItemAspects.register(item, aspects);
			} else {
				//TODO search crafting recipes, if still nothing use ``ItemAspects.UNREGISTERED``
			}
		}
	}
}
