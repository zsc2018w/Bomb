package com.bomb.plus.study.test

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.bomb.common.net.log

class TView : LinearLayout {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                   log("event__1111")

            }
            MotionEvent.ACTION_MOVE -> {
                log("event__2222")

                return false
            }
            MotionEvent.ACTION_UP -> {

                log("event__3333")

            }
        }

        return super.onTouchEvent(event)
    }
}