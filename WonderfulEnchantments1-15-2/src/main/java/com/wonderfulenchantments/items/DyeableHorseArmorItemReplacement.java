package com.wonderfulenchantments.items;

import net.minecraft.item.*;

public class DyeableHorseArmorItemReplacement extends DyeableHorseArmorItem implements IDyeableArmorItem {
	public DyeableHorseArmorItemReplacement( int armorBonus, String resource ) {
		super( armorBonus, resource, ( new Properties() ).maxStackSize( 1 )
			.group( ItemGroup.MISC ) );
	}

	public DyeableHorseArmorItemReplacement( int armorBonus, net.minecraft.util.ResourceLocation texture ) {
		super( armorBonus, texture, ( new Properties() ).maxStackSize( 1 )
			.group( ItemGroup.MISC ) );
	}

	@Override
	public int getItemEnchantability() {
		return 1;
	}

	@Override
	public boolean isEnchantable( ItemStack stack ) {
		return this.getItemStackLimit( stack ) == 1;
	}

}
