package zone.rong.zairyou.api.property.type;

import com.google.common.base.Optional;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.property.FreezableProperty;

public class MaterialProperty extends FreezableProperty<Material> {

    public MaterialProperty() {
        super("material", Material.class);
    }

    @Override
    public Optional<Material> parseValue(String value) {
        return Optional.of(Material.get(value));
    }

    @Override
    public String getName(Material value) {
        return value.getName();
    }
}
