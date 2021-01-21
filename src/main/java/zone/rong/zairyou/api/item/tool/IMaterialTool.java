package zone.rong.zairyou.api.item.tool;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import zone.rong.zairyou.api.material.Material;

public interface IMaterialTool extends IItemColor {

    String mainTagName = "ZairyouTool";
    String materialTag = "Material", durabilityTag = "Durability";

    default NBTTagCompound getMainTag(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            NBTTagCompound mainTag = new NBTTagCompound();
            tag.setTag(mainTagName, mainTag);
            stack.setTagCompound(tag);
            return mainTag;
        }
        return tag.getCompoundTag(mainTagName);
    }

    default Material getMaterial(ItemStack stack) {
        return Material.get(getMainTag(stack).getString(materialTag));
    }

    default Material getMaterial(NBTTagCompound mainTag) {
        return Material.get(mainTag.getString(materialTag));
    }

    default int getDurability(ItemStack stack) {
        return getMainTag(stack).getInteger(durabilityTag);
    }

    default int getDurability(NBTTagCompound mainTag) {
        return mainTag.getInteger(durabilityTag);
    }

    default void setDurability(ItemStack stack, int durability) {
        getMainTag(stack).setInteger(durabilityTag, durability);
    }

    default int increaseDurability(ItemStack stack, int durability) {
        NBTTagCompound mainTag = getMainTag(stack);
        int newDurability = getDurability(mainTag);
        // TODO: int maxUses = getMaterial(mainTag).getToolMaterial().getBase().getMaxUses();
        int maxUses = 0;
        if (newDurability + durability > maxUses) {
            newDurability = maxUses;
        } else {
            newDurability += durability;
        }
        mainTag.setInteger(durabilityTag, newDurability);
        return newDurability;
    }

    default int decreaseDurability(ItemStack stack, int durability) {
        NBTTagCompound mainTag = getMainTag(stack);
        int newDurability = getDurability(mainTag);
        if (newDurability - durability < 0) {
            newDurability = 0;
        } else {
            newDurability -= durability;
        }
        mainTag.setInteger(durabilityTag, newDurability);
        return newDurability;
    }

    @Override
    default int colorMultiplier(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            return getMaterial(stack).getColour();
        }
        return 0;
    }
}
