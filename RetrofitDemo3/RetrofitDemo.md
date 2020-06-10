## Retrofit+Coroutine使用

### 准备工作
1. 官网文档 [https://square.github.io/retrofit/](https://square.github.io/retrofit/)
2. 模拟json数据 [http://jsonplaceholder.typicode.com](http://jsonplaceholder.typicode.com)


### 申请网络权限
打开`app/manifests/AndroidManifest.xml`		
在`<application>`的同级添加			

```
<uses-permission android:name="android.permission.INTERNET"/>
```

### 添加依赖库
```
def retrofit_version = "2.6.0"
implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7"
```

### 使用

操作步骤:
		
* 新建数据模型类	
* 新建数据请求接口
* 新建Retrofit Service
* 发送请求

具体见如下:

```
package cn.xizhipian.retrofitdemo3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// 步骤1:新建数据模型类
data class Post(val userId:Int, val id:Int, val title:String, val body:String)

// 步骤2:新建Api接口, 添加相关方法
interface Api {
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @GET("posts")
    suspend fun suspendGetPosts(): List<Post>
}

class MainActivity : AppCompatActivity() {

    // 步骤3: 新建服务
    private val apiService = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 步骤4:使用
        // 方式一：传统的Callback方式
//        apiService.getPosts().enqueue(object : Callback<List<Post>> {
//            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
//                Log.e("xxx", "$t")
//            }
//
//            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
//                if (response.isSuccessful) {
//                    val posts = response.body()
//                    if (posts != null) {
//                        for (post in posts) {
//                            Log.d("xxx", post.toString())
//                        }
//                    }
//                }
//            }
//        })
//        // 方式2：Call<XX> + Coroutine
//        GlobalScope.launch {
//            val response = apiService.getPosts().awaitResponse()
//            if (response.isSuccessful) {
//                val posts = response.body()
//                if (posts != null) {
//                    for (post in posts) {
//                        Log.d("xxx", post.toString())
//                    }
//                }
//            }
//        }

        // 方式3：suspend + Coroutine
        GlobalScope.launch {
            val posts = apiService.suspendGetPosts()
            for (post in posts) {
                Log.d("xxx", post.toString())
            }
        }
    }
}
```

示例中有三种打开方式:

1. 延续原有的Call<Type>返回类型, 通过onQueue发送请求
2. 延续原有的Call<Type>, 使用await(),awaitResponse()得到结果
3. 使用suspend方法, 真协程方式

### 将结果显示在TextView却崩溃了
```
        button.setOnClickListener {
            GlobalScope.launch {
                val posts = apiService.suspendGetPosts()

                val text = posts.joinToString("\n") { it.toString() }
                withContext(Dispatchers.Main) {
                    Log.d("xxx", "${Thread.currentThread().name}")
                    textView.text = text
                }
            }
        }
```
前面都是直接输出, 学习coroutine时知道使用withContext(Dispatchers.Main) {} 可以切换回主线程, 然而真正使用却崩溃了,但是没有看到原因, 搜索了一番找到了答案.

Q. 看不到错误, 但是却崩溃了		
A: Filters选择No Filter, 神奇的选择`Show only selected Application`看不到错误

Q. 怎么解决崩溃		
A: 引入`implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7"`, 难道coroutine的主线程和android的主线程不同?