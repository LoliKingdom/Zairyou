package zone.rong.zairyou.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nullable;
import java.util.Set;

public interface IBlockGetter {

    void supplyBlock(IBlockState state, Object... arguments);

    @Nullable
    IBlockState getBlockState(Object... arguments);

    Set<Block> getBlocks();

    Set<IBlockState> getBlockStates();

}
