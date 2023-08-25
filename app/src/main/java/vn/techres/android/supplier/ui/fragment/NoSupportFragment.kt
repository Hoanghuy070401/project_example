package vn.techres.android.supplier.ui.fragment

import android.view.View
import vn.techres.android.supplier.app.AppFragment
import vn.techres.android.supplier.databinding.FragmentNoSupportBinding
import vn.techres.android.supplier.ui.activity.HomeActivity

/**
 * @Author: NGUYEN THE DAT
 * @Date: 4/7/2023
 */
class NoSupportFragment : AppFragment<HomeActivity>() {
    companion object {
        fun newInstance(): NoSupportFragment {
            return NoSupportFragment()
        }
    }

    private lateinit var binding: FragmentNoSupportBinding
    override fun getLayoutView(): View {
        binding = FragmentNoSupportBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initData() {

    }
}