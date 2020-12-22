package zone.rong.zairyou.objects;

import net.minecraft.item.EnumRarity;
import zone.rong.zairyou.api.material.Material;

import static zone.rong.zairyou.api.material.Material.of;
import static zone.rong.zairyou.api.fluid.FluidType.*;
import static zone.rong.zairyou.api.material.type.MaterialType.*;

public class Materials {

    public static final Material AIR = of("air", 0x58A7A5).fluid(LIQUID, fluid -> fluid.temperature(79).customName("liquid_air")).fluid(GASEOUS, fluid -> fluid.temperature(290));
    public static final Material COPPER = of("copper", 0xFF7400).types(DUST, INGOT).fluid(MOLTEN, fluid -> fluid.temperature(1385)).tools(1, 144, 5.0F, 1.5F, -3.2F, 8, tools -> tools.axe().hoe().pickaxe().shovel().sword());
    public static final Material ELECTRUM = of("electrum", 0xFFFF64).types(DUST, INGOT, COIL).fluid(MOLTEN, fluid -> fluid.temperature(1337));
    public static final Material GOLD = of("gold", 0xFFFF00).types(DUST, COIL).fluid(MOLTEN, fluid -> fluid.temperature(1337));
    public static final Material REDSTONE = of("redstone", 0xC80000).type(SERVO).noTint(SERVO).texture(SERVO, "redstone_servo").fluid(MOLTEN, fluid -> fluid.noTint().still("blocks/fluids/redstone_still").flow("blocks/fluids/redstone_flow").luminosity(7).density(1200).viscosity(1500).rarity(EnumRarity.UNCOMMON));
    public static final Material SILVER = of("silver", 0xCCE0FF).types(DUST, INGOT, COIL).fluid(MOLTEN, fluid -> fluid.temperature(1235));

    /* Pure Fluid Materials */
    public static final Material RESIN = of("resin", 0xFFBE33).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/resin_still").flow("blocks/fluids/resin_flow").noTint().density(900).viscosity(3000));

    /* Marker/Pseudo Materials - TODO: Match colours with electric tier defaults */
    public static final Material RICH = of("rich", 0x0).types(FERTILIZER, SLAG).noTints(FERTILIZER, SLAG).texture(FERTILIZER, "rich_fertilizer").texture(SLAG, "rich_slag");

    public static final Material FLUX = of("flux", 0x0).type(FERTILIZER).noTint(FERTILIZER).texture(FERTILIZER, "flux_fertilizer");

    public static void init() {
        Material.BASIC.types(FERTILIZER, SLAG).noTints(FERTILIZER, SLAG).texture(FERTILIZER, "basic_fertilizer").texture(SLAG, "custom/basic_slag");
    }

}
