package zone.rong.zairyou;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import zone.rong.zairyou.api.block.IItemBlockProvider;
import zone.rong.zairyou.api.block.IMetaBlock;
import zone.rong.zairyou.api.block.INestedMetaBlock;
import zone.rong.zairyou.api.client.Bakery;
import zone.rong.zairyou.api.client.IModelOverride;
import zone.rong.zairyou.api.fluid.block.DefaultFluidBlock;
import zone.rong.zairyou.api.item.BasicItem;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.MaterialProperty;
import zone.rong.zairyou.api.material.type.ItemMaterialType;
import zone.rong.zairyou.api.ore.OreGrade;
import zone.rong.zairyou.api.ore.stone.StoneType;
import zone.rong.zairyou.api.client.RenderUtils;

import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber
public class ZairyouEvents {

    // Debug
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onToolTipShown(ItemTooltipEvent event) {
        if (event.getFlags().isAdvanced()) {
            Item item = event.getItemStack().getItem();
            if (item instanceof MaterialItem) {
                MaterialItem matItem = (MaterialItem) event.getItemStack().getItem();
                for (String ore : matItem.getOreNames()) {
                    event.getToolTip().add(ore);
                }
            } else if (item instanceof BasicItem) {
                for (int id : OreDictionary.getOreIDs(event.getItemStack())) {
                    event.getToolTip().add(OreDictionary.getOreName(id));
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        Material.all((name, material) -> {
            material.getBlocks().forEach((type, block) -> {
                if (block instanceof IMetaBlock) {
                    ((IMetaBlock<?>) block).alignBlockStateContainer();
                    if (!event.getRegistry().containsValue(block)) {
                        event.getRegistry().register(block);
                    }
                } else {
                    event.getRegistry().register(block);
                }
                if (block instanceof INestedMetaBlock) {
                    INestedMetaBlock<?, ?> nextBlock = ((INestedMetaBlock<?, ?>) block).getNextBlock();
                    while (nextBlock != null) {
                        event.getRegistry().register((Block) nextBlock);
                        nextBlock = nextBlock.getNextBlock();
                    }
                }
            });
            for (final Fluid fluid : material.getFluids().values()) {
                if (FluidRegistry.isFluidDefault(fluid)) {
                    final Block block = fluid.getBlock();
                    if (block != null) {
                        event.getRegistry().register(block.setRegistryName(Zairyou.ID, "fluid_" + fluid.getName()));
                    }
                }
            }
        });
        MaterialProperty.frozen = true;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        BasicItem.REGISTRY.values().forEach(registry::register);
        Material.all((name, material) -> {
            for (final Map.Entry<ItemMaterialType, ItemStack> entry : material.getItems().entrySet()) {
                if (entry.getValue().isEmpty()) {
                    ItemMaterialType type = entry.getKey();
                    Item item = new MaterialItem(material, type).setRegistryName(Zairyou.ID, name + "_" + type.getId());
                    registry.register(item);
                    material.setItem(type, item);
                }
            }
            material.getBlocks().forEach((type, block) -> {
                if (block instanceof IItemBlockProvider) {
                    if (!event.getRegistry().containsValue(((IItemBlockProvider<?>) block).getItemBlock())) {
                        registry.register(((IItemBlockProvider<?>) block).getItemBlock());
                    }
                    if (block instanceof INestedMetaBlock) {
                        INestedMetaBlock<?, ?> nextBlock = ((INestedMetaBlock<?, ?>) block).getNextBlock();
                        while (nextBlock != null) {
                            registry.register(((IItemBlockProvider<?>) nextBlock).getItemBlock());
                            nextBlock = nextBlock.getNextBlock();
                        }
                    }
                }
            });
        });
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event) {
        Material.all((name, material) -> material.getItems().forEach((type, item) -> type.getRecipes().forEach(r -> r.onRecipeRegister(event.getRegistry(), type, material))));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onHandlingItemColours(ColorHandlerEvent.Item event) {
        Material.all(m -> m.getBlocks().forEach((type, block) -> {
            if (block instanceof IItemColor && m.hasTint(type)) {
                event.getItemColors().registerItemColorHandler((IItemColor) block, block);
                if (block instanceof INestedMetaBlock) {
                    INestedMetaBlock<?, ?> nextBlock = ((INestedMetaBlock<?, ?>) block).getNextBlock();
                    while (nextBlock != null) {
                        event.getItemColors().registerItemColorHandler((IItemColor) nextBlock, (Block) nextBlock);
                        nextBlock = nextBlock.getNextBlock();
                    }
                }
            }
        }));
        Material.all(m -> m.getItems().forEach((type, item) -> {
            Item i = item.getItem();
            if (i instanceof IItemColor && m.hasTint(type)) {
                event.getItemColors().registerItemColorHandler((IItemColor) i, i);
            }
        }));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onHandlingBlockColours(ColorHandlerEvent.Block event) {
        Material.all(m -> {
            m.getBlocks().forEach((type, block) -> {
                if (block instanceof IBlockColor && m.hasTint(type)) {
                    event.getBlockColors().registerBlockColorHandler((IBlockColor) block, block);
                    if (block instanceof INestedMetaBlock) {
                        INestedMetaBlock<?, ?> nextBlock = ((INestedMetaBlock<?, ?>) block).getNextBlock();
                        while (nextBlock != null) {
                            event.getBlockColors().registerBlockColorHandler((IBlockColor) nextBlock, (Block) nextBlock);
                            nextBlock = nextBlock.getNextBlock();
                        }
                    }
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
    @SideOnly(Side.CLIENT)
    public static void onTextureStitching(TextureStitchEvent.Pre event) {
        Material.all()
                .stream()
                .filter(m -> !m.getFluids().isEmpty())
                .flatMap(m -> m.getFluids().values().stream())
                .forEach(f -> {
                    event.getMap().registerSprite(f.getStill());
                    event.getMap().registerSprite(f.getFlowing());
                });

        final Set<ResourceLocation> stitch = new ObjectOpenHashSet<>();

        Material.all()
                .stream()
                .flatMap(m -> m.getBlocks().values().stream())
                .filter(b -> b instanceof IModelOverride)
                .map(b -> (IModelOverride) b)
                .forEach(b -> {
                    b.addTextures(stitch);
                    if (b instanceof INestedMetaBlock) {
                        INestedMetaBlock<?, ?> nextBlock = ((INestedMetaBlock<?, ?>) b).getNextBlock();
                        while (nextBlock != null) {
                            ((IModelOverride) nextBlock).addTextures(stitch);
                            nextBlock = nextBlock.getNextBlock();
                        }
                    }
                });

        Material.all()
                .stream()
                .flatMap(m -> m.getItems().values().stream())
                .filter(s -> s.getItem() instanceof IModelOverride)
                .map(s -> (IModelOverride) s.getItem())
                .forEach(i -> i.addTextures(stitch));

        BasicItem.REGISTRY.values().forEach(i -> i.addTextures(stitch));

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
        BasicItem.REGISTRY.values().forEach(BasicItem::onModelRegister);
        Material.all(m -> {
            m.getBlocks().values()
                    .stream()
                    .filter(b -> b instanceof IModelOverride)
                    .map(b -> (IModelOverride) b)
                    .forEach(b -> {
                        b.onModelRegister();
                        if (b instanceof INestedMetaBlock) {
                            INestedMetaBlock<?, ?> nextBlock = ((INestedMetaBlock<?, ?>) b).getNextBlock();
                            while (nextBlock != null) {
                                ((IModelOverride) nextBlock).onModelRegister();
                                nextBlock = nextBlock.getNextBlock();
                            }
                        }
                    });
            m.getItems().values()
                    .stream()
                    .filter(s -> s.getItem() instanceof IModelOverride)
                    .map(s -> (IModelOverride) s.getItem())
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
    @SideOnly(Side.CLIENT)
    public static void onModelBake(ModelBakeEvent event) {
        BasicItem.REGISTRY.values().forEach(b -> b.onModelBake(event));
        Material.all()
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
        Material.all()
                .stream()
                .flatMap(m -> m.getBlocks().values().stream())
                .filter(b -> b instanceof IModelOverride)
                .map(b -> (IModelOverride) b)
                .forEach(b -> {
                    b.onModelBake(event);
                    if (b instanceof INestedMetaBlock) {
                        INestedMetaBlock<?, ?> nextBlock = ((INestedMetaBlock<?, ?>) b).getNextBlock();
                        while (nextBlock != null) {
                            ((IModelOverride) nextBlock).onModelBake(event);
                            nextBlock = nextBlock.getNextBlock();
                        }
                    }
                });

        Material.all()
                .stream()
                .flatMap(m -> m.getItems().values().stream())
                .filter(s -> s.getItem() instanceof IModelOverride)
                .map(s -> (IModelOverride) s.getItem())
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
