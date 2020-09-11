package com.bomb.common.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity

import com.bomb.common.R
import com.zyao89.view.zloading.Z_TYPE
import kotlinx.android.synthetic.main.common__loading_layout.*

class LoadingDialog : Dialog {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common__loading_layout)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.CENTER)
        common_loading_view.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)
        common_loading_view.setColorFilter(
            Color.BLACK,
            PorterDuff.Mode.SRC_ATOP
        )
    }

    fun setCancel(
        cancelable: Boolean = true,
        canceledOnTouchOutside: Boolean = true
    ): LoadingDialog {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(canceledOnTouchOutside)
        return this
    }


}
