package com.wonderfulenchantments.enchantments;

import com.wonderfulenchantments.ConfigHandler;
import com.wonderfulenchantments.RegistryHandler;
import com.wonderfulenchantments.WonderfulEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.UUID;

@Mod.EventBusSubscriber
public class DodgeEnchantment extends Enchantment {
	protected static final UUID MODIFIER_UUID = UUID.fromString( "ad3e064e-e9f6-4747-a86b-46dc4e2a1444" );
	protected static final String MODIFIER_NAME = "KnockBackImmunityTime";
	protected static HashMap< String, Integer > modifiers = new HashMap<>();

	public DodgeEnchantment() {
		super( Rarity.RARE, EnchantmentType.ARMOR_LEGS, new EquipmentSlotType[]{ EquipmentSlotType.LEGS } );
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public int getMinEnchantability( int level ) {
		return 14 * ( level ) + ( ConfigHandler.Values.DODGE.get() ? 0 : RegistryHandler.disableEnchantmentValue );
	}

	@Override
	public int getMaxEnchantability( int level ) {
		return this.getMinEnchantability( level ) + 20;
	}

	@SubscribeEvent
	public static void onEntityHurt( LivingDamageEvent event ) {
		LivingEntity entityLiving = event.getEntityLiving();
		ItemStack pants = entityLiving.getItemStackFromSlot( EquipmentSlotType.LEGS );
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel( RegistryHandler.DODGE.get(), pants );

		if( enchantmentLevel > 0 ) {
			if( !( WonderfulEnchantments.RANDOM.nextDouble() < ( double )enchantmentLevel * 0.125D ) )
				return;

			ServerWorld world = ( ServerWorld )entityLiving.getEntityWorld();
			for( double d = 0.0D; d < 3.0D; d++ ) {
				world.spawnParticle( ParticleTypes.SMOKE, entityLiving.getPosX(), entityLiving.getPosYHeight( 0.25D * ( d + 1.0D ) ), entityLiving.getPosZ(), 32, 0.125D, 0.0D, 0.125D, 0.075D );
				world.spawnParticle( ParticleTypes.LARGE_SMOKE, entityLiving.getPosX(), entityLiving.getPosYHeight( 0.25D * ( d + 1.0D ) ), entityLiving.getPosZ(), 16, 0.125D, 0.0D, 0.125D, 0.025D );
			}
			world.playSound( null, entityLiving.getPosX(), entityLiving.getPosY(), entityLiving.getPosZ(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.AMBIENT, 1.0F, 1.0F );

			pants.damageItem( ( int )event.getAmount(), entityLiving, ( e )->e.sendBreakAnimation( EquipmentSlotType.LEGS ) );
			if( entityLiving instanceof PlayerEntity )
				setImmunity( ( PlayerEntity )( entityLiving ), 50 * enchantmentLevel );

			event.setCanceled( true );
		}
	}

	@SubscribeEvent
	public static void checkPlayersKnockBackImmunity( TickEvent.PlayerTickEvent event ) {
		PlayerEntity player = event.player;
		String nickname = player.getDisplayName().getString();

		if( !modifiers.containsKey( nickname ) )
			modifiers.put( nickname, 0 );

		applyImmunity( player );

		modifiers.replace( nickname, Math.max( modifiers.get( nickname ) - 1, 0 ) );
	}

	private static void setImmunity( PlayerEntity player, int ticks ) {
		String nickname = player.getDisplayName().getString();

		if( !modifiers.containsKey( nickname ) )
			modifiers.put( nickname, 0 );

		modifiers.replace( nickname, ticks );

		applyImmunity( player );
	}

	private static void applyImmunity( PlayerEntity player ) {
		String nickname = player.getDisplayName().getString();

		IAttributeInstance resistance = player.getAttribute( SharedMonsterAttributes.KNOCKBACK_RESISTANCE );
		resistance.removeModifier( MODIFIER_UUID );
		AttributeModifier modifier = new AttributeModifier( MODIFIER_UUID, MODIFIER_NAME, ( modifiers.get( nickname ) > 0 ) ? 1.0D : 0.0D, AttributeModifier.Operation.ADDITION );
		resistance.applyModifier( modifier );
	}
}
