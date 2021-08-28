package lukelin.his.dto.signin.response;

public class TreePatientNodeDto {
    private String title;

    private String key;

    private boolean isLeaf = false;

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isLeaf() {
        return true;
    }

    public boolean isChecked() {
        return true;
    }

}
