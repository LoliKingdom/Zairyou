package zone.rong.zairyou.api.machine;

import it.unimi.dsi.fastutil.bytes.Byte2BooleanArrayMap;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerFluidMap;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.EnumMap;

/**
 * Machine's data store, we hold inventories, tanks and related info in here.
 */
public class MachineDataStore {

    private final IItemHandler itemInput, itemOutput;
    // private final IFluidHandler fluidInput, fluidOutput;
    private final EnumMap<EnumFacing, IItemHandler> itemStores = new EnumMap<>(EnumFacing.class);
    // private final EnumMap<EnumFacing, IFluidHandler> fluidStores = new EnumMap<>(EnumFacing.class);

    public MachineDataStore(Machine machine) {
        this.itemInput = machine.getItemInputs() > 0 ? new ItemStackHandler(machine.getItemInputs()) : null;
        this.itemOutput = machine.getItemOutputs() > 0 ? new ItemStackHandler(machine.getItemOutputs()) : null;
        // this.fluidInput = machine.getInputTanks() > 0 ? new FluidHandlerFluidMap().addHandler() : null;
        // this.fluidOutput = machine.getOutputTanks() > 0 ? new FluidHandlerFluidMap() : null;
        // for (EnumFacing facing : EnumFacing.VALUES) {
            // this.itemStores.put(facing, null); // Prefill
            // this.fluidStores.put(facing, null); // Prefill
        // }
    }

    public void setFacing(boolean isItem, boolean isInput, EnumFacing facing) {
        if (isItem) {
            this.itemStores.replace(facing, isInput ? itemInput : itemOutput);
        } // else {
            // this.fluidStores.replace(facing, isInput ? fluidInput : fluidOutput);
        // }
    }

    public <T> boolean hasCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.itemStores.get(facing) != null;
        }
        // if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            // return this.fluidStores.get(facing) != null;
        // }
        return false;
    }

    @Nullable
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.itemStores.get(facing));
        }
        // if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            // return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.fluidStores.get(facing));
        // }
        return null;
    }

}
