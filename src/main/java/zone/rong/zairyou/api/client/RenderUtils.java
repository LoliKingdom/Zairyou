package zone.rong.zairyou.api.client;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.client.model.baked.BasicBakedModel;

import java.util.Map;
import java.util.function.Function;

public class RenderUtils {

    public static final Function<Block, StateMapper> SIMPLE_STATE_MAPPER = block -> new StateMapper(getSimpleModelLocation(block));

    public static final ItemMeshDefinition SIMPLE_MESH_DEFINITION = stack -> getSimpleModelLocation(Block.getBlockFromItem(stack.getItem()));

    public static IModel singleOverlay;

    public static void buildModels() {
        singleOverlay = load("single_overlay");
    }

    public static ModelResourceLocation getSimpleModelLocation(Block block) {
        return new ModelResourceLocation(Block.REGISTRY.getNameForObject(block), "");
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

    public static IBakedModel textureAndBakeBlock(IModel model, Map<String, String> textures) {
        if (!(textures instanceof ImmutableMap)) {
            textures = ImmutableMap.copyOf(textures);
        }
        model.retexture((ImmutableMap<String, String>) textures);
        return bake(model, new ResourceLocation(textures.get("particle")));
    }

    public static IModel texture(IModel model, String textureElement, ResourceLocation textureLocation) {
        try {
            return model.retexture(ImmutableMap.of(textureElement, textureLocation.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return model;
        }
    }

    public static IModel texture(IModel model, ImmutableMap<String, String> textures) {
        try {
            return model.retexture(textures);
        } catch (Exception e) {
            e.printStackTrace();
            return model;
        }
    }

    public static IBakedModel bake(IModel model, EnumFacing... rotations) {
        TRSRTransformation transformation = TRSRTransformation.identity();
        for (EnumFacing rotation : rotations) {
            transformation = transformation.compose(TRSRTransformation.from(rotation));
        }
        return model.bake(transformation, DefaultVertexFormats.BLOCK, RenderUtils::getTexture);
    }

    public static IBakedModel bake(IModel model, ResourceLocation particle, EnumFacing... rotations) {
        return new BasicBakedModel(particle, bake(model, rotations));
    }

    public static IBakedModel bake(IModel model, ResourceLocation particle) {
        return new BasicBakedModel(particle, bake(model, EnumFacing.VALUES));
    }

    public static int convertRGB2ARGB(int alpha, int colour) {
        return alpha << 24 | ((colour >> 16) & 0xFF) << 16 | ((colour >> 8) & 0xFF) << 8 | colour & 0xFF;
    }

    public static int convertRGB2ARGB(int colour) {
        return 0xFF << 24 | ((colour >> 16) & 0xFF) << 16 | ((colour >> 8) & 0xFF) << 8 | colour & 0xFF;
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
