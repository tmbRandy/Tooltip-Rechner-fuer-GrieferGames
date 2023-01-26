package de.tmb.tooltipRechner;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@net.minecraftforge.fml.common.Mod("tooltiprechner")
public class Mod
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Mod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRenderTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();


        if(stack.hasTag()) {

            if(stack.getTag().contains("currentAmount") || stack.getTag().contains("stackSize")) {
                int currentAmount;

                if(stack.getTag().contains("currentAmount")) {
                    currentAmount = stack.getTag().getInt("currentAmount") * stack.getCount();
                } else {
                    currentAmount = stack.getTag().getInt("stackSize") * stack.getCount();
                }

                int maxStackSize = stack.getMaxStackSize();

                int DKs = currentAmount / (maxStackSize * 9 * 6);
                int rest = currentAmount - (DKs * maxStackSize * 9 * 6);

                int stacks = rest / maxStackSize;
                int items = rest - (stacks * maxStackSize);

                event.getToolTip().add(new StringTextComponent("§e" + DKs + " DKs §6" + stacks + " Stacks §e" + items + " Items"));
            }
        }
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        InterModComms.sendTo("tooltiprechner", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @net.minecraftforge.fml.common.Mod.EventBusSubscriber(bus= net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

    }
}
