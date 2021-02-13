package zone.rong.zairyou.api.ore.stone;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.properties.PropertyHelper;

public class StoneTypeProperty extends PropertyHelper<StoneType> {

    private final ImmutableList<StoneType> allowedTypes;

    public StoneTypeProperty(StoneType... allowedTypes) {
        super("stone_type", StoneType.class);
        this.allowedTypes = ImmutableList.copyOf(allowedTypes);
    }

    @Override
    public ImmutableList<StoneType> getAllowedValues() {
        return this.allowedTypes;
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
