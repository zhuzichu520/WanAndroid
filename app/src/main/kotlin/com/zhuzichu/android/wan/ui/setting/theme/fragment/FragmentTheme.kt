package com.zhuzichu.android.wan.ui.setting.theme.fragment

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.zhuzichu.android.shared.storage.GlobalStorage
import com.zhuzichu.android.wan.BR
import com.zhuzichu.android.wan.R
import com.zhuzichu.android.wan.base.FragmentAnalyticsBase
import com.zhuzichu.android.wan.databinding.FragmentThemeBinding
import com.zhuzichu.android.wan.ui.setting.theme.viewmodel.ViewModelTheme

class FragmentTheme : FragmentAnalyticsBase<FragmentThemeBinding, ViewModelTheme>() {

    override fun setLayoutId(): Int = R.layout.fragment_theme

    override fun bindVariableId(): Int = BR.viewModel

    override fun initData() {
        viewModel.updateTheme()
    }

    override fun initVariable() {
        viewModel.themeChangeEvent.observe(this, Observer {
            GlobalStorage.uiMode = it
            activityCtx.window.setWindowAnimations(R.style.WindowFade)
            AppCompatDelegate.setDefaultNightMode(GlobalStorage.uiMode)
            activityCtx.recreate()
        })
    }
}