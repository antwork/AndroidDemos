package cn.xizhipian.topmenudemo

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    val TAG:String
        get() = this::class.java.toString()

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

    private fun showActivity2() {
        Intent(this, Activity2::class.java).also {
            startActivity(it)
        }
    }

    private fun exit() {
        finish()
    }

}