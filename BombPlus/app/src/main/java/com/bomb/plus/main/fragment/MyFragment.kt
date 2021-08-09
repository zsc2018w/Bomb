package com.bomb.plus.main.fragment

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.bomb.common.basic.BaseLazyFragment
import com.bomb.plus.R

import com.bomb.bus.test.Te2
import com.bomb.common.basic.BaseActivity
import com.bomb.common.net.log
import com.bomb.common.net.toNextPage
import com.bomb.plus.aidl.IRemoteBitmap
import com.bomb.plus.local_store.db.DBManager
import com.bomb.plus.local_store.db.entity.EyeDataBean
import com.bomb.plus.study.basic.TestMain

import com.bomb.plus.local_store.db.entity.User
import com.bomb.plus.local_store.db.database.BombDataBase
import com.bomb.plus.local_store.db.entity.TestBean
import com.bomb.plus.study.TestActivity
import com.bomb.plus.study.found.ListFragment
import kotlinx.android.synthetic.main.fragment_my_home.view.*
import kotlin.math.abs


/**
 * HomeFragment
 */
class MyFragment : BaseLazyFragment() {


    private val url = "https://mobile-files.nxin.com/thumbnail_test.gif?imageView2/0/w/360"


    val dataList = arrayListOf<String>()

    val dataList2 = emptyList<String>()


    override fun getLayoutId(): Int {
        return R.layout.fragment_my_home
    }

    var dataBase: BombDataBase? = null


    class MyObserver : LifecycleObserver {

        @OnLifecycleEvent(value = Lifecycle.Event.ON_ANY)
        fun m1(owner: LifecycleOwner, event: Lifecycle.Event) {
            log("测试参数---->m1")
            log("测试参数---->${event}")
            log("测试参数---->${owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)}")
        }

        @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
        fun m2() {
            log("测试参数---->m2")
        }

    }


    override fun initVm() {
        super.initVm()



        lifecycle.addObserver(MyObserver())

        activity?.applicationContext?.let {


            dataBase =
                context?.applicationContext?.let { context -> DBManager.getInstance(context) }
            val list = arrayListOf<EyeDataBean>()
            list.add(EyeDataBean("测试111", "", "", "", "", "", "", "", "", "", "", 1, "", 1, ""))
            list.add(EyeDataBean("测试222", "", "", "", "", "", "", "", "", "", "", 1, "", 1, ""))
            val m1 = EyeDataBean("测试444", "", "", "", "", "", "", "", "", "", "", 1, "", 1, "")
            list.add(m1)


            val m2 = EyeDataBean("测试555", "", "", "", "", "", "", "", "", "", "", 1, "", 1, "")


            dataBase?.getEyeDao()?.addAll(list)
            dataBase?.getEyeDao()?.add(m2)
            dataBase?.getUserDao()?.insertData(
                User(
                    "张三",
                    "20",
                    true,
                    30
                )
            )

            dataBase?.getTestDao()?.add(TestBean("测试", "升级新增", 3))


        }


        val testMain = TestMain()

        testMain.test()


        val te2=Te2()


          te2.condition()


    }


    override fun initView() {
        super.initView()
        val shap = GradientDrawable()
        shap.shape=GradientDrawable.RECTANGLE
        val anim=ValueAnimator.ofFloat(0f, 1f)

        anim.duration = 1000

        anim.repeatCount = -1;
        anim.addUpdateListener {
               animation ->

           val value= animation.animatedValue as Float


            shap.setStroke(100 * value.toInt(), Test.changeAlpha(Color.RED, value))

            shap.setColor(Test.changeAlpha(Color.parseColor("#ff4e00"), value))

            fView.tLayout.background=shap
        }


        fView.tLayout.setOnClickListener {
            anim.start()
        }




        fView.rv.layoutManager = LinearLayoutManager(activity)
        fView.rv.adapter = ListFragment.ListAdapter()
 /*       fView.bt1.setOnClickListener {

            *//*        var list = dataBase?.getUserDao()?.queryAll()


                    val zList = list as ArrayList<User>*//*


            val intent = Intent()
            val bitmap =
                BitmapFactory.decodeResource(resources, R.mipmap.bg_float_permission_detect)
           // intent.putExtra("bitmap", bitmap)

            intent.extras?.putBinder("bitmap",object:IRemoteBitmap.Stub(){
                override fun getBitmap(): Bitmap {
                     return bitmap
                }

            })
            //intent.extras.putBinder()
            activity?.toNextPage<TestActivity>(intent)

        }*/

    }

    override fun onLazyLoad() {

    }


}
