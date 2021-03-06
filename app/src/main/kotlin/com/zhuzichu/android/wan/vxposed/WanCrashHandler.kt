package com.zhuzichu.android.wan.vxposed

import android.os.Looper
import com.lody.virtual.client.core.CrashHandler
import com.zhuzichu.android.shared.ext.logi
import kotlin.system.exitProcess

open class WanCrashHandler : CrashHandler {
    override fun handleUncaughtException(t: Thread, e: Throwable) {
        if (t == Looper.getMainLooper().thread) {
            exitProcess(0)
        } else {
            "ignore uncaught exception of sub thread".logi(throwable = e)
        }
    }
}