package io.capawesome.capacitorjs.plugins.firebase.analytics;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import com.getcapacitor.Bridge;
import io.capawesome.capacitorjs.plugins.firebase.analytics.classes.options.LogEventOptions;
import io.capawesome.capacitorjs.plugins.firebase.analytics.classes.options.SetConsentOptions;
import io.capawesome.capacitorjs.plugins.firebase.analytics.classes.options.SetCurrentScreenOptions;
import io.capawesome.capacitorjs.plugins.firebase.analytics.classes.options.SetEnabledOptions;
import io.capawesome.capacitorjs.plugins.firebase.analytics.classes.options.SetSessionTimeoutDurationOptions;
import io.capawesome.capacitorjs.plugins.firebase.analytics.classes.options.SetUserIdOptions;
import io.capawesome.capacitorjs.plugins.firebase.analytics.classes.options.SetUserPropertyOptions;
import io.capawesome.capacitorjs.plugins.firebase.analytics.classes.results.GetAppInstanceIdResult;
import io.capawesome.capacitorjs.plugins.firebase.analytics.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.analytics.interfaces.NonEmptyResultCallback;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FirebaseAnalytics {

    private final Context context;
    private final Bridge bridge;

    public FirebaseAnalytics(Context context, Bridge bridge) {
        this.context = context;
        this.bridge = bridge;
    }

    public void getAppInstanceId(@NonNull NonEmptyResultCallback<GetAppInstanceIdResult> callback) {
        getFirebaseAnalyticsInstance()
            .getAppInstanceId()
            .addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Exception exception = task.getException();
                    Log.w(FirebaseAnalyticsPlugin.TAG, "Get AppInstanceId failed.", exception);
                    callback.error(exception);
                    return;
                }

                GetAppInstanceIdResult result = new GetAppInstanceIdResult(task.getResult());
                callback.success(result);
            });
    }

    public void logEvent(@NonNull LogEventOptions options, @NonNull EmptyResultCallback callback) {
        Bundle bundle = FirebaseAnalyticsHelper.createBundleFromJson(options.getParams());
        getFirebaseAnalyticsInstance().logEvent(options.getName(), bundle);
        callback.success();
    }

    public void resetAnalyticsData(@NonNull EmptyResultCallback callback) {
        getFirebaseAnalyticsInstance().resetAnalyticsData();
        callback.success();
    }

    public void setConsent(@NonNull SetConsentOptions options, @NonNull EmptyResultCallback callback) {
        Map<
            com.google.firebase.analytics.FirebaseAnalytics.ConsentType,
            com.google.firebase.analytics.FirebaseAnalytics.ConsentStatus
        > map = new HashMap<>();
        map.put(options.getType(), options.getStatus());
        getFirebaseAnalyticsInstance().setConsent(map);
        callback.success();
    }

    public void setCurrentScreen(@NonNull SetCurrentScreenOptions options, @NonNull EmptyResultCallback callback) {
        Bundle bundle = new Bundle();
        bundle.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_NAME, options.getScreenName());
        bundle.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_CLASS, options.getScreenClassOverride());

        bridge.getActivity().runOnUiThread(
            new Runnable() {
                @Override
                public void run() {
                    getFirebaseAnalyticsInstance().logEvent(com.google.firebase.analytics.FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
                }
            }
        );
        callback.success();
    }

    public void setEnabled(@NonNull SetEnabledOptions options, @NonNull EmptyResultCallback callback) {
        getFirebaseAnalyticsInstance().setAnalyticsCollectionEnabled(options.getEnabled());
        callback.success();
    }

    public void setSessionTimeoutDuration(@NonNull SetSessionTimeoutDurationOptions options, @NonNull EmptyResultCallback callback) {
        int duration = options.getDuration();
        // The Android SDK accepts a negative duration. The iOS SDK ignores it, so do the same here.
        if (duration >= 0) {
            getFirebaseAnalyticsInstance().setSessionTimeoutDuration(TimeUnit.SECONDS.toMillis(duration));
        }
        callback.success();
    }

    public void setUserId(@NonNull SetUserIdOptions options, @NonNull EmptyResultCallback callback) {
        getFirebaseAnalyticsInstance().setUserId(options.getUserId());
        callback.success();
    }

    public void setUserProperty(@NonNull SetUserPropertyOptions options, @NonNull EmptyResultCallback callback) {
        getFirebaseAnalyticsInstance().setUserProperty(options.getKey(), options.getValue());
        callback.success();
    }

    private com.google.firebase.analytics.FirebaseAnalytics getFirebaseAnalyticsInstance() {
        return com.google.firebase.analytics.FirebaseAnalytics.getInstance(context);
    }
}
