package zone.rong.zairyou.api.fluid;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

// TODO
public class DefaultFluidBlock extends BlockFluidClassic {

    public DefaultFluidBlock(Fluid fluid, FluidType type) {
        super(fluid, type == FluidType.MOLTEN ? Material.LAVA : Material.WATER);
    }

}
