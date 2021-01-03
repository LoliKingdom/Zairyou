package zone.rong.zairyou.api.fluid;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.objects.Materials;

import javax.annotation.Nullable;

@Deprecated
public class PotionFluid/* extends Fluid*/ {

    /*
    public static int getPotionColor(FluidStack stack) {
        if (stack.tag != null && stack.tag.hasKey("CustomPotionColor", 99)) {
            return stack.tag.getInteger("CustomPotionColor");
        } else {
            return getPotionTypeFromNBT(stack.tag) == PotionTypes.EMPTY ? 0xF800F8 : PotionUtils.getPotionColorFromEffectList(PotionUtils.getEffectsFromTag(stack.tag));
        }
    }

    public static PotionType getPotionType(FluidStack stack) {
        return getPotionTypeFromNBT(stack.tag);
    }

    public static PotionType getPotionTypeFromNBT(@Nullable NBTTagCompound tag) {
        return tag == null || !tag.hasKey("Potion") ? PotionTypes.EMPTY : PotionType.getPotionTypeForName(tag.getString("Potion"));
    }

    public static FluidStack getPotion(int amount, PotionType type) {
        if (type == null || type == PotionTypes.EMPTY) {
            return null;
        }
        if (type == PotionTypes.WATER) {
            return new FluidStack(FluidRegistry.WATER, amount);
        }
        return addPotionToFluidStack(Materials.POTION.getStack(FluidType.LIQUID, amount), type);
    }

    public static FluidStack getSplashPotion(int amount, PotionType type) {

        if (type == null || type == PotionTypes.EMPTY) {
            return null;
        }
        return addPotionToFluidStack(Materials.SPLASH_POTION.getStack(FluidType.LIQUID, amount), type);
    }

    public static FluidStack getLingeringPotion(int amount, PotionType type) {

        if (type == null || type == PotionTypes.EMPTY) {
            return null;
        }
        return addPotionToFluidStack(Materials.LINGERING_POTION.getStack(FluidType.LIQUID, amount), type);
    }

    @SuppressWarnings("ConstantConditions")
    public static FluidStack addPotionToFluidStack(FluidStack stack, PotionType type) {
        ResourceLocation resourcelocation = PotionType.REGISTRY.getNameForObject(type);
        if (resourcelocation == null) {
            return null;
        }
        if (type == PotionTypes.EMPTY) {
            if (stack.tag != null) {
                stack.tag.removeTag("Potion");
                if (stack.tag.hasNoTags()) {
                    stack.tag = null;
                }
            }
        } else {
            if (stack.tag == null) {
                stack.tag = new NBTTagCompound();
            }
            stack.tag.setString("Potion", resourcelocation.toString());
        }
        return stack;
    }

    public static FluidStack getPotionFluid(int amount, ItemStack stack) {
        Item item = stack.getItem();
        if (item.equals(Items.POTIONITEM)) {
            return getPotion(amount, PotionUtils.getPotionFromItem(stack));
        } else if (item.equals(Items.SPLASH_POTION)) {
            return getSplashPotion(amount, PotionUtils.getPotionFromItem(stack));
        } else if (item.equals(Items.LINGERING_POTION)) {
            return getLingeringPotion(amount, PotionUtils.getPotionFromItem(stack));
        }
        return null;
    }

    private final String prefix;

    public PotionFluid(String fluidName, String prefix) {
        super(fluidName, new ResourceLocation(Zairyou.ID, "blocks/fluids/potion_still"), new ResourceLocation(Zairyou.ID, "blocks/fluids/potion_flow"));
        this.prefix = prefix;
        FluidRegistry.registerFluid(this);
    }

    @Override
    public int getColor() {
        return 0xFFF800F8;
    }

    @Override
    public int getColor(FluidStack stack) {
        return 0xFF000000 | getPotionColor(stack);
    }

    @Override
    public String getLocalizedName(FluidStack stack) {
        PotionType type = PotionUtils.getPotionTypeFromNBT(stack.tag);
        if (type == PotionTypes.EMPTY || type == PotionTypes.WATER) {
            return super.getLocalizedName(stack);
        }
        return I18n.format(type.getNamePrefixed(prefix));
    }
     */

}