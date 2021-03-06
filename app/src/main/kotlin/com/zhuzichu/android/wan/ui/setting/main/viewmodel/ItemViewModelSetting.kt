package com.zhuzichu.android.wan.ui.setting.main.viewmodel

import androidx.annotation.StringRes
import com.zhuzichu.android.mvvm.base.BaseViewModel
import com.zhuzichu.android.wan.R
import com.zhuzichu.android.wan.base.ItemViewModelAnalyticsBase
import com.zhuzichu.android.shared.ext.createCommand

class ItemViewModelSetting(
    viewModel: BaseViewModel,
    val id: Int,
    @StringRes val textId: Int
) : ItemViewModelAnalyticsBase(viewModel) {

    companion object {
        const val LANGUAGES = 0x00
        const val THEME = 0x01
        const val ANIMATION = 0x02
    }

    val onClickItem = createCommand {
        when (id) {
            LANGUAGES -> startFragment(R.id.action_fragmentSetting_to_fragmentLanguages)
            THEME -> startFragment(R.id.action_fragmentSetting_to_fragmentTheme)
            ANIMATION -> startFragment(R.id.action_fragmentSetting_to_fragmentAnimation)
            else -> {
            }
        }
    }

}