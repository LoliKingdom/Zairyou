package zone.rong.zairyou.api.util;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;

public class IdentifiableBlockStateContainer extends BlockStateContainer {

    public IdentifiableBlockStateContainer(Block blockIn, IProperty<?>... properties) {
        super(blockIn, properties);
    }

}
