import Foundation
import Capacitor
import FirebaseCore
import FirebaseAuth

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
// swiftlint:disable type_body_length
@objc(FirebaseAuthenticationPlugin)
public class FirebaseAuthenticationPlugin: CAPPlugin, CAPBridgedPlugin {
    public let errorDeviceUnsupported = "Device is not supported. At least iOS 13 is required."
    public let errorNoUserSignedIn = "No user is signed in."
    public let errorPhoneNumberMissing = "phoneNumber must be provided."
    public let errorProviderIdMissing = "providerId must be provided."
    public let errorVerificationCodeMissing = "verificationCode must be provided."
    public let errorVerificationIdMissing = "verificationId must be provided."
    public let identifier = "FirebaseAuthenticationPlugin"
    public let jsName = "FirebaseAuthentication"
    public let phoneCodeSentEvent = "phoneCodeSent"
    public let phoneVerificationFailedEvent = "phoneVerificationFailed"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "applyActionCode", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "confirmPasswordReset", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "confirmVerificationCode", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "createUserWithEmailAndPassword", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "deleteUser", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "fetchSignInMethodsForEmail", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCurrentUser", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getIdToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getIdTokenResult", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getPendingAuthResult", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getRedirectResult", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getTenantId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isSignInWithEmailLink", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "linkWithApple", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "linkWithEmailAndPassword", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "linkWithEmailLink", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "linkWithFacebook", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "linkWithGameCenter", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "linkWithGithub", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "linkWithGoogle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "linkWithMicrosoft", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "linkWithOpenIdConnect", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "linkWithPhoneNumber", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "linkWithPlayGames", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "linkWithTwitter", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "linkWithYahoo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "reload", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "revokeAccessToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "sendEmailVerification", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "sendPasswordResetEmail", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "sendSignInLinkToEmail", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setLanguageCode", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setPersistence", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setTenantId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInAnonymously", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithApple", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithCustomToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithEmailAndPassword", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithEmailLink", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithFacebook", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithGameCenter", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithGithub", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithGoogle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithMicrosoft", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithPlayGames", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithTwitter", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithYahoo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithOpenIdConnect", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signInWithPhoneNumber", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signOut", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "unlink", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "updateEmail", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "updatePassword", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "updateProfile", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "useAppLanguage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "useEmulator", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "verifyBeforeUpdateEmail", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestAppTrackingTransparencyPermission", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "checkAppTrackingTransparencyPermission", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "FirebaseAuthentication"
    static let eventAuthStateChange = "authStateChange"
    static let eventIdTokenChange = "idTokenChange"
    private var config = FirebaseAuthenticationConfig()
    private var implementation: FirebaseAuthentication?

    override public func load() {
        self.config = firebaseAuthenticationConfig()
        self.implementation = FirebaseAuthentication(plugin: self, config: config)
    }

    @objc public func notifyAuthStateChangeListeners(_ event: AuthStateChangeEvent) {
        self.notifyListeners(Self.eventAuthStateChange, data: event.toJSObject() as? [String: Any], retainUntilConsumed: true)
    }

    @objc public func notifyIdTokenChangeListeners(_ event: IdTokenChangeEvent) {
        self.notifyListeners(Self.eventIdTokenChange, data: event.toJSObject() as? [String: Any], retainUntilConsumed: true)
    }

    @objc func applyActionCode(_ call: CAPPluginCall) {
        do {
            let options = try ApplyActionCodeOptions(call)

            implementation?.applyActionCode(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func checkAppTrackingTransparencyPermission(_ call: CAPPluginCall) {
        implementation?.checkAppTrackingTransparencyPermission(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func confirmPasswordReset(_ call: CAPPluginCall) {
        do {
            let options = try ConfirmPasswordResetOptions(call)

            implementation?.confirmPasswordReset(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func confirmVerificationCode(_ call: CAPPluginCall) {
        guard let verificationId = call.getString("verificationId") else {
            call.reject(errorVerificationIdMissing)
            return
        }
        guard let verificationCode = call.getString("verificationCode") else {
            call.reject(errorVerificationCodeMissing)
            return
        }
        let options = ConfirmVerificationCodeOptions(verificationId: verificationId, verificationCode: verificationCode)

        implementation?.confirmVerificationCode(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                let code = FirebaseAuthenticationHelper.createErrorCode(error: error)
                call.reject(error.localizedDescription, code)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func createUserWithEmailAndPassword(_ call: CAPPluginCall) {
        do {
            let options = try CreateUserWithEmailAndPasswordOptions(call, config: config)

            implementation?.createUserWithEmailAndPassword(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func deleteUser(_ call: CAPPluginCall) {
        implementation?.deleteUser(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func fetchSignInMethodsForEmail(_ call: CAPPluginCall) {
        do {
            let options = try FetchSignInMethodsForEmailOptions(call)

            implementation?.fetchSignInMethodsForEmail(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getCurrentUser(_ call: CAPPluginCall) {
        implementation?.getCurrentUser(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getIdToken(_ call: CAPPluginCall) {
        let options = GetIdTokenOptions(call)

        implementation?.getIdToken(options, completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getIdTokenResult(_ call: CAPPluginCall) {
        let options = GetIdTokenResultOptions(call)

        implementation?.getIdTokenResult(options, completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getPendingAuthResult(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func getRedirectResult(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func getTenantId(_ call: CAPPluginCall) {
        implementation?.getTenantId(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func handlePhoneCodeSent(_ verificationId: String) {
        var result = JSObject()
        result["verificationId"] = verificationId
        notifyListeners(phoneCodeSentEvent, data: result, retainUntilConsumed: true)
    }

    @objc func handlePhoneVerificationFailed(_ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        var result = JSObject()
        result["message"] = error.localizedDescription
        result["code"] = FirebaseAuthenticationHelper.createErrorCode(error: error)
        notifyListeners(phoneVerificationFailedEvent, data: result, retainUntilConsumed: true)
    }

    @objc func isSignInWithEmailLink(_ call: CAPPluginCall) {
        do {
            let options = try IsSignInWithEmailLinkOptions(call)

            implementation?.isSignInWithEmailLink(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func linkWithApple(_ call: CAPPluginCall) {
        implementation?.linkWithApple(call)
    }

    @objc func linkWithEmailAndPassword(_ call: CAPPluginCall) {
        let options = LinkWithEmailAndPasswordOptions(call, config: config)

        implementation?.linkWithEmailAndPassword(options, completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func linkWithEmailLink(_ call: CAPPluginCall) {
        let options = LinkWithEmailLinkOptions(call)

        implementation?.linkWithEmailLink(options, completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func linkWithFacebook(_ call: CAPPluginCall) {
        implementation?.linkWithFacebook(call)
    }

    @objc func linkWithGameCenter(_ call: CAPPluginCall) {
        implementation?.linkWithGameCenter(call)
    }

    @objc func linkWithGithub(_ call: CAPPluginCall) {
        implementation?.linkWithGithub(call)
    }

    @objc func linkWithGoogle(_ call: CAPPluginCall) {
        implementation?.linkWithGoogle(call)
    }

    @objc func linkWithMicrosoft(_ call: CAPPluginCall) {
        implementation?.linkWithMicrosoft(call)
    }

    @objc func linkWithOpenIdConnect(_ call: CAPPluginCall) {
        guard let providerId = call.getString("providerId") else {
            call.reject(errorProviderIdMissing)
            return
        }

        implementation?.linkWithOpenIdConnect(call, providerId: providerId)
    }

    @objc func linkWithPhoneNumber(_ call: CAPPluginCall) {
        guard let phoneNumber = call.getString("phoneNumber") else {
            call.reject(errorPhoneNumberMissing)
            return
        }
        let options = LinkWithPhoneNumberOptions(phoneNumber: phoneNumber)

        do {
            try implementation?.linkWithPhoneNumber(options)
            call.resolve()
        } catch {
            CAPLog.print("[", self.tag, "] ", error)
            call.reject(error.localizedDescription)
        }
    }

    @objc func linkWithPlayGames(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func linkWithTwitter(_ call: CAPPluginCall) {
        implementation?.linkWithTwitter(call)
    }

    @objc func linkWithYahoo(_ call: CAPPluginCall) {
        implementation?.linkWithYahoo(call)
    }

    @objc func reload(_ call: CAPPluginCall) {
        implementation?.reload(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func requestAppTrackingTransparencyPermission(_ call: CAPPluginCall) {
        implementation?.requestAppTrackingTransparencyPermission(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func revokeAccessToken(_ call: CAPPluginCall) {
        do {
            let options = try RevokeAccessTokenOptions(call)

            implementation?.revokeAccessToken(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func sendEmailVerification(_ call: CAPPluginCall) {
        let options = SendEmailVerificationOptions(call)

        implementation?.sendEmailVerification(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func sendPasswordResetEmail(_ call: CAPPluginCall) {
        do {
            let options = try SendPasswordResetEmailOptions(call)

            implementation?.sendPasswordResetEmail(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func sendSignInLinkToEmail(_ call: CAPPluginCall) {
        do {
            let options = try SendSignInLinkToEmailOptions(call)

            implementation?.sendSignInLinkToEmail(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setLanguageCode(_ call: CAPPluginCall) {
        let options = SetLanguageCodeOptions(call)

        implementation?.setLanguageCode(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func setPersistence(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func setTenantId(_ call: CAPPluginCall) {
        do {
            let options = try SetTenantIdOptions(call)

            implementation?.setTenantId(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func signInAnonymously(_ call: CAPPluginCall) {
        implementation?.signInAnonymously(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func signInWithApple(_ call: CAPPluginCall) {
        implementation?.signInWithApple(call)
    }

    @objc func signInWithCustomToken(_ call: CAPPluginCall) {
        let options = SignInWithCustomTokenOptions(call, config: config)

        implementation?.signInWithCustomToken(options, completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func signInWithEmailAndPassword(_ call: CAPPluginCall) {
        let options = SignInWithEmailAndPasswordOptions(call)

        implementation?.signInWithEmailAndPassword(options, completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func signInWithEmailLink(_ call: CAPPluginCall) {
        let options = SignInWithEmailLinkOptions(call)

        implementation?.signInWithEmailLink(options, completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func signInWithFacebook(_ call: CAPPluginCall) {
        implementation?.signInWithFacebook(call)
    }

    @objc func signInWithGameCenter(_ call: CAPPluginCall) {
        implementation?.signInWithGameCenter(call)
    }

    @objc func signInWithGithub(_ call: CAPPluginCall) {
        implementation?.signInWithGithub(call)
    }

    @objc func signInWithGoogle(_ call: CAPPluginCall) {
        implementation?.signInWithGoogle(call)
    }

    @objc func signInWithMicrosoft(_ call: CAPPluginCall) {
        implementation?.signInWithMicrosoft(call)
    }

    @objc func signInWithOpenIdConnect(_ call: CAPPluginCall) {
        guard let providerId = call.getString("providerId") else {
            call.reject(errorProviderIdMissing)
            return
        }

        implementation?.signInWithOpenIdConnect(call, providerId: providerId)
    }

    @objc func signInWithPhoneNumber(_ call: CAPPluginCall) {
        let skipNativeAuth = call.getBool("skipNativeAuth", firebaseAuthenticationConfig().skipNativeAuth)
        guard let phoneNumber = call.getString("phoneNumber") else {
            call.reject(errorPhoneNumberMissing)
            return
        }
        let options = SignInWithPhoneNumberOptions(skipNativeAuth: skipNativeAuth, phoneNumber: phoneNumber)

        do {
            try implementation?.signInWithPhoneNumber(options)
            call.resolve()
        } catch {
            CAPLog.print("[", self.tag, "] ", error)
            call.reject(error.localizedDescription)
        }
    }

    @objc func signInWithPlayGames(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func signInWithTwitter(_ call: CAPPluginCall) {
        implementation?.signInWithTwitter(call)
    }

    @objc func signInWithYahoo(_ call: CAPPluginCall) {
        implementation?.signInWithYahoo(call)
    }

    @objc func signOut(_ call: CAPPluginCall) {
        implementation?.signOut(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func unlink(_ call: CAPPluginCall) {
        do {
            let options = try UnlinkOptions(call)

            implementation?.unlink(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func updateEmail(_ call: CAPPluginCall) {
        do {
            let options = try UpdateEmailOptions(call)

            implementation?.updateEmail(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func updatePassword(_ call: CAPPluginCall) {
        do {
            let options = try UpdatePasswordOptions(call)

            implementation?.updatePassword(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func updateProfile(_ call: CAPPluginCall) {
        let options = UpdateProfileOptions(call)

        implementation?.updateProfile(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func useAppLanguage(_ call: CAPPluginCall) {
        implementation?.useAppLanguage(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func useEmulator(_ call: CAPPluginCall) {
        do {
            let options = try UseEmulatorOptions(call)

            implementation?.useEmulator(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func verifyBeforeUpdateEmail(_ call: CAPPluginCall) {
        do {
            let options = try VerifyBeforeUpdateEmailOptions(call)

            implementation?.verifyBeforeUpdateEmail(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    private func firebaseAuthenticationConfig() -> FirebaseAuthenticationConfig {
        var config = FirebaseAuthenticationConfig()

        config.skipNativeAuth = getConfig().getBoolean("skipNativeAuth", config.skipNativeAuth)
        if let providers = getConfig().getArray("providers") as? [String] {
            config.providers = providers
        }

        return config
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        if let error = error as? CustomError {
            call.reject(error.localizedDescription, error.code)
        } else {
            call.reject(error.localizedDescription, FirebaseAuthenticationHelper.createErrorCode(error: error))
        }
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not implemented on this platform.")
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
