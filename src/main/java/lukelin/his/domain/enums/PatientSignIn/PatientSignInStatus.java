package lukelin.his.domain.enums.PatientSignIn;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PatientSignInStatus {
    pendingSignIn("待入院"),
    signedIn("已入院"),
    pendingSignOut("待出院"),
    signedOut("已出院");

    private String description;

    PatientSignInStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String toString() {
        return this.description;
    }
}
