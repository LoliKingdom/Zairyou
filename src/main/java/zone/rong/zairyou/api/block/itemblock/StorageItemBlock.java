package zone.rong.zairyou.api.block.itemblock;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import zone.rong.zairyou.api.block.StorageBlock;
import zone.rong.zairyou.api.material.type.BlockMaterialType;

public class StorageItemBlock extends MetaItemBlock {

    public StorageItemBlock(StorageBlock block) {
        super(block);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        StorageBlock storage = (StorageBlock) this.getBlock();
        return I18n.format(BlockMaterialType.STORAGE.getTranslationKey(), I18n.format(storage.getAllowedTypes().getAllowedValues().get(stack.getMetadata()).getTranslationKey()));
    }

}
