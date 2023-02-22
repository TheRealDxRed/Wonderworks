package net.dxred.wonderworks.registry;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;

public final class ModMaterials {
	public static final Material WORKBENCH = (new Builder(MaterialColor.COLOR_BROWN))
		.notSolidBlocking()
		.build();

	public static final Material BAG_OF_HOLDING = (new Builder(MaterialColor.CLAY))
		.notSolidBlocking()
		.build();

	public static class Builder {
		private PushReaction pushReaction = PushReaction.NORMAL;
		private boolean blocksMotion = true;
		private boolean flammable = false;
		private boolean liquid = false;
		private boolean replaceable = false;
		private boolean solid = true;
		private final MaterialColor color;
		private boolean solidBlocking = true;

		public Builder(MaterialColor pColor) {
			this.color = pColor;
		}

		public Builder liquid() {
			this.liquid = true;
			return this;
		}

		public Builder nonSolid() {
			this.solid = false;
			return this;
		}

		public Builder noCollider() {
			this.blocksMotion = false;
			return this;
		}

		public Builder notSolidBlocking() {
			this.solidBlocking = false;
			return this;
		}

		public Builder flammable() {
			this.flammable = true;
			return this;
		}

		public Builder replaceable() {
			this.replaceable = true;
			return this;
		}

		public Builder destroyOnPush() {
			this.pushReaction = PushReaction.DESTROY;
			return this;
		}

		public Builder notPushable() {
			this.pushReaction = PushReaction.BLOCK;
			return this;
		}

		public Material build() {
			return new Material(color, liquid, solid, blocksMotion, solidBlocking, flammable, replaceable, pushReaction);
		}
	}
}
