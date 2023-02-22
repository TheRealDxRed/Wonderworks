package net.dxred.wonderworks.datagen;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

public record JsonDataProvider<T>(Gson gson, DataGenerator generator, PackType resourceType, String folder, Codec<T> codec, Map<ResourceLocation,T> objects) implements DataProvider {
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void run(CachedOutput output) throws IOException {
		Path resourcesFolder = this.generator.getOutputFolder();
		this.objects.forEach((id, object) -> {
			Path jsonLocation = resourcesFolder.resolve(String.join("/", this.resourceType.getDirectory(), id.getNamespace(), this.folder, id.getPath() + ".json"));
			this.codec.encodeStart(JsonOps.INSTANCE, object)
				.resultOrPartial(s -> LOGGER.error("{} {} provider failed to encode {}", this.folder, this.resourceType.getDirectory(), jsonLocation, s))
				.ifPresent(jsonElement -> {
					try {
						//DataProvider.save(this.gson, cache, jsonElement, jsonLocation);
						DataProvider.saveStable(output, jsonElement, jsonLocation);
					} catch (IOException e) {
						LOGGER.error("{} {} provider failed to save {}", this.folder, this.resourceType.getDirectory(), jsonLocation, e);
					}
				});
		});
	}

	@Override
	public String getName() {
		return String.format("%s %s provider", this.folder, this.resourceType.getDirectory());
	}
}