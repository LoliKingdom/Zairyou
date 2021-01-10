package zone.rong.zairyou.api.item;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zone.rong.zairyou.api.client.Bakery;
import zone.rong.zairyou.api.client.IModelOverride;
import zone.rong.zairyou.api.client.RenderUtils;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.ItemMaterialType;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MaterialItem extends Item implements IModelOverride, IItemColor {

    private final Material material;
    private final ItemMaterialType itemMaterialType;
    private final String[] oreNames;

    public MaterialItem(Material material, ItemMaterialType itemMaterialType) {
        this.material = material;
        this.itemMaterialType = itemMaterialType;
        this.oreNames = material.getOreNames(itemMaterialType);
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    public Material getMaterial() {
        return material;
    }

    public ItemMaterialType getMaterialType() {
        return itemMaterialType;
    }

    public String getPrimaryOreName() {
        return oreNames[0];
    }

    public String[] getOreNames() {
        return oreNames;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(itemMaterialType.getTranslationKey(), I18n.format(material.getTranslationKey()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        String formula = material.getChemicalFormula();
        if (!formula.isEmpty()) {
            tooltip.add(TextFormatting.AQUA.toString() + formula);
        }
    }

    @Nullable
    @Override
    public String getCreatorModId(ItemStack stack) {
        return itemMaterialType.getModName();
    }

    @Override
    public void addTextures(Set<ResourceLocation> textures) {
        textures.addAll(Arrays.asList(material.getTextures(itemMaterialType)));
    }

    @Override
    public void onModelRegister() {
        final ModelResourceLocation location = RenderUtils.getSimpleModelLocation(this);
        ModelBakery.registerItemVariants(this, location);
        ModelLoader.setCustomMeshDefinition(this, stack -> location);
    }

    @Override
    public void onModelBake(ModelBakeEvent event) {
        event.getModelRegistry().putObject(RenderUtils.getSimpleModelLocation(this),
                Bakery.INSTANCE.getItemDepartment()
                        .template(Bakery.ModelType.NORMAL_ITEM)
                        .prepareTextures("layer", material.getTextures(itemMaterialType))
                        .bake().take());
    }

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        return tintIndex == 0 ? material.getColour() : -1;
    }
}
