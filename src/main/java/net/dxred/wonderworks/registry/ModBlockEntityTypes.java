package net.dxred.wonderworks.registry;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.block.entity.BagOfHoldingBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlockEntityTypes {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WonderworksMod.MODID);

	public static final RegistryObject<BlockEntityType<BagOfHoldingBlockEntity>> BAG_OF_HOLDING = REGISTRY.register("bag_of_holding", () -> BlockEntityType.Builder.of(BagOfHoldingBlockEntity::new, ModBlocks.BAG_OF_HOLDING.get()).build(null));
}
