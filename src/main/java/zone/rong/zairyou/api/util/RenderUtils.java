package zone.rong.zairyou.api.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public class RenderUtils {

    public static final Function<Block, StateMapper> SIMPLE_STATE_MAPPER = block -> new StateMapper(getSimpleModelLocation(block));

    public static final ItemMeshDefinition SIMPLE_MESH_DEFINITION = stack -> getSimpleModelLocation(Block.getBlockFromItem(stack.getItem()));

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
