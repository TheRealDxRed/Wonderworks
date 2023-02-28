package net.dxred.wonderworks.item;

import java.util.List;

import javax.annotation.Nullable;

import net.dxred.wonderworks.menu.GuidebookMenu;
import net.dxred.wonderworks.util.ModTranslationComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

//TODO: replace entire class with Modonomicon implementation

public class GuidebookItem extends Item implements MenuProvider {
	protected final ContainerData data;

	public GuidebookItem(Properties pProperties) {
		super(pProperties);

		this.data = new ContainerData() {
			@Override
			public int get(int index) {
				return switch (index) {
					default -> 0;
				};
			}

			@Override
			public void set(int index, int value) {
				switch (index) {
					default -> {}
				}
			}

			@Override
			public int getCount() {
				return 0;
			}
		};
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		ItemStack stack = pPlayer.getItemInHand(pUsedHand);

		//TODO open specific page when player is crouching

		//pPlayer.openItemGui(stack, pUsedHand);
		pPlayer.openMenu((MenuProvider)stack.getItem());

		return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, stack);
	}

	@Override
	@Nullable
	public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
		return new GuidebookMenu(pContainerId, pPlayerInventory, this.data);
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("explorers_notebook");
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		pTooltipComponents.add(ModTranslationComponents.GUIDEBOOK_DESCRIPTION);

		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}
}
