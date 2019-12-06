package com.zhuzichu.android.wan.ui.opencv.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zhuzichu.android.shared.base.ViewModelAnalyticsBase
import com.zhuzichu.android.shared.extension.map
import com.zhuzichu.android.wan.BR
import com.zhuzichu.android.wan.R
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

import javax.inject.Inject

class ViewModelOpencv @Inject constructor() : ViewModelAnalyticsBase() {

    companion object {
        const val TYPE_GRAY = 0
        const val TYPE_ERODE = 1
        const val TYPE_BLUR = 2
        const val TYPE_CANNY = 3
    }

    private val closure: Int.() -> Unit = {
        val id = when (this) {
            TYPE_GRAY -> {
                R.id.action_fragmentOpencv_to_fragmentGray
            }
            TYPE_ERODE -> {
                R.id.action_fragmentOpencv_to_fragmentErode
            }
            TYPE_BLUR -> {
                R.id.action_fragmentOpencv_to_fragmentBlur
            }
            TYPE_CANNY -> {
                R.id.action_fragmentOpencv_to_fragmentCanny
            }
            else -> {
                R.id.action_fragmentOpencv_to_fragmentGray
            }
        }
        startFragment(id)
    }

    val items = MutableLiveData<List<Any>>().also {
        it.value = listOf(
            ItemViewModelOpencv(this, TYPE_GRAY, R.string.opencv_gray, closure),
            ItemViewModelOpencv(this, TYPE_ERODE, R.string.opencv_erode_dilate, closure),
            ItemViewModelOpencv(this, TYPE_BLUR, R.string.opencv_blur, closure),
            ItemViewModelOpencv(this, TYPE_CANNY, R.string.opencv_canny, closure)
        )
    }

    val itemBinding = OnItemBindClass<Any>().apply {
        map<ItemViewModelOpencv>(BR.item, R.layout.item_opencv)
    }

}