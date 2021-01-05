package zone.rong.zairyou.api.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionCreativeTab extends CreativeTabs {

    public PotionCreativeTab() {
        super("potion");
    }

    @Override
    public ItemStack getTabIconItem() {
        return ItemStack.EMPTY;
        // return FluidUtil.getFilledBucket(PotionFluid.getPotion(1000, PotionTypes.STRENGTH));
    }

    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> items) {
        /*
        ForgeRegistries.POTION_TYPES.getValuesCollection().stream()
                .filter(p -> p != PotionTypes.EMPTY)
                .forEach(p -> items.add(FluidUtil.getFilledBucket(PotionFluid.getPotion(1000, p))));
         */
    }

}
