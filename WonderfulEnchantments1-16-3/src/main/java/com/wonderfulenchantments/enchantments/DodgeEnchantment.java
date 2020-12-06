package com.wonderfulenchantments.enchantments;

import com.wonderfulenchantments.AttributeHelper;
import com.wonderfulenchantments.AttributeHelper.Attributes;
import com.wonderfulenchantments.RegistryHandler;
import com.wonderfulenchantments.WonderfulEnchantmentHelper;
import com.wonderfulenchantments.WonderfulEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnegative;
import java.util.HashMap;
import java.util.Map;

import static com.wonderfulenchantments.WonderfulEnchantmentHelper.increaseLevelIfEnchantmentIsDisabled;

@Mod.EventBusSubscriber
public class DodgeEnchantment extends Enchantment {
	protected static final AttributeHelper attributeHelper = new AttributeHelper( "ad3e064e-e9f6-4747-a86b-46dc4e2a1444", "KnockBackImmunityTime",
		Attributes.KNOCKBACK_RESISTANCE, AttributeModifier.Operation.ADDITION
	);
	protected static HashMap< Integer, Integer > immunitiesLeft = new HashMap<>(); // holding pair (entityID, ticks left)
	protected static final double dodgeChancePerLevel = 0.125D;

	public DodgeEnchantment() {
		super( Rarity.RARE, EnchantmentType.ARMOR_LEGS, new EquipmentSlotType[]{ EquipmentSlotType.LEGS } );
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public int getMinEnchantability( int level ) {
		return 14 * level + increaseLevelIfEnchantmentIsDisabled( this );
	}

	@Override
	public int getMaxEnchantability( int level ) {
		return this.getMinEnchantability( level ) + 20;
	}

	@SubscribeEvent
	public static void onEntityHurt( LivingDamageEvent event ) {
		LivingEntity livingEntity = event.getEntityLiving();
		ItemStack pants = livingEntity.getItemStackFromSlot( EquipmentSlotType.LEGS );
		int dodgeLevel = EnchantmentHelper.getEnchantmentLevel( RegistryHandler.DODGE.get(), pants );

		if( dodgeLevel > 0 ) {
			if( !( WonderfulEnchantments.RANDOM.nextDouble() < ( double )dodgeLevel * dodgeChancePerLevel ) )
				return;

			spawnParticlesAndPlaySounds( livingEntity );
			setImmunity( livingEntity, WonderfulEnchantmentHelper.secondsToTicks( 2.5D ) * dodgeLevel );
			pants.damageItem( Math.max( ( int )( event.getAmount() * 0.5f ), 1 ), livingEntity,
				( e )->e.sendBreakAnimation( EquipmentSlotType.LEGS )
			);
			event.setCanceled( true );
		}
	}

	@SubscribeEvent
	public static void updateEntitiesKnockBackImmunity( TickEvent.WorldTickEvent event ) {
		for( Map.Entry< Integer, Integer > pair : immunitiesLeft.entrySet() ) {
			Entity entity = event.world.getEntityByID( pair.getKey() );

			if( entity instanceof LivingEntity )
				updateImmunity( ( LivingEntity )entity );

			pair.setValue( Math.max( pair.getValue() - 1, 0 ) );
		}

		immunitiesLeft.values()
			.removeIf( value->( value == 0 ) );
	}

	protected static void setImmunity( LivingEntity livingEntity, @Nonnegative int ticks ) {
		immunitiesLeft.put( livingEntity.getEntityId(), ticks );

		updateImmunity( livingEntity );
	}

	protected static void updateImmunity( LivingEntity livingEntity ) {
		double immunity = ( immunitiesLeft.get( livingEntity.getEntityId() ) > 0 ) ? 1.0D : 0.0D;

		attributeHelper.setValue( immunity )
			.apply( livingEntity );
	}

	protected static void spawnParticlesAndPlaySounds( LivingEntity livingEntity ) {
		ServerWorld world = ( ServerWorld )livingEntity.getEntityWorld();
		for( double d = 0.0D; d < 3.0D; d++ ) {
			Vector3d emitterPosition = new Vector3d( 0.0D, livingEntity.getHeight() * 0.25D * ( d + 1.0D ), 0.0D ).add(
				livingEntity.getPositionVec() );
			for( int i = 0; i < 2; i++ )
				world.spawnParticle( i == 0 ? ParticleTypes.LARGE_SMOKE : ParticleTypes.SMOKE, emitterPosition.getX(), emitterPosition.getY(),
					emitterPosition.getZ(), 16 * i, 0.125D, 0.0D, 0.125D, 0.075D
				);
		}
		world.playSound( null, livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE,
			SoundCategory.AMBIENT, 1.0F, 1.0F
		);
	}
}
