package net.dxred.wonderworks.menu;

import net.dxred.wonderworks.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

public class GuidebookMenu extends AbstractContainerMenu {
	public GuidebookMenu(int pContainedId, Inventory pInventory, FriendlyByteBuf pFriendlyByteBuf) {
		this(pContainedId, pInventory, new SimpleContainerData(2));
	}

	public GuidebookMenu(int pContainerId, Inventory pInventory, ContainerData pContainerData) {
		super(ModMenuTypes.GUIDEBOOK.get(), pContainerId);
	}

	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		//TODO what?
		return pPlayer.getInventory().getItem(pIndex);
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return false;
	}
}
