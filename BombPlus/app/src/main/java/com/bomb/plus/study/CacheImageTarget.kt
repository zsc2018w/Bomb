package com.bomb.plus.study

import android.graphics.drawable.Drawable
import com.bomb.common.net.log
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import java.io.File

open class CacheImageTarget : Target<File> {


    override fun onLoadStarted(placeholder: Drawable?) {
      log("cache_load---->onLoadStarted")
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        log("cache_load---->onLoadFailed")
    }

    override fun getSize(cb: SizeReadyCallback) {
        log("cache_load---->getSize")
        cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
    }

    override fun getRequest(): Request? {
        return null
    }

    override fun onStop() {
        log("cache_load---->onStop")
    }

    override fun setRequest(request: Request?) {
    }

    override fun removeCallback(cb: SizeReadyCallback) {
        log("cache_load---->removeCallback")
    }

    override fun onLoadCleared(placeholder: Drawable?) {
        log("cache_load---->onLoadCleared")
    }

    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
        log("cache_load---->onResourceReady")
    }

    override fun onStart() {
        log("cache_load---->onStart")
    }

    override fun onDestroy() {
        log("cache_load---->onDestroy")
    }

}
