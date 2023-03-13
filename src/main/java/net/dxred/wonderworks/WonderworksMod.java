package net.dxred.wonderworks;

import com.mojang.logging.LogUtils;

import net.dxred.wonderworks.block.grower.GreatwoodTreeGrower;
import net.dxred.wonderworks.block.grower.SilverwoodTreeGrower;
import net.dxred.wonderworks.item.ItemAspects;
import net.dxred.wonderworks.registry.ModBlockEntityTypes;
import net.dxred.wonderworks.registry.ModBlocks;
import net.dxred.wonderworks.registry.ModCreativeTab;
import net.dxred.wonderworks.registry.ModFeatures;
import net.dxred.wonderworks.registry.ModItems;
import net.dxred.wonderworks.registry.ModMenuTypes;
import net.dxred.wonderworks.registry.ModSoundEvents;
import net.dxred.wonderworks.util.ModWoodType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MaterialColor;
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

	public static final ModWoodType GREATWOOD = new ModWoodType(
		"greatwood",
		new GreatwoodTreeGrower(),
		MaterialColor.COLOR_BROWN,
		MaterialColor.COLOR_BROWN,
		ModCreativeTab.TAB_WONDERWORKS,
		ModBlocks.REGISTRY,
		ModItems.REGISTRY
	);

	public static final ModWoodType SILVERWOOD = new ModWoodType(
		"silverwood",
		new SilverwoodTreeGrower(),
		MaterialColor.COLOR_LIGHT_GRAY,
		MaterialColor.SNOW,
		ModCreativeTab.TAB_WONDERWORKS,
		ModBlocks.REGISTRY,
		ModItems.REGISTRY
	);

	public WonderworksMod() {
		LOGGER.info("Hello Wonderworks!");
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(this::commonSetup);

		ModBlocks.REGISTRY.register(modEventBus);
		ModBlockEntityTypes.REGISTRY.register(modEventBus);
		ModFeatures.REGISTRY.register(modEventBus);
		ModItems.REGISTRY.register(modEventBus);
		ModMenuTypes.REGISTRY.register(modEventBus);
		ModSoundEvents.REGISTRY.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		ItemAspects.genMap();
	}

	public static ResourceLocation resourceLocation(String pName) {
		return new ResourceLocation(MODID, pName);
	}
}
