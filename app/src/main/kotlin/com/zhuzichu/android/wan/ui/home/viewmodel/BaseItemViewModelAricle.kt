package com.zhuzichu.android.wan.ui.home.viewmodel

import android.graphics.Color
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.uber.autodispose.autoDispose
import com.zhuzichu.android.wan.base.ItemViewModelAnalyticsBase
import com.zhuzichu.android.wan.base.ViewModelAnalyticsBase
import com.zhuzichu.android.shared.ext.autoLoading
import com.zhuzichu.android.shared.ext.createCommand
import com.zhuzichu.android.shared.ext.toColorByResId
import com.zhuzichu.android.shared.ext.toast
import com.zhuzichu.android.wan.R
import com.zhuzichu.android.wan.repository.entity.BeanArticle
import com.zhuzichu.android.wan.ui.home.domain.UseCaseCollect
import com.zhuzichu.android.wan.ui.home.domain.UseCaseUnCollect
import com.zhuzichu.android.wan.ui.web.ActivityWeb


abstract class BaseItemViewModelAricle(
    val viewModel: ViewModelAnalyticsBase,
    private val bean: BeanArticle,
    private val useCaseCollect: UseCaseCollect,
    private val useCaseUnCollect: UseCaseUnCollect
) : ItemViewModelAnalyticsBase(viewModel) {

    val id = bean.id

    val title = MutableLiveData(bean.title)

    val article = MutableLiveData(bean)

    val collectColor = MutableLiveData<Int>()

    val onClickItem = createCommand {
        startActivity(ActivityWeb::class.java, bundleOf(ActivityWeb.URL to bean.link))
    }

    val onClickCollect = createCommand {
        if (bean.collect == true) {
            unCollect()
        } else {
            collect()
        }
    }

    init {
        updateCollect()
    }

    private fun updateCollect() {
        collectColor.value = if (bean.collect == false)
            Color.parseColor("#4A4A4A")
        else
            R.color.color_primary.toColorByResId()
    }

    private fun collect() {
        if (id == null) {
            "not id".toast()
            return
        }
        useCaseCollect.execute(id)
            .autoLoading(viewModel)
            .autoDispose(viewModel)
            .subscribe(
                {
                    bean.collect = true
                    updateCollect()
                },
                {
                    viewModel.handleThrowable(it)
                }
            )
    }

    private fun unCollect() {
        if (id == null) {
            "not id".toast()
            return
        }
        useCaseUnCollect.execute(id)
            .autoLoading(viewModel)
            .autoDispose(viewModel)
            .subscribe(
                {
                    bean.collect = false
                    updateCollect()
                },
                {
                    viewModel.handleThrowable(it)
                }
            )
    }
}