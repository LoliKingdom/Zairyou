package zone.rong.zairyou.api.client;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import zone.rong.zairyou.api.client.model.baked.FaceBakeryExtender;

import java.util.Map;
import java.util.function.UnaryOperator;

public interface IBakeable<T> {

    default IBakedModel bakeModel(IModel model, UnaryOperator<IModel> mutate, Map<String, String> sprites, VertexFormat format) {
        if (!sprites.containsKey("particle")) {
            sprites.put("particle", sprites.get("layer0"));
        }
        IModel retextured = model.retexture(ImmutableMap.copyOf(sprites));
        if (mutate != null) {
            retextured = mutate.apply(retextured);
        }
        return retextured.bake(retextured.getDefaultState(), format, ModelLoader.defaultTextureGetter());
    }

    @Deprecated
    default IBakedModel bake(FaceBakery faceBakery, ModelBlock baseModel, Map<String, String> sprites, Int2IntMap tints) {
        ModelBlock model = new ModelBlock(baseModel.getParentLocation(), baseModel.getElements(), sprites, baseModel.ambientOcclusion, baseModel.isGui3d(), baseModel.getAllTransforms(), baseModel.getOverrides());
        SimpleBakedModel.Builder builder = new SimpleBakedModel.Builder(model, model.createOverrides());
        // builder.setTexture(RenderUtils.getTexture(this.sprites.getOrDefault("particle", this.sprites.get("#layer0"))));
        builder.setTexture(RenderUtils.getTexture(model.textures.getOrDefault("particle", model.textures.get("layer0"))));
        for (BlockPart part : model.getElements()) {
            part.mapFaces.forEach((facing, blockFace) -> {
                if (tints.containsKey(blockFace.tintIndex)) {
                    builder.addFaceQuad(facing, ((FaceBakeryExtender) faceBakery).makeTintedQuad(
                            part.positionFrom,
                            part.positionTo,
                            blockFace,
                            RenderUtils.getTexture(model.textures.get(blockFace.texture.substring(1))),
                            facing,
                            ModelRotation.X0_Y0,
                            part.partRotation,
                            false,
                            tints.get(blockFace.tintIndex)));
                } else {
                    builder.addFaceQuad(facing, faceBakery.makeBakedQuad(
                            part.positionFrom,
                            part.positionTo,
                            blockFace,
                            // blockFace.texture.equals("#layer0") ? getTexture(base) : getTexture(overlay),
                            RenderUtils.getTexture(model.textures.get(blockFace.texture.substring(1))),
                            facing,
                            ModelRotation.X0_Y0,
                            part.partRotation,
                            false,
                            part.shade));
                }
            });
        }
        add(builder);
        return builder.makeBakedModel();
    }

    default void add(SimpleBakedModel.Builder builder) { }

    T template(Bakery.ModelType template);

    T prepareTexture(String element, ResourceLocation textureLocation);

    T prepareTexture(String element, String textureLocation);

    T prepareTextures(String element, ResourceLocation[] textureLocations);

    T mutate(UnaryOperator<IModel> model);

    T bake();

    IBakedModel take();

}
