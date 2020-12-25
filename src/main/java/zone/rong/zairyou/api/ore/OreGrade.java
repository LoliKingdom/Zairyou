package zone.rong.zairyou.api.ore;

public enum OreGrade {

    NORMAL(""),
    POOR("poor"),
    RICH("rich");

    private final String append;

    OreGrade(String append) {
        this.append = append;
    }

    public String getAppend() {
        return append;
    }

}
