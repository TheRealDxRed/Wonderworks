package net.dxred.wonderworks.tooltip;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Matrix4f;

import net.dxred.wonderworks.WonderworksMod;
import net.dxred.wonderworks.aspect.AspectStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public class AspectClientTooltipComponent implements ClientTooltipComponent {
	private final int ASPECTS_PER_ROW = 6;
	private final AspectTooltipComponent component;
	private final int scale = 2 * Minecraft.getInstance().font.lineHeight;
	private final int spacing = scale + 2;

	public AspectClientTooltipComponent(AspectTooltipComponent pComponent) {
		this.component = pComponent;
	}

	@Override
	public int getHeight() {
		return this.spacing * (this.component.aspects.size() / ASPECTS_PER_ROW);
	}

	@Override
	public int getWidth(Font pFont) {
		return this.spacing * (this.component.aspects.size() % (ASPECTS_PER_ROW + 1));
	}

	@Override
	public void renderImage(Font pFont, int pMouseX, int pMouseY, PoseStack pPoseStack, ItemRenderer pItemRenderer, int pBlitOffset) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		for (int i = 0; i < this.component.aspects.size(); i++) {
			AspectStack aStack = this.component.aspects.get(i);
			ResourceLocation texture = WonderworksMod.resourceLocation("textures/aspect/" + aStack.getAspect().getTag() + ".png");
			int count = aStack.getCount();
			int draw_x = pMouseX + (i % ASPECTS_PER_ROW) * this.spacing;
			int draw_y = pMouseY + (i / ASPECTS_PER_ROW) * this.spacing;
			float[] color_channels = colorIntToFloat(aStack.getAspect().getColor());

			RenderSystem.setShaderColor(color_channels[0], color_channels[1], color_channels[2], 1.0f);
			RenderSystem.setShaderTexture(0, texture);
			GuiComponent.blit(pPoseStack, draw_x, draw_y, pBlitOffset, 0, 0, this.scale, this.scale, this.scale, this.scale);

			pPoseStack.pushPose();
			pPoseStack.translate(0.0D, 0.0D, 400.0D);

			if (count > 1) {
				pFont.draw(
					pPoseStack,
					"" + count,
					draw_x + 13,
					draw_y + 13,
					0x7f7f7f
				);

				pFont.draw(
					pPoseStack,
					Component.literal("" + count),
					draw_x + 12,
					draw_y + 12,
					0xffffff
				);
			}
			
			pPoseStack.popPose();
		}
	}

	@Override
	public void renderText(Font pFont, int pX, int pY, Matrix4f pMatrix4f, BufferSource pBufferSource) {

	}

	private static float[] colorIntToFloat(int pColor) {
		float[] outColor = new float[] {
			(float)((pColor >> 16) & 0xff) / 255.0f,
			(float)((pColor >> 8) & 0xff) / 255.0f,
			(float)(pColor & 0xff) / 255.0f
		};

		return outColor;
	}

	public static record AspectTooltipComponent(List<AspectStack> aspects, int count) implements TooltipComponent { }
}
