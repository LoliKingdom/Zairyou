package zone.rong.zairyou.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class MetaBlock<C extends Comparable<C>> extends Block implements IMetaBlock<C> {

    public MetaBlock(Material material) {
        super(material);
    }

}
