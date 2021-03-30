package zone.rong.zairyou;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipeMetalMelting;
import net.dries007.tfc.api.types.Metal;
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
import zone.rong.zairyou.api.block.metablock.AbstractMetaBlock;
import zone.rong.zairyou.api.client.Bakery;
import zone.rong.zairyou.api.client.IModelOverride;
import zone.rong.zairyou.api.fluid.block.DefaultFluidBlock;
import zone.rong.zairyou.api.item.BasicItem;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.MaterialFlag;
import zone.rong.zairyou.api.material.MetalMaterial;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;
import zone.rong.zairyou.api.modsupport.tfc.TFCMetalWorkaround;
import zone.rong.zairyou.api.ore.OreGrade;
import zone.rong.zairyou.api.client.RenderUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    @SubscribeEvent
    public static void onRegisterHeatRecipeEvent(RegistryEvent.Register<HeatRecipe> event) {
        Material.all(MetalMaterial.class).filter(m -> m.getMetalTier().isAtMost(Metal.Tier.TIER_VI)).forEach(m -> {
            if (m.hasFlag(MaterialFlag.ORE)) {
                // event.getRegistry().register(new OreHeatRecipe(m));
                event.getRegistry().register(new HeatRecipeMetalMelting(m.getRepresentation()).setRegistryName(Zairyou.ID, "heating_" + m.getName()));
            }
        });
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        BlockMaterialType.instantiateBlocks();
        Material.all().stream()
                .map(Material::getBlocks)
                .map(Map::values)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .filter(b -> b instanceof AbstractMetaBlock)
                .collect(Collectors.toSet())
                .forEach(event.getRegistry()::register);
        Material.all((name, material) -> {
            for (final Fluid fluid : material.getFluids().values()) {
                if (FluidRegistry.isFluidDefault(fluid)) {
                    final Block block = fluid.getBlock();
                    if (block != null) {
                        event.getRegistry().register(block.setRegistryName(Zairyou.ID, "fluid_" + fluid.getName()));
                    }
                }
            }
            if (material instanceof MetalMaterial) {
                TFCMetalWorkaround.pre$runAfterBlockRegister((MetalMaterial) material);
            }
        });
        TFCMetalWorkaround.post$runAfterBlockRegister();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        BasicItem.REGISTRY.values().forEach(registry::register);
        Material.all().stream()
                .map(Material::getBlocks)
                .map(Map::values)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .filter(b -> b instanceof IItemBlockProvider)
                .collect(Collectors.toSet())
                .forEach(b -> {
                    Supplier<?> supplier = ((IItemBlockProvider<?>) b).getItemBlock();
                    if (supplier != null) {
                        Item itemBlock = (Item) supplier.get();
                        if (itemBlock != null) {
                            registry.register(itemBlock);
                        }
                    }
                });
        Material.all((name, material) -> {
            for (final Map.Entry<ItemMaterialType, ItemStack> entry : material.getItems().entrySet()) {
                if (entry.getValue().isEmpty()) {
                    ItemMaterialType type = entry.getKey();
                    Item item = type.getItemSupplier().apply(material);
                    registry.register(item.setRegistryName(Zairyou.ID, name + "_" + type.getId()));
                    material.setItem(type, item);
                }
            }
        });
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event) {
        Material.all((name, material) -> material.getItems().forEach((type, item) -> type.getRecipes().forEach(r -> r.onRecipeRegister(event.getRegistry(), type, material))));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onHandlingItemColours(ColorHandlerEvent.Item event) {
        Material.all(material -> {
            material.getBlocks()
                    .forEach((b, c) -> {
                if (material.hasTint(b)) {
                    c.stream().filter(block -> block instanceof IItemColor)
                            .collect(Collectors.toSet())
                            .forEach(block -> event.getItemColors().registerItemColorHandler((IItemColor) block, block));
                }
            });
            material.getItems().forEach((type, item) -> {
                Item i = item.getItem();
                if (i instanceof IItemColor && material.hasTint(type)) {
                    event.getItemColors().registerItemColorHandler((IItemColor) i, i);
                }
            });
        });
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onHandlingBlockColours(ColorHandlerEvent.Block event) {
        Material.all(m -> {
            m.getBlocks().forEach((b, c) -> {
                if (m.hasTint(b)) {
                    c.stream().filter(block -> block instanceof IBlockColor).collect(Collectors.toSet()).forEach(block -> event.getBlockColors().registerBlockColorHandler((IBlockColor) block, block));
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
                .flatMap(Collection::stream)
                .filter(b -> b instanceof IModelOverride)
                .map(b -> (IModelOverride) b)
                .collect(Collectors.toSet())
                .forEach(b -> b.addTextures(stitch));

        Material.all()
                .stream()
                .flatMap(m -> m.getItems().values().stream())
                .filter(s -> s.getItem() instanceof IModelOverride)
                .map(s -> (IModelOverride) s.getItem())
                .forEach(i -> i.addTextures(stitch));

        BasicItem.REGISTRY.values().forEach(i -> i.addTextures(stitch));

        stitch.forEach(rl -> event.getMap().registerSprite(rl));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegister(ModelRegistryEvent event) {
        BasicItem.REGISTRY.values().forEach(BasicItem::onModelRegister);
        Material.all(m -> {
            m.getBlocks().values()
                    .stream()
                    .flatMap(Collection::stream)
                    .filter(b -> b instanceof IModelOverride)
                    .map(b -> (IModelOverride) b)
                    .collect(Collectors.toSet())
                    .forEach(IModelOverride::onModelRegister);
            m.getItems().values()
                    .stream()
                    .filter(s -> s.getItem() instanceof IModelOverride)
                    .map(s -> (IModelOverride) s.getItem())
                    .forEach(IModelOverride::onModelRegister);
            m.getFluids().forEach((t, f) -> {
                Block block = f.getBlock();
                if (block instanceof DefaultFluidBlock) {
                    ModelLoader.setCustomStateMapper(block, RenderUtils.SIMPLE_STATE_MAPPER.apply(block));
                }
            });
        });
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
                .flatMap(Collection::stream)
                .filter(b -> b instanceof IModelOverride)
                .map(b -> (IModelOverride) b)
                .collect(Collectors.toSet())
                .forEach(b -> b.onModelBake(event));

        Material.all()
                .stream()
                .flatMap(m -> m.getItems().values().stream())
                .filter(s -> s.getItem() instanceof IModelOverride)
                .map(s -> (IModelOverride) s.getItem())
                .forEach(i -> i.onModelBake(event));

        Bakery.shutdown();
    }

}
