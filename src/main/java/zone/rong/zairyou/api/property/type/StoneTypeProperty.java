package zone.rong.zairyou.api.property.type;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.properties.PropertyHelper;
import zone.rong.zairyou.api.ore.stone.StoneType;
import zone.rong.zairyou.api.property.FreezableProperty;

public class StoneTypeProperty extends FreezableProperty<StoneType> {

    public StoneTypeProperty() {
        super("stone_type", StoneType.class);
    }

    @Override
    @SuppressWarnings("all")
    public Optional<StoneType> parseValue(String value) {
        try {
            return Optional.of(StoneType.valueOf(value));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return Optional.absent();
    }

    @Override
    public String getName(StoneType value) {
        return value.getName();
    }

}
