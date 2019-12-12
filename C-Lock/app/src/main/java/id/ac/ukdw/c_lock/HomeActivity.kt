package id.ac.ukdw.c_lock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.active_home.*
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    lateinit var viewflip: ViewFlipper
    lateinit var name: String
    var email:String = FirebaseAuth.getInstance().currentUser?.email.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.active_home)
        var viewpage: ViewPager = findViewById(R.id.viewpager_main)
        viewflip = findViewById(R.id.img_flip)
        viewpage.adapter = PagerAdapter(supportFragmentManager)
        tab_main.setupWithViewPager(viewpage)

        var images: IntArray = intArrayOf(R.drawable.asd, R.drawable.ruang)

        for(image in images){
            FlipImages(image)
        }

        txtUsername.text = "Hello, ${GetName(email)}"

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var i: Intent = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    fun FlipImages(image: Int){
        var imgView: ImageView = ImageView(this)
        imgView.setBackgroundResource(image)

        viewflip.addView(imgView)
        viewflip.flipInterval = 3000
        viewflip.isAutoStart = true

        viewflip.setInAnimation(this, R.anim.righttoleft)
        viewflip.setOutAnimation(this, R.anim.righttoleft)
    }

    fun GetName(email: String):String{
        var tampung:String = email.substring(0,email.indexOf("@"))
        var tampung2:String = ""
        for (i in tampung){
            if (!i.isLetter()){
                tampung2 = tampung2 + " "
            }
            else{
                tampung2 = tampung2 + i
            }
        }
        return tampung2
    }

}
