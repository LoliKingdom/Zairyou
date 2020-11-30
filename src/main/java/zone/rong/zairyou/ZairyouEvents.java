package zone.rong.zairyou;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.MaterialType;
import zone.rong.zairyou.api.tile.MachineTileEntity;

@Mod.EventBusSubscriber
public class ZairyouEvents {

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        GameRegistry.registerTileEntity(MachineTileEntity.class, MachineTileEntity.ID);
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        Material.REGISTRY.values().forEach(material -> {
            for (final MaterialType type : material.getAllowedTypes()) {
                event.getRegistry().register(material.setItem(type, new MaterialItem(material, type)).setRegistryName(Zairyou.ID, material.getName() + "_" + type.toString()));
            }
        });
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onHandlingItemColours(ColorHandlerEvent.Item event) {
        Material.REGISTRY.values()
                .forEach(m -> m.getItems().values()
                        .forEach(i -> event.getItemColors().registerItemColorHandler((stack, tintIndex) -> m.getColour(), i)));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegister(ModelRegistryEvent event) {
        Material.REGISTRY.values()
                .forEach(m -> m.getItems().values()
                        .forEach(i -> {
                            final ModelResourceLocation loc = m.getTexture(i.getMaterialType());
                            ModelBakery.registerItemVariants(i, loc);
                            ModelLoader.setCustomMeshDefinition(i, stack -> loc);
                        }));
    }

}
