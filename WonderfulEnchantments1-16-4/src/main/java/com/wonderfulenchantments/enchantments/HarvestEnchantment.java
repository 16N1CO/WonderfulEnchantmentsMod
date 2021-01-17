package com.wonderfulenchantments.enchantments;

import com.mlib.config.DoubleConfig;
import com.mlib.config.IntegerConfig;
import com.wonderfulenchantments.Instances;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Enchantment that automatically replants seeds and gives a chance to improve nearby crops. */
@Mod.EventBusSubscriber
public class HarvestEnchantment extends WonderfulEnchantment {
	public final IntegerConfig range;
	public final DoubleConfig growChance;

	public HarvestEnchantment() {
		super( Rarity.UNCOMMON, EnchantmentType.DIGGER, EquipmentSlotType.MAINHAND, "Harvest" );
		String range_comment = "Range increase per enchantment level. (per block in x-axis and z-axis)";
		String grow_comment = "Chance for increasing age of nearby crops. (calculated for each crop separately)";
		this.range = new IntegerConfig( "range", range_comment, false, 1, 1, 3 );
		this.growChance = new DoubleConfig( "grow_chance", grow_comment, false, 0.05, 0.0, 1.0 );
		this.enchantmentGroup.addConfigs( this.range, this.growChance );

		setMaximumEnchantmentLevel( 3 );
		setDifferenceBetweenMinimumAndMaximum( 30 );
		setMinimumEnchantabilityCalculator( level->( 10 * level ) );
	}

	@Override
	public boolean canApply( ItemStack stack ) {
		return stack.getItem() instanceof HoeItem && super.canApply( stack );
	}

	/** Adding possibility to harvest crops with right click. */
	@SubscribeEvent
	public static void onRightClick( PlayerInteractEvent.RightClickBlock event ) {
		ItemStack itemStack = event.getItemStack();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel( Instances.HARVEST, itemStack );
		PlayerEntity player = event.getPlayer();
		BlockPos position = event.getPos();
		if( enchantmentLevel <= 0 )
			return;

		BlockState blockState = player.world.getBlockState( position );
		if( !( blockState.getBlock() instanceof CropsBlock ) )
			return;

		CropsBlock crops = ( CropsBlock )blockState.getBlock();
		if( !crops.isMaxAge( blockState ) )
			return;

		crops.harvestBlock( player.world, player, position, blockState, null, itemStack );
		player.world.playSound( null, position, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.AMBIENT, 0.25f, 0.5f );
	}
}
