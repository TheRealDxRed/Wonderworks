package net.dxred.wonderworks.datagen;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.util.ModWoodType;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, WonderworksMod.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		registerModWoodType(WonderworksMod.GREATWOOD);
		registerModWoodType(WonderworksMod.SILVERWOOD);
	}

	private void registerModWoodType(ModWoodType pWoodType) {
		//ResourceLocation PLANKS_TEXTURE = new ResourceLocation(WonderworksMod.MODID, );
		ResourceLocation PLANKS_TEXTURE = WonderworksMod.resourceLocation("block/" + pWoodType.BASENAME + "_planks");

		this.simpleBlock(pWoodType.SAPLING.get(), models().cross(pWoodType.BASENAME + "_sapling", WonderworksMod.resourceLocation("block/" + pWoodType.BASENAME + "_sapling")));
		this.simpleBlock(pWoodType.LEAVES.get());
		this.logBlock((RotatedPillarBlock)pWoodType.LOG.get());
		this.simpleBlock(pWoodType.WOOD.get());
		this.logBlock((RotatedPillarBlock)pWoodType.STRIPPED_LOG.get());
		this.simpleBlock(pWoodType.STRIPPED_WOOD.get());
		this.simpleBlock(pWoodType.PLANKS.get());
		this.stairsBlock((StairBlock)pWoodType.STAIRS.get(), PLANKS_TEXTURE);
		this.slabBlock((SlabBlock)pWoodType.SLAB.get(), PLANKS_TEXTURE, PLANKS_TEXTURE);
		this.fenceBlock((FenceBlock)pWoodType.FENCE.get(), PLANKS_TEXTURE);
		this.fenceGateBlock((FenceGateBlock)pWoodType.FENCE_GATE.get(), PLANKS_TEXTURE);
		this.doorBlock((DoorBlock)pWoodType.DOOR.get(), WonderworksMod.resourceLocation("block/" + pWoodType.BASENAME + "_door_bottom"), WonderworksMod.resourceLocation("block/" + pWoodType.BASENAME + "_door_top"));
		this.trapdoorBlock((TrapDoorBlock)pWoodType.TRAPDOOR.get(), WonderworksMod.resourceLocation("block/" + pWoodType.BASENAME + "_trapdoor"), false);
		this.pressurePlateBlock((PressurePlateBlock)pWoodType.PRESSURE_PLATE.get(), PLANKS_TEXTURE);
		this.buttonBlock((ButtonBlock)pWoodType.BUTTON.get(), PLANKS_TEXTURE);
		this.signBlock((StandingSignBlock)pWoodType.SIGN.get(), (WallSignBlock)pWoodType.WALL_SIGN.get(), PLANKS_TEXTURE);
	}
}
