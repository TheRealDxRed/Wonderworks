package net.dxred.wonderworks.block.entity;

import net.dxred.wonderworks.block.BagOfHoldingBlock;
import net.dxred.wonderworks.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;

public class BagOfHoldingBlockEntity extends BaseContainerBlockEntity {
	// 9 slots times 5 rows
	public static final int SLOTS = 9*6;

	private NonNullList<ItemStack> items = NonNullList.withSize(SLOTS, ItemStack.EMPTY);
	private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
		protected void onOpen(Level pLevel, BlockPos pPos, BlockState pState) {
			//TODO custom open/close sound
			//BagOfHoldingBlockEntity.this.playSound(pState, ModSoundEvents.BAG_OF_HOLDING_OPEN);
			BagOfHoldingBlockEntity.this.playSound(pState, SoundEvents.ARMOR_EQUIP_LEATHER);
			BagOfHoldingBlockEntity.this.updateBlockState(pState, true);
		}

		protected void onClose(Level pLevel, BlockPos pPos, BlockState pState) {
			//TODO custom open/close sound
			//BagOfHoldingBlockEntity.this.playSound(pState, ModSoundEvents.BAG_OF_HOLDING_CLOSE);
			BagOfHoldingBlockEntity.this.playSound(pState, SoundEvents.ARMOR_EQUIP_LEATHER);
			BagOfHoldingBlockEntity.this.updateBlockState(pState, false);
		}

		protected void openerCountChanged(Level pLevel, BlockPos pPos, BlockState pState, int pCount, int pOpenCount) {}

		protected boolean isOwnContainer(Player pPlayer) {
			if (pPlayer.containerMenu instanceof ChestMenu) {
				Container container = ((ChestMenu)pPlayer.containerMenu).getContainer();
				return container == BagOfHoldingBlockEntity.this;
			} else {
				return false;
			}
		}
	};

	public BagOfHoldingBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(ModBlockEntityTypes.BAG_OF_HOLDING.get(), pPos, pBlockState);
	}

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		ContainerHelper.saveAllItems(pTag, this.items);
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(pTag, this.items);
	}

	@Override
	public int getContainerSize() {
		return SLOTS;
	}

	protected NonNullList<ItemStack> getItems() {
		return this.items;
	}

	protected void setItems(NonNullList<ItemStack> pItems) {
		this.items = pItems;
	}

	protected Component getDefaultName() {
		return Component.translatable("container.bag_of_holding");
	}

	protected AbstractContainerMenu createMenu(int pId, Inventory pPlayer) {
		return ChestMenu.sixRows(pId, pPlayer, this);
	}

	@Override
	public void startOpen(Player pPlayer) {
		if (!this.remove && !pPlayer.isSpectator()) {
			this.openersCounter.incrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
		}
	}

	@Override
	public void stopOpen(Player pPlayer) {
		if (!this.remove && !pPlayer.isSpectator()) {
			this.openersCounter.decrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
		}
	}

	public void recheckOpen() {
		if (!this.remove) {
			this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
		}
	}

	void updateBlockState(BlockState pState, boolean pOpen) {
		this.level.setBlock(this.getBlockPos(), pState.setValue(BagOfHoldingBlock.OPEN, Boolean.valueOf(pOpen)), 3);
	}

	void playSound(BlockState pState, SoundEvent pSound) {
		Vec3i v3 = pState.getValue(BagOfHoldingBlock.FACING).getNormal();
		double x = (double)this.worldPosition.getX() + 0.5d + (double)v3.getX() / 2.0d;
		double y = (double)this.worldPosition.getY() + 0.5d + (double)v3.getY() / 2.0d;
		double z = (double)this.worldPosition.getZ() + 0.5d + (double)v3.getZ() / 2.0d;
		this.level.playSound((Player)null, x, y, z, pSound, SoundSource.BLOCKS, 0.5f, this.level.random.nextFloat() * 1.0f + 0.9f);
	}

	@Override
	public boolean isEmpty() {
		return this.getItems().stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public ItemStack getItem(int pSlot) {
		return this.getItems().get(pSlot);
	}

	@Override
	public ItemStack removeItem(int pSlot, int pAmount) {
		ItemStack stack = ContainerHelper.removeItem(this.getItems(), pSlot, pAmount);
		if (stack.isEmpty()) {
			this.setChanged();
		}

		return stack;
	}

	@Override
	public ItemStack removeItemNoUpdate(int pSlot) {
		return ContainerHelper.takeItem(this.getItems(), pSlot);
	}

	@Override
	public void setItem(int pSlot, ItemStack pStack) {
		this.getItems().set(pSlot, pStack);
		if (pStack.getCount() > this.getMaxStackSize()) {
			pStack.setCount(this.getMaxStackSize());
		}

		this.setChanged();
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		} else {
			return !(pPlayer.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) > 64.0D);
		}
	}

	@Override
	public void clearContent() {
		this.getItems().clear();
	}
}
