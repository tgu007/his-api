package lukelin.his.dto.signin.response;

import java.util.List;

public class TreeWardRoomNodeDto {
    private String title;

    private String key;

    private List<TreePatientNodeDto> children;

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

    public List<TreePatientNodeDto> getChildren() {
        return children;
    }

    public void setChildren(List<TreePatientNodeDto> children) {
        this.children = children;
    }

    public boolean isChecked() {
        return true;
    }
}
