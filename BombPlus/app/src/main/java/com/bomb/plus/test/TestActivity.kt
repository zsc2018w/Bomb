package com.bomb.plus.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bomb.plus.R
import com.bomb.plus.utils.PermissionsUtils.requestRecordAudio
import android.media.projection.MediaProjectionManager

import android.content.Intent

import android.app.AlertDialog
import android.content.DialogInterface
import android.media.projection.MediaProjection

import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.util.DisplayMetrics
import android.view.View
import com.bomb.common.net.log
import com.bomb.plus.test.RecordEvent.TYPE_PREPARE
import kotlinx.android.synthetic.main.activity_test.*
import org.greenrobot.eventbus.EventBus


class TestActivity : AppCompatActivity(), View.OnClickListener {

    var projectionManager: MediaProjectionManager? = null
    var mediaProjection: MediaProjection? = null
    var metrics: DisplayMetrics? = null
    var mediaRecorder: MediaRecorder? = null
    var virtualDisplay: VirtualDisplay? = null
    val REQUEST_CODE = 808
    var recordPath: String? = null
    var tFloat: RecordFloatWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
        requestPermissions()

    }


    private fun initView() {
        RecordScreenManager.instance.init(this)
        bt1.setOnClickListener(this)
        bt2.setOnClickListener(this)
        bt3.setOnClickListener(this)
        bt4.setOnClickListener(this)
        bt4.isSelected = true
    }


    private fun requestPermissions() {

        requestRecordAudio(this, onGranted = {

        }, onDenied = {

        })
        val permission=FloatWindowParamManager.checkPermission(this)
        log("是否有悬浮权限----》$permission")
        if (permission){// && !RomUtils.isVivoRom()) {
             log("有权限")
      /*      Toast.makeText(this@MainActivity, R.string.has_float_permission, Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(applicationContext, FloatWindowService::class.java)
            intent.action = FloatWindowService.ACTION_CHECK_PERMISSION_AND_TRY_ADD
            startService(intent)*/
        } else {
            log("无权限")
   /*         Toast.makeText(this@MainActivity, R.string.no_float_permission, Toast.LENGTH_SHORT)
                .show()*/
            showOpenPermissionDialog()
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt1 -> {
                RecordScreenManager.instance.prepareRecord()
            }
            R.id.bt2 -> {
                RecordScreenManager.instance.stopRecord()
                //MediaScannerConnection.scanFile(this, arrayOf(recordPath), null, null)
            }
            R.id.bt3 -> {
                RecordScreenManager.instance.test()
            }

        R.id.bt4 -> {
            bt4.isSelected = !bt4.isSelected
            if (bt4.isSelected) {
                bt4.text = "录制声音"
            } else {
                bt4.text = "不录制声音"
            }
        }
    }
}


    private fun showOpenPermissionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("悬浮窗权限")
        builder.setMessage("申请悬浮权限")
        builder.setPositiveButton(android.R.string.ok,
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                FloatWindowParamManager.tryJumpToPermissionPage(applicationContext)

              //  val intent = Intent(applicationContext, FloatWindowService::class.java)
                //intent.action = FloatWindowService.ACTION_CHECK_PERMISSION_AND_TRY_ADD
                //startService(intent)
            })

        builder.setNegativeButton(android.R.string.cancel,
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })

        builder.show()
    }

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == REQUEST_CODE ) {
        EventBus.getDefault().post(
            RecordEvent(
                TYPE_PREPARE,
                requestCode,
                resultCode,
                data
            )
        )
    }
}

}
