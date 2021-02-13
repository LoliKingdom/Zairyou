package zone.rong.zairyou.api.block;

import net.minecraft.item.ItemBlock;

public interface IItemBlockProvider<T extends ItemBlock> {

    T getItemBlock();

}
