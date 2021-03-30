package zone.rong.zairyou.api.ore;

import net.dries007.tfc.api.types.Rock;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import zone.rong.zairyou.api.block.metablock.AbstractMetaBlock;
import zone.rong.zairyou.api.block.metablock.MutableMetaBlockBuilder;
import zone.rong.zairyou.api.client.Bakery;
import zone.rong.zairyou.api.client.IModelOverride;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.MaterialFlag;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;
import zone.rong.zairyou.api.ore.stone.StoneType;
import zone.rong.zairyou.api.property.type.StoneTypeProperty;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

public class OreBlock extends AbstractMetaBlock<StoneTypeProperty, StoneType, OreItemBlock> implements IModelOverride, IBlockColor, IItemColor {

    private final Material material;
    private final OreGrade oreGrade;

    public static void create() {
        Material.all()
                .stream()
                .filter(m -> m.hasFlag(MaterialFlag.ORE))
                .forEach(m -> {
                    new MutableMetaBlockBuilder<>(String.join("_", m.getName(), "ore", "block"), OreBlock.class, net.minecraft.block.material.Material.ROCK, StoneTypeProperty.class)
                            .entries(StoneType.VALUES)
                            .argument(Material.class, m)
                            .argument(OreGrade.class, OreGrade.NORMAL)
                            .build().forEach((s, o) -> m.setBlock(BlockMaterialType.ORE, s, o.getDefaultState().withProperty(o.freezableProperty, s)));

                    new MutableMetaBlockBuilder<>(String.join("_", "poor", m.getName(), "ore", "block"), OreBlock.class, net.minecraft.block.material.Material.ROCK, StoneTypeProperty.class)
                            .entries(StoneType.VALUES)
                            .argument(Material.class, m)
                            .argument(OreGrade.class, OreGrade.POOR)
                            .build().forEach((s, o) -> m.setBlock(BlockMaterialType.ORE_POOR, s, o.getDefaultState().withProperty(o.freezableProperty, s)));

                    new MutableMetaBlockBuilder<>(String.join("_", "rich", m.getName(), "ore", "block"), OreBlock.class, net.minecraft.block.material.Material.ROCK, StoneTypeProperty.class)
                            .entries(StoneType.VALUES)
                            .argument(Material.class, m)
                            .argument(OreGrade.class, OreGrade.RICH)
                            .build().forEach((s, o) -> m.setBlock(BlockMaterialType.ORE_RICH, s, o.getDefaultState().withProperty(o.freezableProperty, s)));
                });
    }

    public OreBlock(net.minecraft.block.material.Material vanillaMaterial, StoneTypeProperty property, Material material, OreGrade oreGrade) {
        super(vanillaMaterial, property);
        this.material = material;
        this.oreGrade = oreGrade;
        setSoundType(SoundType.STONE);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        /*
        setHardness(3.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", material.getToolMaterial().getHarvestLevel() - 1);
         */
    }

    public Material getMaterial() {
        return material;
    }

    public OreGrade getOreGrade() {
        return oreGrade;
    }

    @Override
    public Supplier<OreItemBlock> getItemBlock() {
        return () -> new OreItemBlock(this);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        switch (oreGrade) {
            case NORMAL:
                drops.add(material.getItem(ItemMaterialType.ORE, true));
                break;
            case POOR:
                drops.add(material.getItem(ItemMaterialType.ORE_POOR, true));
                break;
            case RICH:
                drops.add(material.getItem(ItemMaterialType.ORE_RICH, true));
                break;
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        switch (oreGrade) {
            case NORMAL:
                return material.getItem(ItemMaterialType.ORE, false).getItem();
            case POOR:
                return material.getItem(ItemMaterialType.ORE_POOR, false).getItem();
            case RICH:
                return material.getItem(ItemMaterialType.ORE_RICH, false).getItem();
            default:
                return Items.AIR;
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void addTextures(Set<ResourceLocation> textures) { }

    @Override
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(state.getBlock().getRegistryName().toString() + "_" + state.getValue(freezableProperty).getName());
            }
        });
        this.blockState.getValidStates().forEach(s -> {
            StoneType stoneType = s.getValue(freezableProperty);
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), freezableProperty.getAllowedValues().indexOf(stoneType), new ModelResourceLocation(this.getRegistryName().toString() + "_" + stoneType.getName()));
        });
    }

    @Override
    public void onModelBake(ModelBakeEvent event) {
        this.blockState.getValidStates().forEach(s -> {
           StoneType stoneType = s.getValue(freezableProperty);
            event.getModelRegistry().putObject(new ModelResourceLocation(this.getRegistryName().toString() + "_" + stoneType.getName()),
                    Bakery.INSTANCE.getBlockDepartment()
                            .template(Bakery.ModelType.SINGLE_OVERLAY_BLOCK)
                            .prepareTexture("layer0", stoneType.getTypeLocation(Rock.Type.RAW))
                            .prepareTexture("layer1", this.material.getTexture(this.oreGrade.getBlockRepresentation(), 1))
                            .bake()
                            .take());
        });
    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
        return tintIndex == 0 ? material.getColour() : -1;
    }

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        return tintIndex == 0 ? material.getColour() : -1;
    }

}
