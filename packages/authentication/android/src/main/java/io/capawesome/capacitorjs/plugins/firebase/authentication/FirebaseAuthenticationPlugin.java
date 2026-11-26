package io.capawesome.capacitorjs.plugins.firebase.authentication;

import android.content.Intent;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.ConfirmVerificationCodeOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.CustomException;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.LinkWithPhoneNumberOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.PhoneVerificationCompletedEvent;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.SignInWithPhoneNumberOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.events.AuthStateChangeEvent;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.events.IdTokenChangeEvent;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.ApplyActionCodeOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.ConfirmPasswordResetOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.CreateUserWithEmailAndPasswordOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.DeleteUserOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.FetchSignInMethodsForEmailOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.GetCurrentUserOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.GetIdTokenOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.GetIdTokenResultOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.GetTenantIdOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.IsSignInWithEmailLinkOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.LinkWithEmailAndPasswordOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.LinkWithEmailLinkOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.ReloadOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.RevokeAccessTokenOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SendEmailVerificationOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SendPasswordResetEmailOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SendSignInLinkToEmailOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SetLanguageCodeOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SetTenantIdOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SignInAnonymouslyOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SignInWithCustomTokenOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SignInWithEmailAndPasswordOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SignInWithEmailLinkOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.SignOutOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.UnlinkOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.UpdateEmailOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.UpdatePasswordOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.UpdateProfileOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.UseAppLanguageOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.UseEmulatorOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.options.VerifyBeforeUpdateEmailOptions;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.FetchSignInMethodsForEmailResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.GetCurrentUserResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.GetIdTokenResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.GetIdTokenResultResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.GetTenantIdResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.IsSignInWithEmailLinkResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.classes.results.UnlinkResult;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.EmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.firebase.authentication.interfaces.Result;

@CapacitorPlugin(name = "FirebaseAuthentication")
public class FirebaseAuthenticationPlugin extends Plugin {

    public static final String ERROR_CODE_PREFIX = "auth";
    public static final String ERROR_NO_BROWSER_AVAILABLE = "No browser is available to complete the sign-in flow.";
    public static final String ERROR_NO_USER_SIGNED_IN = "No user is signed in.";
    public static final String ERROR_PHONE_NUMBER_MISSING = "phoneNumber must be provided.";
    public static final String ERROR_PHONE_RESEND_TOKEN_MISSING =
        "signInWithPhoneNumber must be called once before using the resendCode option.";
    public static final String ERROR_PROVIDER_ID_MISSING = "providerId must be provided.";
    public static final String ERROR_VERIFICATION_CODE_MISSING = "verificationCode must be provided.";
    public static final String ERROR_VERIFICATION_ID_MISSING = "verificationId must be provided.";
    public static final String EVENT_AUTH_STATE_CHANGE = "authStateChange";
    public static final String EVENT_ID_TOKEN_CHANGE = "idTokenChange";
    public static final String PHONE_CODE_SENT_EVENT = "phoneCodeSent";
    public static final String PHONE_VERIFICATION_COMPLETED_EVENT = "phoneVerificationCompleted";
    public static final String PHONE_VERIFICATION_FAILED_EVENT = "phoneVerificationFailed";
    public static final String TAG = "FirebaseAuthentication";

    private FirebaseAuthenticationConfig config;
    private FirebaseAuthentication implementation;

    @PluginMethod
    public void applyActionCode(PluginCall call) {
        try {
            ApplyActionCodeOptions options = new ApplyActionCodeOptions(call);
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

            implementation.applyActionCode(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void checkAppTrackingTransparencyPermission(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void confirmPasswordReset(PluginCall call) {
        try {
            ConfirmPasswordResetOptions options = new ConfirmPasswordResetOptions(call);
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

            implementation.confirmPasswordReset(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void confirmVerificationCode(PluginCall call) {
        try {
            String verificationId = call.getString("verificationId");
            if (verificationId == null) {
                call.reject(ERROR_VERIFICATION_ID_MISSING);
                return;
            }
            String verificationCode = call.getString("verificationCode");
            if (verificationCode == null) {
                call.reject(ERROR_VERIFICATION_CODE_MISSING);
                return;
            }
            ConfirmVerificationCodeOptions options = new ConfirmVerificationCodeOptions(verificationId, verificationCode);
            NonEmptyResultCallback callback = new NonEmptyResultCallback() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    String code = FirebaseAuthenticationHelper.createErrorCode(exception);
                    call.reject(exception.getMessage(), code);
                }
            };

            implementation.confirmVerificationCode(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void createUserWithEmailAndPassword(PluginCall call) {
        try {
            CreateUserWithEmailAndPasswordOptions options = new CreateUserWithEmailAndPasswordOptions(call, config);
            NonEmptyResultCallback<SignInResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull SignInResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.createUserWithEmailAndPassword(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void deleteUser(PluginCall call) {
        try {
            DeleteUserOptions options = new DeleteUserOptions(call);
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

            implementation.deleteUser(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void fetchSignInMethodsForEmail(PluginCall call) {
        try {
            FetchSignInMethodsForEmailOptions options = new FetchSignInMethodsForEmailOptions(call);
            NonEmptyResultCallback<FetchSignInMethodsForEmailResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull FetchSignInMethodsForEmailResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.fetchSignInMethodsForEmail(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getCurrentUser(PluginCall call) {
        try {
            GetCurrentUserOptions options = new GetCurrentUserOptions(call);
            NonEmptyResultCallback<GetCurrentUserResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetCurrentUserResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getCurrentUser(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getIdToken(PluginCall call) {
        try {
            GetIdTokenOptions options = new GetIdTokenOptions(call);
            NonEmptyResultCallback<GetIdTokenResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetIdTokenResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getIdToken(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getIdTokenResult(PluginCall call) {
        try {
            GetIdTokenResultOptions options = new GetIdTokenResultOptions(call);
            NonEmptyResultCallback<GetIdTokenResultResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetIdTokenResultResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getIdTokenResult(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getPendingAuthResult(PluginCall call) {
        try {
            implementation.getPendingAuthResult(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void getRedirectResult(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void getTenantId(PluginCall call) {
        try {
            GetTenantIdOptions options = new GetTenantIdOptions(call);
            NonEmptyResultCallback<GetTenantIdResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetTenantIdResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getTenantId(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void handlePhoneCodeSent(String verificationId) {
        JSObject result = new JSObject();
        result.put("verificationId", verificationId);
        notifyListeners(PHONE_CODE_SENT_EVENT, result, true);
    }

    public void handlePhoneVerificationCompleted(@NonNull final PhoneVerificationCompletedEvent event) {
        notifyListeners(PHONE_VERIFICATION_COMPLETED_EVENT, event.toJSObject(), true);
    }

    public void handlePhoneVerificationFailed(Exception exception) {
        Logger.error(TAG, exception.getMessage(), exception);
        JSObject result = new JSObject();
        result.put("message", exception.getMessage());
        result.put("code", FirebaseAuthenticationHelper.createErrorCode(exception));
        notifyListeners(PHONE_VERIFICATION_FAILED_EVENT, result, true);
    }

    @PluginMethod
    public void isSignInWithEmailLink(PluginCall call) {
        try {
            IsSignInWithEmailLinkOptions options = new IsSignInWithEmailLinkOptions(call);
            NonEmptyResultCallback<IsSignInWithEmailLinkResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsSignInWithEmailLinkResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.isSignInWithEmailLink(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void linkWithApple(PluginCall call) {
        try {
            implementation.linkWithApple(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void linkWithEmailAndPassword(PluginCall call) {
        try {
            LinkWithEmailAndPasswordOptions options = new LinkWithEmailAndPasswordOptions(call);
            NonEmptyResultCallback<SignInResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull SignInResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.linkWithEmailAndPassword(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void linkWithEmailLink(PluginCall call) {
        try {
            LinkWithEmailLinkOptions options = new LinkWithEmailLinkOptions(call);
            NonEmptyResultCallback<SignInResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull SignInResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.linkWithEmailLink(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void linkWithFacebook(PluginCall call) {
        try {
            implementation.linkWithFacebook(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void linkWithGithub(PluginCall call) {
        try {
            implementation.linkWithGithub(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void linkWithGoogle(PluginCall call) {
        try {
            implementation.linkWithGoogle(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void linkWithMicrosoft(PluginCall call) {
        try {
            implementation.linkWithMicrosoft(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void linkWithOpenIdConnect(PluginCall call) {
        try {
            String providerId = call.getString("providerId");
            if (providerId == null) {
                call.reject(ERROR_PROVIDER_ID_MISSING);
                return;
            }

            implementation.linkWithOpenIdConnect(call, providerId);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void linkWithPhoneNumber(PluginCall call) {
        try {
            String phoneNumber = call.getString("phoneNumber");
            if (phoneNumber == null) {
                call.reject(ERROR_PHONE_NUMBER_MISSING);
                return;
            }
            boolean resendCode = call.getBoolean("resendCode", false);
            Long timeout = call.getInt("timeout", 60).longValue();
            LinkWithPhoneNumberOptions options = new LinkWithPhoneNumberOptions(phoneNumber, resendCode, timeout);

            implementation.linkWithPhoneNumber(options);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void linkWithPlayGames(PluginCall call) {
        try {
            implementation.linkWithPlayGames(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void linkWithTwitter(PluginCall call) {
        try {
            implementation.linkWithTwitter(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void linkWithYahoo(PluginCall call) {
        try {
            implementation.linkWithYahoo(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    public void load() {
        config = getFirebaseAuthenticationConfig();
        implementation = new FirebaseAuthentication(this, config);
    }

    public void notifyAuthStateChangeListeners(@NonNull AuthStateChangeEvent event) {
        notifyListeners(EVENT_AUTH_STATE_CHANGE, event.toJSObject(), true);
    }

    public void notifyIdTokenChangeListeners(@NonNull IdTokenChangeEvent event) {
        notifyListeners(EVENT_ID_TOKEN_CHANGE, event.toJSObject(), true);
    }

    @PluginMethod
    public void reload(PluginCall call) {
        try {
            ReloadOptions options = new ReloadOptions(call);
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

            implementation.reload(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void requestAppTrackingTransparencyPermission(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void revokeAccessToken(PluginCall call) {
        try {
            RevokeAccessTokenOptions options = new RevokeAccessTokenOptions(call);
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

            implementation.revokeAccessToken(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void sendEmailVerification(PluginCall call) {
        try {
            SendEmailVerificationOptions options = new SendEmailVerificationOptions(call);
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

            implementation.sendEmailVerification(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void sendPasswordResetEmail(PluginCall call) {
        try {
            SendPasswordResetEmailOptions options = new SendPasswordResetEmailOptions(call);
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

            implementation.sendPasswordResetEmail(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void sendSignInLinkToEmail(PluginCall call) {
        try {
            SendSignInLinkToEmailOptions options = new SendSignInLinkToEmailOptions(call);
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

            implementation.sendSignInLinkToEmail(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setLanguageCode(PluginCall call) {
        try {
            SetLanguageCodeOptions options = new SetLanguageCodeOptions(call);
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

            implementation.setLanguageCode(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setPersistence(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void setTenantId(PluginCall call) {
        try {
            SetTenantIdOptions options = new SetTenantIdOptions(call);
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

            implementation.setTenantId(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void signInAnonymously(PluginCall call) {
        try {
            SignInAnonymouslyOptions options = new SignInAnonymouslyOptions(call, config);
            NonEmptyResultCallback<SignInResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull SignInResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.signInAnonymously(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void signInWithApple(PluginCall call) {
        try {
            implementation.signInWithApple(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void signInWithCustomToken(PluginCall call) {
        try {
            SignInWithCustomTokenOptions options = new SignInWithCustomTokenOptions(call, config);
            NonEmptyResultCallback<SignInResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull SignInResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.signInWithCustomToken(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void signInWithEmailAndPassword(PluginCall call) {
        try {
            SignInWithEmailAndPasswordOptions options = new SignInWithEmailAndPasswordOptions(call, config);
            NonEmptyResultCallback<SignInResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull SignInResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.signInWithEmailAndPassword(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void signInWithEmailLink(PluginCall call) {
        try {
            SignInWithEmailLinkOptions options = new SignInWithEmailLinkOptions(call, config);
            NonEmptyResultCallback<SignInResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull SignInResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.signInWithEmailLink(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void signInWithFacebook(PluginCall call) {
        try {
            implementation.signInWithFacebook(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void signInWithGameCenter(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void signInWithGithub(PluginCall call) {
        try {
            implementation.signInWithGithub(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void signInWithGoogle(PluginCall call) {
        try {
            implementation.signInWithGoogle(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void signInWithMicrosoft(PluginCall call) {
        try {
            implementation.signInWithMicrosoft(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void signInWithOpenIdConnect(PluginCall call) {
        try {
            String providerId = call.getString("providerId");
            if (providerId == null) {
                call.reject(ERROR_PROVIDER_ID_MISSING);
                return;
            }

            implementation.signInWithOpenIdConnect(call, providerId);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void signInWithPhoneNumber(PluginCall call) {
        try {
            boolean skipNativeAuth = call.getBoolean("skipNativeAuth", this.config.getSkipNativeAuth());
            String phoneNumber = call.getString("phoneNumber");
            if (phoneNumber == null) {
                call.reject(ERROR_PHONE_NUMBER_MISSING);
                return;
            }
            boolean resendCode = call.getBoolean("resendCode", false);
            Long timeout = call.getInt("timeout", 60).longValue();
            SignInWithPhoneNumberOptions options = new SignInWithPhoneNumberOptions(skipNativeAuth, phoneNumber, resendCode, timeout);

            implementation.signInWithPhoneNumber(options);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void signInWithPlayGames(PluginCall call) {
        try {
            implementation.signInWithPlayGames(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void signInWithTwitter(PluginCall call) {
        try {
            implementation.signInWithTwitter(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void signInWithYahoo(PluginCall call) {
        try {
            implementation.signInWithYahoo(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            String code = FirebaseAuthenticationHelper.createErrorCode(exception);
            call.reject(exception.getMessage(), code);
        }
    }

    @PluginMethod
    public void signOut(PluginCall call) {
        try {
            SignOutOptions options = new SignOutOptions(call);
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

            implementation.signOut(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    public void startActivityForResult(PluginCall call, Intent intent, String callbackName) {
        super.startActivityForResult(call, intent, callbackName);
    }

    @PluginMethod
    public void unlink(PluginCall call) {
        try {
            UnlinkOptions options = new UnlinkOptions(call);
            NonEmptyResultCallback<UnlinkResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull UnlinkResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.unlink(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void updateEmail(PluginCall call) {
        try {
            UpdateEmailOptions options = new UpdateEmailOptions(call);
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

            implementation.updateEmail(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void updatePassword(PluginCall call) {
        try {
            UpdatePasswordOptions options = new UpdatePasswordOptions(call);
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

            implementation.updatePassword(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void updateProfile(PluginCall call) {
        try {
            UpdateProfileOptions options = new UpdateProfileOptions(call);
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

            implementation.updateProfile(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void useAppLanguage(PluginCall call) {
        try {
            UseAppLanguageOptions options = new UseAppLanguageOptions(call);
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

            implementation.useAppLanguage(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void useEmulator(PluginCall call) {
        try {
            UseEmulatorOptions options = new UseEmulatorOptions(call);
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

            implementation.useEmulator(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void verifyBeforeUpdateEmail(PluginCall call) {
        try {
            VerifyBeforeUpdateEmailOptions options = new VerifyBeforeUpdateEmailOptions(call);
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

            implementation.verifyBeforeUpdateEmail(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    protected void handleOnDestroy() {
        if (implementation != null) {
            implementation.destroy();
        }
        super.handleOnDestroy();
    }

    private FirebaseAuthenticationConfig getFirebaseAuthenticationConfig() {
        FirebaseAuthenticationConfig config = new FirebaseAuthenticationConfig();

        boolean skipNativeAuth = getConfig().getBoolean("skipNativeAuth", config.getSkipNativeAuth());
        config.setSkipNativeAuth(skipNativeAuth);
        String[] providers = getConfig().getArray("providers", config.getProviders());
        config.setProviders(providers);

        return config;
    }

    @ActivityCallback
    private void handleGoogleAuthProviderLinkActivityResult(@Nullable PluginCall call, @Nullable ActivityResult result) {
        if (call == null || result == null) {
            return;
        }
        implementation.handleGoogleAuthProviderLinkActivityResult(call, result);
    }

    @ActivityCallback
    private void handleGoogleAuthProviderSignInActivityResult(@Nullable PluginCall call, @Nullable ActivityResult result) {
        if (call == null || result == null) {
            return;
        }
        implementation.handleGoogleAuthProviderSignInActivityResult(call, result);
    }

    @ActivityCallback
    private void handlePlayGamesAuthProviderLinkActivityResult(@Nullable PluginCall call, @Nullable ActivityResult result) {
        if (call == null || result == null) {
            return;
        }
        implementation.handlePlayGamesAuthProviderLinkActivityResult(call, result);
    }

    @ActivityCallback
    private void handlePlayGamesAuthProviderSignInActivityResult(@Nullable PluginCall call, @Nullable ActivityResult result) {
        if (call == null || result == null) {
            return;
        }
        implementation.handlePlayGamesAuthProviderSignInActivityResult(call, result);
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        Logger.error(TAG, message, exception);
        String code = exception instanceof CustomException
            ? ((CustomException) exception).getCode()
            : FirebaseAuthenticationHelper.createErrorCode(exception);
        if (code == null) {
            call.reject(message);
        } else {
            call.reject(message, code);
        }
    }

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("This method is not implemented on this platform.");
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
