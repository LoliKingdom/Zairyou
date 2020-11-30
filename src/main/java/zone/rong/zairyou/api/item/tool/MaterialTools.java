package zone.rong.zairyou.api.item.tool;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.item.*;

import java.util.List;

public class MaterialTools {

    private final ExtendedToolMaterial toolMaterial;
    private final List<Item> tools = new ObjectArrayList<>();

    public MaterialTools(ExtendedToolMaterial toolMaterial) {
        this.toolMaterial = toolMaterial;
    }

    public MaterialTools axe() {
        this.tools.add(new ItemAxe(toolMaterial.getBase(), toolMaterial.getBase().getAttackDamage(), toolMaterial.getAttackSpeed()) { });
        return this;
    }

    public MaterialTools hoe() {
        this.tools.add(new ItemHoe(toolMaterial.getBase()));
        return this;
    }

    public MaterialTools pickaxe() {
        this.tools.add(new ItemPickaxe(toolMaterial.getBase()) { });
        return this;
    }

    public MaterialTools shovel() {
        this.tools.add(new ItemSpade(toolMaterial.getBase()));
        return this;
    }

    public MaterialTools sword() {
        this.tools.add(new ItemSword(toolMaterial.getBase()));
        return this;
    }

    public List<Item> getTools() {
        return tools;
    }

    /*
    public List<Item> build() {
        return new ObjectArrayList<>(tools);
    }
     */

}
