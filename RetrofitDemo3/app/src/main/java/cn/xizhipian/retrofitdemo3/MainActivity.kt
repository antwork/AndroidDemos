package cn.xizhipian.retrofitdemo3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
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
//        GlobalScope.launch {
//            val posts = apiService.suspendGetPosts()
////            for (post in posts) {
////                Log.d("xxx", post.toString())
////            }
//        }

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
    }
}