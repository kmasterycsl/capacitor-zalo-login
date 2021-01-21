package com.khanhtran.zalologin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.widget.Toast;
//import android.widget.Toast;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.zing.zalo.zalosdk.kotlin.oauth.IAuthenticateCompleteListener;
import com.zing.zalo.zalosdk.kotlin.oauth.LoginVia;
import com.zing.zalo.zalosdk.kotlin.oauth.ZaloSDK;
import com.zing.zalo.zalosdk.kotlin.oauth.model.ErrorResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Map;

@NativePlugin()
public class ZaloLogin extends Plugin   {
    public void load() {
        // Called when the plugin is first constructed in the bridge
//        ZaloSDK sdk = new ZaloSDK(getActivity());
    }

    @PluginMethod()
    public void echo(final PluginCall call) throws Exception {
//        Toast.makeText(getActivity(), "PRE CALL ECHO", Toast.LENGTH_LONG);
        saveCall(call);
        final Context ctx = getContext();
        new ZaloSDK(getContext()).authenticate(getActivity(), LoginVia.WEB, new IAuthenticateCompleteListener() {
            @Override
            public void onAuthenticateSuccess(long l, @NotNull String s, @NotNull Map<String, ?> map) {
                android.widget.Toast.makeText(ctx, "hello my friend", Toast.LENGTH_LONG).show();
                PluginCall lastCall = getSavedCall();
                JSObject data = new JSObject();
                data.put("hello", "ok");
                lastCall.resolve(data);
                call.resolve(data);
            }

            @Override
            public void onAuthenticateError(int i, @NotNull String s) {
                android.widget.Toast.makeText(ctx, "hello my friend", Toast.LENGTH_LONG).show();
                PluginCall lastCall = getSavedCall();
                JSObject data = new JSObject();
                data.put("hello", "ok");
                lastCall.resolve(data);
                call.resolve(data);
            }

            @Override
            public void onAuthenticateError(int i, @Nullable String s, @NotNull ErrorResponse errorResponse) {
                android.widget.Toast.makeText(ctx, "hello my friend", Toast.LENGTH_LONG).show();
                PluginCall lastCall = getSavedCall();
                JSObject data = new JSObject();
                data.put("hello", "ok");
                lastCall.resolve(data);
                call.resolve(data);
            }
        });
    }


    public void getApplicationHashKey() throws Exception {
        PackageInfo info = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), PackageManager.GET_SIGNATURES);
        for (Signature signature : info.signatures) {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(signature.toByteArray());
            String sig = Base64.encodeToString(md.digest(), Base64.DEFAULT).trim();
            if (sig.trim().length() > 0) {
//                Toast.makeText(getContext(), sig, Toast.LENGTH_LONG);
            }
        }
    }

}