package id.ac.ukdw.c_lock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)

        val handler: Handler = Handler()
        var anim: Animation = AnimationUtils.loadAnimation(this,R.anim.fadein)
        imgSplash.startAnimation(anim);
        handler.postDelayed(Runnable {
            if(FirebaseAuth.getInstance().currentUser != null){
                startActivity(Intent(applicationContext, HomeActivity::class.java))
                finish()
            }else{
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            }
        }, 1000)
    }

}
