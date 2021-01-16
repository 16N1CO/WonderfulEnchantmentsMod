package com.wonderfulenchantments.items;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShieldItem;

public class ShieldItemReplacement extends ShieldItem {
	public ShieldItemReplacement() {
		super( ( new Properties() ).maxDamage( 336 )
			.group( ItemGroup.COMBAT ) );
	}

	@Override
	public int getItemEnchantability() {
		return 1;
	}
}