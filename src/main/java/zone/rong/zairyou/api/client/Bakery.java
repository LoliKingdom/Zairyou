package zone.rong.zairyou.api.client.model.baked;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.common.model.TRSRTransformation;
import zone.rong.zairyou.api.client.RenderUtils;

import java.util.Map;

public class Bakery {

    private static final FaceBakery FACE_BAKERY = new FaceBakery();
    // private static final ItemModelGenerator ITEM_MODEL_GENERATOR = new ItemModelGenerator();

    public static final Bakery INSTANCE = new Bakery();

    private final Map<String, String> sprites = new Object2ObjectOpenHashMap<>();
    private final Int2IntMap tints = new Int2IntOpenHashMap();

    private boolean currentlyBaking = false;
    private ModelType template;
    private IBakedModel block, item;

    private Bakery() { }

    public Bakery template(ModelType template) {
        this.template = template;
        return this;
    }

    public Bakery prepareTexture(String element, ResourceLocation textureLocation) {
        // this.sprites.put(element.startsWith("#") ? element : "#".concat(element), textureLocation.toString());
        this.sprites.put(element, textureLocation.toString());
        return this;
    }

    public Bakery prepareTexture(String element, String textureLocation) {
        // this.sprites.put(element.startsWith("#") ? element : "#".concat(element), textureLocation);
        this.sprites.put(element, textureLocation);
        return this;
    }

    public Bakery tint(int tintIndex, int rgb) {
        this.tints.put(tintIndex, rgb);
        return this;
    }

    public void bake(boolean wantBlock, boolean wantItem) {
        currentlyBaking = true;
        if (!wantBlock && !wantItem) {
            currentlyBaking = false;
            return;
        }
        final ModelBlock baseModel = this.template.baseModel;
        ModelBlock model = new ModelBlock(baseModel.getParentLocation(), baseModel.getElements(), this.sprites, baseModel.ambientOcclusion, baseModel.isGui3d(), baseModel.getAllTransforms(), baseModel.getOverrides());
        if (wantBlock) {
            SimpleBakedModel.Builder builder = new SimpleBakedModel.Builder(model, model.createOverrides());
            // builder.setTexture(RenderUtils.getTexture(this.sprites.getOrDefault("particle", this.sprites.get("#layer0"))));
            builder.setTexture(RenderUtils.getTexture(model.textures.getOrDefault("particle", model.textures.get("layer0"))));
            for (BlockPart part : model.getElements()) {
                part.mapFaces.forEach((facing, blockFace) -> {
                    BakedQuad quad = FACE_BAKERY.makeBakedQuad(
                            part.positionFrom,
                            part.positionTo,
                            blockFace,
                            // blockFace.texture.equals("#layer0") ? getTexture(base) : getTexture(overlay),
                            RenderUtils.getTexture(model.textures.get(blockFace.texture.substring(1))),
                            facing,
                            ModelRotation.X0_Y0,
                            part.partRotation,
                            false,
                            part.shade);
                    builder.addFaceQuad(facing, this.tints.containsKey(blockFace.tintIndex) ? new TintedBakedQuad(quad, this.tints.get(blockFace.tintIndex)) : quad);
                });
            }
            this.block = new WrappedBakedModel(builder.makeBakedModel(), false);
        }
        if (wantItem) {
            this.item = new WrappedBakedModel(new ItemLayerModel(model).bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, RenderUtils::getTexture), true);
        }
        currentlyBaking = false;
    }

    public IBakedModel receiveBlock() {
        if (currentlyBaking) {
            throw new RuntimeException("Cannot retrieved IBakedModels whilst baking.");
        }
        IBakedModel take = this.block;
        this.block = null;
        return take;
    }

    public IBakedModel receiveItem() {
        if (currentlyBaking) {
            throw new RuntimeException("Cannot retrieved IBakedModels whilst baking.");
        }
        IBakedModel take = this.item;
        this.item = null;
        return take;
    }

    public enum ModelType {

        SINGLE_OVERLAY("{\n" +
                "  \"parent\": \"block/block\",\n" +
                "  \"elements\": [\n" +
                "    {\n" +
                "      \"from\": [0, 0, 0],\n" +
                "      \"to\": [16, 16, 16],\n" +
                "      \"faces\": {\n" +
                "        \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#layer0\", \"cullface\": \"down\" },\n" +
                "        \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#layer0\", \"cullface\": \"up\" },\n" +
                "        \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#layer0\", \"cullface\": \"north\" },\n" +
                "        \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#layer0\", \"cullface\": \"south\" },\n" +
                "        \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#layer0\", \"cullface\": \"west\" },\n" +
                "        \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#layer0\", \"cullface\": \"east\" }\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"from\": [0, 0, 0],\n" +
                "      \"to\": [16, 16, 16],\n" +
                "      \"faces\": {\n" +
                "        \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#layer1\", \"tintindex\": 1, \"cullface\": \"down\" },\n" +
                "        \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#layer1\", \"tintindex\": 1, \"cullface\": \"up\" },\n" +
                "        \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#layer1\", \"tintindex\": 1, \"cullface\": \"north\" },\n" +
                "        \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#layer1\", \"tintindex\": 1, \"cullface\": \"south\" },\n" +
                "        \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#layer1\", \"tintindex\": 1, \"cullface\": \"west\" },\n" +
                "        \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#layer1\", \"tintindex\": 1, \"cullface\": \"east\" }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}");

        final ModelBlock baseModel;

        ModelType(String json) {
            this.baseModel = ModelBlock.deserialize(json);
        }

    }

}
