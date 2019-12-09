package id.ac.ukdw.c_lock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_count_down.*

class CountDownActivity : AppCompatActivity() {
    lateinit var count: CountDownTimer
    var timeLeft: Long = 5000
    lateinit var status: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        status = intent.getStringExtra("stat")
        if (status.equals("0")){
            txtDefault.text = "PINTU AKAN TERBUKA..."
            startTimer()
        }else{
            txtDefault.text = "PINTU AKAN TERTUTUP..."
            startTimer()
        }
    }

    fun startTimer(){
        count = object : CountDownTimer(timeLeft, 1000){
            override fun onFinish() {
                progressBarCount.visibility = View.GONE
                if(status.equals("0")){
                    Toast.makeText(baseContext, "TERBUKA", Toast.LENGTH_LONG).show()
                    val i: Intent = Intent(baseContext, HomeActivity::class.java)
                    startActivity(i)
                    finish()
                }else{
                    Toast.makeText(baseContext, "TERTUTUP", Toast.LENGTH_LONG).show()
                    val i: Intent = Intent(baseContext, HomeActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                progressBarCount.visibility = View.VISIBLE
                timeLeft  = millisUntilFinished
            }
        }.start()
    }
}
