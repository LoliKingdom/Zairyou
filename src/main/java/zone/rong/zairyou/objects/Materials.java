package zone.rong.zairyou.objects;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.EnumRarity;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import zone.rong.zairyou.api.fluid.PotionFluid;
import zone.rong.zairyou.api.fluid.block.PotionFluidBlock;
import zone.rong.zairyou.api.material.Material;

import static zone.rong.zairyou.api.material.Material.of;
import static zone.rong.zairyou.api.fluid.FluidType.*;
import static zone.rong.zairyou.api.material.type.ItemMaterialType.*;

public class Materials {

    /*

		fluidPotion = new FluidPotion("potion", "thermalfoundation", "potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON);
		fluidPotionSplash = new FluidPotion("potion_splash", "thermalfoundation", "splash_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON);
		fluidPotionLingering = new FluidPotion("potion_lingering", "thermalfoundation", "lingering_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON);

		fluidRedstone = new FluidCore("redstone", "thermalfoundation").setLuminosity(7).setDensity(1200).setViscosity(1500).setRarity(EnumRarity.UNCOMMON);
		fluidGlowstone = new FluidCore("glowstone", "thermalfoundation").setLuminosity(15).setDensity(-500).setViscosity(100).setGaseous(true).setRarity(EnumRarity.UNCOMMON);
		fluidEnder = new FluidCore("ender", "thermalfoundation").setLuminosity(3).setDensity(4000).setViscosity(2500).setRarity(EnumRarity.UNCOMMON);
		fluidPyrotheum = new FluidCore("pyrotheum", "thermalfoundation").setLuminosity(15).setDensity(2000).setViscosity(1200).setTemperature(4000).setRarity(EnumRarity.RARE);
		fluidCryotheum = new FluidCore("cryotheum", "thermalfoundation").setDensity(4000).setViscosity(4000).setTemperature(50).setRarity(EnumRarity.RARE);
		fluidAerotheum = new FluidCore("aerotheum", "thermalfoundation").setDensity(-800).setViscosity(100).setGaseous(true).setRarity(EnumRarity.RARE);
		fluidPetrotheum = new FluidCore("petrotheum", "thermalfoundation").setDensity(4000).setViscosity(1500).setTemperature(350).setRarity(EnumRarity.RARE);
		fluidMana = new FluidCore("mana", "thermalfoundation").setLuminosity(15).setDensity(600).setViscosity(6000).setTemperature(350).setRarity(EnumRarity.EPIC);
     */

    public static final Material AIR = of("air", 0x58A7A5).fluid(LIQUID, fluid -> fluid.temperature(79).customName("liquid_air")).fluid(GASEOUS, fluid -> fluid.customTranslation().temperature(290));
    public static final Material COAL = of("coal", 0x464646).ore().fluid(LIQUID, fluid -> fluid.still("blocks/fluids/coal_still").flow("blocks/fluids/coal_flow").noTint().customTranslation().density(900).viscosity(2000));
    public static final Material COPPER = of("copper", 0xFF7400).ore().types(DUST, INGOT).fluid(MOLTEN, fluid -> fluid.temperature(1385)).tools(1, 144, 5.0F, 1.5F, -3.2F, 8, tools -> tools.axe().hoe().pickaxe().shovel().sword());
    public static final Material ELECTRUM = of("electrum", 0xFFFF64).types(DUST, INGOT, COIL).fluid(MOLTEN, fluid -> fluid.temperature(1337));
    public static final Material GOLD = of("gold", 0xFFFF00).ore().types(DUST, COIL).fluid(MOLTEN, fluid -> fluid.temperature(1337));
    public static final Material REDSTONE = of("redstone", 0xC80000).ore().type(SERVO).noTint(SERVO).texture(SERVO, "custom/redstone_servo").fluid(MOLTEN, fluid -> fluid.noTint().customTranslation().still("blocks/fluids/redstone_still").flow("blocks/fluids/redstone_flow").luminosity(7).density(1200).viscosity(1500).rarity(EnumRarity.UNCOMMON));
    public static final Material SILVER = of("silver", 0xC0C0C0).ore().types(DUST, INGOT, COIL).fluid(MOLTEN, fluid -> fluid.temperature(1235));

    public static final Material NITER = of("niter", 0xFFC8C8).types(DUST);

    /* Pure Fluid Materials */
    public static final Material BIOCRUDE = of("biocrude", 0x346217).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/biocrude_still").flow("blocks/fluids/biocrude_flow").noTint().customTranslation().density(1500).viscosity(2500));
    public static final Material CREOSOTE = of("creosote", 0x804000).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/creosote_still").flow("blocks/fluids/creosote_flow").noTint().customTranslation().density(1100).viscosity(2000));
    public static final Material CRUDE_OIL = of("crude_oil", 0x666666).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/crude_oil_still").flow("blocks/fluids/crude_oil_flow").noTint().customTranslation().density(900).viscosity(2000));
    public static final Material EXPERIENCE = of("experience", 0x28CE0A).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/experience_still").flow("blocks/fluids/experience_flow").noTint().luminosity(12).density(-200).viscosity(200).rarity(EnumRarity.UNCOMMON));
    public static final Material MUSHROOM_STEW = of("mushroom_stew", 0xB79474).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/mushroom_stew_still").flow("blocks/fluids/mushroom_stew_flow").noTint().customTranslation().density(2000).viscosity(2000));
    public static final Material REFINED_BIOFUEL = of("refined_biofuel", 0x7FA814).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/refined_biofuel_still").flow("blocks/fluids/refined_biofuel_flow").noTint().customTranslation().density(750).viscosity(800));
    public static final Material REFINED_OIL = of("refined_oil", 0x666666).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/refined_oil_still").flow("blocks/fluids/refined_oil_flow").noTint().customTranslation().density(800).viscosity(1400));
    public static final Material REFINED_FUEL = of("refined_fuel", 0xFFFF00).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/refined_fuel_still").flow("blocks/fluids/refined_fuel_flow").noTint().customTranslation().density(750).viscosity(800));
    public static final Material RESIN = of("resin", 0xA66E00).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/resin_still").flow("blocks/fluids/resin_flow").noTint().customTranslation().density(900).viscosity(3000));
    public static final Material SAP = of("sap", 0x946426).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/sap_still").flow("blocks/fluids/sap_flow").noTint().customTranslation().density(1050).viscosity(1500));
    public static final Material SEED_OIL = of("seed_oil", 0xC4FF00).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/seed_oil_still").flow("blocks/fluids/seed_oil_flow").noTint().customTranslation().density(950).viscosity(1300));
    // Fixme UNUSED: public static final Material SYRUP = of("syrup", 0x946426).fluid(GASEOUS, fluid -> fluid.still("blocks/fluids/sap_still").flow("blocks/fluids/sap_flow").noTint().density(1400).viscosity(2500));
    public static final Material STEAM = of("steam", 0xFFFFFF).fluid(GASEOUS, fluid -> fluid.still("blocks/fluids/steam_still").flow("blocks/fluids/steam_flow").noTint().customTranslation().viscosity(200).temperature(750));
    public static final Material TREE_OIL = of("tree_oil", 0x8F7638).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/tree_oil_still").flow("blocks/fluids/tree_oil_flow").noTint().customTranslation().density(900).viscosity(1200));

    /** Potions **/
    // public static final Material POTION = of("potion", 0xF800F8).fluid(LIQUID, new PotionFluid("potion", "potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON));
    // public static final Material SPLASH_POTION = of("potion_splash", 0xF800F8).fluid(LIQUID, new PotionFluid("potion_splash", "splash_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON));
    // public static final Material LINGERING_POTION = of("potion_lingering", 0xF800F8).fluid(LIQUID, new PotionFluid("potion_lingering", "lingering_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON));

    /* Marker/Pseudo Materials - TODO: Match colours with electric tier defaults */
    public static final Material RICH = of("rich").types(FERTILIZER, SLAG).noTints(FERTILIZER, SLAG).texture(FERTILIZER, "custom/rich_fertilizer").texture(SLAG, "custom/rich_slag");

    public static final Material FLUX = of("flux").type(FERTILIZER).noTint(FERTILIZER).texture(FERTILIZER, "custom/flux_fertilizer");

    public static void init() {
        Material.BASIC.types(FERTILIZER, SLAG).noTints(FERTILIZER, SLAG).texture(FERTILIZER, "custom/basic_fertilizer").texture(SLAG, "custom/basic_slag");
        Potions.init();
        // POTION.getFluid(LIQUID).setBlock(new PotionFluidBlock((PotionFluid) POTION.getFluid(LIQUID), LIQUID));
        // SPLASH_POTION.getFluid(LIQUID).setBlock(new PotionFluidBlock((PotionFluid) SPLASH_POTION.getFluid(LIQUID), LIQUID));
        // LINGERING_POTION.getFluid(LIQUID).setBlock(new PotionFluidBlock((PotionFluid) LINGERING_POTION.getFluid(LIQUID), LIQUID));
    }

    public static class Potions {

        private static final BiMap<PotionType, Material> potionMaterials = HashBiMap.create();

        public static void init() {
            ForgeRegistries.POTION_TYPES.getValuesCollection().forEach(p -> {
                if (p != PotionTypes.WATER) {
                    for (PotionFormat format : PotionFormat.VALUES) {
                        potionMaterials.put(p, Material.of(format.name + "_" + PotionType.REGISTRY.getNameForObject(p).getResourcePath(), PotionUtils.getPotionColor(p)).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/potion_still").flow("blocks/fluids/potion_flow").luminosity(3).density(500).viscosity(1500).rarity(EnumRarity.UNCOMMON)));
                    }
                }
            });
        }

        public static Material getMaterial(PotionType type) {
            return potionMaterials.get(type);
        }

        public static PotionType getPotionType(Material material) {
            return potionMaterials.inverse().get(material);
        }

        public enum PotionFormat {

            NORMAL("potion"),
            SPLASH("splash_potion"),
            LINGERING("lingering_potion");

            public static final PotionFormat[] VALUES;

            static {
                VALUES = values();
            }

            final String name;
            final String prefix;

            PotionFormat(String name) {
                this.name = name;
                this.prefix = name.concat(".effect.");
            }

        }

    }

}
