package zone.rong.zairyou.api.material;

public abstract class AbstractMaterialBuilderAppender<T extends AbstractMaterialBuilderAppender<T, ?>, V> {

    protected MaterialBuilder existingBuilder;

    public AbstractMaterialBuilderAppender(MaterialBuilder existingBuilder) {
        this.existingBuilder = existingBuilder;
    }

    public MaterialBuilder back() {
        return existingBuilder;
    }

    public abstract Class<V> getViewerClass();

    public abstract V building();

    public abstract static class Viewer<T> {

        protected T appender; // Don't expose

        public Viewer(T appender) {
            this.appender = appender;
        }

    }

}
