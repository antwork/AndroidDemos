## Bottom Navigation
> 同`iOS`的`UITabBar`		
> 官网参考:[https://material.io/develop/android/docs/getting-started/](https://material.io/develop/android/docs/getting-started/)

### 添加依赖库
```
    implementation 'com.google.android.material:material:1.3.0-alpha01'
```

### 准备素材
选中res文件夹 -- 选中drawable文件夹 -- 右击 - New - Image Asset - IconType选择ActionBar and Tab Icons - 命名`ic_home` -- Clip Art选择图标 -- 保存

### 创建Menu
选中res文件夹 -- 选中menu文件夹 -- 右击 -- New -- Menu resouces file -- 命名 -- 保存

### 将Menu加入Activity的layout中
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <FrameLayout
        android:id="@+id/flFragment"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintBottom_toTopOf="@+id/bottomNavigationView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNavigationView"
        android:layout_width="match_parent"
        android:layout_height="75dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:menu="@menu/botom_nav_menu" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### 创建Fragments
res/layout中添加`fragment1.xml`

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/txtView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:hint="Fragment1"
        android:textSize="30sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget
```

同样方法创建 `fragment2.xml`

在java/包名 下创建 fragment1.kt和fragment2.kt(与MainActivity同级)

内容参考

```
package cn.xizhipian.bottomnavdemo

import androidx.fragment.app.Fragment

class Fragment1:Fragment(R.layout.fragment1) {
}
```

### 添加关联事件
修改`MainActivity.kt`

```
package cn.xizhipian.bottomnavdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = Fragment1()
        val secondFragment = Fragment2()
        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.imHome -> setCurrentFragment(firstFragment)
                R.id.imProfile -> setCurrentFragment(secondFragment)
                else -> throw IllegalAccessException()
            }
            true
        }

        // 设置Badge
        bottomNavigationView.getOrCreateBadge(R.id.imProfile).apply {
            number = 10
            isVisible = true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}
```