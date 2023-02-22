package net.dxred.wonderworks.registry;

import net.dxred.wonderworks.WonderworksMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModSoundEvents {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WonderworksMod.MODID);

	public static final RegistryObject<SoundEvent> BAG_OF_HOLDING_OPEN = REGISTRY.register("bag_of_holding.open", () -> new SoundEvent(new ResourceLocation("bag_of_holding.open")));
	public static final RegistryObject<SoundEvent> BAG_OF_HOLDING_CLOSE = REGISTRY.register("bag_of_holding.close", () -> new SoundEvent(new ResourceLocation("bag_of_holding.close")));
}
