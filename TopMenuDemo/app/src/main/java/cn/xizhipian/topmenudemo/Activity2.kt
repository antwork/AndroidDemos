package cn.xizhipian.topmenudemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class Activity2 : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        Log.d(TAG, "from Activity2")
    }
}