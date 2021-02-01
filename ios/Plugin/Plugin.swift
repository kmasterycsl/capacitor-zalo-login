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
            call.resolve([
                "oauthCode": response?.oauthCode,
                "userId": response?.userId,
                "displayName": response?.displayName,
                "phoneNumber": response?.phoneNumber,
                "dob": response?.dob,
                "gender": response?.gender,
            ])
        } else if let response = response,
             response.errorCode != -1001 { // not cancel
        }
    }
}
