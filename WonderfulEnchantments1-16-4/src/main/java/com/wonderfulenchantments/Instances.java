package com.wonderfulenchantments;

import com.wonderfulenchantments.curses.FatigueCurse;
import com.wonderfulenchantments.curses.IncompatibilityCurse;
import com.wonderfulenchantments.curses.SlownessCurse;
import com.wonderfulenchantments.curses.VampirismCurse;
import com.wonderfulenchantments.enchantments.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;

public class Instances {
	/*
	// Fishing Rod Enchantments
	public static final RegistryObject< Enchantment > FISHING_FANATIC = ENCHANTMENTS.register( "fishing_fanatic", FanaticEnchantment::new );

	// Sword Enchantments
	public static final RegistryObject< Enchantment > HUMAN_SLAYER = ENCHANTMENTS.register( "human_slayer", HumanSlayerEnchantment::new );
	public static final RegistryObject< Enchantment > PUFFERFISH_VENGEANCE = ENCHANTMENTS.register( "pufferfish_vengeance", PufferfishVengeanceEnchantment::new );
	public static final RegistryObject< Enchantment > LEECH = ENCHANTMENTS.register( "leech", LeechEnchantment::new );

	// Armor Enchantments
	public static final RegistryObject< Enchantment > DODGE = ENCHANTMENTS.register( "dodge", DodgeEnchantment::new );
	public static final RegistryObject< Enchantment > ENLIGHTENMENT = ENCHANTMENTS.register( "enlightenment", EnlightenmentEnchantment::new );
	public static final RegistryObject< Enchantment > PHOENIX_DIVE = ENCHANTMENTS.register( "phoenix_dive", PhoenixDiveEnchantment::new );
	public static final RegistryObject< Enchantment > MAGIC_PROTECTION = ENCHANTMENTS.register( "magic_protection", MagicProtectionEnchantment::new );

	// Shield Enchantments
	public static final RegistryObject< Enchantment > VITALITY = ENCHANTMENTS.register( "vitality", VitalityEnchantment::new );
	public static final RegistryObject< Enchantment > IMMORTALITY = ENCHANTMENTS.register( "immortality", ImmortalityEnchantment::new );
	public static final RegistryObject< Enchantment > ABSORBER = ENCHANTMENTS.register( "absorber", AbsorberEnchantment::new );

	// Tool Enchantments
	public static final RegistryObject< Enchantment > SMELTER = ENCHANTMENTS.register( "smelter", SmelterEnchantment::new );
	public static final RegistryObject< Enchantment > GOTTA_MINE_FAST = ENCHANTMENTS.register( "gotta_mine_fast", GottaMineFastEnchantment::new );
	public static final RegistryObject< Enchantment > TELEKINESIS = ENCHANTMENTS.register( "telekinesis", TelekinesisEnchantment::new );

	// Horse Armor Enchantments
	public static final RegistryObject< Enchantment > SWIFTNESS = ENCHANTMENTS.register( "swiftness", SwiftnessEnchantment::new );
	public static final RegistryObject< Enchantment > HORSE_PROTECTION = ENCHANTMENTS.register( "horse_protection", HorseProtectionEnchantment::new );
	public static final RegistryObject< Enchantment > HORSE_FROST_WALKER = ENCHANTMENTS.register( "horse_frost_walker", HorseFrostWalkerEnchantment::new );

	// Bow Enchantments
	public static final RegistryObject< Enchantment > HUNTER = ENCHANTMENTS.register( "hunter", HunterEnchantment::new );

	// Trident Enchantments
	public static final RegistryObject< Enchantment > ELDER_GUARDIAN_FAVOR = ENCHANTMENTS.register( "elder_guardian_favor", ElderGaurdianFavorEnchantment::new );

	*/

	// Shield Enchantments
	// --
	// --
	public static final AbsorberEnchantment ABSORBER;

	// Curses
	public static final SlownessCurse SLOWNESS;
	public static final FatigueCurse FATIGUE;
	public static final IncompatibilityCurse INCOMPATIBILITY;
	public static final VampirismCurse VAMPIRISM;

	static {
		ABSORBER = new AbsorberEnchantment();

		SLOWNESS = new SlownessCurse();
		FATIGUE = new FatigueCurse();
		INCOMPATIBILITY = new IncompatibilityCurse();
		VAMPIRISM = new VampirismCurse();

		WonderfulEnchantments.CONFIG_HANDLER.register( ModLoadingContext.get() );
	}
}
