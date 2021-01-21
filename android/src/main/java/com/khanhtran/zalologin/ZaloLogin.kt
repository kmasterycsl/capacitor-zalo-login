package com.khanhtran.zalologin

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.text.TextUtils
import android.util.Base64
import android.widget.Toast
import com.getcapacitor.*
import com.zing.zalo.zalosdk.kotlin.core.module.ZaloSDKInitProvider
import com.zing.zalo.zalosdk.kotlin.oauth.Constant
import com.zing.zalo.zalosdk.kotlin.oauth.IAuthenticateCompleteListener
import com.zing.zalo.zalosdk.kotlin.oauth.LoginVia
import com.zing.zalo.zalosdk.kotlin.oauth.ZaloSDK
import java.security.MessageDigest

@NativePlugin(requestCodes = [Constant.ZALO_AUTHENTICATE_REQUEST_CODE])
class ZaloLogin : Plugin() {
    private lateinit var zaloSDK: ZaloSDK

    override fun load() {
        zaloSDK = ZaloSDK(activity);
    }

    @PluginMethod
    fun echo(call: PluginCall) {
        val ctx = context
        Toast.makeText(ctx, "hello my friend", Toast.LENGTH_LONG).show()
        zaloSDK.authenticate(activity, LoginVia.WEB, authenticateListener);
    }

    private val authenticateListener = object : IAuthenticateCompleteListener {
        @SuppressLint("SetTextI18n")
        override fun onAuthenticateSuccess(uid: Long, code: String, data: Map<String, Any>) {
            activity.runOnUiThread() {
                val displayName = data[Constant.user.DISPLAY_NAME]
                Toast.makeText(context, displayName.toString(), Toast.LENGTH_LONG).show()
            }
            val displayName = data[Constant.user.DISPLAY_NAME]
            Toast.makeText(context, displayName.toString(), Toast.LENGTH_LONG).show()
        }

        override fun onAuthenticateError(errorCode: Int, message: String) {
            activity.runOnUiThread() {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
            if (!TextUtils.isEmpty(message)) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        zaloSDK.onActivityResult(activity, requestCode, resultCode, data);
    }


    val applicationHashKey: Unit
        get() {
            val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val sig = Base64.encodeToString(md.digest(), Base64.DEFAULT).trim { it <= ' ' }
                if (sig.trim { it <= ' ' }.length > 0) {
                    Toast.makeText(getContext(), sig, Toast.LENGTH_LONG);
                }
            }
        }
}