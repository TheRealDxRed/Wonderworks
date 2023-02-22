package net.dxred.wonderworks.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WorkbenchBlock extends Block {
	private static final VoxelShape SHAPE = makeShape();

	public WorkbenchBlock(Properties pProperties) {
		super(pProperties);
	}

	private static VoxelShape makeShape() {
		VoxelShape shape = Shapes.empty();

		shape = Shapes.join(shape, Shapes.box(0/16D, 10/16D, 0/16D, 16/16D, 16/16D, 16/16D), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(2/16D, 0/16D, 11/16D, 5/16D, 10/16D, 14/16D), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(11/16D, 0/16D, 2/16D, 14/16D, 10/16D, 5/16D), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(2/16D, 0/16D, 2/16D, 5/16D, 10/16D, 5/16D), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(11/16D, 0/16D, 11/16D, 14/16D, 10/16D, 14/16D), BooleanOp.OR);

		return shape;
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return SHAPE;
	}
}
