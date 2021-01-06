package zone.rong.zairyou.objects;

import net.minecraftforge.oredict.OreDictionary;
import zone.rong.zairyou.api.item.BasicItem;

public class ZairyouItems {

    public static final BasicItem GLOB_ROSIN = BasicItem.of("thermalfoundation", "rosin_glob", "glob_rosin");
    public static final BasicItem GLOB_TAR = BasicItem.of("thermalfoundation", "tar_glob", "glob_tar");

    public static void init() { }

    public static void oreDictInit() {
        OreDictionary.registerOre("globRosin", GLOB_ROSIN);
        OreDictionary.registerOre("globTar", GLOB_TAR);
    }

}
