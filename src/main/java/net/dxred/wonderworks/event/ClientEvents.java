package net.dxred.wonderworks.event;

import java.util.List;

import com.mojang.datafixers.util.Either;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.aspect.AspectStack;
import net.dxred.wonderworks.item.ItemAspects;
import net.dxred.wonderworks.tooltip.AspectClientTooltipComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = WonderworksMod.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class ClientEvents {
	@SubscribeEvent
	public static void clientSetupEvent(FMLClientSetupEvent pEvent) {
	}

	@SubscribeEvent
	public static void tooltipComponents(RegisterClientTooltipComponentFactoriesEvent pEvent) {
		pEvent.register(AspectClientTooltipComponent.AspectTooltipComponent.class, AspectClientTooltipComponent::new);
	}

	//@SubscribeEvent
	//TODO learn forge's new event system i guess
	public static void renderTooltipEventGatherComponents(RenderTooltipEvent.GatherComponents pEvent) {
		if (Screen.hasShiftDown()) {
			ItemStack stack = pEvent.getItemStack();
			List<AspectStack> aspects = ItemAspects.get(stack.getItem());

			pEvent.getTooltipElements().add(Either.right(new AspectClientTooltipComponent.AspectTooltipComponent(aspects, aspects.size())));
		}
	}
}
