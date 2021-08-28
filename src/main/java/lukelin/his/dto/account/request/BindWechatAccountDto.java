package lukelin.his.dto.account.request;

import lukelin.sdk.account.dto.request.CredentialsDTO;

import java.util.UUID;

public class BindWechatAccountDto extends CredentialsDTO {
    private UUID oauthAccountId;

    public UUID getOauthAccountId() {
        return oauthAccountId;
    }

    public void setOauthAccountId(UUID oauthAccountId) {
        this.oauthAccountId = oauthAccountId;
    }
}
