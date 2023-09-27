package io.capawesome.capacitorjs.plugins.firebase.authentication;

import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.UserInfo;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class FirebaseAuthenticationHelper {

    public static class ProviderId {

        public static final String APPLE = "apple.com";
        public static final String FACEBOOK = "facebook.com";
        public static final String GITHUB = "github.com";
        public static final String GOOGLE = "google.com";
        public static final String MICROSOFT = "microsoft.com";
        public static final String PLAY_GAMES = "playgames.google.com";
        public static final String TWITTER = "twitter.com";
        public static final String YAHOO = "yahoo.com";
        public static final String PASSWORD = "password";
        public static final String PHONE = "phone";
    }

    @Nullable
    public static String createErrorCode(@Nullable Exception exception) {
        if (exception == null) {
            return null;
        } else if (exception instanceof FirebaseAuthException) {
            String errorCode = ((FirebaseAuthException) exception).getErrorCode();
            errorCode = errorCode.replaceFirst("ERROR_", "");
            return snakeToKebabCase(errorCode);
        }
        return null;
    }

    public static JSObject createSignInResult(
        @Nullable FirebaseUser user,
        @Nullable AuthCredential credential,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken,
        @Nullable AdditionalUserInfo additionalUserInfo
    ) {
        return FirebaseAuthenticationHelper.createSignInResult(user, credential, idToken, nonce, accessToken, null, additionalUserInfo);
    }

    public static JSObject createSignInResult(
        @Nullable FirebaseUser user,
        @Nullable AuthCredential credential,
        @Nullable String idToken,
        @Nullable String nonce,
        @Nullable String accessToken,
        @Nullable String serverAuthCode,
        @Nullable AdditionalUserInfo additionalUserInfo
    ) {
        JSObject userResult = FirebaseAuthenticationHelper.createUserResult(user);
        JSObject credentialResult = FirebaseAuthenticationHelper.createCredentialResult(
            credential,
            idToken,
            nonce,
            accessToken,
            serverAuthCode
        );
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
        result.put("metadata", FirebaseAuthenticationHelper.createUserMetadataResult(user.getMetadata()));
        result.put("phoneNumber", user.getPhoneNumber());
        result.put("photoUrl", user.getPhotoUrl());
        result.put("providerData", FirebaseAuthenticationHelper.createUserProviderDataResult(user.getProviderData()));
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
        @Nullable String accessToken,
        @Nullable String serverAuthCode
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
        if (serverAuthCode != null) {
            result.put("serverAuthCode", serverAuthCode);
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

    private static JSObject createUserMetadataResult(@Nullable FirebaseUserMetadata metadata) {
        JSObject result = new JSObject();
        if (metadata == null) {
            return result;
        }
        result.put("creationTime", metadata.getCreationTimestamp());
        result.put("lastSignInTime", metadata.getLastSignInTimestamp());
        return result;
    }

    private static JSArray createUserProviderDataResult(List<? extends UserInfo> providerData) {
        JSArray result = new JSArray();
        for (UserInfo userInfo : providerData) {
            JSObject userInfoResult = new JSObject();
            userInfoResult.put("displayName", (userInfo.getDisplayName() == null ? JSONObject.NULL : userInfo.getDisplayName()));
            userInfoResult.put("email", (userInfo.getEmail() == null ? JSONObject.NULL : userInfo.getEmail()));
            userInfoResult.put("phoneNumber", (userInfo.getPhoneNumber() == null ? JSONObject.NULL : userInfo.getPhoneNumber()));
            userInfoResult.put("photoUrl", (userInfo.getPhotoUrl() == null ? JSONObject.NULL : userInfo.getPhotoUrl()));
            userInfoResult.put("providerId", userInfo.getProviderId());
            userInfoResult.put("uid", userInfo.getUid());
            result.put(userInfoResult);
        }
        return result;
    }

    private static String snakeToKebabCase(String snakeCase) {
        return snakeCase.replaceAll("_+", "-").toLowerCase();
    }
}
