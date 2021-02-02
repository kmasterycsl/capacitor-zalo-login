import Foundation
import Capacitor
import ZaloSDK

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(ZaloLogin)
public class ZaloLogin: CAPPlugin {
    private let zaloSDK =  ZaloSDK.sharedInstance()

    public override func load() {
        let zaloAppId = Bundle.main.object(forInfoDictionaryKey: "ZaloAppId") as? String

        if (zaloAppId == nil) {
            print("Missing ZaloAppId in Info.plist.")
        } else {
            zaloSDK?.initialize(withAppId: zaloAppId)
        }
    }
    
    
    @objc func login(_  call: CAPPluginCall) {
        DispatchQueue.main.async {
            self.zaloSDK?.authenticateZalo(
                with: ZAZAloSDKAuthenTypeViaZaloAppAndWebView,
                parentController: self.bridge.viewController
            ) { oauthResponse in
                return self.onAuthenticateComplete(with: oauthResponse, call: call)
            }
        }
    }
    
    @objc func logout(_  call: CAPPluginCall) {
        DispatchQueue.main.async {
            self.zaloSDK?.unauthenticate();
            call.resolve();
        }
    }
    
    func onAuthenticateComplete(with response: ZOOauthResponseObject?, call: CAPPluginCall) {
        if response?.isSucess == true {
            zaloSDK?.getZaloUserProfile(callback: { (profileResponse) in
                self.onGetProfileComplete(with: profileResponse, oauthResponse: response, call: call)
            })
        } else {
            if (response?.errorCode == -1001) {
                call.reject("User rejected.")
            } else {
                call.reject(response?.errorMessage ?? "Can not get oauth code.")
            }
        }
    }
    
    func onGetProfileComplete(with profileResponse: ZOGraphResponseObject?, oauthResponse: ZOOauthResponseObject?, call: CAPPluginCall) {
        if profileResponse?.isSucess == true {
            call.resolve([
                "id": profileResponse?.data["id"] as Any,
                "name": profileResponse?.data["name"] as Any,
                "gender": profileResponse?.data["gender"] as Any,
                "birthday": profileResponse?.data["birthday"] as Any,
                "picture": profileResponse?.data["picture"] as Any,
                "oauthCode": oauthResponse?.oauthCode as Any,
            ])
        } else {
            call.reject(profileResponse?.errorMessage ?? "Can not get profile from oauth code.")
        }
    }
}
