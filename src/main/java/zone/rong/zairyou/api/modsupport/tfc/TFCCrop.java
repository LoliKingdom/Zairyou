package zone.rong.zairyou.api.modsupport.tfc;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.dries007.tfc.api.types.ICrop;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class TFCCrop implements ICrop {

    public static final Map<String, TFCCrop> REGISTRY = new Object2ObjectOpenHashMap<>();

    private final String id;

    private final long growthTicks;
    private final int maxStages;
    private final float tempMinAlive, tempMinGrow, tempMaxGrow, tempMaxAlive;
    private final float rainMinAlive, rainMinGrow, rainMaxGrow, rainMaxAlive;
    private final ItemStack drop, immatureDrop;
    private final InfoProvider infoProvider;

    protected TFCCrop(String id,
                      long growthTicks,
                      int maxStages,
                      float tempMinAlive,
                      float tempMinGrow,
                      float tempMaxGrow,
                      float tempMaxAlive,
                      float rainMinAlive,
                      float rainMinGrow,
                      float rainMaxGrow,
                      float rainMaxAlive,
                      ItemStack drop,
                      ItemStack immatureDrop,
                      @Nullable InfoProvider infoProvider) {
        this.id = id;
        this.growthTicks = growthTicks;
        this.maxStages = maxStages;
        this.tempMinAlive = tempMinAlive;
        this.tempMinGrow = tempMinGrow;
        this.tempMaxGrow = tempMaxGrow;
        this.tempMaxAlive = tempMaxAlive;
        this.rainMinAlive = rainMinAlive;
        this.rainMinGrow = rainMinGrow;
        this.rainMaxGrow = rainMaxGrow;
        this.rainMaxAlive = rainMaxAlive;
        this.drop = drop;
        this.immatureDrop = immatureDrop;
        this.infoProvider = infoProvider;
    }

    /**
     * @return the minimum time the crop will take to grow one stage (in ticks)
     */
    @Override
    public long getGrowthTicks() {
        return growthTicks * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.TICKS_IN_DAY;
    }

    /**
     * @return the maximum stage of growth (when current stage == max stage, the crop is fully grown)
     */
    @Override
    public int getMaxStage() {
        return maxStages - 1;
    }

    /**
     * Checks if the conditions are valid for world gen spawning / living
     *
     * @param temperature the temperature, in -30 - +30
     * @param rainfall    the rainfall, in 0 - 500
     * @return true if the crop should spawn here
     */
    @Override
    public boolean isValidConditions(float temperature, float rainfall) {
        return tempMinAlive < temperature && temperature < tempMaxAlive && rainMinAlive < rainfall && rainfall < rainMaxAlive;
    }

    /**
     * A stricter version of the above check. Allows the crop to grow (advance in stages)
     *
     * @param temperature the temperature, in -30 - +30
     * @param rainfall    the rainfall, in 0 - 500
     * @return true if the crop is allowed to grow.(false doesn't mean death)
     */
    @Override
    public boolean isValidForGrowth(float temperature, float rainfall) {
        return tempMinGrow < temperature && temperature < tempMaxGrow && rainMinGrow < rainfall && rainfall < rainMaxGrow;
    }

    /**
     * Get the food item dropped by the crop upon breaking / picking
     *
     * @param growthStage the current growth stage
     */
    @Override
    public ItemStack getFoodDrop(int growthStage) {
        if (growthStage == this.getMaxStage()) {
            return this.drop.copy();
        } else if (growthStage == this.getMaxStage() - 1) {
            return this.immatureDrop.copy();
        }
        return ItemStack.EMPTY;
    }

    /**
     * Add tooltip info
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInfo(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (this.infoProvider == null) {
            if (GuiScreen.isShiftKeyDown()) {
                tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.climate_info"));
                tooltip.add(TextFormatting.BLUE + I18n.format("tfc.tooltip.climate_info_rainfall", (int) rainMinGrow, (int) rainMaxGrow));
                tooltip.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.climate_info_temperature", String.format("%.1f", tempMinGrow), String.format("%.1f", tempMaxGrow)));
            } else {
                tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.hold_shift_for_climate_info"));
            }
        } else {
            this.infoProvider.addInfo(stack, world, tooltip, flag);
        }
    }

    public static class Builder {

        private final String id;

        private long growthTicks;
        private int maxStages;
        private float tempMinAlive;
        private float tempMinGrow;
        private float tempMaxGrow;
        private float tempMaxAlive;
        private float rainMinAlive;
        private float rainMinGrow;
        private float rainMaxGrow;
        private float rainMaxAlive;
        private ItemStack drop;
        private ItemStack immatureDrop;
        private InfoProvider infoProvider;

        public Builder(String id) {
            this.id = id;
        }

        public Builder ticks(long growthTicks) {
            this.growthTicks = growthTicks;
            return this;
        }

        public Builder maxStages(int maxStages) {
            this.maxStages = maxStages;
            return this;
        }

        public Builder tempMinAlive(float tempMinAlive) {
            this.tempMinAlive = tempMinAlive;
            return this;
        }

        public Builder tempMinGrow(float tempMinGrow) {
            this.tempMinGrow = tempMinGrow;
            return this;
        }

        public Builder tempMaxGrow(float tempMaxGrow) {
            this.tempMaxGrow = tempMaxGrow;
            return this;
        }

        public Builder tempMaxAlive(float tempMaxAlive) {
            this.tempMaxAlive = tempMaxAlive;
            return this;
        }

        public Builder rainMinAlive(float rainMinAlive) {
            this.rainMinAlive = rainMinAlive;
            return this;
        }

        public Builder rainMinGrow(float rainMinGrow) {
            this.rainMinGrow = rainMinGrow;
            return this;
        }

        public Builder rainMaxGrow(float rainMaxGrow) {
            this.rainMaxGrow = rainMaxGrow;
            return this;
        }

        public Builder rainMaxAlive(float rainMaxAlive) {
            this.rainMaxAlive = rainMaxAlive;
            return this;
        }

        public Builder drop(ItemStack drop) {
            this.drop = drop;
            return this;
        }

        public Builder earlyDrop(ItemStack immatureDrop) {
            this.immatureDrop = immatureDrop;
            return this;
        }

        public Builder info(InfoProvider infoProvider) {
            this.infoProvider = infoProvider;
            return this;
        }

        public TFCCrop createTFCCrop() {
            return new TFCCrop(id, growthTicks, maxStages, tempMinAlive, tempMinGrow, tempMaxGrow, tempMaxAlive, rainMinAlive, rainMinGrow, rainMaxGrow, rainMaxAlive, drop, immatureDrop, infoProvider);
        }
    }

    public interface ConditionsChecker {

        boolean check(float temperature, float rainfall);

    }

    public interface InfoProvider {

        void addInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn);

    }

}
