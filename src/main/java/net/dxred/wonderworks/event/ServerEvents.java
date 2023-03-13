package net.dxred.wonderworks.event;

import net.dxred.wonderworks.WonderworksMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = WonderworksMod.MODID, value = Dist.DEDICATED_SERVER, bus = Bus.MOD)
public final class ServerEvents {
	
}
