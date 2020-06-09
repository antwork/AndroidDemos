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