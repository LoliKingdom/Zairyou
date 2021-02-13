package zone.rong.zairyou.api.block;

import net.minecraft.block.Block;

public interface IZairyouBlockBuilder<B extends Block> {

    B build();

}
