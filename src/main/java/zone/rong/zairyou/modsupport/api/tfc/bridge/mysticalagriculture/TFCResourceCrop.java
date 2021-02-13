package zone.rong.zairyou.modsupport.api.tfc.bridge.mysticalagriculture;

import net.minecraft.item.ItemStack;
import zone.rong.zairyou.modsupport.api.tfc.TFCCrop;

import javax.annotation.Nullable;

public class TFCResourceCrop extends TFCCrop {

    protected TFCResourceCrop(String id, long growthTicks, int maxStages, float tempMinAlive, float tempMinGrow, float tempMaxGrow, float tempMaxAlive, float rainMinAlive, float rainMinGrow, float rainMaxGrow, float rainMaxAlive, ItemStack drop, ItemStack immatureDrop, @Nullable TFCCrop.InfoProvider infoProvider) {
        super(id, growthTicks, maxStages, tempMinAlive, tempMinGrow, tempMaxGrow, tempMaxAlive, rainMinAlive, rainMinGrow, rainMaxGrow, rainMaxAlive, drop, immatureDrop, infoProvider);
    }

}
