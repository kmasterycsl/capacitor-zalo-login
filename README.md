# capacitor-zalo-login
## Prerequisite
- Register developer account, visit: https://developers.zalo.me.
- After that create new app, activate. Remember `ZaloAppId`.

## Demo
- Inside /example folder.

## For Android

## For IOS
- In Zalo developer page -> products -> login -> ios -> add your app's bundleId.
- Open your `ios/App/App/info.plist`. Add `ZaloAppId`:
```
<dict>
    ...
	<key>ZaloAppId</key>
	<string>xxxxxxxxxxxxxx</string>
</dict>
```
- For more information visit https://developers.zalo.me/docs/sdk/ios-sdk/tich-hop/xcode-post-460

## For Web
WIP

## API
### Login
```
import { Plugins } from '@capacitor/core';

Plugins.ZaloLogin
    .login()
    .then(zaloUser => {
        console.log(zaloUser);
    })
    .catch(e => {
        console.error('zaloLogin error:', e)
    });
```


### Logout
```
import { Plugins } from '@capacitor/core';

Plugins.ZaloLogin.logout()
```