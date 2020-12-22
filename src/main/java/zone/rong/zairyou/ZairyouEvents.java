package zone.rong.zairyou;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelFluid;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zone.rong.zairyou.api.fluid.DefaultFluidBlock;
import zone.rong.zairyou.api.fluid.FluidType;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.MaterialType;
import zone.rong.zairyou.api.tile.MachineTileEntity;
import zone.rong.zairyou.api.util.RenderUtils;

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

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        GameRegistry.registerTileEntity(MachineTileEntity.class, MachineTileEntity.ID);

        Material.REGISTRY.values()
                .stream()
                .flatMap(m -> m.getFluids().values().stream())
                .forEach(f -> {
                    if (FluidRegistry.isFluidDefault(f)) {
                        final Block block = f.getBlock();
                        if (block != null) {
                            event.getRegistry().register(block.setRegistryName(Zairyou.ID, "fluid_" + f.getName()));
                        }
                    }
                });

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        Material.REGISTRY.values().forEach(material -> {
            for (final MaterialType type : material.getAllowedTypes()) {
                event.getRegistry().register(material.setItem(type, new MaterialItem(material, type)).setRegistryName(Zairyou.ID, material.getName() + "_" + type.toString()));
            }
            for (final Fluid fluid : material.getFluids().values()) {
                Block fluidBlock = fluid.getBlock();
                event.getRegistry().register(new ItemBlock(fluidBlock).setRegistryName(Zairyou.ID, "fluid_" + fluid.getName()).setCreativeTab(CreativeTabs.MATERIALS));
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
        Material.REGISTRY.values()
                .stream()
                .filter(m -> !m.getFluids().isEmpty())
                .flatMap(m -> m.getFluids().values().stream())
                .forEach(f -> {
                    event.getMap().registerSprite(f.getStill());
                    event.getMap().registerSprite(f.getFlowing());
                });
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegister(ModelRegistryEvent event) {
        Material.REGISTRY.values()
                .forEach(m -> {
                    m.getItems().forEach((t, i) -> {
                                final ModelResourceLocation loc = m.getTexture(t);
                                ModelBakery.registerItemVariants(i, loc);
                                ModelLoader.setCustomMeshDefinition(i, stack -> loc);
                            });
                    m.getFluids().forEach((t, f) -> {
                        Block block = f.getBlock();
                        if (block instanceof DefaultFluidBlock) {
                            ModelLoader.setCustomStateMapper(block, RenderUtils.SIMPLE_STATE_MAPPER.apply(block));
                        }
                    });
                });
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        Material.REGISTRY.values()
                .stream()
                .flatMap(m -> m.getFluids().values().stream())
                .forEach(f -> {
                    Block block = f.getBlock();
                    if (block instanceof DefaultFluidBlock) {
                        ModelFluid modelFluid = new ModelFluid(f);
                        IBakedModel bakedModel = modelFluid.bake(modelFluid.getDefaultState(), DefaultVertexFormats.ITEM, RenderUtils::getTexture);
                        event.getModelRegistry().putObject(RenderUtils.getSimpleModelLocation(block), bakedModel);
                    }
                });
    }

}
