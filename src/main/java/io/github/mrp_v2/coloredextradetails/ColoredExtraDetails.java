package io.github.mrp_v2.coloredextradetails;

import io.github.mrp_v2.coloredextradetails.util.ObjectHolder;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ColoredExtraDetails.ID) public class ColoredExtraDetails
{
    public static final String ID = "colored" + "extra" + "details";
    public static final String DISPLAY_NAME = "Colored Extra Details";

    public ColoredExtraDetails()
    {
        ObjectHolder.registerListeners(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
