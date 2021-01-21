package zone.rong.zairyou.api.material;

public interface IMaterialAppender<T> {

    void onBuilding(Material material);

    Material cast();

}
