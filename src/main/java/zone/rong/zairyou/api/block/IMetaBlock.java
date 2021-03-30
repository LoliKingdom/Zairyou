package zone.rong.zairyou.api.block;

import net.minecraft.block.properties.IProperty;

public interface MetaBlock<C extends Comparable<C>> {

    IProperty<C> getAllowedTypes();

    void alignBlockStateContainer();

}
