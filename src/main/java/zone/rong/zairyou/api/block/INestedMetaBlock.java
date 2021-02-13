package zone.rong.zairyou.api.block;

import net.minecraft.block.Block;

import javax.annotation.Nullable;

public interface INestedMetaBlock<T extends Block & INestedMetaBlock<T, C>, C extends Comparable<C>> extends IMetaBlock<C> {

    @Nullable T getNextBlock();

    @Nullable T getSpecificBlock(C propertyValue);

    @Override
    default void alignBlockStateContainer() { } // nested blocks do not use this approach
}
