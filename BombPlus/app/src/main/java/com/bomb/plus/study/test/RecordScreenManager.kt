package com.bomb.plus.study.test

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.util.DisplayMetrics
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.bomb.common.net.log
import com.bomb.common.utils.ToastUtils
import com.bomb.plus.BuildConfig
import java.io.File
import java.lang.Exception


class RecordScreenManager private constructor() {



    companion object {
        const val REQUEST_CODE = 808
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            RecordScreenManager()
        }
    }

    var mActivity: Activity? = null
    var metrics: DisplayMetrics? = null
    var projectionManager: MediaProjectionManager? = null
    var mediaProjection: MediaProjection? = null
    var mediaRecorder: MediaRecorder? = null
    var virtualDisplay: VirtualDisplay? = null


    var recordPath: String? = null
    var recordVoice: Boolean = true

    /**
     * 初始化页面配置  必须先调用
     */
    fun init(activity: Activity) {
        this.mActivity = activity
        metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
    }

    /**
     * 是否录制声音  录制之前配置生效
     */
    fun setRecordVocie(voice: Boolean) {
        this.recordVoice = voice
    }


    /**
     * 录制准备
     */
    fun prepareRecord() {
        projectionManager =
            mActivity?.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        val captureIntent = projectionManager?.createScreenCaptureIntent()
        mActivity?.startActivityForResult(captureIntent, REQUEST_CODE)
        log("录制-->prepare")
    }

    /**
     * 开始录制
     */
    fun startRecord() {
        var w = metrics?.widthPixels ?: 0
        var h = metrics?.heightPixels ?: 0

        w -= w % 10
        h -= h % 10
        log("录制初始化--w--->$w h--->$h")

        try {
            mediaRecorder = MediaRecorder().apply {
                if (recordVoice) {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                }
                setVideoSource(MediaRecorder.VideoSource.SURFACE)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                recordPath = getPath() + "${System.currentTimeMillis()}.mp4"
                setOutputFile(recordPath)
                setVideoSize(w, h)
                setVideoEncoder(MediaRecorder.VideoEncoder.H264)
                if (recordVoice) {
                    setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                }
                setVideoEncodingBitRate(5 * 1024 * 1024)
                setVideoFrameRate(30)

                prepare()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            log("录制配置异常-->$e")
        }

        virtualDisplay = mediaProjection?.createVirtualDisplay(
            "MainScreen",
            metrics?.widthPixels ?: 0,
            metrics?.heightPixels ?: 0,
            metrics?.densityDpi ?: 0,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            mediaRecorder?.surface,
            null,
            null
        )
        showFloatWindow()
        mediaRecorder?.start()
        log("录制开始")

    }


    fun stopRecord() {
        try {
            mediaRecorder?.stop()
            mediaRecorder?.reset()
            virtualDisplay?.release()
            mediaProjection?.stop()
            log("录制完成---")
            ToastUtils.show("录制完成")
        } catch (e: Exception) {
            log("录制关闭----》$e")
        } finally {
            removeFloatWindow()
        }


    }

    /**
     * 录制准备回调
     */
    fun onPrepareResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            log("录制准备完成")
            mediaProjection = projectionManager?.getMediaProjection(resultCode, data!!)
            startRecord()
        } else {
            log("录制准备失败")
        }
    }


    fun showFloatWindow() {
        val intent = Intent(mActivity?.applicationContext, FloatWindowService::class.java)
        intent.action = FloatWindowService.ADD_FLOAT_WINDOW
        mActivity?.startService(intent)
    }

    fun removeFloatWindow() {
        val intent = Intent(mActivity?.applicationContext, FloatWindowService::class.java)
        intent.action = FloatWindowService.REMOVE_FLOAT_WINDOW
        mActivity?.startService(intent)
    }

    fun killService() {
        val intent = Intent(mActivity?.applicationContext, FloatWindowService::class.java)
        intent.action = FloatWindowService.KILL_SERVICE
        mActivity?.startService(intent)
    }


    fun onEvent(event: RecordEvent) {
        val type = event.type
        when (type) {
            RecordEvent.TYPE_PREPARE -> {
                onPrepareResult(event.requestCode, event.resultCode, event.data)
            }
            RecordEvent.TYPE_RECORD_FINISH->{
                stopRecord()
            }
            else -> {

            }
        }
    }

    fun getPath(): String {
        val filePath = mActivity?.application?.filesDir
        log("基础路径---》$filePath")
        val dirPath = "$filePath/video/"
        val file = File(dirPath)
        if (!file.exists()) {
            file.mkdir()
        }
        return dirPath
    }


    fun test() {
        val fromUrl = MimeTypeMap.getFileExtensionFromUrl(recordPath)
        log("fromUrl--->$fromUrl")
        val type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fromUrl)
        log("type--->$type")
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = FileProvider.getUriForFile(
            mActivity!!,
            BuildConfig.APPLICATION_ID + ".fileprovider",
            File(recordPath!!)
        )
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(uri, type)
        mActivity?.startActivity(intent)
    }




    fun onClear() {
        killService()
    }


}