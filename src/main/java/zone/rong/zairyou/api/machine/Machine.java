package zone.rong.zairyou.api.machine;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import zone.rong.zairyou.api.tile.MachineTileEntity;

import java.util.function.Function;

public class Machine {

    public static final Object2ObjectMap<String, Machine> REGISTRY = new Object2ObjectOpenHashMap<>();

    public static final Machine WIREMILL = new Machine("wiremill", MachineTileEntity::new).itemInput(1).itemOutput(1);

    private final String name;
    private final Function<Machine, MachineTileEntity> tileEntityConstructor;

    private int itemInputSlots, itemOutputSlots, inputFluidTanks, outputFluidTanks = 0;

    public Machine(String name, Function<Machine, MachineTileEntity> tileCreator) {
        this.name = name;
        this.tileEntityConstructor = tileCreator;
    }

    public String getName() {
        return name;
    }

    public boolean hasInventory() {
        return this.itemInputSlots > 0 || this.itemOutputSlots > 0;
    }

    public int getItemInputs() {
        return itemInputSlots;
    }

    public int getItemOutputs() {
        return itemOutputSlots;
    }

    public boolean hasTanks() {
        return this.inputFluidTanks > 0 || this.outputFluidTanks > 0;
    }

    public int getInputTanks() {
        return inputFluidTanks;
    }

    public int getOutputTanks() {
        return outputFluidTanks;
    }

    public Machine recipe() {
        return this;
    }

    public Machine itemInput(int slots) {
        this.itemInputSlots = slots;
        return this;
    }

    public Machine itemOutput(int slots) {
        this.itemOutputSlots = slots;
        return this;
    }

    public Machine fluidInput(int tanks) {
        this.inputFluidTanks = tanks;
        return this;
    }

    public Machine fluidOutput(int tanks) {
        this.outputFluidTanks = tanks;
        return this;
    }

    public MachineTileEntity instantiate() {
        return this.tileEntityConstructor.apply(this);
    }

}
