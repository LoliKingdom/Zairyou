package zone.rong.zairyou.api.fluid.block;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import zone.rong.zairyou.api.fluid.FluidType;

// TODO
public class DefaultFluidBlock extends BlockFluidClassic {

    public DefaultFluidBlock(Fluid fluid, FluidType type) {
        super(fluid, type == FluidType.MOLTEN ? Material.LAVA : Material.WATER);
    }

}
