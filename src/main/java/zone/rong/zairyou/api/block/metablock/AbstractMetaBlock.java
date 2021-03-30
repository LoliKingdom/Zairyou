package zone.rong.zairyou.api.block.metablock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import zone.rong.zairyou.api.block.IItemBlockProvider;
import zone.rong.zairyou.api.block.IMetaBlock;
import zone.rong.zairyou.api.block.itemblock.MetaItemBlock;
import zone.rong.zairyou.api.property.FreezableProperty;
import zone.rong.zairyou.api.util.IdentifiableBlockStateContainer;

import java.util.function.Supplier;

public abstract class AbstractMetaBlock<P extends FreezableProperty<C>, C extends Comparable<C>, I extends MetaItemBlock> extends Block implements IMetaBlock<C>, IItemBlockProvider<I> {

    protected final P freezableProperty;

    public AbstractMetaBlock(Material material, P freezableProperty) {
        super(material);
        this.freezableProperty = freezableProperty;
    }

    @Override
    public P getAllowedTypes() {
        return freezableProperty;
    }

    @Override
    public Supplier<I> getItemBlock() {
        return () -> (I) new MetaItemBlock(this);
    }

    @Override
    public void alignBlockStateContainer() {
        if (!(this.blockState instanceof IdentifiableBlockStateContainer)) {
            this.blockState = new IdentifiableBlockStateContainer(this, freezableProperty);
            this.setDefaultState(this.blockState.getBaseState());
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(freezableProperty, freezableProperty.getAllowedValues().get(placer.getHeldItem(hand).getMetadata()));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(freezableProperty, freezableProperty.getAllowedValues().get(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return freezableProperty.getAllowedValues().indexOf(state.getValue(freezableProperty));
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (tab == this.getCreativeTabToDisplayOn()) {
            this.blockState.getValidStates().forEach(s -> list.add(new ItemStack(this, 1, this.getMetaFromState(s))));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, freezableProperty.getAllowedValues().indexOf(state.getValue(freezableProperty)));
    }

}
