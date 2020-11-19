package com.wonderfulenchantments;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod( modid = WonderfulEnchantments.MOD_ID, name = WonderfulEnchantments.NAME, version = WonderfulEnchantments.VERSION )
public class WonderfulEnchantments {
    @Mod.Instance
    public static WonderfulEnchantments instance;

    @Mod.EventHandler
    public void init( FMLInitializationEvent event ) {
        MinecraftForge.EVENT_BUS.register( this );
    }

    public static final String
        MOD_ID              = "wonderful_enchantments",
        NAME                = "Wonderful Enchantments",
        VERSION             = "0.6.0";

    public static final Logger LOGGER = LogManager.getLogger();
    public static final Random RANDOM = new Random();
}