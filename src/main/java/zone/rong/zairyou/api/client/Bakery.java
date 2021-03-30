package zone.rong.zairyou.api.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import zone.rong.zairyou.Zairyou;

import java.util.Map;
import java.util.function.UnaryOperator;

public class Bakery {

    // @Deprecated private static FaceBakery FACE_BAKERY = null;
    public static Bakery INSTANCE = new Bakery();

    public static void shutdown() {
        INSTANCE = null;
    }

    private final BlockDepartment blockDepartment;
    private final ItemDepartment itemDepartment;

    protected Bakery() {
        this.blockDepartment = new BlockDepartment();
        this.itemDepartment = new ItemDepartment();
    }

    public BlockDepartment getBlockDepartment() {
        return blockDepartment;
    }

    public ItemDepartment getItemDepartment() {
        return itemDepartment;
    }

    public static class BlockDepartment implements IBakeable<BlockDepartment> {

        private final Map<String, String> sprites = new Object2ObjectOpenHashMap<>();

        private boolean currentlyBaking = false;
        private ModelType template;
        private UnaryOperator<IModel> mutate;
        private IBakedModel product;

        @Override
        public BlockDepartment template(ModelType template) {
            this.currentlyBaking = true;
            this.template = template;
            return this;
        }

        @Override
        public BlockDepartment prepareTexture(String element, ResourceLocation textureLocation) {
            this.sprites.put(element, textureLocation.toString());
            return this;
        }

        @Override
        public BlockDepartment prepareTexture(String element, String textureLocation) {
            this.sprites.put(element, textureLocation);
            return this;
        }

        @Override
        public BlockDepartment prepareTextures(String element, ResourceLocation[] textureLocations) {
            int layer = 0;
            for (ResourceLocation location : textureLocations) {
                this.sprites.put(element + layer++, location.toString());
            }
            return this;
        }

        @Override
        public BlockDepartment mutate(UnaryOperator<IModel> model) {
            this.mutate = model;
            return this;
        }

        @Override
        public BlockDepartment bake() {
            if (!this.currentlyBaking) {
                throw new RuntimeException("Tried baking with no ingredients!");
            }
            this.product = bakeModel(this.template.model, this.mutate, this.sprites, DefaultVertexFormats.BLOCK);
            // this.product = bake(Bakery.FACE_BAKERY, this.template.baseModel, this.sprites, this.tints);
            this.template = null;
            this.sprites.clear();
            this.currentlyBaking = false;
            return this;
        }

        @Override
        public IBakedModel take() {
            if (this.currentlyBaking) {
                throw new RuntimeException("The bake isn't done yet!");
            }
            IBakedModel take = this.product;
            this.product = null;
            return take;
        }

    }

    public static class ItemDepartment implements IBakeable<ItemDepartment> {

        private final Map<String, String> sprites = new Object2ObjectOpenHashMap<>();

        private boolean currentlyBaking = false;
        private ModelType template;
        private UnaryOperator<IModel> mutate;
        private IBakedModel product;

        @Override
        public ItemDepartment template(ModelType template) {
            this.currentlyBaking = true;
            this.template = template;
            return this;
        }

        @Override
        public ItemDepartment prepareTexture(String element, ResourceLocation textureLocation) {
            this.sprites.put(element, textureLocation.toString());
            return this;
        }

        @Override
        public ItemDepartment prepareTexture(String element, String textureLocation) {
            this.sprites.put(element, textureLocation);
            return this;
        }

        @Override
        public ItemDepartment prepareTextures(String element, ResourceLocation[] textureLocations) {
            int layer = 0;
            for (ResourceLocation location : textureLocations) {
                this.sprites.put(element + layer++, location.toString());
            }
            return this;
        }

        @Override
        public ItemDepartment mutate(UnaryOperator<IModel> model) {
            this.mutate = model;
            return this;
        }

        @Override
        public ItemDepartment bake() {
            if (!this.currentlyBaking) {
                throw new RuntimeException("Tried baking with no ingredients!");
            }
            this.product = bakeModel(this.template.model, this.mutate, this.sprites, DefaultVertexFormats.ITEM);
            // this.product = bake(Bakery.FACE_BAKERY, this.template.baseModel, this.sprites, this.tints);
            this.template = null;
            this.sprites.clear();
            this.currentlyBaking = false;
            return this;
        }

        @Override
        public IBakedModel take() {
            if (this.currentlyBaking) {
                throw new RuntimeException("The bake isn't done yet!");
            }
            IBakedModel take = this.product;
            this.product = null;
            return take;
        }

    }

    public static class ModelType {

        public static final ModelType NORMAL_BLOCK = new ModelType(Zairyou.ID, "block/normal");
        public static final ModelType SINGLE_OVERLAY_BLOCK = new ModelType(Zairyou.ID, "block/single_overlay");
        public static final ModelType NORMAL_ITEM = new ModelType("minecraft", "item/generated");
        public static final ModelType HANDHELD_ITEM = new ModelType("minecraft", "item/handheld");

        private final IModel model;

        public ModelType(String locationDomain, String locationPath) {
            this.model = RenderUtils.load(locationDomain, locationPath);
        }

        public ModelType(IModel model) {
            this.model = model;
        }

        public IModel getModel() {
            return model;
        }

    }

    /*
    public enum ModelType {

        NORMAL_BLOCK(true, Zairyou.ID, "block/normal", "block/block"),
        NORMAL_TINTED(true, Zairyou.ID, "block/normal_tinted", NORMAL_BLOCK.baseModel.parent),
        SINGLE_OVERLAY(true, Zairyou.ID, "block/single_overlay", NORMAL_BLOCK.baseModel.parent),
        NORMAL_ITEM(false, "minecraft", "item/generated", "zairyou:models/item/simple.json"),
        TOOL(false, "minecraft", "item/handheld", NORMAL_ITEM.baseModel, NORMAL_ITEM.baseModel.parent);

        final boolean forBlock;
        final ModelBlock baseModel;

        @SuppressWarnings("ConstantConditions")
        ModelType(boolean forBlock, String domain, String path) {
            this.forBlock = forBlock;
            IResource resource = null;
            try {
                resource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(domain, "models/" + path + ".json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.baseModel = ModelBlock.deserialize(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
        }

        ModelType(boolean forBlock, String domain, String path, String... parents) {
            this(forBlock, domain, path);
            try {
                ModelBlock parent = this.baseModel;
                for (String s : parents) {
                    IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(s));
                    parent.parent = ModelBlock.deserialize(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                    parent = parent.parent;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ModelType(boolean forBlock, String domain, String path, Object... parents) {
            this(forBlock, domain, path);
            try {
                ModelBlock parent = this.baseModel;
                for (Object parentObj : parents) {
                    if (parentObj instanceof String) {
                        IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(parentObj.toString()));
                        parent.parent = ModelBlock.deserialize(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                    } else if (parentObj instanceof ModelBlock) {
                        parent.parent = (ModelBlock) parentObj;
                    } else {
                        throw new IllegalArgumentException("Parent Object has to either be a String or a ModelBlock");
                    }
                    parent = parent.parent;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
     */

}
