package zone.rong.zairyou.objects;

import net.minecraft.item.EnumRarity;
import zone.rong.zairyou.api.material.Material;

import static zone.rong.zairyou.api.material.Material.of;
import static zone.rong.zairyou.api.fluid.FluidType.*;
import static zone.rong.zairyou.api.material.type.ItemMaterialType.*;

public class Materials {

    /*
		fluidCreosote = new FluidCore("creosote", "thermalfoundation").setDensity(1100).setViscosity(2000);
		fluidCoal = new FluidCore("coal", "thermalfoundation").setDensity(900).setViscosity(2000);
		fluidCrudeOil = new FluidCore("crude_oil", "thermalfoundation").setDensity(900).setViscosity(2000);
		fluidRefinedOil = new FluidCore("refined_oil", "thermalfoundation").setDensity(800).setViscosity(1400);
		fluidFuel = new FluidCore("refined_fuel", "thermalfoundation").setDensity(750).setViscosity(800);

		fluidSap = new FluidCore("sap", "thermalfoundation").setDensity(1050).setViscosity(1500);
		fluidSyrup = new FluidCore("syrup", "thermalfoundation").setDensity(1400).setViscosity(2500);
		fluidResin = new FluidCore("resin", "thermalfoundation").setDensity(900).setViscosity(3000);
		fluidTreeOil = new FluidCore("tree_oil", "thermalfoundation").setDensity(900).setViscosity(1200);

		fluidSeedOil = new FluidCore("seed_oil", "thermalfoundation").setDensity(950).setViscosity(1300);
		fluidBiocrude = new FluidCore("biocrude", "thermalfoundation").setDensity(1500).setViscosity(2500);
		fluidBiofuel = new FluidCore("refined_biofuel", "thermalfoundation").setDensity(750).setViscosity(800);

		fluidMushroomStew = new FluidCore("mushroom_stew", "thermalfoundation").setDensity(2000).setViscosity(2000);
		fluidExperience = new FluidCore("experience", "thermalfoundation").setLuminosity(12).setDensity(-200).setViscosity(200).setGaseous(true).setRarity(EnumRarity.UNCOMMON);

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

    public static final Material AIR = of("air", 0x58A7A5).fluid(LIQUID, fluid -> fluid.temperature(79).customName("liquid_air")).fluid(GASEOUS, fluid -> fluid.temperature(290));
    public static final Material COAL = of("coal", 0x464646).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/coal_still").flow("blocks/fluids/coal_flow").noTint().density(900).viscosity(2000));
    public static final Material COPPER = of("copper", 0xFF7400).ore().types(DUST, INGOT).fluid(MOLTEN, fluid -> fluid.temperature(1385)).tools(1, 144, 5.0F, 1.5F, -3.2F, 8, tools -> tools.axe().hoe().pickaxe().shovel().sword());
    public static final Material ELECTRUM = of("electrum", 0xFFFF64).types(DUST, INGOT, COIL).fluid(MOLTEN, fluid -> fluid.temperature(1337));
    public static final Material GOLD = of("gold", 0xFFFF00).types(DUST, COIL).fluid(MOLTEN, fluid -> fluid.temperature(1337));
    public static final Material REDSTONE = of("redstone", 0xC80000).type(SERVO).noTint(SERVO).texture(SERVO, "custom/redstone_servo").fluid(MOLTEN, fluid -> fluid.noTint().still("blocks/fluids/redstone_still").flow("blocks/fluids/redstone_flow").luminosity(7).density(1200).viscosity(1500).rarity(EnumRarity.UNCOMMON));
    public static final Material SILVER = of("silver", 0xCCE0FF).ore().types(DUST, INGOT, COIL).fluid(MOLTEN, fluid -> fluid.temperature(1235));

    /* Pure Fluid Materials */
    public static final Material CREOSOTE = of("creosote", 0x804000).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/creosote_still").flow("blocks/fluids/creosote_flow").noTint().density(1100).viscosity(2000));
    public static final Material CRUDE_OIL = of("oil", 0x804000).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/crude_oil_still").flow("blocks/fluids/crude_oil_flow").noTint().density(900).viscosity(2000));
    public static final Material REFINED_OIL = of("refined_oil", 0x804000).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/refined_oil_still").flow("blocks/fluids/refined_oil_flow").noTint().density(800).viscosity(1400));
    public static final Material RESIN = of("resin", 0xFFBE33).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/resin_still").flow("blocks/fluids/resin_flow").noTint().density(900).viscosity(3000));
    public static final Material STEAM = of("steam", 0xFFFFFF).fluid(GASEOUS, fluid -> fluid.still("blocks/fluids/steam_still").flow("blocks/fluids/steam_flow").noTint().viscosity(200).temperature(750));

    /* Marker/Pseudo Materials - TODO: Match colours with electric tier defaults */
    public static final Material RICH = of("rich").types(FERTILIZER, SLAG).noTints(FERTILIZER, SLAG).texture(FERTILIZER, "custom/rich_fertilizer").texture(SLAG, "custom/rich_slag");

    public static final Material FLUX = of("flux").type(FERTILIZER).noTint(FERTILIZER).texture(FERTILIZER, "custom/flux_fertilizer");

    public static void init() {
        Material.BASIC.types(FERTILIZER, SLAG).noTints(FERTILIZER, SLAG).texture(FERTILIZER, "custom/basic_fertilizer").texture(SLAG, "custom/basic_slag");
    }

}
