package net.dxred.wonderworks.datagen;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.aspect.Aspect;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class AspectFontProvider implements DataProvider {
	static Logger LOGGER = LogManager.getLogger();

	DataGenerator generator;

	public AspectFontProvider(DataGenerator pGenerator) {
		this.generator = pGenerator;
	}

	@Override
	public void run(CachedOutput pOutput) throws IOException {
		DataGenerator.PathProvider fontPathProvider = this.generator.createPathProvider(DataGenerator.Target.RESOURCE_PACK, "font");

		JsonArray fontProviders = new JsonArray();

		Set<String> keys = Aspect.aspects.keySet();
		Iterator<String> key_iter = keys.iterator();

		while (key_iter.hasNext()) {
			// do something to
			String key = key_iter.next();
			fontProviders.add(new AspectGenerator(Aspect.getAspect(key)).generate());
		}

		fontProviders.add(generateAsciiFont());

		JsonObject object = new JsonObject();
		object.add("providers", fontProviders);

		Path jsonLocation = fontPathProvider.json(new ResourceLocation(WonderworksMod.MODID, "aspects"));

		// save to file
		try {
			saveStable(pOutput, object, jsonLocation);
			LOGGER.info("Saved to {}", jsonLocation);
		} catch (IOException e) {
			LOGGER.error("Provider failed to save {}", jsonLocation, e);
		}
	}

	@Override
	public String getName() {
		return "Aspects Font";
	}

	static void saveStable(CachedOutput pOutput, JsonElement pJson, Path pPath) throws IOException {
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
		Writer writer = new OutputStreamWriter(bytearrayoutputstream, StandardCharsets.UTF_8);
		JsonWriter jsonwriter = new JsonWriter(writer);
		jsonwriter.setSerializeNulls(false);
		jsonwriter.setIndent("\t");
		GsonHelper.writeValue(jsonwriter, pJson, KEY_COMPARATOR);
		jsonwriter.close();

		byte[] inData = bytearrayoutputstream.toByteArray();
		ByteArrayOutputStream outData = new ByteArrayOutputStream();
		HashingOutputStream hashingoutputstream = new HashingOutputStream(Hashing.sha1(), outData);
		boolean skipNext = false;

		for (int i = 0; i < inData.length; i++) {
			if (skipNext) {
				skipNext = false;
			} else {
				byte b = inData[i];
				if (b == '\\') {
					skipNext = true;
				}
				hashingoutputstream.write(b);
			}
		}

		pOutput.writeIfNeeded(pPath, outData.toByteArray(), hashingoutputstream.hash());
	}

	public JsonObject generateAsciiFont() {
		JsonArray array = new JsonArray();
		array.add("\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000");
		array.add("\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000");
		array.add("\\u0020\\u0021\\u0022\\u0023\\u0024\\u0025\\u0026\\u0027\\u0028\\u0029\\u002a\\u002b\\u002c\\u002d\\u002e\\u002f");
		array.add("\\u0030\\u0031\\u0032\\u0033\\u0034\\u0035\\u0036\\u0037\\u0038\\u0039\\u003a\\u003b\\u003c\\u003d\\u003e\\u003f");
		array.add("\\u0040\\u0041\\u0042\\u0043\\u0044\\u0045\\u0046\\u0047\\u0048\\u0049\\u004a\\u004b\\u004c\\u004d\\u004e\\u004f");
		array.add("\\u0050\\u0051\\u0052\\u0053\\u0054\\u0055\\u0056\\u0057\\u0058\\u0059\\u005a\\u005b\\u005c\\u005d\\u005e\\u005f");
		array.add("\\u0060\\u0061\\u0062\\u0063\\u0064\\u0065\\u0066\\u0067\\u0068\\u0069\\u006a\\u006b\\u006c\\u006d\\u006e\\u006f");
		array.add("\\u0070\\u0071\\u0072\\u0073\\u0074\\u0075\\u0076\\u0077\\u0078\\u0079\\u007a\\u007b\\u007c\\u007d\\u007e\\u0000");
		array.add("\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000");
		array.add("\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u00a3\\u0000\\u0000\\u0192");
		array.add("\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u00aa\\u00ba\\u0000\\u0000\\u00ac\\u0000\\u0000\\u0000\\u00ab\\u00bb");
		array.add("\\u2591\\u2592\\u2593\\u2502\\u2524\\u2561\\u2562\\u2556\\u2555\\u2563\\u2551\\u2557\\u255d\\u255c\\u255b\\u2510");
		array.add("\\u2514\\u2534\\u252c\\u251c\\u2500\\u253c\\u255e\\u255f\\u255a\\u2554\\u2569\\u2566\\u2560\\u2550\\u256c\\u2567");
		array.add("\\u2568\\u2564\\u2565\\u2559\\u2558\\u2552\\u2553\\u256b\\u256a\\u2518\\u250c\\u2588\\u2584\\u258c\\u2590\\u2580");
		array.add("\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u2205\\u2208\\u0000");
		array.add("\\u2261\\u00b1\\u2265\\u2264\\u2320\\u2321\\u00f7\\u2248\\u00b0\\u2219\\u0000\\u221a\\u207f\\u00b2\\u25a0\\u0000");

		JsonObject object = new JsonObject();
		object.addProperty("type", "bitmap");
		object.addProperty("file", "minecraft:font/ascii.png");
		object.addProperty("ascent", 7);
		object.add("chars", array);

		return object;
	}

	private static class AspectGenerator {
		Aspect aspect;

		public AspectGenerator(Aspect pAspect) {
			this.aspect = pAspect;
		}

		public JsonObject generate() {
			JsonObject object = new JsonObject();
			JsonArray unicode = new JsonArray();
			unicode.add(aspect.getUnicodeAsString());

			object.addProperty("type", "bitmap");
			object.addProperty("file", WonderworksMod.MODID + ":aspect/" + aspect.getTag() + ".png");
			object.addProperty("ascent", 9);
			object.addProperty("height", 10);
			object.add("chars", unicode);

			return object;
		}
	}
}
