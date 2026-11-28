package io.capawesome.capacitorjs.plugins.firebase.appcheck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.CustomException;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.events.TokenChangedEvent;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.options.GetTokenOptions;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.options.SetTokenAutoRefreshEnabledOptions;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.classes.results.GetTokenResult;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.appcheck.interfaces.Result;

@CapacitorPlugin(name = "FirebaseAppCheck")
public class FirebaseAppCheckPlugin extends Plugin {

    public static final String EVENT_TOKEN_CHANGED = "tokenChanged";
    public static final String TAG = "FirebaseAppCheck";

    private final FirebaseAppCheck implementation = new FirebaseAppCheck(this);

    @PluginMethod
    public void getToken(PluginCall call) {
        try {
            GetTokenOptions options = new GetTokenOptions(call);
            NonEmptyResultCallback<GetTokenResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetTokenResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getToken(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        try {
            InitializeOptions options = new InitializeOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.initialize(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyTokenChangedListeners(@NonNull TokenChangedEvent event) {
        notifyListeners(EVENT_TOKEN_CHANGED, event.toJSObject(), true);
    }

    @PluginMethod
    public void setTokenAutoRefreshEnabled(PluginCall call) {
        try {
            SetTokenAutoRefreshEnabledOptions options = new SetTokenAutoRefreshEnabledOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.setTokenAutoRefreshEnabled(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    protected void handleOnDestroy() {
        implementation.destroy();
        super.handleOnDestroy();
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        Logger.error(TAG, message, exception);
        String code = exception instanceof CustomException ? ((CustomException) exception).getCode() : null;
        if (code == null) {
            call.reject(message);
        } else {
            call.reject(message, code);
        }
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
