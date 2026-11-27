package io.capawesome.capacitorjs.plugins.firebase.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.firebase.app.classes.CustomException;
import io.capawesome.capacitorjs.plugins.firebase.app.classes.options.InitializeAppOptions;
import io.capawesome.capacitorjs.plugins.firebase.app.classes.results.GetAppsResult;
import io.capawesome.capacitorjs.plugins.firebase.app.classes.results.GetNameResult;
import io.capawesome.capacitorjs.plugins.firebase.app.classes.results.GetOptionsResult;
import io.capawesome.capacitorjs.plugins.firebase.app.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.app.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.app.interfaces.Result;

@CapacitorPlugin(name = "FirebaseApp")
public class FirebaseAppPlugin extends Plugin {

    public static final String TAG = "FirebaseApp";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";

    @Nullable
    private FirebaseApp implementation;

    @Override
    public void load() {
        implementation = new FirebaseApp(this);
    }

    @PluginMethod
    public void getApps(PluginCall call) {
        try {
            NonEmptyResultCallback<GetAppsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetAppsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getApps(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getName(PluginCall call) {
        try {
            NonEmptyResultCallback<GetNameResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetNameResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getName(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getOptions(PluginCall call) {
        try {
            NonEmptyResultCallback<GetOptionsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetOptionsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getOptions(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void initializeApp(PluginCall call) {
        try {
            InitializeAppOptions options = new InitializeAppOptions(call);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.initializeApp(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
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
