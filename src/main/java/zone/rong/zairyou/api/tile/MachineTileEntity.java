package zone.rong.zairyou.api.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.machine.Machine;

public class MachineTileEntity extends BaseTileEntity implements ITickable {

    public static final ResourceLocation ID = new ResourceLocation(Zairyou.ID, "machine");

    private Machine machine;

    public MachineTileEntity() {

    }

    public MachineTileEntity(Machine machine) {
        this.machine = machine;
    }

    @Override
    public void update() {

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.machine = Machine.REGISTRY.get(compound.getString("Machine"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setString("Machine", this.machine.getName());
        return super.writeToNBT(compound);
    }

}
