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
    
    func onAuthenticateComplete(with response: ZOOauthResponseObject?, call: CAPPluginCall) {
        if response?.isSucess == true {
            zaloSDK?.getZaloUserProfile(callback: { (profileResponse) in
                self.onGetProfileComplete(with: profileResponse, call: call)
            })
        } else {
            if (response?.errorCode == -1001) {
                call.reject("User rejected.")
            } else {
                call.reject(response?.errorMessage ?? "Can not get oauth code.")
            }
        }
    }
    
    func onGetProfileComplete(with profileResponse: ZOGraphResponseObject?, call: CAPPluginCall) {
        if profileResponse?.isSucess == true {
            call.resolve([
                "id": profileResponse?.data["id"] as Any,
                "name": profileResponse?.data["name"] as Any,
                "gender": profileResponse?.data["gender"] as Any,
                "birthday": profileResponse?.data["birthday"] as Any,
                "picture": profileResponse?.data["picture"] as Any,
            ])
        } else {
            call.reject(profileResponse?.errorMessage ?? "Can not get profile from oauth code.")
        }
    }
}
