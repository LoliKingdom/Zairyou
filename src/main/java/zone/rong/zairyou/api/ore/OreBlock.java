package zone.rong.zairyou.api.ore;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import zone.rong.zairyou.api.client.Bakery;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.IMaterialBlock;
import zone.rong.zairyou.api.ore.stone.StoneType;

import java.util.Set;

public class OreBlock extends Block implements IMaterialBlock {

    public static final PropertyInteger STONE_TYPES = PropertyInteger.create("stone_types", 0, StoneType.VALUES.length - 1);

    private final Material material;
    private final OreGrade grade;

    public OreBlock(Material material, OreGrade grade) {
        super(net.minecraft.block.material.Material.ROCK);
        this.material = material;
        this.grade = grade;
        setSoundType(SoundType.STONE);
        setHardness(3.0F);
        setResistance(5.0F);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STONE_TYPES); // TODO: Query Dimensions
    }

    public Material getMaterial() {
        return material;
    }

    public OreGrade getGrade() {
        return grade;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(STONE_TYPES, placer.getHeldItem(hand).getMetadata());
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STONE_TYPES, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STONE_TYPES);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (tab == this.getCreativeTabToDisplayOn()) {
            for (int i = 0; i < StoneType.VALUES.length; i++) {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, state.getValue(STONE_TYPES));
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
                return new ModelResourceLocation(OreBlock.this.getRegistryName().toString() + "_" + state.getValue(STONE_TYPES));
            }
        });
        for (int i = 0; i < StoneType.VALUES.length; i++) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(OreBlock.this.getRegistryName().toString() + "_" + i));
        }
    }

    @Override
    public void onModelBake(ModelBakeEvent event) {
        for (int i = 0; i < StoneType.VALUES.length; i++) {
            Bakery bake = Bakery.INSTANCE
                    .template(Bakery.ModelType.SINGLE_OVERLAY)
                    .prepareTexture("layer0", StoneType.VALUES[i].getBaseTexture())
                    .prepareTexture("layer1", "zairyou:blocks/grade/normal")
                    .tint(1, material.getColour());
            bake.bake(true, false);
            event.getModelRegistry().putObject(new ModelResourceLocation(OreBlock.this.getRegistryName().toString() + "_" + i), bake.receiveBlock());
        }
    }
}
