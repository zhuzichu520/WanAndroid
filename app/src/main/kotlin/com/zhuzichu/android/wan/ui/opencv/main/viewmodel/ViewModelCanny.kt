package com.zhuzichu.android.wan.ui.opencv.main.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.uber.autodispose.autoDispose
import com.zhuzichu.android.libs.tool.toDouble
import com.zhuzichu.android.mvvm.databinding.BindingCommand
import com.zhuzichu.android.shared.base.ViewModelAnalyticsBase
import com.zhuzichu.android.shared.extension.bindToSchedulers
import com.zhuzichu.android.shared.global.AppGlobal
import com.zhuzichu.android.wan.R
import com.zhuzichu.android.wan.manager.OpencvManager
import io.reactivex.Flowable
import javax.inject.Inject

class ViewModelCanny @Inject constructor(
    private val opencvManager: OpencvManager
) : ViewModelAnalyticsBase() {

    private val src = BitmapFactory.decodeResource(AppGlobal.context.resources, R.mipmap.guidao)

    var temp: Bitmap? = null

    private var min = 3.0

    private var max = 9.0

    val bitmap = MutableLiveData<Bitmap>().apply {
        value = src
    }

    val onSeekMaxThresholdCommand = BindingCommand<Int>(consumer = {
        this?.let {
            if (it % 3 != 0)
                return@BindingCommand
            max = toDouble(it)
            updateBitmap()
        }
    })

    val onSeekMinThresholdCommand = BindingCommand<Int>(consumer = {
        this?.let {
            if (it % 3 != 0)
                return@BindingCommand
            min = toDouble(it)
            updateBitmap()
        }
    })

    val onClickReturn = BindingCommand<Any>({
        bitmap.value = src
    })

    private fun updateBitmap() {
        Flowable.just(src.copy(src.config, true))
            .map {
                opencvManager.canny(it, min, max, 3, true)
            }
            .bindToSchedulers()
            .autoDispose(this)
            .subscribe {
                temp?.recycle()
                bitmap.value = it
                temp=it
            }
    }

    override fun onCleared() {
        super.onCleared()
        src.recycle()
        temp?.recycle()
    }

}