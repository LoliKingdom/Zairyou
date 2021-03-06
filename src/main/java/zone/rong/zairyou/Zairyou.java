package zone.rong.zairyou;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.objects.ZairyouBlocks;
import zone.rong.zairyou.objects.ZairyouItems;
import zone.rong.zairyou.objects.Materials;

@Mod(modid = Zairyou.ID, name = Zairyou.NAME, version = "0.1")
public class Zairyou {

    public static final String ID = "zairyou";
    public static final String NAME = "Zairyou";

    public static final Logger LOGGER = LogManager.getLogger(ID);

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        Materials.init();
        ZairyouBlocks.init();
        ZairyouItems.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // TFCFallables.init();
        ZairyouItems.oreDictInit();
        Material.all()
                .stream()
                .map(Material::getItems)
                .flatMap(m -> m.values().stream())
                .filter(s -> s.getItem() instanceof MaterialItem)
                .map(s -> (MaterialItem) s.getItem())
                .forEach(i -> {
                    for (final String ore : i.getOreNames()) {
                        OreDictionary.registerOre(ore, i);
                    }
                });
    }

}
