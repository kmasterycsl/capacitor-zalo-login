package com.khanhtran.capacitorplugins.zalologin

import android.content.Intent
import android.content.pm.PackageManager
import android.util.Base64
import com.getcapacitor.*
import com.zing.zalo.zalosdk.kotlin.oauth.Constant
import com.zing.zalo.zalosdk.kotlin.oauth.IAuthenticateCompleteListener
import com.zing.zalo.zalosdk.kotlin.oauth.LoginVia
import com.zing.zalo.zalosdk.kotlin.oauth.ZaloSDK
import com.zing.zalo.zalosdk.kotlin.oauth.model.ErrorResponse
import com.zing.zalo.zalosdk.kotlin.openapi.ZaloOpenApi;
import com.zing.zalo.zalosdk.kotlin.openapi.ZaloOpenApiCallback
import org.json.JSONObject
import java.lang.Exception
import java.security.MessageDigest

@NativePlugin(requestCodes = [Constant.ZALO_AUTHENTICATE_REQUEST_CODE])
class ZaloLogin : Plugin() {
    private lateinit var zaloSDK: ZaloSDK

    override fun load() {
        zaloSDK = ZaloSDK(activity);
    }

    @PluginMethod
    fun login(call: PluginCall) {
        val viaString = call.getString("loginVia", "APP_OR_WEB");
        val viaEnum: LoginVia;
        when (viaString) {
            "APP_OR_WEB" -> viaEnum = LoginVia.APP_OR_WEB;
            "APP" -> viaEnum = LoginVia.APP;
            "WEB" -> viaEnum = LoginVia.WEB;
            else -> { // Note the block
                throw Exception("loginVia is not valid");
            }
        }
        saveCall(call);
        zaloSDK.authenticate(activity, viaEnum, authenticateListener);
    }

    private val authenticateListener = object : IAuthenticateCompleteListener {
        override fun onAuthenticateSuccess(uid: Long, code: String, data: Map<String, Any>) {
            if (savedLastCall == null) {
                return;
            }
            val openApi = ZaloOpenApi(context, code);
            val fields = arrayOf("id", "birthday", "gender", "picture", "name");
            openApi.getProfile(fields, getProfileListener);
        }

        override fun onAuthenticateError(errorCode: Int, message: String) {
            if (savedLastCall == null) {
                return;
            }
            savedLastCall.reject(message);
        }

        override fun onAuthenticateError(errorCode: Int, errorMsg: String?, errorResponse: ErrorResponse) {
            if (savedLastCall == null) {
                return;
            }
            savedLastCall.reject(errorMsg);
        }
    }

    private val getProfileListener = object : ZaloOpenApiCallback {
        override fun onResult(data: JSONObject?) {
            if (savedLastCall == null) {
                return;
            }
            savedLastCall.resolve(JSObject.fromJSONObject(data));
        }
    }

    @PluginMethod
    fun getApplicationHashKey(call: PluginCall) {
        val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
        for (signature in info.signatures) {
            val md = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            val sig = Base64.encodeToString(md.digest(), Base64.DEFAULT).trim { it <= ' ' }
            if (sig.trim { it <= ' ' }.length > 0) {
                val data = JSObject();
                data.put("signature", sig);
                call.resolve(data);
            } else {
                call.reject("Signature is not available.");
            }
        }
    }

    override fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        zaloSDK.onActivityResult(activity, requestCode, resultCode, data);
    }
}