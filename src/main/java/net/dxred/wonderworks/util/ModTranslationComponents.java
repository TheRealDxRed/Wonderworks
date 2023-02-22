package net.dxred.wonderworks.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public final class ModTranslationComponents {
	public static final Component GUIDEBOOK_NAME = Component.translatable(ModNames.GUIDEBOOK);
	public static final Component GUIDEBOOK_DESCRIPTION = Component.translatable("item." + ModNames.NAMESPACE + ModNames.GUIDEBOOK + ".description").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC);
}
