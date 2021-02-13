package zone.rong.zairyou.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.block.itemblock.StorageItemBlock;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.MaterialProperty;
import zone.rong.zairyou.api.util.IdentifiableBlockStateContainer;

import javax.annotation.Nullable;

public class StorageBlock extends Block implements IItemBlockProvider<StorageItemBlock>, IBlockColor, IItemColor, IMetaBlock<Material> {

    private final MaterialProperty allowedTypes;
    private final StorageItemBlock itemBlock;

    protected StorageBlock(Material material, net.minecraft.block.material.Material vanillaMaterial, int count) {
        super(vanillaMaterial);
        this.allowedTypes = new MaterialProperty(material);
        this.itemBlock = new StorageItemBlock(this);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        String name = "storage_block_" + count;
        setRegistryName(Zairyou.ID, name);
        this.itemBlock.setRegistryName(Zairyou.ID, name);
    }

    @Override
    public StorageItemBlock getItemBlock() {
        return itemBlock;
    }

    @Override
    public MaterialProperty getAllowedTypes() {
        return allowedTypes;
    }

    @Override
    public void alignBlockStateContainer() {
        if (!(this.blockState instanceof IdentifiableBlockStateContainer)) {
            this.blockState = new IdentifiableBlockStateContainer(this, this.allowedTypes);
            this.setDefaultState(this.blockState.getBaseState());
        }
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
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
        return tintIndex == 1 ? this.allowedTypes.getAllowedValues().get(this.getMetaFromState(state)).getColour() : -1;
    }

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        return tintIndex == 1 ? this.allowedTypes.getAllowedValues().get(stack.getMetadata()).getColour() : -1;
    }

    public static class Builder implements IZairyouBlockBuilder<StorageBlock> {

        static StorageBlock lastBlock;
        static int count = 0;

        private final Material material;

        private net.minecraft.block.material.Material vanillaMaterial = net.minecraft.block.material.Material.IRON;

        public Builder(Material material) {
            this.material = material;
        }

        public Builder material(net.minecraft.block.material.Material material) {
            this.vanillaMaterial = material;
            return this;
        }

        @Override
        public StorageBlock build() {
            if (lastBlock != null && lastBlock.allowedTypes.appendMaterial(material)) {
                return lastBlock;
            }
            return lastBlock = new StorageBlock(material, vanillaMaterial, count++);
        }

    }

}
