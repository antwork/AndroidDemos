## Menu使用
> 官网参考文档: [https://developer.android.com/guide/topics/resources/menu-resource](https://developer.android.com/guide/topics/resources/menu-resource)

#### 步骤一: 检测文件夹存在:`res/menu`

假设res文件夹下存在`menu`文件夹, 则跳过		
否则右击res文件夹 - New - Android Resources Directory - Resource Type中选择Menu, 单击OK

#### 步骤二: 创建Menu

右击刚创建的menu文件夹 - New - Menu Resource File - 设置File Name - OK

编辑menu, 添加items

```
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <item
        android:id="@+id/miSetting"
        android:title="Settings"
        android:icon="@drawable/ic_settings"
        app:showAsAction="always" />
    <item
        android:id="@+id/miCloud"
        android:title="Clouds"
        android:icon="@drawable/ic_cloud"
        app:showAsAction="ifRoom" />
    <item
        android:id="@+id/miProfile"
        android:title="Profile"
        app:showAsAction="never" />
    <item
        android:id="@+id/miPhone"
        android:title="Phone"
        app:showAsAction="never" />
</menu>
```

showAsAction  | 说明
------------- | -------------
ifRoom	| 只有在应用栏中有空间的情况下，才将此项放置其中。如果没有足够的空间来容纳标记为 "ifRoom" 的所有项，则 orderInCategory 值最低的项会显示为操作，其余项将显示在溢出菜单中。
withText	 | 此外，还会随操作项添加标题文本（由 android:title 定义）。您可以将此值与某个其他值一起作为标记集添加，用竖线 `|` 分隔。
never	| 不得将此项放在应用栏中，而应将其列在应用栏的溢出菜单中。
always	| 始终将此项放在应用栏中。除非此项必须始终显示在操作栏中，否则请勿使用该值。将多个项设置为始终显示为操作项，会导致它们与应用栏中的其他界面重叠。
collapseActionView	| 与此操作项相关联的操作视图（由 android:actionLayout 或 android:actionViewClass 声明）是可收起的。在 API 级别 14 中引入。

#### 步骤三: 加入Activity

* 重写Activity的onCreatPanelMenu方法关联menu, 返回true表示已经创建
* 重写onOptionsItemSelected 响应选择Menu

```
    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.miCloud -> showActivity2()
            R.id.miSetting -> Toast.makeText(this, "Select Settings", Toast.LENGTH_SHORT).show()
            R.id.miProfile -> Toast.makeText(this, "Select Profile", Toast.LENGTH_SHORT).show()
            R.id.miPhone -> exit()
            else -> throw IllegalAccessException("无效的id")
        }
        return true
    }
```

### 其他思考
Q: 怎么让menu全局使用?

A: 创建 `open class BaseActivity: AppCompactActivity() {}`, 在其中重写onCreatePanelMenu和onOptionSelect方法

Q: Log.d("Tag", xxxx) 的Tag是否也可以在基类中控制, 避免每个Activity都要自己写

A: 		

```
val atTag:String
        get() = this::class.java.toString()
```