package zone.rong.zairyou;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zone.rong.zairyou.api.fluid.FluidType;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.MaterialType;
import zone.rong.zairyou.api.tile.MachineTileEntity;

@Mod.EventBusSubscriber
public class ZairyouEvents {

    // Debug
    @SubscribeEvent
    public static void onToolTipShown(ItemTooltipEvent event) {
        if (event.getFlags().isAdvanced() && event.getItemStack().getItem() instanceof MaterialItem) {
            MaterialItem matItem = (MaterialItem) event.getItemStack().getItem();
            for (String ore : matItem.getOreNames()) {
                event.getToolTip().add(ore);
            }
        }
    }

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
        Material.REGISTRY.values().forEach(m -> m.getItems().forEach((t, i) -> {
                            if (m.hasTint(t)) {
                                if (!t.hasOverlayTexture()) {
                                    event.getItemColors().registerItemColorHandler((stack, tintIndex) -> m.getColour(), i);
                                } else {
                                    event.getItemColors().registerItemColorHandler((stack, tintIndex) -> tintIndex == 0 ? -1 : m.getColour(), i);
                                }
                            }
                        }));
    }

    @SubscribeEvent
    public static void onTextureStitching(TextureStitchEvent.Pre event) {
        // for (FluidType fluidType : FluidType.VALUES) {
            // event.getMap().registerSprite(fluidType.getStillTexture());
            // event.getMap().registerSprite(fluidType.getFlowingTexture());
        // }
        event.getMap().registerSprite(FluidType.LIQUID.getStillTexture());
        event.getMap().registerSprite(FluidType.LIQUID.getFlowingTexture());
        event.getMap().registerSprite(FluidType.MOLTEN.getStillTexture());
        event.getMap().registerSprite(FluidType.MOLTEN.getFlowingTexture());
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
