package vn.techres.android.supplier.ui.dialog

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import vn.techres.base.BaseDialog
import vn.techres.base.action.AnimAction
import vn.techres.base.action.ToastAction
import vn.techres.android.supplier.R
import java.io.File

/**
 * @Author: NGUYEN THE DAT
 * @Date: 4/8/2023
 */
class UpdateDialog{

    class Builder(context: Context) : BaseDialog.Builder<Builder>(context), ToastAction {

        private val nameView: TextView? by lazy { findViewById(R.id.tv_update_name) }
        private val detailsView: TextView? by lazy { findViewById(R.id.tv_update_details) }
        private val updateView: TextView? by lazy { findViewById(R.id.tv_update_update) }
        private val closeView: TextView? by lazy { findViewById(R.id.tv_update_close) }

        /** Apk 文件 */
        private var apkFile: File? = null


        /** 是否强制更新 */
        private var forceUpdate = false


        /** 当前是否下载完毕 */
        private var goMarket: Intent? = null

        init {
            setContentView(R.layout.update_dialog)
            setAnimStyle(AnimAction.ANIM_BOTTOM)
            setCancelable(false)
            setOnClickListener(updateView, closeView)
            detailsView?.movementMethod = ScrollingMovementMethod()
        }

        /**
         * 设置版本名
         */
        fun setVersionName(name: CharSequence?): Builder = apply {
            nameView?.text = name
        }

        /**
         * 设置更新日志
         */
        fun setUpdateLog(text: CharSequence?): Builder = apply {
            detailsView?.text = text
            detailsView?.visibility = if (text == null) View.GONE else View.VISIBLE
        }

        /**
         * 设置强制更新
         */
        fun setForceUpdate(force: Boolean): Builder = apply {
            forceUpdate = force
            closeView?.visibility = if (force) View.GONE else View.VISIBLE
            setCancelable(!force)
        }

        /**
         * 设置下载 url
         */
        fun setDownloadUrl(url: String?): Builder = apply {
            val localUri = Uri.parse(url)
            goMarket = Intent(Intent.ACTION_VIEW, localUri)
        }

        override fun onClick(view: View) {
            if (view === closeView) {
                dismiss()
            } else if (view === updateView) {
                // 判断下载状态
                startActivity(goMarket!!)
                dismiss()
            }
        }
    }
}
