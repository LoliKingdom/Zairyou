package zone.rong.zairyou;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
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
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.IMaterialBlock;
import zone.rong.zairyou.api.material.type.ItemMaterialType;
import zone.rong.zairyou.api.ore.OreBlock;
import zone.rong.zairyou.api.ore.OreGrade;
import zone.rong.zairyou.api.ore.OreItemBlock;
import zone.rong.zairyou.api.ore.stone.StoneType;
import zone.rong.zairyou.api.tile.MachineTileEntity;
import zone.rong.zairyou.api.client.RenderUtils;
import zone.rong.zairyou.objects.Materials;

import java.util.Set;

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

        Material.REGISTRY.forEach((name, material) -> {
            material.getBlocks().forEach((type, block) -> event.getRegistry().register(block.setRegistryName(Zairyou.ID, name + "_" + type.toString())));
            for (final Fluid fluid : material.getFluids().values()) {
                if (FluidRegistry.isFluidDefault(fluid)) {
                    final Block block = fluid.getBlock();
                    if (block != null) {
                        event.getRegistry().register(block.setRegistryName(Zairyou.ID, "fluid_" + fluid.getName()));
                    }
                }
            }
        });

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        Material.REGISTRY.forEach((name, material) -> {
            for (final ItemMaterialType type : material.getAllowedItemTypes()) {
                event.getRegistry().register(material.setItem(type, new MaterialItem(material, type).setRegistryName(Zairyou.ID, name + "_" + type.toString())));
            }
            for (final Fluid fluid : material.getFluids().values()) {
                Block fluidBlock = fluid.getBlock();
                event.getRegistry().register(new ItemBlock(fluidBlock).setRegistryName(Zairyou.ID, "fluid_" + fluid.getName()).setCreativeTab(CreativeTabs.MATERIALS));
            }
            material.getBlocks().forEach((type, block) -> {
                if (type == BlockMaterialType.ORE) {
                    event.getRegistry().register(new OreItemBlock((OreBlock) block).setRegistryName(block.getRegistryName()));
                } else {
                    event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
                }
            });
        });
        Materials.REDSTONE.setItem(ItemMaterialType.DUST, Items.REDSTONE);
        Materials.GOLD.setItem(ItemMaterialType.INGOT, Items.GOLD_INGOT);
    }

    @Deprecated
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    // TODO - move this to custom baking, without handling item colours, we tint the BakedQuads straight.
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
    @SideOnly(Side.CLIENT)
    public static void onHandlingBlockColours(ColorHandlerEvent.Block event) {
        // event.getBlockColors().registerBlockColorHandler(((state, world, pos, tintIndex) -> tintIndex == 0 ? -1 : Materials.GOLD.getColour()), temporaryBlock);
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

        final Set<ResourceLocation> stitch = new ObjectOpenHashSet<>();

        Material.REGISTRY.values()
                .stream()
                .flatMap(m -> m.getBlocks().values().stream())
                .filter(b -> b instanceof IMaterialBlock)
                .map(b -> (IMaterialBlock) b)
                .forEach(b -> b.addTextures(stitch));

        for (final StoneType stoneType : StoneType.VALUES) {
            event.getMap().registerSprite(stoneType.getBaseTexture());
        }

        for (final OreGrade oreGrade : OreGrade.VALUES) {
            event.getMap().registerSprite(oreGrade.getTextureLocation());
        }

        stitch.forEach(rl -> event.getMap().registerSprite(rl));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegister(ModelRegistryEvent event) {
        Material.REGISTRY.values()
                .forEach(m -> {
                    m.getBlocks().values()
                            .stream()
                            .filter(b -> b instanceof IMaterialBlock)
                            .map(b -> (IMaterialBlock) b)
                            .forEach(IMaterialBlock::onModelRegister);
                    m.getItems().forEach((t, i) -> {
                        if (i instanceof MaterialItem) {
                            final ModelResourceLocation loc = m.getTexture(t);
                            ModelBakery.registerItemVariants(i, loc);
                            ModelLoader.setCustomMeshDefinition(i, stack -> loc);
                        }
                    });
                    m.getFluids().forEach((t, f) -> {
                        Block block = f.getBlock();
                        if (block instanceof DefaultFluidBlock) {
                            ModelLoader.setCustomStateMapper(block, RenderUtils.SIMPLE_STATE_MAPPER.apply(block));
                        }
                    });
                });
        /*
        ModelLoader.setCustomStateMapper(temporaryBlock, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return RenderUtils.getSimpleModelLocation(temporaryBlock);
            }
        });
        // Item temp = Item.getItemFromBlock(temporaryBlock);
        // ModelLoader.setCustomModelResourceLocation(temp, 0, RenderUtils.getSimpleModelLocation(temp));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(temporaryBlock), 0, RenderUtils.getSimpleModelLocation(temporaryBlock));
         */
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
        Material.REGISTRY.values()
                .stream()
                .flatMap(m -> m.getBlocks().values().stream())
                .filter(b -> b instanceof IMaterialBlock)
                .map(b -> (IMaterialBlock) b)
                .forEach(b -> b.onModelBake(event));
        /*
        Bakery temporary = Bakery.INSTANCE
                .template(Bakery.ModelType.SINGLE_OVERLAY)
                .prepareTexture("layer0", StoneType.ANDESITE.getBaseTexture())
                .prepareTexture("layer1", Zairyou.ID + ":blocks/grade/normal")
                .tint(1, Materials.GOLD.getColour());
        temporary.bake(true, true);
        event.getModelRegistry().putObject(RenderUtils.getSimpleModelLocation(temporaryBlock), temporary.receiveBlock());
         */
        // event.getModelRegistry().putObject(RenderUtils.getSimpleModelLocation(Item.getItemFromBlock(temporaryBlock)), temporary.receiveItem());
        /*event.getModelRegistry().putObject(RenderUtils.getSimpleModelLocation(temporaryBlock),
                RenderUtils.textureAndBakeBlock(RenderUtils.singleOverlay,
                        ImmutableMap.of(
                                "particle", StoneType.ANDESITE.getBaseTexture().toString(),
                                "stone", StoneType.ANDESITE.getBaseTexture().toString(),
                                "ore", Zairyou.ID + ":blocks/grade/normal")));

         */
    }

}
