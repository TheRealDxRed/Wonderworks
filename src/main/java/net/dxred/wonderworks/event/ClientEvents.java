package net.dxred.wonderworks.event;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.tooltip.AspectClientTooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
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
}
