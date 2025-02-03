package io.capawesome.capacitorjs.plugins.firebase.analytics;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Bridge;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class FirebaseAnalytics {

    private final Context context;
    private final Bridge bridge;

    public FirebaseAnalytics(Context context, Bridge bridge) {
        this.context = context;
        this.bridge = bridge;
    }

    @Nullable
    public void getAppInstanceId(@NonNull final GetAppInstanceIdCallback resultCallback) {
        getFirebaseAnalyticsInstance()
            .getAppInstanceId()
            .addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Exception exception = task.getException();
                    Log.w(FirebaseAnalyticsPlugin.TAG, "Get AppInstanceId failed.", exception);
                    resultCallback.error(exception.getMessage());
                    return;
                }

                String appInstanceId = task.getResult();
                resultCallback.success(appInstanceId);
            });
    }

    public void setConsent(
        @NonNull com.google.firebase.analytics.FirebaseAnalytics.ConsentType consentType,
        @NonNull com.google.firebase.analytics.FirebaseAnalytics.ConsentStatus consentStatus
    ) {
        Map<
            com.google.firebase.analytics.FirebaseAnalytics.ConsentType,
            com.google.firebase.analytics.FirebaseAnalytics.ConsentStatus
        > map = new HashMap<>();
        map.put(consentType, consentStatus);
        getFirebaseAnalyticsInstance().setConsent(map);
    }

    public void setUserId(@Nullable String userId) {
        getFirebaseAnalyticsInstance().setUserId(userId);
    }

    public void setUserProperty(@NonNull String key, @Nullable String value) {
        getFirebaseAnalyticsInstance().setUserProperty(key, value);
    }

    public void setCurrentScreen(String screenName, String screenClass) {
        Bundle bundle = new Bundle();
        bundle.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_NAME, screenName);
        bundle.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_CLASS, screenClass);

        bridge
            .getActivity()
            .runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        getFirebaseAnalyticsInstance().logEvent(com.google.firebase.analytics.FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
                    }
                }
            );
    }

    public void logEvent(@NonNull String key, @Nullable JSONObject json) {
        Bundle bundle = FirebaseAnalyticsHelper.createBundleFromJson(json);
        getFirebaseAnalyticsInstance().logEvent(key, bundle);
    }

    public void setSessionTimeoutDuration(long duration) {
        getFirebaseAnalyticsInstance().setSessionTimeoutDuration(duration);
    }

    public void setEnabled(boolean enabled) {
        getFirebaseAnalyticsInstance().setAnalyticsCollectionEnabled(enabled);
    }

    public void resetAnalyticsData() {
        getFirebaseAnalyticsInstance().resetAnalyticsData();
    }

    private com.google.firebase.analytics.FirebaseAnalytics getFirebaseAnalyticsInstance() {
        return com.google.firebase.analytics.FirebaseAnalytics.getInstance(context);
    }
}
