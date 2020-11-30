package zone.rong.zairyou.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import zone.rong.zairyou.api.machine.Machine;

public class MachineBlock extends Block implements ITileEntityProvider {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    private final Machine machine;

    public MachineBlock(Machine machine) {
        super(Material.IRON);
        this.machine = machine;
        this.setHardness(5.0F);
        this.setSoundType(SoundType.METAL);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return machine.instantiate();
    }

}
