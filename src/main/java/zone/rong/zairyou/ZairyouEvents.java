package zone.rong.zairyou;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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
import net.minecraftforge.registries.IForgeRegistry;
import zone.rong.zairyou.api.client.Bakery;
import zone.rong.zairyou.api.client.IModelOverride;
import zone.rong.zairyou.api.fluid.block.DefaultFluidBlock;
import zone.rong.zairyou.api.fluid.block.tile.PotionFluidTileEntity;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;
import zone.rong.zairyou.api.ore.OreBlock;
import zone.rong.zairyou.api.ore.OreGrade;
import zone.rong.zairyou.api.ore.OreItemBlock;
import zone.rong.zairyou.api.ore.stone.StoneType;
import zone.rong.zairyou.api.tile.MachineTileEntity;
import zone.rong.zairyou.api.client.RenderUtils;
import zone.rong.zairyou.api.util.RecipeUtil;
import zone.rong.zairyou.objects.Materials;

import java.util.Set;

@Mod.EventBusSubscriber
public class ZairyouEvents {

    // Debug
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
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
        GameRegistry.registerTileEntity(PotionFluidTileEntity.class, PotionFluidTileEntity.ID);

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
        Materials.GOLD.setItem(ItemMaterialType.INGOT, Items.GOLD_INGOT);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        Material.REGISTRY.values().forEach(m -> m.getItems().forEach((type, item) -> {
            switch (type) {
                case TINY_DUST:
                    registry.register(RecipeUtil.addShaped(m.getName() + "_tiny_dust_to_dust", false, m.getStack(ItemMaterialType.DUST, 1),
                            "xxx", "xxx", "xxx", 'x', item));
                case SMALL_DUST:
                    registry.register(RecipeUtil.addShaped(m.getName() + "_small_dust_to_dust", false, m.getStack(ItemMaterialType.DUST, 1),
                            "xx", "xx", 'x', item));
                case DUST:
                    ItemStack ingotStack = m.getStack(ItemMaterialType.INGOT, 1);
                    if (!ingotStack.isEmpty()) {
                        RecipeUtil.addSmelting(item, ingotStack, 0.5F);
                    }
                    break;
                default:
                    break;
            }
        }));
    }

    @Deprecated
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onHandlingItemColours(ColorHandlerEvent.Item event) {
        Material.REGISTRY.values().forEach(m -> m.getBlocks().forEach((type, block) -> {
            if (block instanceof IItemColor && m.hasTint(type)) {
                event.getItemColors().registerItemColorHandler((IItemColor) block, block);
            }
        }));
        Material.REGISTRY.values().forEach(m -> m.getItems().forEach((type, item) -> {
            if (item instanceof IItemColor && m.hasTint(type)) {
                event.getItemColors().registerItemColorHandler((IItemColor) item, item);
            }
        }));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onHandlingBlockColours(ColorHandlerEvent.Block event) {
        Material.REGISTRY.values().forEach(m -> {
            m.getBlocks().forEach((type, block) -> {
                if (block instanceof IBlockColor && m.hasTint(type)) {
                    event.getBlockColors().registerBlockColorHandler((IBlockColor) block, block);
                }
            });
            m.getFluids().forEach((type, fluid) -> {
                if (fluid.getBlock() instanceof IBlockColor) {
                    event.getBlockColors().registerBlockColorHandler((IBlockColor) fluid.getBlock(), fluid.getBlock());
                }
            });
        });
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
                .filter(b -> b instanceof IModelOverride)
                .map(b -> (IModelOverride) b)
                .forEach(b -> b.addTextures(stitch));

        Material.REGISTRY.values()
                .stream()
                .flatMap(m -> m.getItems().values().stream())
                .filter(i -> i instanceof IModelOverride)
                .map(i -> (IModelOverride) i)
                .forEach(i -> i.addTextures(stitch));

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
                            .filter(b -> b instanceof IModelOverride)
                            .map(b -> (IModelOverride) b)
                            .forEach(IModelOverride::onModelRegister);
                    m.getItems().values()
                            .stream()
                            .filter(i -> i instanceof IModelOverride)
                            .map(i -> (IModelOverride) i)
                            .forEach(IModelOverride::onModelRegister);
                    /*
                    m.getItems().forEach((t, i) -> {
                        if (i instanceof MaterialItem) {
                            final ModelResourceLocation loc = m.getTexture(t);
                            ModelBakery.registerItemVariants(i, loc);
                            ModelLoader.setCustomMeshDefinition(i, stack -> loc);
                        }
                    });
                     */
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
                .filter(b -> b instanceof IModelOverride)
                .map(b -> (IModelOverride) b)
                .forEach(b -> b.onModelBake(event));

        Material.REGISTRY.values()
                .stream()
                .flatMap(m -> m.getItems().values().stream())
                .filter(i -> i instanceof IModelOverride)
                .map(i -> (IModelOverride) i)
                .forEach(i -> i.onModelBake(event));

        Bakery.shutdown();
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
