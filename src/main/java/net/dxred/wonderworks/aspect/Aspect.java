package net.dxred.wonderworks.aspect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import net.dxred.wonderworks.WonderworksMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class Aspect {
	String tag;
	Aspect[] components;
	int color;
	char unicode;
	String unicodeString;
	ResourceLocation image;

	public static HashMap<Integer, Aspect> mixList = new HashMap<Integer, Aspect>();
	public static LinkedHashMap<String, Aspect> aspects = new LinkedHashMap<String, Aspect>();

	public Aspect(String pTag, int pColor, int pUnicode, Aspect[] pComponents) {
		if (aspects.containsKey(pTag)) throw new IllegalArgumentException(pTag + " already registered");
		if (pUnicode > 0xffff) throw new IllegalArgumentException(pUnicode + " must not be larger than 0xffff (65535)");
		this.tag = pTag;
		this.color = pColor;
		this.unicode = (char)(0x0000 + pUnicode);
		this.unicodeString = String.format("\\u%04x", pUnicode);
		this.components = pComponents;
		this.image = new ResourceLocation(WonderworksMod.MODID, "aspect/" + pTag);
		aspects.put(pTag, this);

		if (components != null) {
			int h = ( components[0].getTag() + components[1].getTag() ).hashCode();
			mixList.put(h, this);
		}
	}

	public int getColor() {
		return this.color;
	}

	public Component getName() {
		return Component.translatable("aspects.wonderworks." + tag);
	}

	public String getTag() {
		return this.tag;
	}

	public Aspect[] getComponents() {
		return this.components;
	}

	public char getUnicode() {
		return this.unicode;
	}

	public String getUnicodeAsString() {
		return this.unicodeString;
	}

	public ResourceLocation getImage() {
		return this.image;
	}

	public static Aspect getAspect(String pTag) {
		return aspects.get(pTag);
	}

	public static List<AspectStack> getAllAspects(int pStackSize) {
		Set<String> keys = aspects.keySet();
		Iterator<String> iter = keys.iterator();
		ArrayList<AspectStack> out = new ArrayList<AspectStack>();

		while (iter.hasNext()) {
			String key = iter.next();
			out.add(new AspectStack(aspects.get(key), pStackSize));
		}

		return out;
	}

	public boolean isPrimal() {
		return this.components == null || this.components.length == 0;
	}

	// Primal Aspects
	public static final Aspect AIR = new Aspect("air", 0xffff7e, 0xe000, null);
	public static final Aspect EARTH = new Aspect("earth", 0x56c000, 0xe001, null);
	public static final Aspect FIRE = new Aspect("fire", 0xff5a01, 0xe002, null);
	public static final Aspect WATER = new Aspect("water", 0x3cd4fc, 0xe003, null);
	public static final Aspect ORDER = new Aspect("order", 0xd5d4ec, 0xe004, null);
	public static final Aspect ENTROPY = new Aspect("entropy", 0x404040, 0xe005, null);

	// Compound Aspects (Secondary, Primal + Primal)
	public static final Aspect VOID = new Aspect("void", 0x888888, 0xe006, new Aspect[] { AIR, ENTROPY });
	public static final Aspect LIGHT = new Aspect("light", 0xffffc0, 0xe007, new Aspect[] { AIR, FIRE });
	public static final Aspect MOTION = new Aspect("motion", 0xcdccf4, 0xe008, new Aspect[] { AIR, ORDER });
	public static final Aspect COLD = new Aspect("cold", 0xe1ffff, 0xe009, new Aspect[] { FIRE, ENTROPY });
	public static final Aspect CRYSTAL = new Aspect("crystal", 0x80ffff, 0xe00a, new Aspect[] { EARTH, AIR });
	public static final Aspect METAL = new Aspect("metal", 0xb5b5cd, 0xe00b, new Aspect[] { EARTH, ORDER });
	public static final Aspect LIFE = new Aspect("life", 0xde0005, 0xe00c, new Aspect[] { EARTH, WATER });
	public static final Aspect DEATH = new Aspect("death", 0x6a0005, 0xe00d, new Aspect[] { WATER, ENTROPY });
	public static final Aspect ENERGY = new Aspect("energy", 0xc0ffff, 0xe00e, new Aspect[] { ORDER, FIRE });
	public static final Aspect EXCHANGE = new Aspect("exchange", 0x578357, 0xe00f, new Aspect[] { ENTROPY, ORDER });

	// Compound Aspects (Tertiary, Primal/Secondary + Secondary)
	public static final Aspect MAGIC = new Aspect("magic", 0xcf00ff, 0xe010, new Aspect[] { ENERGY, AIR });
	public static final Aspect AURA = new Aspect("aura", 0xffc0ff, 0xe011, new Aspect[] { MAGIC, AIR });
	public static final Aspect ALCHEMY = new Aspect("alchemy", 0x23ac9d, 0xe012, new Aspect[] { MAGIC, WATER });
	public static final Aspect FLUX = new Aspect("flux", 0x800080, 0xe013, new Aspect[] { ENTROPY, MAGIC });
	public static final Aspect DARKNESS = new Aspect("darkness", 0x222222, 0xe014, new Aspect[] { VOID, LIGHT });
	public static final Aspect ELDRITCH = new Aspect("eldritch", 0x805080, 0xe015, new Aspect[] { VOID, DARKNESS });
	public static final Aspect FLIGHT = new Aspect("flight", 0xe7e7d7, 0xe016, new Aspect[] { AIR, MOTION });
	public static final Aspect PLANT = new Aspect("plant", 0x01ac00, 0xe017, new Aspect[] { LIFE, EARTH });
	public static final Aspect TOOL = new Aspect("tool", 0x4040ee, 0xe018, new Aspect[] { METAL, ENERGY });
	public static final Aspect CRAFT = new Aspect("craft", 0x809d80, 0xe019, new Aspect[] { EXCHANGE, TOOL });
	public static final Aspect MECHANISM = new Aspect("mechanism", 0x8080a0, 0xe01a, new Aspect[] { MOTION, TOOL });
	public static final Aspect TRAP = new Aspect("trap", 0x9a8080, 0xe01b, new Aspect[] { MOTION, ENTROPY });
	public static final Aspect SOUL = new Aspect("soul", 0xebebfb, 0xe01c, new Aspect[] { LIFE, DEATH });
	public static final Aspect MIND = new Aspect("mind", 0xf9967f, 0xe01d, new Aspect[] { FIRE, SOUL });
	public static final Aspect SENSES = new Aspect("senses", 0xc0ffc0, 0xe01e, new Aspect[] { AIR, SOUL });
	public static final Aspect AVERSION = new Aspect("aversion", 0xc05050, 0xe01f, new Aspect[] { SOUL, ENTROPY });
	public static final Aspect PROTECT = new Aspect("protect", 0x00c0c0, 0xe020, new Aspect[] { SOUL, EARTH });
	public static final Aspect DESIRE = new Aspect("desire", 0xe6be44, 0xe021, new Aspect[] { SOUL, VOID });
	public static final Aspect UNDEAD = new Aspect("undead", 0x3a4000, 0xe022, new Aspect[] { MOTION, DEATH });
	public static final Aspect BEAST = new Aspect("beast", 0x9f6409, 0xe023, new Aspect[] { MOTION, LIFE });
	public static final Aspect MAN = new Aspect("man", 0xffd7c0, 0xe024, new Aspect[] { SOUL, LIFE });
}
