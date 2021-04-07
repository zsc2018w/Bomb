package com.bomb.plus.main.fragment

import androidx.appcompat.app.AlertDialog
import com.bomb.common.basic.BaseLazyFragment
import com.bomb.plus.R

import com.bomb.bus.test.Te2
import com.bomb.common.basic.BaseActivity
import com.bomb.common.net.log
import com.bomb.common.net.toNextPage
import com.bomb.plus.local_store.db.DBManager
import com.bomb.plus.local_store.db.entity.EyeDataBean
import com.bomb.plus.study.basic.TestMain

import com.bomb.plus.local_store.db.entity.User
import com.bomb.plus.local_store.db.database.BombDataBase
import com.bomb.plus.local_store.db.entity.TestBean
import com.bomb.plus.study.TestActivity
import kotlinx.android.synthetic.main.fragment_my_home.view.*
import kotlin.concurrent.thread


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




    override fun initVm() {
        super.initVm()




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


        Te2()




    }

  
    override fun initView() {
        super.initView()

        fView.bt1.setOnClickListener {

    /*        var list = dataBase?.getUserDao()?.queryAll()


            val zList = list as ArrayList<User>*/


            activity?.toNextPage<TestActivity>()

        }

    }

    override fun onLazyLoad() {

    }


}
