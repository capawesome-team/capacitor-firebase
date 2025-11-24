package app.capgo.capacitor.firebase.authentication.classes;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.auth.GetTokenResult;
import app.capgo.capacitor.firebase.authentication.interfaces.Result;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GetIdTokenResultResult implements Result {

    @Nullable
    private GetTokenResult tokenResult;

    private SimpleDateFormat iso8601Format;

    public GetIdTokenResultResult(@Nullable GetTokenResult tokenResult) {
        this.tokenResult = tokenResult;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        if (tokenResult != null) {
            result.put("authTime", tokenResult.getAuthTimestamp() * 1000L);
            result.put("expirationTime", tokenResult.getExpirationTimestamp() * 1000L);
            result.put("issuedAtTime", tokenResult.getIssuedAtTimestamp() * 1000L);
            result.put("signInProvider", tokenResult.getSignInProvider());
            result.put("signInSecondFactor", tokenResult.getSignInSecondFactor());

            JSObject claims = new JSObject();
            for (String key : tokenResult.getClaims().keySet()) {
                claims.put(key, tokenResult.getClaims().get(key));
            }
            result.put("claims", claims);
        }
        return result;
    }
}
