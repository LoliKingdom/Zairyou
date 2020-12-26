package zone.rong.zairyou.api.client;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.common.model.TRSRTransformation;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.client.model.baked.TintedBakedQuad;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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

    public Bakery() { }

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
            this.sprites.clear();
            this.tints.clear();
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
            this.block = builder.makeBakedModel(); // new WrappedBakedModel(builder.makeBakedModel(), false);
        }
        if (wantItem) {
            this.item = new ItemLayerModel(model).bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, RenderUtils::getTexture);
        }
        currentlyBaking = false;
        this.sprites.clear();
        this.tints.clear();
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

        NORMAL(Zairyou.ID, "block/normal", "block/block"),
        NORMAL_TINTED(Zairyou.ID, "block/normal_tinted", "block/block"),
        SINGLE_OVERLAY(Zairyou.ID, "block/single_overlay", "block/block");

        final ModelBlock baseModel;

        @SuppressWarnings("ConstantConditions")
        ModelType(String domain, String path) {
            IResource resource = null;
            try {
                resource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(domain, "models/" + path + ".json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.baseModel = ModelBlock.deserialize(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
        }

        ModelType(String domain, String path, String parent) {
            this(domain, path);
            IResource resource = null;
            try {
                resource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("models/" + parent + ".json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.baseModel.parent = ModelBlock.deserialize(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
        }

    }

}
