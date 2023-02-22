package net.dxred.wonderworks;

import com.mojang.logging.LogUtils;

import net.dxred.wonderworks.registry.ModBlockEntityTypes;
import net.dxred.wonderworks.registry.ModBlocks;
import net.dxred.wonderworks.registry.ModItems;
import net.dxred.wonderworks.registry.ModMenuTypes;
import net.dxred.wonderworks.registry.ModSoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(WonderworksMod.MODID)
public class WonderworksMod {
	public static final String MODID = "wonderworks";
	public static final Logger LOGGER = LogUtils.getLogger();

	public WonderworksMod() {
		LOGGER.info("Hello Wonderworks!");
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(this::commonSetup);

		ModBlocks.REGISTRY.register(modEventBus);
		ModBlockEntityTypes.REGISTRY.register(modEventBus);
		ModItems.REGISTRY.register(modEventBus);
		ModMenuTypes.REGISTRY.register(modEventBus);
		ModSoundEvents.REGISTRY.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
	}
}
