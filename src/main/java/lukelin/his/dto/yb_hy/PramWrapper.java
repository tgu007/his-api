package lukelin.his.dto.yb_hy;

public class PramWrapper {
    private String operator = "default";

    private String infoNumber;

    private String inputNodeName;

    private Object input;

    private String inputNodeNameTwo;

    private Object inputTwo;

    private String areaCode = "";

    private String downloadFileName;

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getInputNodeNameTwo() {
        return inputNodeNameTwo;
    }

    public void setInputNodeNameTwo(String inputNodeNameTwo) {
        this.inputNodeNameTwo = inputNodeNameTwo;
    }

    public Object getInputTwo() {
        return inputTwo;
    }

    public void setInputTwo(Object inputTwo) {
        this.inputTwo = inputTwo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getInfoNumber() {
        return infoNumber;
    }

    public void setInfoNumber(String infoNumber) {
        this.infoNumber = infoNumber;
    }

    public String getInputNodeName() {
        return inputNodeName;
    }

    public void setInputNodeName(String inputNodeName) {
        this.inputNodeName = inputNodeName;
    }

    public Object getInput() {
        return input;
    }

    public void setInput(Object input) {
        this.input = input;
    }
}
