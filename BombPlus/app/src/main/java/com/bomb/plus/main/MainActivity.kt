package com.bomb.plus.main


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bomb.common.basic.BaseActivity
import com.bomb.common.basic.BaseFragment
import com.bomb.common.utils.StatusBarUtil
import com.bomb.plus.R
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

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        StatusBarUtil.setStatusBarNoSpace(this)
        onTabSelected(HOME_TAG)
        bnv_bottom_bar.setOnNavigationItemSelectedListener(selectedListener)

    }


    private val selectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.tab_home -> {
                StatusBarUtil.darkModel(this,false)
                onTabSelected(HOME_TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab_fund -> {
                onTabSelected(FUND_TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab_photo -> {
                StatusBarUtil.darkModel(this,true)
                onTabSelected(GANK_TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab_my -> {
                onTabSelected(MY_TAG)
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
                fragment = HomeFragment()
                myFragment = fragment
            }
        }
        return fragment!!
    }


}
