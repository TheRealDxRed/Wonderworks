package net.dxred.wonderworks.block;

import java.util.List;

import javax.annotation.Nullable;

import net.dxred.wonderworks.block.entity.BagOfHoldingBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BagOfHoldingBlock extends BaseEntityBlock {
	private static final VoxelShape SHAPE = makeShape();
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
	public static final ResourceLocation CONTENTS = new ResourceLocation("contents");

	public BagOfHoldingBlock(Properties pProperties) {
		super(pProperties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.FALSE));
	}

	private static VoxelShape makeShape() {
		VoxelShape shape = Shapes.empty();

		shape = Shapes.join(shape, Shapes.box(0/16D, 0/16D, 0/16D, 16/16D, 10/16D, 16/16D), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(3/16D, 10/16D, 3/16D, 13/16D, 14/16D, 13/16D), BooleanOp.OR);

		return shape;
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		if (pLevel.isClientSide()) {
			return InteractionResult.SUCCESS;
		} else {
			ItemStack useItem = pPlayer.getUseItem();
			BlockEntity entity = pLevel.getBlockEntity(pPos);
			if (entity instanceof BagOfHoldingBlockEntity bagEntity) {
				if (pPlayer.isCrouching() && useItem.equals(ItemStack.EMPTY, false)) {
					ItemStack stack = new ItemStack(this.asItem());
					entity.saveToItem(stack);
					if (bagEntity.hasCustomName()) {
						stack.setHoverName(bagEntity.getCustomName());
					}

					pPlayer.addItem(stack);
					pLevel.removeBlock(pPos, false);

					return InteractionResult.CONSUME;
				}

				pPlayer.openMenu((MenuProvider)entity);
			}

			return InteractionResult.CONSUME;
		}
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return SHAPE;
	}

	@Override
	public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		if (!pState.is(pNewState.getBlock())) {
			BlockEntity entity = pLevel.getBlockEntity(pPos);
			if (entity instanceof Container) {
				//Containers.dropContents(pLevel, pPos, (Container)entity);
				pLevel.updateNeighbourForOutputSignal(pPos, this);
			}

			super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
		}
	}

	@Override
	public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
		BlockEntity entity = pLevel.getBlockEntity(pPos);
		if (entity instanceof BagOfHoldingBlockEntity) {
			((BagOfHoldingBlockEntity)entity).recheckOpen();
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BagOfHoldingBlockEntity(pPos, pState);
	}

	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.MODEL;
	}

	@Override
	public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
		if (pStack.hasCustomHoverName()) {
			BlockEntity entity = pLevel.getBlockEntity(pPos);
			if (entity instanceof BagOfHoldingBlockEntity) {
				((BagOfHoldingBlockEntity)entity).setCustomName(pStack.getHoverName());
			}
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState pState) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(pLevel.getBlockEntity(pPos));
	}

	@Override
	public BlockState rotate(BlockState pState, Rotation pRotation) {
		return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState pState, Mirror pMirror) {
		return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
		pBuilder.add(FACING, OPEN);
	}

	@Override
	public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
		BlockEntity entity = pLevel.getBlockEntity(pPos);
		if (entity instanceof BagOfHoldingBlockEntity bagEntity) {
			if (!pLevel.isClientSide && pPlayer.isCreative() && !bagEntity.isEmpty()) {
				ItemStack stack = new ItemStack(this.asItem());
				entity.saveToItem(stack);
				if (bagEntity.hasCustomName()) {
					stack.setHoverName(bagEntity.getCustomName());
				}

				ItemEntity itemEntity = new ItemEntity(pLevel, (double)pPos.getX() + 0.5d, (double)pPos.getY() + 0.5d, (double)pPos.getX() + 0.5d, stack);
				itemEntity.setDefaultPickUpDelay();
				pLevel.addFreshEntity(itemEntity);
			}
		}

		super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
	}

	@Override
	public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
		BlockEntity entity = pBuilder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);

		if (entity instanceof BagOfHoldingBlockEntity bagEntity) {
			pBuilder = pBuilder.withDynamicDrop(CONTENTS, (context, consumer) -> {
				for (int i = 0; i < bagEntity.getContainerSize(); i++) {
					consumer.accept(bagEntity.getItem(i));
				}
			});
		}

		return super.getDrops(pState, pBuilder);
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
		super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
		CompoundTag tag = BlockItem.getBlockEntityData(pStack);

		if (tag != null) {
			if (tag.contains("Items", 9)) {
				NonNullList<ItemStack> stacks = NonNullList.withSize(BagOfHoldingBlockEntity.SLOTS, ItemStack.EMPTY);
				ContainerHelper.loadAllItems(tag, stacks);

				int i = 0;
				int j = 0;

				for (ItemStack stack : stacks) {
					if (!stack.isEmpty()) {
						j++;

						if (i <= 4) {
							i++;

							MutableComponent component = stack.getHoverName().copy();
							component.append(" x").append(String.valueOf(stack.getCount()));
							pTooltip.add(component);
						}
					}
				}

				if (j - i > 0) {
					pTooltip.add(Component.translatable("container.bag_of_holding.more", j - i).withStyle(ChatFormatting.ITALIC));
				}
			}
		}
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
	}
}
