package de.tmb.tooltipRechner;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@net.minecraftforge.fml.common.Mod(modid = Mod.MODID, name = Mod.NAME, version = Mod.VERSION)
public class Mod
{

    public static final String MODID = "tooltiprechner";
    public static final String NAME = "Tooltip Rechner";
    public static final String VERSION = "1.12.2-1.0";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(Mod.this);
    }

    @SubscribeEvent
    public void onRenderTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();


        if(stack.hasTagCompound()) {

            if(stack.getTagCompound().hasKey("currentAmount") || stack.getTagCompound().hasKey("stackSize")) {
                int currentAmount;

                if(stack.getTagCompound().hasKey("currentAmount")) {
                    currentAmount = stack.getTagCompound().getInteger("currentAmount") * stack.getCount();
                } else {
                    currentAmount = stack.getTagCompound().getInteger("stackSize") * stack.getCount();
                }

                int maxStackSize = stack.getMaxStackSize();

                int DKs = currentAmount / (maxStackSize * 9 * 6);
                int rest = currentAmount - (DKs * maxStackSize * 9 * 6);

                int stacks = rest / maxStackSize;
                int items = rest - (stacks * maxStackSize);

                event.getToolTip().add("§e" + DKs + " DKs §6" + stacks + " Stacks §e" + items + " Items");
            }
        }
    }
}
