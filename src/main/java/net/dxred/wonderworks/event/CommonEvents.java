package net.dxred.wonderworks.event;

import net.dxred.wonderworks.WonderworksMod;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = WonderworksMod.MODID, bus = Bus.MOD)
public final class CommonEvents {
	//@SubscribeEvent
	public static void onDatapackSyncEvent(OnDatapackSyncEvent pEvent) {
		//TODO: loading aspects from datapacks
	}
}
