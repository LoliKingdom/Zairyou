package zone.rong.zairyou.api.client;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import zone.rong.zairyou.Zairyou;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import java.util.EnumMap;
import java.util.function.Function;

public class RenderUtils {

    public static final Function<Block, StateMapper> SIMPLE_STATE_MAPPER = block -> new StateMapper(getSimpleModelLocation(block));
    public static final ItemMeshDefinition SIMPLE_MESH_DEFINITION = stack -> getSimpleModelLocation(Block.getBlockFromItem(stack.getItem()));

    private static EnumMap<ItemCameraTransforms.TransformType, Matrix4f> TRANSFORM_MAP_ITEM = new EnumMap<>(ItemCameraTransforms.TransformType.class);
    private static EnumMap<ItemCameraTransforms.TransformType, Matrix4f> TRANSFORM_MAP_BLOCK = new EnumMap<>(ItemCameraTransforms.TransformType.class);

    private static final Matrix4f[] FACING_TO_MATRIX = new Matrix4f[] {
            getMat(new AxisAngle4f(new Vector3f(1, 0, 0), 4.7124f)),
            getMat(new AxisAngle4f(new Vector3f(1, 0, 0), 1.5708f)),
            getMat(new AxisAngle4f(new Vector3f(0, 1, 0), 0f)),
            getMat(new AxisAngle4f(new Vector3f(0, 1, 0), 3.1416f)),
            getMat(new AxisAngle4f(new Vector3f(0, 1, 0), 1.5708f)),
            getMat(new AxisAngle4f(new Vector3f(0, 1, 0), 4.7124f)),
    };

    static {
        TRANSFORM_MAP_ITEM.put(ItemCameraTransforms.TransformType.GUI, getTransform(0, 0, 0, 0, 0, 0, 1f).getMatrix());
        TRANSFORM_MAP_ITEM.put(ItemCameraTransforms.TransformType.GROUND, getTransform(0, 2, 0, 0, 0, 0, 0.5f).getMatrix());
        TRANSFORM_MAP_ITEM.put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, getTransform(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f).getMatrix());
        TRANSFORM_MAP_ITEM.put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, getTransform(0, 3, 1, 0, 0, 0, 0.55f).getMatrix());
        TRANSFORM_MAP_ITEM.put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, getTransform(1.13f, 3.2f, 1.13f, 0, 90, -25, 0.68f).getMatrix());
        TRANSFORM_MAP_ITEM.put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, getTransform(0f, 4.0f, 0.5f, 0, 90, -55, 0.85f).getMatrix());

        TRANSFORM_MAP_BLOCK.put(ItemCameraTransforms.TransformType.GUI, getTransform(0, 0, 0, 30, 225, 0, 0.625f).getMatrix());
        TRANSFORM_MAP_BLOCK.put(ItemCameraTransforms.TransformType.GROUND, getTransform(0, 2, 0, 0, 0, 0, 0.25f).getMatrix());
        TRANSFORM_MAP_BLOCK.put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, getTransform(0, 0, 0, 0, 45, 0, 0.4f).getMatrix());
        TRANSFORM_MAP_BLOCK.put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, getTransform(0, 0, 0, 0, 0, 0, 0.4f).getMatrix());
        TRANSFORM_MAP_BLOCK.put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, getTransform(0, 0, 0, 45, 0, 0, 0.4f).getMatrix());
        TRANSFORM_MAP_BLOCK.put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, getTransform(0, 0, 0, 45, 0, 0, 0.4f).getMatrix());
    }

    public static ModelResourceLocation getSimpleModelLocation(Block block) {
        return new ModelResourceLocation(Block.REGISTRY.getNameForObject(block), "");
    }

    public static ModelResourceLocation getSimpleModelLocation(Item item) {
        return new ModelResourceLocation(Item.REGISTRY.getNameForObject(item), "inventory");
    }

    public static TextureMap getTextureMap() {
        return Minecraft.getMinecraft().getTextureMapBlocks();
    }

    public static TextureAtlasSprite getMissingSprite() {
        return getTextureMap().getMissingSprite();
    }

    public static TextureAtlasSprite getTexture(String location) {
        return getTextureMap().getAtlasSprite(location);
    }

    public static TextureAtlasSprite getTexture(ResourceLocation location) {
        return getTexture(location.toString());
    }

    /** Model Helpers **/
    public static IModel load(String path) {
        return load(new ModelResourceLocation(Zairyou.ID + ":" + path));
    }

    public static IModel load(String domain, String path) {
        return load(new ModelResourceLocation(domain + ":" + path));
    }

    public static IModel load(ModelResourceLocation loc) {
        try {
            return ModelLoaderRegistry.getModel(loc);
        } catch (Exception e) {
            System.err.println("ModelBase.load() failed due to " + e + ":");
            e.printStackTrace();
            return ModelLoaderRegistry.getMissingModel();
        }
    }

    public static Matrix4f getItemTransform(ItemCameraTransforms.TransformType type) {
        Matrix4f mat = TRANSFORM_MAP_ITEM.get(type);
        return mat != null ? mat : TRSRTransformation.identity().getMatrix();
    }

    public static Matrix4f getBlockTransform(ItemCameraTransforms.TransformType type) {
        Matrix4f mat = TRANSFORM_MAP_BLOCK.get(type);
        return mat != null ? mat : TRSRTransformation.identity().getMatrix();
    }

    public static int convertRGB2ARGB(int alpha, int colour) {
        return alpha << 24 | ((colour >> 16) & 0xFF) << 16 | ((colour >> 8) & 0xFF) << 8 | colour & 0xFF;
    }

    public static int convertRGB2ARGB(int colour) {
        return 0xFF << 24 | ((colour >> 16) & 0xFF) << 16 | ((colour >> 8) & 0xFF) << 8 | colour & 0xFF;
    }

    public static int convertRGB2ABGR(int colour) {
        return 0xFF << 24 | ((colour & 0xFF) << 16) | ((colour >> 8) & 0xFF) << 8 | (colour >> 16) & 0xFF;
    }

    public static TRSRTransformation getTransform(float tx, float ty, float tz, float ax, float ay, float az, float s) {
        return new TRSRTransformation(new Vector3f(tx / 16, ty / 16, tz / 16), TRSRTransformation.quatFromXYZDegrees(new Vector3f(ax, ay, az)), new Vector3f(s, s, s), null);
    }

    public static Matrix4f getMat(AxisAngle4f angle) {
        Matrix4f mat = new Matrix4f();
        mat.setIdentity();
        mat.setRotation(angle);
        return mat;
    }

    public static class StateMapper extends StateMapperBase {

        private final ModelResourceLocation location;

        public StateMapper(ModelResourceLocation location) {
            this.location = location;
        }

        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
            return location;
        }

    }

}
