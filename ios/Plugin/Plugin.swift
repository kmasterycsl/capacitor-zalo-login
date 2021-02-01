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
        zaloSDK?.authenticateZalo(with: ZAZAloSDKAuthenTypeViaZaloAppAndWebView, parentController: self.bridge.viewController) { (response) in
            self.onAuthenticateComplete(with: response, call: call)
        }
    }
    
    func onAuthenticateComplete(with response: ZOOauthResponseObject?, call: CAPPluginCall) {
        
        if response?.isSucess == true {
            zaloSDK?.getZaloUserProfile(callback: { (response2) in
                self.onGetProfileComplete(with: response2, call: call)
            })
        } else if let response = response,
             response.errorCode != -1001 { // not cancel
        }
    }
    
    func onGetProfileComplete(with response: ZOGraphResponseObject?, call: CAPPluginCall) {
        call.resolve([
            "id": response?.data["id"],
            "name": response?.data["name"],
            "gender": response?.data["gender"],
            "birthday": response?.data["birthday"],
            "picture": response?.data["picture"],
        ])
    }
}
