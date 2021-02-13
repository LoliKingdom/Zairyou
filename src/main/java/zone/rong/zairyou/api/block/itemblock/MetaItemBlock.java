package zone.rong.zairyou.api.block.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class MetaItemBlock extends ItemBlock {

    public MetaItemBlock(Block block) {
        super(block);
        setHasSubtypes(true);
    }

}
