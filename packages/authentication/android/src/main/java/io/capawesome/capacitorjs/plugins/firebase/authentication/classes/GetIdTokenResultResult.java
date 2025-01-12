package io.capawesome.capacitorjs.plugins.firebase.authentication.classes;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.auth.IdTokenResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.Result;

public class GetIdTokenResultResult implements Result {

    @Nullable
    private IdTokenResult tokenResult;

    public GetIdTokenResultResult(@Nullable IdTokenResult tokenResult) {
        this.tokenResult = tokenResult;
    }

    @Nullable
    public String getTokenResult() {
        return tokenResult;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        if (tokenResult != null) {
            result.put("authTime", tokenResult.getAuthTime()); 
            result.put("expirationTime", tokenResult.getExpirationTimestamp()); 
            result.put("issuedAtTime", tokenResult.getIssuedAtTimestamp());
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
