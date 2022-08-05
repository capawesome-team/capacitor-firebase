package io.capawesome.capacitorjs.plugins.firebase.authentication;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;
import java.util.Map;

public class FirebaseAuthenticationHelper {

    public static JSObject createSignInResult(
        @Nullable FirebaseUser user,
        @Nullable AuthCredential credential,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken,
        @Nullable AdditionalUserInfo additionalUserInfo
    ) {
        JSObject userResult = FirebaseAuthenticationHelper.createUserResult(user);
        JSObject credentialResult = FirebaseAuthenticationHelper.createCredentialResult(credential, idToken, nonce, accessToken);
        JSObject additionalUserInfoResult = FirebaseAuthenticationHelper.createAdditionalUserInfoResult(additionalUserInfo);
        JSObject result = new JSObject();
        result.put("user", userResult);
        result.put("credential", credentialResult);
        result.put("additionalUserInfo", additionalUserInfoResult);
        return result;
    }

    @Nullable
    public static JSObject createUserResult(@Nullable FirebaseUser user) {
        if (user == null) {
            return null;
        }
        JSObject result = new JSObject();
        result.put("displayName", user.getDisplayName());
        result.put("email", user.getEmail());
        result.put("emailVerified", user.isEmailVerified());
        result.put("isAnonymous", user.isAnonymous());
        result.put("phoneNumber", user.getPhoneNumber());
        result.put("photoUrl", user.getPhotoUrl());
        result.put("providerId", user.getProviderId());
        result.put("tenantId", user.getTenantId());
        result.put("uid", user.getUid());
        return result;
    }

    @Nullable
    public static JSObject createCredentialResult(
        @Nullable AuthCredential credential,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken
    ) {
        if (credential == null && idToken == null && nonce == null && accessToken == null) {
            return null;
        }
        JSObject result = new JSObject();
        if (credential != null) {
            result.put("providerId", credential.getProvider());
            if (credential instanceof OAuthCredential) {
                String oAuthAccessToken = ((OAuthCredential) credential).getAccessToken();
                if (oAuthAccessToken != null) {
                    result.put("accessToken", oAuthAccessToken);
                }
                String oAuthIdToken = ((OAuthCredential) credential).getIdToken();
                if (oAuthIdToken != null) {
                    result.put("idToken", oAuthIdToken);
                }
                String oAuthSecret = ((OAuthCredential) credential).getSecret();
                if (oAuthSecret != null) {
                    result.put("secret", oAuthSecret);
                }
            }
        }
        if (idToken != null) {
            result.put("idToken", idToken);
        }
        if (nonce != null) {
            result.put("nonce", nonce);
        }
        if (accessToken != null) {
            result.put("accessToken", accessToken);
        }
        return result;
    }

    @Nullable
    public static JSObject createAdditionalUserInfoResult(@Nullable AdditionalUserInfo additionalUserInfo) {
        if (additionalUserInfo == null) {
            return null;
        }
        JSObject result = new JSObject();
        result.put("isNewUser", additionalUserInfo.isNewUser());
        if (additionalUserInfo.getProfile() != null) {
            JSObject profileResult = new JSObject();
            for (Map.Entry<String, Object> entry : additionalUserInfo.getProfile().entrySet()) {
                profileResult.put(entry.getKey(), entry.getValue());
            }
            result.put("profile", profileResult);
        }
        if (additionalUserInfo.getProviderId() != null) {
            result.put("providerId", additionalUserInfo.getProviderId());
        }
        if (additionalUserInfo.getUsername() != null) {
            result.put("username", additionalUserInfo.getUsername());
        }
        return result;
    }
}
