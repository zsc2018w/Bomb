package com.bomb.plus.main



import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.MessageQueue
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bomb.common.basic.BaseActivity
import com.bomb.common.net.log
import com.bomb.common.utils.StatusBarUtil
import com.bomb.plus.R
import com.bomb.plus.main.fragment.GankFragment
import com.bomb.plus.main.fragment.HomeFragment
import com.bomb.plus.main.fragment.MyFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


/**
 * 主页面
 */
class MainActivity : BaseActivity() {

    private val HOME_TAG = "home_tag"
    private val FUND_TAG = "fund_tag"
    private val GANK_TAG = "GANK_tag"
    private val MY_TAG = "my_tag"
    private var homeFragment: Fragment? = null
    private var fundFragment: Fragment? = null
    private var gankFragment: Fragment? = null
    private var myFragment: Fragment? = null
    private var fragmentManager: FragmentManager? = null
    private var currentFragment: Fragment? = null

    private val handler = object :Handler(Looper.getMainLooper(), Callback {
          return@Callback false
    }) {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {

        StatusBarUtil.setStatusBarNoSpace(this)
        onTabSelected(MY_TAG)
        bnv_bottom_bar.selectedItemId= R.id.tab_my
        bnv_bottom_bar.setOnNavigationItemSelectedListener(selectedListener)

        Looper.myQueue().addIdleHandler {
            log("test-->空闲----》1111")
            return@addIdleHandler false
        }

        Looper.myQueue().addIdleHandler(object : MessageQueue.IdleHandler {
            override fun queueIdle(): Boolean {
                log("test-->空闲----》2222")
                return true
            }

        })

    }

    private val selectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.tab_home -> {
                StatusBarUtil.darkModel(this,false)
                onTabSelected(HOME_TAG)
                window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab_fund -> {
                StatusBarUtil.darkModel(this,false)
                onTabSelected(FUND_TAG)
            /*    if (window != null) {
                    val paint = Paint()
                    val cm = ColorMatrix()
                    cm.setSaturation(0f)
                    paint.colorFilter = ColorMatrixColorFilter(cm)
                    window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
                }*/
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab_photo -> {
                StatusBarUtil.darkModel(this,true)
                onTabSelected(GANK_TAG)

     /*           val cdt1=object :android.os.CountDownTimer(3000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val c = millisUntilFinished / 1000
                        val b= c+1
                        log("cdt1111->$millisUntilFinished-->剩余时间--》$c"+"--->"+b)
                    }

                    override fun onFinish() {
                        log("cdt1111->完成")
                    }
                }
                    .start()*/
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab_my -> {
                StatusBarUtil.darkModel(this,true)
                onTabSelected(MY_TAG)

              /*  startActivity(Intent(this@MainActivity,TestActivity::class.java))
                try {
                    val cdt=object : CountDownTimer(3000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val c = millisUntilFinished / 1000
                            val b= c+1

                            window.decorView.post {
                                log("cdt->$millisUntilFinished-->剩余时间--》$c"+"--->"+b)
                            }
                        }

                        override fun onFinish() {
                            log("cdt->完成")
                        }
                    }
                        .start()




                    log("scope--->${Environment.getExternalStorageDirectory()}")
                    log("scope--->${Environment.getExternalStorageState()}")
                    log("scope--->${filesDir}")
                    log("scope--->${cacheDir}")
                    log("scope--->${externalCacheDir}")
                    log("scope--->${ProHelper.mApp.getExternalFilesDir(null)}")
                    val user=TUser("罗雕",18)
                    val oos=ObjectOutputStream(FileOutputStream("${filesDir}/cache2.txt"))
                    oos.writeObject(user)
                    oos.close()
                }catch (e:Exception){
                    log("scope--->$e")
                }

*/
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }

    private fun getTransaction(): FragmentTransaction? {
        if (fragmentManager == null) {
            fragmentManager = supportFragmentManager
        }
        return fragmentManager?.beginTransaction()
    }


    private fun onTabSelected(tag: String) {
        hideFragmentAll()
        currentFragment = fragmentManager?.findFragmentByTag(tag)?.apply {
            showFragment(this)
        } ?: getTagFragment(tag).apply {
            addFragment(this, tag)
        }
    }

    private fun showFragment(fragment: Fragment) {
        getTransaction()?.show(fragment)?.commit()
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        getTransaction()?.add(R.id.fl_frame_layout, fragment, tag)?.commit()
    }

    private fun hideFragmentAll() {
        val transaction = getTransaction()
        if (homeFragment != null) {
            transaction?.hide(homeFragment!!)
        }
        if (fundFragment != null) {
            transaction?.hide(fundFragment!!)
        }
        if (gankFragment != null) {
            transaction?.hide(gankFragment!!)
        }
        if (myFragment != null) {
            transaction?.hide(myFragment!!)
        }
        transaction?.commitAllowingStateLoss()
    }

    private fun getTagFragment(tag: String): Fragment {
        var fragment: Fragment? = null
        when (tag) {
            HOME_TAG -> {
                fragment = HomeFragment()
                homeFragment = fragment
            }
            FUND_TAG -> {
                fragment = HomeFragment()
                fundFragment = fragment
            }
            GANK_TAG -> {
                fragment = GankFragment()
                gankFragment = fragment
            }
            MY_TAG -> {
                fragment = MyFragment()
                myFragment = fragment
            }
        }
        return fragment!!
    }


    override fun onBackPressed() {
       // super.onBackPressed()

        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        super.onKeyDown(keyCode, event)
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //onBackPressed()
            finish()
            return true
        }
        return false
    }


}
