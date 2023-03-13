package net.dxred.wonderworks.util;

import java.util.function.Supplier;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.registry.ModCreativeTab;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModWoodType {
	public final WoodType TYPE;
	public final String BASENAME;
	public final RegistryObject<Block> SAPLING;
	public final RegistryObject<Block> LEAVES;
	public final RegistryObject<Block> LOG;
	public final RegistryObject<Block> WOOD;
	public final RegistryObject<Block> STRIPPED_LOG;
	public final RegistryObject<Block> STRIPPED_WOOD;
	public final RegistryObject<Block> PLANKS;
	public final RegistryObject<Block> STAIRS;
	public final RegistryObject<Block> SLAB;
	public final RegistryObject<Block> FENCE;
	public final RegistryObject<Block> FENCE_GATE;
	public final RegistryObject<Block> DOOR;
	public final RegistryObject<Block> TRAPDOOR;
	public final RegistryObject<Block> PRESSURE_PLATE;
	public final RegistryObject<Block> BUTTON;
	public final RegistryObject<Block> SIGN;
	public final RegistryObject<Block> WALL_SIGN;

	public final RegistryObject<Item> SIGN_ITEM;

	public ModWoodType(String pName, AbstractTreeGrower pTreeGrower, MaterialColor pPlanksColor, MaterialColor pBarkColor, CreativeModeTab pTab, DeferredRegister<Block> pBlockRegistry, DeferredRegister<Item> pItemRegistry) {
		this.TYPE = WoodType.register(WoodType.create(WonderworksMod.MODID + ":" + pName));
		this.BASENAME = pName;
		this.SAPLING = registerWithItem(pName + "_sapling", () -> new SaplingBlock(pTreeGrower, BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), pTab, pBlockRegistry, pItemRegistry);
		this.LEAVES = registerWithItem(pName + "_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion()), pTab, pBlockRegistry, pItemRegistry);
		this.LOG = registerWithItem(pName + "_log", () -> log(pPlanksColor, pBarkColor), pTab, pBlockRegistry, pItemRegistry);
		this.WOOD = registerWithItem(pName + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, pBarkColor).strength(2.0f).sound(SoundType.WOOD)), pTab, pBlockRegistry, pItemRegistry);
		this.STRIPPED_LOG = registerWithItem("stripped_" + pName + "_log", () -> log(pPlanksColor, pPlanksColor), pTab, pBlockRegistry, pItemRegistry);
		this.STRIPPED_WOOD = registerWithItem("stripped_" + pName + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, pPlanksColor).strength(2.0f).sound(SoundType.WOOD)), pTab, pBlockRegistry, pItemRegistry);
		this.PLANKS = registerWithItem(pName + "_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, pPlanksColor).strength(2.0f, 3.0f).sound(SoundType.WOOD)), pTab, pBlockRegistry, pItemRegistry);
		this.STAIRS = registerWithItem(pName + "_stairs", () -> new StairBlock(this.PLANKS.get()::defaultBlockState, BlockBehaviour.Properties.copy(this.PLANKS.get())), pTab, pBlockRegistry, pItemRegistry);
		this.SLAB = registerWithItem(pName + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(this.PLANKS.get())), pTab, pBlockRegistry, pItemRegistry);
		this.FENCE = registerWithItem(pName + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(this.PLANKS.get())), pTab, pBlockRegistry, pItemRegistry);
		this.FENCE_GATE = registerWithItem(pName + "_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(this.PLANKS.get())), pTab, pBlockRegistry, pItemRegistry);
		this.DOOR = registerWithItem(pName + "_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(this.PLANKS.get()).strength(3.0f).noOcclusion()), pTab, pBlockRegistry, pItemRegistry);
		this.TRAPDOOR = registerWithItem(pName + "_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(this.DOOR.get())), pTab, pBlockRegistry, pItemRegistry);
		this.PRESSURE_PLATE = registerWithItem(pName + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(this.PLANKS.get()).noCollission().strength(0.5f)), pTab, pBlockRegistry, pItemRegistry);
		this.BUTTON = registerWithItem(pName + "_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(1.0f).sound(SoundType.WOOD)), pTab, pBlockRegistry, pItemRegistry);
		this.SIGN = registerWithoutItem(pName + "_sign", () -> new StandingSignBlock(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1.0f).sound(SoundType.WOOD), this.TYPE), pBlockRegistry);
		this.WALL_SIGN = registerWithoutItem(pName + "_wall_sign", () -> new WallSignBlock(BlockBehaviour.Properties.copy(this.SIGN.get()), this.TYPE), pBlockRegistry);

		this.SIGN_ITEM = pItemRegistry.register(pName, () -> new SignItem(new Item.Properties().tab(ModCreativeTab.TAB_WONDERWORKS), this.SIGN.get(), this.WALL_SIGN.get()));
	}

	private static RegistryObject<Block> registerWithItem(String pName, Supplier<? extends Block> pSupplier, CreativeModeTab pTab, DeferredRegister<Block> pBlockRegistry, DeferredRegister<Item> pItemRegistry) {
		RegistryObject<Block> block = pBlockRegistry.register(pName, pSupplier);
		pItemRegistry.register(pName, () -> new BlockItem(block.get(), new Item.Properties().tab(pTab)));
		return block;
	}

	private static RegistryObject<Block> registerWithoutItem(String pName, Supplier<? extends Block> pSupplier, DeferredRegister<Block> pBlockRegistry) {
		return pBlockRegistry.register(pName, pSupplier);
	}

	private static RotatedPillarBlock log(MaterialColor pColor1, MaterialColor pColor2) {
		return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pColor1 : pColor2).strength(2.0f).sound(SoundType.WOOD));
	}
}
