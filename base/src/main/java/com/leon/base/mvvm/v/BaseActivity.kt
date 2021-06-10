package com.leon.base.mvvm.v

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.leon.base.R
import com.leon.base.receover.NetworkChangeReceiver
import com.leon.common.utils.StatusBarUtil

abstract class BaseActivity<VB : ViewDataBinding?> : AppCompatActivity() {
    private var receiver: NetworkChangeReceiver? = null
    protected var binding: VB? = null
    protected val titleBinding: ViewDataBinding? = null
        protected get() {
            if (field == null) {
                if (!haveDefaultTitle) {
                    finish()
                    throw IllegalStateException("Pls call override getTitleLayoutId() and return a real layout id.")
                } else {
                    Log.w(
                        "Title",
                        "You have set a title bar, do not call this method getTitleBinding()"
                    )
                }
            }
            return field
        }
    private val haveDefaultTitle = false
    private var downX = 0f
    private var downY = 0f
    private var lastTime: Long = 0
//    protected var titleBar: View? = null
    private val bottom = 0

    //手势
    protected var gestureDetector //触摸手势监听事件
            : GestureDetector? = null

    protected fun configGestureDetector() {
        gestureDetector = GestureDetector(this, object : GestureDetector.OnGestureListener {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onShowPress(e: MotionEvent) {}
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return false
            }

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                return false
            }

            override fun onLongPress(e: MotionEvent) {}
            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1 == null || e2 == null) {
                    return false
                }
                return if (e1.x - e2.x < 0 && Math.abs((e1.x - e2.x).toInt()) > 150) {
                    //向右滑动的判断，如果手指从左向右滑动，就走这个方法
                    prepageAction()
                    true
                } else if (e1.x - e2.x > 0 && Math.abs((e1.x - e2.x).toInt()) > 150) {
                    //向左滑动的判断，如果手指从右向左滑动，就走这个方法
                    nextpageAction()
                    true
                } else {
                    false
                }
            }
        })
    }

    protected fun prepageAction() {}
    protected fun nextpageAction() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        //去除标题栏
        if (removeActionBar()) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        requestPermissions()
        super.onCreate(savedInstanceState)
        //        ARouter.getInstance().inject(this);
        parseBundleData()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //黑色
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            setStatusBarColor()
        }
        ARouter.getInstance().inject(this)
        configWindowSize()
        initContentView()
        registerNetworkChangeReceiver()
        initView()
        initData()
        setStatusBarColor()
    }

    /**
     * TODO 如果需要配置窗口大小，请重写
     */
    fun configWindowSize() {}
    protected fun setStatusBarColor() {
        StatusBarUtil.setColor(this, resources.getColor(R.color.title_bg_color), 0)
    }

    /*
     * TODO 读取页面跳转传递的数据
     */
    private fun parseBundleData() {
        if (this.intent != null) {
            val bundle = this.intent.extras
            bundle?.let { getTransmitBundleData(it) }
        }
    }

    protected fun getTransmitBundleData(bundle: Bundle) {}
    private fun initContentView() {
//        if (haveTitle()) {
//            ActivityBaseBinding baseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
//            int titleLayoutId = getTitleLayoutId();
//            if (titleLayoutId != -1) {
//                baseBinding.stub.getViewStub().setLayoutResource(titleLayoutId);
//                baseBinding.stub.getViewStub().inflate();
//                titleBinding = baseBinding.stub.getBinding();
//            } else {
//                ViewGroup root = (ViewGroup) baseBinding.statusBar.getParent();
//                root.removeView(baseBinding.stub.getViewStub());
//                titleBar = getTitleBar();
//                root.addView(titleBar);
//                if (titleBar instanceof TitleBarView) {
//                    initTitleBar((TitleBarView) titleBar);
//                } else {
//                    initTitleBar(titleBar);
//                }
//                titleBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        titleBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        int[] rect = new int[2];
//                        titleBar.getLocationInWindow(rect);
//                        bottom = rect[1] - StatusBarUtil.getStatusBarHeight(BaseActivity.this) + titleBar.getMeasuredHeight();
//                    }
//                });
//            }
//            binding = DataBindingUtil.inflate(LayoutInflater.from(this), getLayoutId(), ((ViewGroup) baseBinding.getRoot()), false);
//            ((LinearLayout) baseBinding.getRoot()).addView(binding.getRoot());
//        } else {
//            binding = DataBindingUtil.setContentView(this, getLayoutId());
//        }
//        binding.setLifecycleOwner(this);
    }

    fun updateTitleText(aViewId: Int, aUpdateText: String?) {
//        ((TitleBarView) titleBar).updateTitleText(aViewId, aUpdateText);
    }

    protected fun setTitle(title: String?) {
//        updateTitleText(R.id.titlebar_title_text, title);
    }

    /**
     * @return layout id
     */
    @get:LayoutRes
    protected abstract val layoutId: Int

    /**
     * 初始化view
     */
    protected abstract fun initView()
    protected fun initData() {}
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            downX = ev.x
            downY = ev.y
            if (System.currentTimeMillis() - lastTime < MIN_CLICK_DELAY_TIME) {
                return true
            }
        } else if (ev.action == MotionEvent.ACTION_UP) {
            if (Math.abs(ev.x - downX) < 20 || Math.abs(ev.y - downY) < 20) {
                if (System.currentTimeMillis() - lastTime > MIN_CLICK_DELAY_TIME) {
                    lastTime = System.currentTimeMillis()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 去除ActionBar
     */
    protected fun removeActionBar(): Boolean {
        return true
    }

    /**
     * 是否有title
     *
     * @return
     */
    protected fun haveTitle(): Boolean {
        return true
    }

    /**
     * 子类自定义title布局
     *
     * @return
     */
    @get:LayoutRes
    protected val titleLayoutId: Int
        protected get() = -1

    private fun getTitleBar(): View? {
//        TitleBarView titleBar = new TitleBarView(this);
//        titleBar.setListener(btnIndex -> {
//            if (btnIndex == R.id.titlebar_left_image) {
//                //返回首页
//                onTitleBarBackClick();
//            } else if (btnIndex == R.id.titlebar_right_image) {
//                //退出登录
//                new SettingDialog.Builder(this)
//                        .setTop(bottom)
//                        .setCallback(this::logoutAction)
//                        .create().show();
//            } else if (btnIndex == R.id.titlebar_right_custom_img) {
//                //右侧图标点击
//                onTitleBarRightCustomImgClick();
//            } else if (btnIndex == R.id.titlebar_right_text) {
//                onTitleBarRightTextClick();
//            }
//        });
//        haveDefaultTitle = true;
//        return titleBar;
        return null
    }
    //    protected void initTitleBar(TitleBarView titleBar) {
    //
    //    }
    //    protected void initTitleBar(View titleBar) {
    //
    //    }
    /**
     * @return 申明需要动态申请的权限。
     */
    @get:CallSuper
    @get:SuppressLint("ResourceAsColor")
    protected val permissions: Array<String>?
    //    protected void initTitleBar(TitleBarView titleBar) {
    //
    //    }
    //    protected void initTitleBar(View titleBar) {
    //
    //    }
        /**
         * @return 申明需要动态申请的权限。
         */
        protected get() = null

    /**
     * 权限申请
     */
    private fun requestPermissions() {
        val permissions = permissions
        if (permissions != null && permissions.size > 0) {
            if (!checkPermissions()) {
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST)
            }
        }
    }

    /**
     * 权限检查
     *
     * @return 是否全部被允许
     */
    protected fun checkPermissions(): Boolean {
        val neededPermissions = permissions
        if (neededPermissions == null || neededPermissions.size == 0) {
            return true
        }
        var allGranted = true
        for (neededPermission in neededPermissions) {
            allGranted = allGranted and (ContextCompat.checkSelfPermission(
                this,
                neededPermission
            ) == PackageManager.PERMISSION_GRANTED)
        }
        return allGranted
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var isAllGranted = true
            for (grantResult in grantResults) {
                isAllGranted = isAllGranted and (grantResult == PackageManager.PERMISSION_GRANTED)
            }
            afterRequestPermission(isAllGranted)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            getTheResult(requestCode, data)
        }
    }

    /**
     * onActivityResult消息回传
     *
     * @param requestCode
     * @param data
     */
    protected fun getTheResult(requestCode: Int, data: Intent?) {}

    /**
     * 请求权限的回调
     *
     * @param isAllGranted 是否全部被允许
     */
    protected fun afterRequestPermission(isAllGranted: Boolean) {}
    open fun showToast(str: String?) {
//        Toast.makeText(BaseApplication.getInstance(), str, Toast.LENGTH_SHORT).show();
    }

    open fun showToast(stringId: Int) {
        showToast(resources.getString(stringId))
    }

    protected fun hideInput() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val v = window.peekDecorView()
        if (null != v) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    private fun registerNetworkChangeReceiver() {
        receiver = NetworkChangeReceiver(this)
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (receiver != null) {
            unregisterReceiver(receiver)
            receiver!!.onDestroy()
            receiver = null
        }
    }

    protected fun onTitleBarRightTextClick() {}

    /*
     * TODO 退出登录
     */
    fun logoutAction() {
//        CustomGeneralDialog.with(this)
//                .setRightImage(R.mipmap.icon_warning)
//                .setRightBtnText(getString(R.string.sure))
//                .setMessage(getString(R.string.tip_cancellation_remove))
//                .setLeftBtnText(getString(R.string.cancel))
//                .setTitle(getString(R.string.attention))
//                .setCancelOutside(true)
//                .setCancelable(true)
//                .setDialogListener(btnId -> {
//                    if (btnId == R.id.right_btn) {
//                        //需要调用退出登录的接口
//                        Bundle bundle = new Bundle();
//                        bundle.putBoolean("logout", true);
//                        ARouter.getInstance().build(Path.LOGIN).withFlags(Intent.FLAG_ACTIVITY_NEW_TASK).with(bundle).navigation();
//                        //避免跳转的时候白屏
//                        new Handler().postDelayed(this::backAction, 3000);
//                    }
//                }).create().show();
    }

    /**
     * TODO 关闭当前页面
     */
    protected fun finishActivity() {
        finish()
    }

    /**
     * TODO 响应back时间，默认是直接关闭，如果需要不同的业务处理，需要重写该方法
     */
    protected fun backAction() {
        this.finishActivity()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backAction()
    }

    protected fun onTitleBarBackClick() {
        onBackPressed()
    }

    protected fun onTitleBarRightCustomImgClick() {}

    companion object {
        private const val PERMISSION_REQUEST = 100
        private const val MIN_CLICK_DELAY_TIME: Long = 120
    }
}