# Toolbar的实现 #

此次的笔记的目的:

Toolbar的简介:

1. 一: 单独的toolbar详解和布局
2. 二: 使用  include 和 viewstub来加载
3. 三: 自定义Toolbar的实现
3. 四: 沉浸式的实现
4. 五: 基于6.0权限的获取
5. 本片涉及到沉浸式,demo运行时注意api的版本,后面设计到6.0注意使用api要大于或者=23;

## Toolbar 的详解 ##

Toolbar是在在Android5.0之后实现的,使用的时候要添加v7包的依赖

v7包的依赖:

直接在build.gradle中添加:

 		/*依赖v7包*/
    compile 'com.android.support:appcompat-v7:25.3.1'

至于版本: 依赖后不符合会有相应的提示信息来提示:

v7和v4的本地的地址:

		D:\develop\AndroidSdk\android-sdk-windows\android-sdk-windows\extras\android\support

其实也就是: 在你使用的sdk的extra下面的android文件夹下面的support支持包:

 图片展示如下:
			
![本地的v7包](http://i.imgur.com/gjjiOai.png)


接着我们在as中切换到project视图下面:

在 External Libraries会看到我们依赖的jar包等:

 查看v7包的图片展示:

![as查看依赖的v7包](http://i.imgur.com/oyCoZ7E.jpg)


  最后在widget文件夹下面我们找到了TOOlbar
    
来上代码(展示一下子):

 	 `
	public class Toolbar extends ViewGroup {
    private static final String TAG = "Toolbar";

    private ActionMenuView mMenuView;
    private TextView mTitleTextView;
    private TextView mSubtitleTextView;
    private ImageButton mNavButtonView;
    private ImageView mLogoView;
		..........
    };`


使用和创建Toolbar之前我们需要知道:使用的v7包,相应的需要设置appcompat的主题:

 相当于是ACtionbar的升级,使用的时候需要设置系统的actionbar和tittlebar的不显示和第三方的actionbar和notittle设置为true

创建一个自己的theme主题

      <!--自定义自己的主题目的就是使用 toolbat  第一系统的actionbar和tittlebar还有第三方的-->
    <style name="MyToolbartheme" parent="Theme.AppCompat.Light.DarkActionBar">

        <!--自定义的一个处理系统toolbar和tittlebar的样式-->
        <item name="android:windowActionBar">false</item>
        <item name="android:windowNoTitle">true</item>

        <!--设置处理第三方的actionbar和toolbar-->
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>



        <!-- Customize your theme here.就是自定义的颜色 -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimary</item>
        <item name="colorAccent">@color/colorAccent</item>

        <!--设置最顶部的颜色,要求就是api在21 以上,运行的时候注意api的版本-->
        <item name="android:colorPrimaryDark">@color/colorAccent</item>

        <!--设置的就是默认的窗体的颜色-->
        <item name="android:windowBackground">@color/ceshi</item>
        
    </style>

使用自己的toolbar主题:不定义colorprimary会根据Dark的色调,这里你自己定义的coloprimary颜色就是你想要展示的颜色;下面对系统主题里面的颜色进行相应的解释:


 colorPrimaryDark是我们手机最顶端的状态栏的背景颜色（改变它需要Android5.0以及以上的手机支持才行）。

 colorPrimary是指导航栏的颜色。

 colorAccent是指我们常用控件比如Button等的颜色。

 textColorPrimary是指我们导航栏中标题的颜色。

 windowBackground是指我们窗体的默认颜色。

 navigationBarColor是指Android手机中虚拟按键的背景颜色。

![图片的展示](http://i.imgur.com/hTB7HIC.png)

    

    <?xml version="1.0" encoding="utf-8"?>
	<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.kunstudy.toolbardemo">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/MyToolbartheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>

                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
    </application>

	</manifest>

在application中使用,会在以后创建的所有的activity中都有效果;如果后面的activity需要其他的主题,在清单文件中声明activity的时候后面再次设置一个主题就ok了!


开始创建独立toolbar控件布局:

    <?xml version="1.0" encoding="utf-8"?>
	<android.support.v7.widget.Toolbar
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/tb_toolbar"
    android:layout_width="match_parent"
    android:layout_height="@dimen/height_toolbar"
    android:background="?attr/colorPrimary"
    android:minHeight="?actionBarSize"
    app:navigationIcon="@mipmap/icon_back"
    app:titleTextColor="@color/white">

    <TextView
        android:id="@+id/tv_toolbar_title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:drawablePadding="@dimen/dist_2"
        android:gravity="center"
        android:lines="1"
        android:textColor="@color/white"
        android:textSize="@dimen/textSize_title"/>

    <TextView
        android:id="@+id/tv_toolbar_right_text"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="right"
        android:layout_marginRight="@dimen/dist_10"
        android:background="?android:attr/selectableItemBackground"
        android:padding="@dimen/dist_10"
        android:textColor="@color/white"
        android:textSize="@dimen/textSize_content"
        android:visibility="gone"/>

	</android.support.v7.widget.Toolbar>

设置了toolbar的高度和系统系统设置的actionbar的高度一致,设置了toolbar的颜色使用系统定义的colorprimary颜色,设置了toolbar的左边的图片.我们首先依赖到一个布局看看效果:

图片的展示区:

![第一次运行的效果图](http://i.imgur.com/fAvZqA2.png)


之后的操作就是获取里面的控件进行设置相应的逻辑,我们这里使用viewstub进行按需加载的设置:

简单的介绍一下子ViewStub(直接继承View)


    public final class ViewStub extends View {
    private int mInflatedId;
    private int mLayoutResource;

    private WeakReference<View> mInflatedViewRef;

    private LayoutInflater mInflater;
    private OnInflateListener mInflateListener;

	'''''''其他代码
	}

ViewStub 是一个不可见的，大小为0的View，最佳用途就是实现View的延迟加载，避免资源浪费，在需要的时候才加载View;
 首先来说说ViewStub的一些特点：

   1. ViewStub只能Inflate一次，之后ViewStub对象会被置为空。按句话说，某个被ViewStub指定的布局被Inflate后，就不会够再通过ViewStub来控制它了。

   2. ViewStub只能用来Inflate一个布局文件，而不是某个具体的View，当然也可以把View写在某个布局文件中。

我们今天先在主布局中实现viewstub

    <?xml version="1.0" encoding="utf-8"?>
	<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    >
  	 <!--
    <include
        android:id="@+id/tb_mian"
        layout="@layout/toolbar">
    </include>-->
    <!--使用Viewstub来实现按需加载-->
    <ViewStub
        android:layout="@layout/toolbar"
        android:id="@+id/vb_mian_show"
        android:layout_width="match_parent"
        android:layout_height="?actionBarSize"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"/>
	</LinearLayout>

代码中的实现:
 
    public class MainActivity extends AppCompatActivity {

    private ViewStub vb_mian_show;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLoading = true;

        //  按需加载的实现

        vb_mian_show = (ViewStub) findViewById(R.id.vb_mian_show);
        //设置
        if (isLoading){
            vb_mian_show.inflate();
        }


    }
	}
 
最后的结果第一次使用include的效果是一致的.

## 自定义Toolbar的实现 ##
获取我们会说这还不如到时候我直接隐藏actionbar和toolbar之后直接设置一个布局在上面,我们可以自定义toolbar
 
下面我们就开始自定义toolbar的实现:

创建一个类继承Toolbar,重写三个构造方法,开始关联三个方法,这三个构造方法的第一个构造方法是直接new的时候使用,第二个方法是在布局中是使用会走这个方法,第三个很明显是有style的;

参考博客:[http://blog.csdn.net/wuyinlei/article/details/50099259](http://blog.csdn.net/wuyinlei/article/details/50099259 "参考的博客地址")


直接将源码贴出:

    

	public class CustomToolbar extends Toolbar {
    //添加布局必不可少的工具
    private LayoutInflater mInflater;

    //搜索框
    private EditText mEditSearchView;
    //标题
    private TextView mTextTitle;
    //右边按钮
    private ImageButton mRightButton;
    //左边按钮
    private ImageButton mLeftButton;

    private View mView;

    public CustomToolbar(Context context) {
        this(context, null);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //  开始初始化
        initView();
        // 获取属性值
        //Set the content insets for this toolbar relative to layout direction(方向).
        setContentInsetsRelative(10, 10);

        if (attrs != null) {
            //读写自定义的属性，如果不会自己写的时候，就看看官方文档是怎么写的哈
            /**
             * 下面是摘自官方文档
             * final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
             R.styleable.Toolbar, defStyleAttr, 0);

             mTitleTextAppearance = a.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
             mSubtitleTextAppearance = a.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
             mGravity = a.getInteger(R.styleable.Toolbar_android_gravity, mGravity);
             mButtonGravity = Gravity.TOP;
             mTitleMarginStart = mTitleMarginEnd = mTitleMarginTop = mTitleMarginBottom =
             a.getDimensionPixelOffset(R.styleable.Toolbar_titleMargins, 0);

             final int marginStart = a.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginStart, -1);
             if (marginStart >= 0) {
             mTitleMarginStart = marginStart;
             }

             *
             */
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.LetToolBar, defStyleAttr, 0);

            final Drawable leftIcon = a.getDrawable(R.styleable.LetToolBar_leftButtonIcon);
            if (leftIcon != null) {
                setLeftButtonIcon(leftIcon);
            }

            final Drawable rightIcon = a.getDrawable(R.styleable.LetToolBar_rightButtonIcon);
            if (rightIcon != null) {
                setRightButtonIcon(rightIcon);
            }


            boolean isShowSearchView = a.getBoolean(R.styleable.LetToolBar_isShowSearchView, false);

            //如果要显示searchView的时候
            if (isShowSearchView) {
                showSearchView();
                hideTitleView();
            }
            //资源的回收
            a.recycle();
        }
    }

    private void initView() {
        if (mView == null) {
            //初始化
            mInflater = LayoutInflater.from(getContext());
            //添加布局文件
            mView = mInflater.inflate(R.layout.custom_toolbar, null);

            //绑定控件
            //绑定控件
            mEditSearchView = (EditText) mView.findViewById(R.id.toolbar_searchview);
            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mLeftButton = (ImageButton) mView.findViewById(R.id.toolbar_leftButton);
            mRightButton = (ImageButton) mView.findViewById(R.id.toolbar_rightButton);


            //然后使用LayoutParams把控件添加到子view中
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);

        }
    }
    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if (mTextTitle != null) {
            mTextTitle.setText(title);
            showTitleView();
        }
    }

    //隐藏标题
    public void hideTitleView() {
        if (mTextTitle != null)
            mTextTitle.setVisibility(GONE);
    }

    //显示标题
    public void showTitleView() {
        if (mTextTitle != null)
            mTextTitle.setVisibility(VISIBLE);
    }

    //显示搜索框
    public void showSearchView() {
        if (mEditSearchView != null)
            mEditSearchView.setVisibility(VISIBLE);
    }

    //隐藏搜索框
    public void hideSearchView() {
        if (mEditSearchView != null) {
            mEditSearchView.setVisibility(GONE);
        }
    }

    //给右侧按钮设置图片，也可以在布局文件中直接引入
    // 如：app:leftButtonIcon="@drawable/icon_back_32px"
    public void setRightButtonIcon(Drawable icon) {
        if (mRightButton != null) {
            mRightButton.setImageDrawable(icon);
            mRightButton.setVisibility(VISIBLE);
        }
    }

    //给左侧按钮设置图片，也可以在布局文件中直接引入
    private void setLeftButtonIcon(Drawable icon) {
        if (mLeftButton != null){
            mLeftButton.setImageDrawable(icon);
            mLeftButton.setVisibility(VISIBLE);
        }
    }

    //设置右侧按钮监听事件
    public void setRightButtonOnClickLinster(OnClickListener linster) {
        mRightButton.setOnClickListener(linster);
    }

    //设置左侧按钮监听事件
    public void setLeftButtonOnClickLinster(OnClickListener linster) {
        mLeftButton.setOnClickListener(linster);
    }
	}

需要的自定义的属性和样式和资源文件和布局:

 布局:custom_toolbar

    <?xml version="1.0" encoding="utf-8"?>

	<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="wrap_content">

    <ImageButton
        android:id="@+id/toolbar_leftButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentLeft="true"
        android:layout_centerVertical="true"
        android:textColor="@color/white"
        android:visibility="gone"
        style="@android:style/Widget.Material.Toolbar.Button.Navigation"
        />
    <EditText
        android:id="@+id/toolbar_searchview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_centerVertical="true"
        android:gravity="center"
        android:drawableLeft="@mipmap/icon_search"
        style="@style/search_view"
        android:hint="请输入搜索内容"

        android:visibility="gone"
        />

    <TextView
        android:id="@+id/toolbar_title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:layout_gravity="center"
        android:gravity="center"
        android:textColor="@color/white"
        android:textSize="20sp"
        android:visibility="gone"
        />

    <ImageButton
        android:id="@+id/toolbar_rightButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_centerVertical="true"
        android:textColor="@color/white"
        android:visibility="gone"
        style="@android:style/Widget.Material.Toolbar.Button.Navigation"
        />

	</RelativeLayout>

自定义属性(新建attrs资源文件):

    <?xml version="1.0" encoding="utf-8"?>
	<resources>
        <declare-styleable name="LetToolBar">
            <attr name="leftButtonIcon" format="reference"/>
            <attr name="rightButtonIcon" format="reference"/>
            <attr name="isShowSearchView" format="boolean"/>
        </declare-styleable>
	</resources>

搜索框选中的颜色变化(drawable下面):命名: selector_search_view.xml
    
    <?xml version="1.0" encoding="utf-8"?>
	<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_enabled="false">
        <shape android:shape="rectangle">
            <corners android:radius="8dp"/>
            <solid android:color="#eb4f38"/>
        </shape>
    </item>
    <item android:state_pressed="true">
        <shape android:shape="rectangle">
            <corners android:radius="8dp"/>
            <solid android:color="#eb4f38"/>
        </shape>
    </item>
    <item>
        <shape android:shape="rectangle">
            <corners android:radius="8dp"/>
            <solid android:color="#D82917"/>
        </shape>
    </item>

	</selector>

使用的search_view的样式:

     <!--自定义toolbar的样式-->
    <style name="search_view">
        <item name="android:textSize">18sp</item>
        <item name="android:textColor">@color/white</item>
        <item name="android:textColorHint">@color/white</item>
        <item name="android:background">@drawable/selector_search_view</item>
        <item name="android:paddingTop">6dp</item>
        <item name="android:paddingBottom">6dp</item>
        <item name="android:paddingLeft">4dp</item>
        <item name="android:paddingRight">4dp</item>
        <item name="android:singleLine">true</item>
    </style>

需要的图片,文档最后会上传demo都github上.请移步下载.

布局中的使用(布局中直接使用):

    
    <com.kunstudy.toolbardemo.view.CustomToolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:layout_gravity="center"
        android:background="?attr/colorPrimary"
        android:minHeight="?attr/actionBarSize"
        app:leftButtonIcon="@mipmap/icon_back"
        app:rightButtonIcon="@mipmap/icon_right_64"
        app:title="第二个页面"
        app:isShowSearchView="false"
        >
	</com.kunstudy.toolbardemo.view.CustomToolbar>


代码控制:

     //绑定这个控件就行了哈
        mToolBar = (CustomToolbar) findViewById(R.id.toolbar);

        //当然也可以调用里面的各种方法，来实现你想要的搜索框还是按钮和标题，当然也可以在布局文件中直接实现，是不是很简单呢
         mToolBar.hideTitleView();
        mToolBar.showSearchView();

里面还有很多的方法可以使用设置,上面的是展示搜索框的设置.

 
## 实现沉浸式 ##
https://www.virtualbox.org/wiki/Downloads(虚拟机的下载地址)

沉浸式的实现是在api 19的之后才开始支持的:

第一种方法就是依赖jar包:

	//状态栏
	compile 'com.readystatesoftware.systembartint:systembartint:1.0.3'

之后就是在使用的页面布局的根布局设置下面属性

	 android:clipToPadding="true"
    android:fitsSystemWindows="true"

上面的两个属性的说明:

第一个属性是设置设置为true,clipToPadding:控件的绘制区域是否在padding里面, 值为true时padding那么绘制的区域就不包括padding区域; 
第二个属性:作用就是你的contentview是否忽略actionbar,title,屏幕的底部虚拟按键，将整个屏幕当作可用的空间。
正常情况，contentview可用的空间是去除了actionbar,title,底部按键的空间后剩余的可用区域；这个属性设置为true,则忽略，false则不忽略

代码设置:

    
    private void initSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            //修改window的综合属性flags
            //WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS含义为状态栏透明
            winParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            win.setAttributes(winParams);
        }
        //调用开源库SystemBarTintManager进行状态栏着色 产生沉浸式效果
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);//使用状态栏着色可用
        tintManager.setStatusBarTintColor(Color.GREEN);//指定颜色进行着色
    }

github上集成好的:

[https://github.com/gyf-dev/ImmersionBar](https://github.com/gyf-dev/ImmersionBar "集成好的")

再次说一下子,实在是太棒了!


## 基于6.0的权限的获取 ##

参考学习的博客地址:

Android6.0之后的权限分为两大块,分别是normal permissions 和Dangeroous Permission 正常的权限是不涉及到用户的隐私的,例如手机联网,蓝牙,NFC的权限;复制博客上的分类:

    ACCESS_LOCATION_EXTRA_COMMANDS
	ACCESS_NETWORK_STATE
	ACCESS_NOTIFICATION_POLICY
	ACCESS_WIFI_STATE
	UETOOTH
	BLUETOOTH_ADMIN
	BROADCAST_STICKY
	CHANGE_NETWORK_STATE
	CHANGE_WIFI_MULTICAST_STATE
	HANGE_WIFI_STATE
	DISABLE_KEYGUARD
	EXPAND_STATUS_BAR
	GET_PACKAGE_SIZE
	INSTALL_SHORTCUT
	NTERNET
	KILL_BACKGROUND_PROCESSES
	MODIFY_AUDIO_SETTINGS
	NFC
	READ_SYNC_SETTINGS
	READ_SYNC_STATS
	RECEIVE_BOOT_COMPLETED
	REORDER_TASKS
	REQUEST_INSTALL_PACKAGES
	SET_ALARM
	SET_TIME_ZONE
	SET_WALLPAPER
	SET_WALLPAPER_HINTS
	TRANSMIT_IR
	UNINSTALL_SHORTCUT
	USE_FINGERPRINT
	VIBRATE
	WAKE_LOCK
	WRITE_SYNC_SETTINGS

危险的权限:

    group:android.permission-group.CONTACTS  //   注意group  contacts 连接

  	permission:android.permission.WRITE_CONTACTS
  	permission:android.permission.GET_ACCOUNTS
  	permission:android.permission.READ_CONTACTS

	group:android.permission-group.PHONE  //  注意  phone 电话
  	permission:android.permission.READ_CALL_LOG
 	permission:android.permission.READ_PHONE_STATE
  	permission:android.permission.CALL_PHONE
 	permission:android.permission.WRITE_CALL_LOG
  	permission:android.permission.USE_SIP
  	permission:android.permission.PROCESS_OUTGOING_CALLS
  	permission:com.android.voicemail.permission.ADD_VOICEMAIL

	group:android.permission-group.CALENDAR  //  注意  calendar  日历
  	permission:android.permission.READ_CALENDAR
  	permission:android.permission.WRITE_CALENDAR

	group:android.permission-group.CAMERA  // 注意 camera
  	permission:android.permission.CAMERA

	group:android.permission-group.SENSORS   //注意  sensors
  	permission:android.permission.BODY_SENSORS

	group:android.permission-group.LOCATION   //  location 定位相关的
  	permission:android.permission.ACCESS_FINE_LOCATION
  	permission:android.permission.ACCESS_COARSE_LOCATION

	group:android.permission-group.STORAGE    // storage 存贮保存数据相关的
  	permission:android.permission.READ_EXTERNAL_STORAGE
  	permission:android.permission.WRITE_EXTERNAL_STORAGE

	group:android.permission-group.MICROPHONE  // microphone 音频
  	permission:android.permission.RECORD_AUDIO

	group:android.permission-group.SMS    // sms 短信
  	permission:android.permission.READ_SMS
  	permission:android.permission.RECEIVE_WAP_PUSH
  	permission:android.permission.RECEIVE_MMS
  	permission:android.permission.RECEIVE_SMS
  	permission:android.permission.SEND_SMS
  	permission:android.permission.READ_CELL_BROADCASTS

### Android6.0的授权机制 ###

app运行在Android 6.0或者是之上的手机上,授权机制是这样的,首先是在清单文件中声明权限,如果是Normal 的权限会和以前一样,不同点就是危险的权限的获取上面,我们在上面可以看出危险的程序是分组的,我们一 sms这一组的权限来说明,如果我们的app已经获取到了sms组中的例如 读取sms的权限,此时我们需要的是发送短信的权限,那么这个权限会立即授予我们,这组里面的其他权限也是如此,但是要是我们没有获取到这一组的任何一个期限,我们需要申请动态的申请权限.

步凑简介:
	
1. 第一在清单文件中申明需要的权限   // 和以前一样
2. 检查是否获取到权限

	![动态获取权限](http://i.imgur.com/NLgAet2.png)

我们可以看出,没有获取权限的返回值是 -1也就是 Packmanger.PERMISSION_GRANTED的值,是定义好的.

3.动态的获取权限

    // 参数说明:第一个上下文,第二个是权限名称,可以是多个,最后一个是自己定义的一个响应码和后面的设备获取权限的回调有关
    // 此时要动态的获取权限  注意防范名 可以同时提取多个权限
            ActivityCompat.requestPermissions(SecondActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

回调权限的获取

验证requestCode定位到你的申请，然后验证grantResults对应于申请的结果，这里的数组对应于申请时的第二个权限字符串数组。如果你同时申请两个权限，那么grantResults的length就为2，分别记录你两个权限的申请结果。

	 @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.  如果请求关闭了,那这个数据是空的
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d(TAG, "checkPerssion: 检查是否获取权限的返回值"+"=====================" +ContextCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS));
                } else {

                    // permission denied, boo! Disable the     没有获取到权限
                    // functionality that depends on this permission.


                }
                return;
            }
        }
    }
    
   
4.ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
        Manifest.permission.READ_CONTACTS);
这个API主要用于给用户一个申请权限的解释，该方法只有在用户在上一次已经拒绝过你的这个权限申请。也就是说，用户已经拒绝一次了，你又弹个授权框，你需要给用户一个解释，为什么要授权，则使用该方法。 

### 最后总的步骤和封装 ###



    // Here, thisActivity is the current activity
	if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.需要的权限)
        != PackageManager.PERMISSION_GRANTED) {

    // Should we show an explanation?
    if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
            Manifest.permission.需要的权限)) {

        // Show an expanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.

    } else {

        // No explanation needed, we can request the permission.

        ActivityCompat.requestPermissions(thisActivity,
                new String[]{Manifest.permission.READ_CONTACTS},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS(自己定义的响应码));

        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
    }
	}

封装:

github上关于权限的一个 
[https://github.com/hotchemi/PermissionsDispatcher](https://github.com/hotchemi/PermissionsDispatcher "statr最多的")

鸿洋的:
[https://github.com/hongyangAndroid/MPermissions](https://github.com/hongyangAndroid/MPermissions)













 




















