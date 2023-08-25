package vn.techres.android.supplier.app

import com.hjq.http.listener.OnHttpListener
import okhttp3.Call
import vn.techres.base.BaseFragment
import vn.techres.base.action.ToastAction
import vn.techres.android.supplier.http.model.HttpData

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 03/10/2022
 */
abstract class AppFragment<A : AppActivity> : BaseFragment<A>(),
    ToastAction, OnHttpListener<Any> {


    /**
     * 当前加载对话框是否在显示中
     */
    open fun isShowDialog(): Boolean {
        val activity: A = getAttachActivity() ?: return false
        return activity.isShowDialog()
    }

    override fun initView() {
        //code
    }

    /**
     * 显示加载对话框
     */
    open fun showDialog() {
        getAttachActivity()?.showDialog()
    }

    /**
     * 隐藏加载对话框
     */
    open fun hideDialog() {
        getAttachActivity()?.hideDialog()
    }

    /**
     * [OnHttpListener]
     */
    override fun onStart(call: Call) {
        //  showDialog()
    }

    override fun onSucceed(result: Any) {
        if (result !is HttpData<*>) {
            return
        }
        toast(result.getMessage())
        //  hideDialog()
    }

    override fun onFail(e: Exception) {
        //   hideDialog()
        toast(e.message)
    }

    override fun onEnd(call: Call) {
        //   hideDialog()
    }
}