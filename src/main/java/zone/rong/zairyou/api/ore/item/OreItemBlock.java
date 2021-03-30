package zone.rong.zairyou.api.ore.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import zone.rong.zairyou.api.block.itemblock.MetaItemBlock;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.ore.block.OreBlock;

import javax.annotation.Nullable;

public class OreItemBlock extends MetaItemBlock {

    public OreItemBlock(OreBlock block) {
        super(block);
    }

    @Nullable
    @Override
    public String getCreatorModId(ItemStack stack) {
        return ((OreBlock) this.block).getAllowedTypes().getAllowedValues().get(stack.getMetadata()).getModId();
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        OreBlock ore = (OreBlock) this.getBlock();
        return I18n.format(BlockMaterialType.ORE.getTranslationKey(),
                I18n.format(ore.getOreGrade().getTranslationKey()),
                I18n.format(ore.getAllowedTypes().getAllowedValues().get(stack.getMetadata()).getTranslationKey()),
                I18n.format(ore.getMaterial().getTranslationKey()));
    }

}
