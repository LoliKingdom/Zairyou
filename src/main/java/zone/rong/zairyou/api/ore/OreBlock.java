package zone.rong.zairyou.api.ore;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import zone.rong.zairyou.api.material.Material;
public class OreBlock extends Block {

    public static final PropertyInteger STONE_TYPES = PropertyInteger.create("stone_types", 0, 15);

    private final Material material;
    private final OreGrade grade;

    public OreBlock(Material material, OreGrade grade) {
        super(net.minecraft.block.material.Material.ROCK);
        this.material = material;
        this.grade = grade; // TODO: Get OreGrade from Material
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
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
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
            for (int i = 0; i < 16; i++) {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
