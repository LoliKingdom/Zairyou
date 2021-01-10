package zone.rong.zairyou.api.event;

import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.Event;
import zone.rong.zairyou.api.material.MaterialBuilder;

import java.util.function.UnaryOperator;

public class MaterialRegisterEvent extends Event {

    private final String name;
    private final int colour;
    private final MaterialBuilder builder;
    private final ModContainer parent;

    public MaterialRegisterEvent(String name, int colour, MaterialBuilder builder, ModContainer parent) {
        this.name = name;
        this.colour = colour;
        this.builder = builder;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public int getColour() {
        return colour;
    }

    public ModContainer getMaterialParent() {
        return parent;
    }

    public void modifyMaterial(UnaryOperator<MaterialBuilder> builder) {
        builder.apply(this.builder);
    }

}
