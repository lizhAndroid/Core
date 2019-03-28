# Core

## 整理的基本Android开发框架，其中有几个部分是来源于开源框架

1.BaseAdapter 来自 https://github.com/CymChad/BaseRecyclerViewAdapterHelper  
2.PhotoView 来自 https://github.com/chrisbanes/PhotoView  
3.WheelPicker 来自 https://github.com/AigeStudio/WheelPicker  
4.zxing 基于https://github.com/zxing/zxing，其中部分代码来自网络，年代久远已不可考    

### 感谢以上优秀第三方，如有侵犯，联系我删除

> 下面是一些基本用法：
> 首先在application中调用**Core.init**初始化，**ActivityStackUtil**基于这个操作可以管理所有activity和fragment。  
>  这个注册方法如果传入实现了**IToolbar**的类，则会自动为界面添加toolbar布局，传入**IBaseListSetup**的实现类可以添加部分BaseListXX的方法  
> **ActivityStackUtil**可以关闭指定activity、关闭除某个activity以外所有activity，往activity或fragment实现了IEvent的方法传递值  
> **LinkMovementMethodReplacement**可以配合**TextColorBuilder**做富文本点击事件  
> **BaseActivity**、**BaseDialog**、**BaseDialogFragment**、**BaseFragment**、**BasePopupWindow**定义了一些基本的方法，BaseActivity和BaseFragment自带**权限管理、跳转传值和取值**方法  
> **BaseListActivity**、**BaseListFragment**是BaseListXX系列，统一实现了一些列表方法，**刷新、下拉**等自动封装，与**BaseAdapter**配合使用  
> **BaseRefreshableActivity**、**BaseRefreshableFragment**是**带刷新**的BaseActivity和BaseFragment  
> **BaseWebFragment**实现了一个webview的基本（最佳？）使用方案，可以直接套用  
> **FragmentShellActivity**是一个可以直接套入Fragment的activity  
> *StatusBarPlaceholder*的高度总是等于状态栏高度，可以用来在沉浸的时候占位  
> **TitleCompat**实现了状态栏沉浸的绝大多数方法  
> **DuBanner**是一个基于recyclerview的轮播控件，oldsdk分支没有   

还有更多功能没写出来，基本就是这些   
