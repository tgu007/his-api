package lukelin.his.dto.yb;

import java.util.List;

public class MatchResp {
    private List<EntityMatchUploadResultSaveDto> matchRespList;

    public List<EntityMatchUploadResultSaveDto> getMatchRespList() {
        return matchRespList;
    }

    public void setMatchRespList(List<EntityMatchUploadResultSaveDto> matchRespList) {
        this.matchRespList = matchRespList;
    }
}
