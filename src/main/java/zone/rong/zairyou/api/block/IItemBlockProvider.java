package zone.rong.zairyou.api.block;

import net.minecraft.item.ItemBlock;

import java.util.function.Supplier;

public interface IItemBlockProvider<T extends ItemBlock> {

    Supplier<T> getItemBlock();

}
