package zone.rong.zairyou.api.ore;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.block.IItemBlockProvider;
import zone.rong.zairyou.api.block.INestedMetaBlock;
import zone.rong.zairyou.api.block.IZairyouBlockBuilder;
import zone.rong.zairyou.api.client.Bakery;
import zone.rong.zairyou.api.client.IModelOverride;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.ore.stone.StoneType;
import zone.rong.zairyou.api.ore.stone.StoneTypeProperty;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class OreBlock extends Block implements INestedMetaBlock<OreBlock, StoneType>, IItemBlockProvider<OreItemBlock>, IModelOverride, IBlockColor, IItemColor {

    private final Material material;
    private final OreGrade oreGrade;
    private final OreItemBlock itemBlock;
    private final StoneTypeProperty allowedTypes;

    @Nullable private OreBlock nextBlock;

    protected OreBlock(Material material, OreGrade oreGrade, StoneType[] allowedTypes, int recursion) {
        super(net.minecraft.block.material.Material.ROCK);
        this.material = material;
        this.oreGrade = oreGrade;
        this.itemBlock = new OreItemBlock(this);
        StoneType[] currentTypes;
        if (allowedTypes.length > 16) {
            currentTypes = new StoneType[16];
            System.arraycopy(allowedTypes, 0, currentTypes, 0, 16);
            StoneType[] nextTypes = new StoneType[allowedTypes.length - 16];
            System.arraycopy(allowedTypes, 16, nextTypes, 0, allowedTypes.length - 16);
            this.nextBlock = new OreBlock(material, oreGrade, nextTypes, recursion + 1);
        } else {
            currentTypes = allowedTypes;
        }
        this.allowedTypes = new StoneTypeProperty(currentTypes);
        this.blockState = new BlockStateContainer(this, this.allowedTypes);
        this.setDefaultState(this.blockState.getBaseState());
        setSoundType(SoundType.STONE);
        setHardness(3.0F);
        setResistance(5.0F);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        String name = String.join("_", oreGrade.name(), material.getName(), "ore", String.valueOf(recursion));
        setRegistryName(Zairyou.ID, name);
        this.itemBlock.setRegistryName(Zairyou.ID, name);
    }

    public Material getMaterial() {
        return material;
    }

    public OreGrade getOreGrade() {
        return oreGrade;
    }

    @Override
    public StoneTypeProperty getAllowedTypes() {
        return allowedTypes;
    }

    @Nullable
    @Override
    public OreBlock getNextBlock() {
        return nextBlock;
    }

    @Nullable
    @Override
    public OreBlock getSpecificBlock(StoneType propertyValue) {
        if (nextBlock == null) {
            return this.allowedTypes.getAllowedValues().contains(propertyValue) ? this : null;
        } else {
            return nextBlock.getSpecificBlock(propertyValue);
        }
    }

    @Override
    public OreItemBlock getItemBlock() {
        return itemBlock;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(allowedTypes, allowedTypes.getAllowedValues().get(placer.getHeldItem(hand).getMetadata()));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(allowedTypes, allowedTypes.getAllowedValues().get(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return allowedTypes.getAllowedValues().indexOf(state.getValue(allowedTypes));
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (tab == this.getCreativeTabToDisplayOn()) {
            this.blockState.getValidStates().forEach(s -> list.add(new ItemStack(this, 1, this.getMetaFromState(s))));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, allowedTypes.getAllowedValues().indexOf(state.getValue(allowedTypes)));
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
                return new ModelResourceLocation(state.getBlock().getRegistryName().toString() + "_" + state.getValue(allowedTypes).getName());
            }
        });
        this.blockState.getValidStates().forEach(s -> {
            StoneType stoneType = s.getValue(allowedTypes);
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), allowedTypes.getAllowedValues().indexOf(stoneType), new ModelResourceLocation(this.getRegistryName().toString() + "_" + stoneType.getName()));
        });
    }

    @Override
    public void onModelBake(ModelBakeEvent event) {
        this.blockState.getValidStates().forEach(s -> {
           StoneType stoneType = s.getValue(allowedTypes);
            event.getModelRegistry().putObject(new ModelResourceLocation(this.getRegistryName().toString() + "_" + stoneType.getName()),
                    Bakery.INSTANCE.getBlockDepartment()
                            .template(Bakery.ModelType.NORMAL_BLOCK)
                            .prepareTexture("layer0", stoneType.getBaseTexture())
                            .prepareTexture("layer1", OreGrade.NORMAL.getTextureLocation())
                            .bake().take());
        });
    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
        return tintIndex == 1 ? material.getColour() : -1;
    }

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        return tintIndex == 1 ? material.getColour() : -1;
    }

    public static class Builder implements IZairyouBlockBuilder<OreBlock> {

        private final Material material;
        private final OreGrade oreGrade;

        private List<StoneType> allowedTypes;

        public Builder(Material material, OreGrade oreGrade) {
            this.material = material;
            this.oreGrade = oreGrade;
        }

        public Builder stoneTypes(StoneType... types) {
            if (this.allowedTypes == null) {
                this.allowedTypes = new ObjectArrayList<>(types.length);
            }
            Collections.addAll(allowedTypes, types);
            return this;
        }

        @Override
        public OreBlock build() {
            return new OreBlock(material, oreGrade, allowedTypes == null ? StoneType.VALUES : (StoneType[]) allowedTypes.toArray(), 0);
        }

    }

}
