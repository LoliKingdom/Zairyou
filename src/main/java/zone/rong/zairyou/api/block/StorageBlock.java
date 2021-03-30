package zone.rong.zairyou.api.block;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import zone.rong.zairyou.api.block.itemblock.StorageItemBlock;
import zone.rong.zairyou.api.block.metablock.AbstractMetaBlock;
import zone.rong.zairyou.api.block.metablock.MutableMetaBlockBuilder;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.ore.stone.StoneType;
import zone.rong.zairyou.api.property.type.MaterialProperty;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class StorageBlock extends AbstractMetaBlock<MaterialProperty, Material, StorageItemBlock> implements IBlockColor, IItemColor {

    public static void create() {
        new MutableMetaBlockBuilder<>("block", StorageBlock.class, net.minecraft.block.material.Material.ROCK, MaterialProperty.class)
                .entries(Material.all().stream().filter(m -> m.hasType(BlockMaterialType.STORAGE)).collect(Collectors.toList()))
                .modify((l, b) -> l.forEach(m -> m.setBlock(BlockMaterialType.STORAGE, b.getDefaultState().withProperty(b.freezableProperty, m))))
                .build();
    }

    public StorageBlock(net.minecraft.block.material.Material vanillaMaterial, MaterialProperty property) {
        super(vanillaMaterial, property);
    }

    @Override
    public Supplier<StorageItemBlock> getItemBlock() {
        return () -> new StorageItemBlock(this);
    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
        return tintIndex == 1 ? state.getValue(freezableProperty).getColour() : -1;
    }

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        return tintIndex == 1 ? this.freezableProperty.getAllowedValues().get(stack.getMetadata()).getColour() : -1;
    }

    public static class Getter implements IBlockGetter {

        private final Map<Material, IBlockState> map = new Object2ObjectOpenHashMap<>();

        @Override
        public void supplyBlock(IBlockState state, Object... arguments) {
            if (arguments.length > 1) {
                throw new UnsupportedOperationException("This getter only allows 1 argument.");
            }
            for (Object argument : arguments) {
                if (!(argument instanceof Material)) {
                    throw new UnsupportedOperationException("Argument not supported");
                }
            }
            map.put((Material) arguments[0], state);
        }

        @Nullable
        @Override
        public IBlockState getBlockState(Object... arguments) {
            if (arguments.length > 1) {
                throw new UnsupportedOperationException("This getter only allows 1 argument.");
            }
            return map.get(arguments[0]);
        }

        @Override
        public Set<Block> getBlocks() {
            return ObjectSets.unmodifiable(new ObjectOpenHashSet<>(map.values().stream().map(IBlockState::getBlock).iterator()));
        }

        @Override
        public Set<IBlockState> getBlockStates() {
            return ImmutableSet.copyOf(map.values());
        }
    }

}
