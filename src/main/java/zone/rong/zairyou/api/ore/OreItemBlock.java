package zone.rong.zairyou.api.ore;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.ore.stone.StoneType;

import javax.annotation.Nullable;

public class OreItemBlock extends ItemBlock {

    public OreItemBlock(OreBlock block) {
        super(block);
        setHasSubtypes(true);
    }

    @Nullable
    @Override
    public String getCreatorModId(ItemStack stack) {
        return StoneType.VALUES[stack.getMetadata()].getModId();
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        OreBlock ore = (OreBlock) this.getBlock();
        return I18n.format(BlockMaterialType.ORE.getTranslationKey(),
                I18n.format(ore.getGrade().getTranslationKey()),
                I18n.format(StoneType.VALUES[stack.getMetadata()].getTranslationKey()),
                I18n.format(ore.getMaterial().getTranslationKey()));
    }

}
