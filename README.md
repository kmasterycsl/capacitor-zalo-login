# capacitor-zalo-login
Capacitory community plugin for Zalo Login.

## Installation
WIP

## Demo
- See inside /example folder.

## Prerequisites
- Register developer account, visit: https://developers.zalo.me.
- Then create new app, activate. Remember `YOUR_ZaloAppId` value.

## For Android
- In Zalo developer page -> products -> login -> android -> add your app's packageName, hashKey.
- Update `android/src/main/res/values/strings.xml` add values
```xml
<string name="zalosdk_app_id" translatable="false">YOUR_ZaloAppId</string>
<string name="zalosdk_login_protocol_schema" translatable="false">zalo-YOUR_ZaloAppId</string>
```
- Update `example/android/app/src/main/AndroidManifest.xml` add meta-data
```xml
<application>
    ...
    <meta-data
        android:name="com.zing.zalo.zalosdk.appID"
        android:value="@string/zalosdk_app_id" />
</application>
```
- Update `android/app/src/main/java/io/ionic/starter/MainActivity.java` add `ZaloLogin` class

```java

import com.khanhtran.capacitorplugins.zalologin.ZaloLogin;

public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Initializes the Bridge
    this.init(savedInstanceState, new ArrayList<Class<? extends Plugin>>() {{
      // Additional plugins you've installed go here
      // Ex: add(TotallyAwesomePlugin.class);
      add(ZaloLogin.class);
    }});
  }
```
- For more information visit https://developers.zalo.me/docs/sdk/android-sdk-8
## For IOS
- In Zalo developer page -> products -> login -> ios -> add your app's bundleId.
- Open your `ios/App/App/info.plist`. Add `ZaloAppId`:
```plist
<dict>
	<key>ZaloAppId</key>
	<string>YOUR_ZaloAppId</string>
</dict>
```
- For more information visit https://developers.zalo.me/docs/sdk/ios-sdk/tich-hop/xcode-post-460

## For Web
WIP

## Supported methods

| Name                  | Android | iOS | Web |
| :-------------------- | :------ | :-- | :-- |
| login                 | ✅      | ✅  | WIP |
| getProfile            | ✅      | ✅  | WIP |
| logout                | ✅      | ✅  | WIP |
| getApplicationHashKey | ✅      | ❌  | WIP |

## API
### Login
```ts
import { Plugins } from '@capacitor/core';

Plugins.ZaloLogin
    .login()
    .then(zaloUser => {
        console.log(zaloUser);
    });
```
### User profile
```ts
import { Plugins } from '@capacitor/core';

Plugins.ZaloLogin
    .getProfile()
    .then(zaloUser => {
        console.log(zaloUser);
    });
```

### Logout
```ts
import { Plugins } from '@capacitor/core';

Plugins.ZaloLogin.logout()
```

### Get application hashkey
```ts
import { Plugins } from '@capacitor/core';

Plugins.ZaloLogin
    .getApplicationHashKey()
    .then(hashKey => {
        console.log(hashKey);
    });
```