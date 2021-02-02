**Prerequisite**
- Register developer account, visit: https://developers.zalo.me.
- After that create new app, activate. Remember `ZaloAppId`.

**Demo**
- Inside example folder.

**For IOS**
- In Zalo developer page -> products -> login -> ios -> add your app's bundleId.
- Open your `ios/App/App/info.plist`. Add `ZaloAppId`:
```
<dict>
    ...
	<key>ZaloAppId</key>
	<string>xxxxxxxxxxxxxx</string>
</dict>
```
For more information visit https://developers.zalo.me/docs/sdk/ios-sdk/tich-hop/xcode-post-460

