package vn.techres.android.supplier.ui.activity

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.hjq.http.EasyHttp
import com.hjq.http.listener.HttpCallback
import vn.techres.base.PagerAdapter
import vn.techres.android.supplier.R
import vn.techres.android.supplier.app.AppActivity
import vn.techres.android.supplier.app.AppFragment
import vn.techres.android.supplier.cache.SettingCache
import vn.techres.android.supplier.cache.UserCache
import vn.techres.android.supplier.constants.AppConstants
import vn.techres.android.supplier.constants.ModuleClassConstants
import vn.techres.android.supplier.databinding.HomeActivityBinding
import vn.techres.android.supplier.http.api.CheckVersionApi
import vn.techres.android.supplier.http.model.HttpData
import vn.techres.android.supplier.http.model.VersionData
import vn.techres.android.supplier.other.AppConfig
import vn.techres.android.supplier.ui.dialog.UpdateDialog
import vn.techres.android.supplier.ui.fragment.NoSupportFragment
import kotlin.system.exitProcess

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 28/09/2022
 */
class HomeActivity : AppActivity() {
    lateinit var binding: HomeActivityBinding


    private var currentPage = 2
    private var twice = false

    // You can use this method to check if the activity is currently resumed or not
    override fun getLayoutView(): View {
        binding = HomeActivityBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(33)
    override fun initView() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (twice) {
                    exitProcess(0)
                }
                twice = true
                toast(getString(R.string.back_confirm))
                postDelayed({ twice = false }, 2000)
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun initData() {
        val fragments: List<Fragment>
        fragments = listOf(
            NoSupportFragment(),
            NoSupportFragment(),
            NoSupportFragment(),
//            Class.forName(ModuleClassConstants.UTILITY_FRAGMENT)
//                .newInstance() as AppFragment<*>
            NoSupportFragment()
            )


        val adapter = PagerAdapter(this, fragments)

        //Setup menu
        binding.mBottomNavigationView.itemIconTintList = null
        val menu = binding.mBottomNavigationView.menu
        for (i in 0 until menu.size()) {
            binding.mBottomNavigationView.findViewById<BottomNavigationItemView>(menu.getItem(i).itemId)
                .setOnLongClickListener { true }
        }
        binding.contentView.adapter = adapter
        binding.contentView.offscreenPageLimit = adapter.itemCount - 1
        binding.contentView.setCurrentItem(currentPage, false)
        binding.mBottomNavigationView.selectedItemId = R.id.menu_message

        //Chặn viewpager2 vuốt
        binding.contentView.isUserInputEnabled = false


        binding.mBottomNavigationView.setOnItemSelectedListener { item ->
            // Handle item selection here
            when (item.itemId) {
                R.id.menu_kaizen -> {
                    currentPage = 0
                    binding.contentView.setCurrentItem(currentPage, false)
                    return@setOnItemSelectedListener true
                }

                R.id.menu_work -> {
                    currentPage = 1
                    binding.contentView.setCurrentItem(currentPage, false)

                    return@setOnItemSelectedListener true
                }

                R.id.menu_message -> {
                    currentPage = 2
                    binding.contentView.setCurrentItem(currentPage, false)
                    return@setOnItemSelectedListener true
                }

                R.id.menu_notification -> {
                    currentPage = 3
                    binding.contentView.setCurrentItem(currentPage, false)

                    return@setOnItemSelectedListener true
                }

                R.id.menu_utility -> {
                    currentPage = 4
                    binding.contentView.setCurrentItem(currentPage, false)

                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }
        }

    }
}