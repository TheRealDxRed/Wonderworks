package net.dxred.wonderworks.event;

import java.util.List;

import com.mojang.datafixers.util.Either;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.aspect.Aspect;
import net.dxred.wonderworks.aspect.AspectStack;
import net.dxred.wonderworks.tooltip.AspectClientTooltipComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = WonderworksMod.MODID, value = Dist.CLIENT, bus = Bus.FORGE)
public class ToolTipEvent {
	private static final List<AspectStack> testStacks = List.of(
		new AspectStack(Aspect.AIR, 1),
		new AspectStack(Aspect.WATER, 2),
		new AspectStack(Aspect.EARTH, 3),
		new AspectStack(Aspect.FIRE, 4),
		new AspectStack(Aspect.ORDER, 5),
		new AspectStack(Aspect.ENTROPY, 6)
	);

	@SubscribeEvent
	public static void renderTooltipEventGatherComponents(RenderTooltipEvent.GatherComponents pEvent) {
		if (Screen.hasShiftDown()) {
			pEvent.getTooltipElements().add(Either.right(new AspectClientTooltipComponent.AspectTooltipComponent(testStacks, testStacks.size())));
		}
	}
}
