package net.dxred.wonderworks.datagen;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.dxred.wonderworks.WonderworksMod;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WonderworksMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	public static Logger LOGGER = LogUtils.getLogger();

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		ModBlockTagsProvider modBlockTagsProvider = new ModBlockTagsProvider(generator, helper);

		generator.addProvider(true, new ModLootTableProvider(generator));
		generator.addProvider(true, new ModBlockStateProvider(generator, helper));
		generator.addProvider(true, new ModItemModelProvider(generator, helper));
		generator.addProvider(true, modBlockTagsProvider);
		generator.addProvider(true, new ModItemTagsProvider(generator, modBlockTagsProvider, helper));
	}
}
