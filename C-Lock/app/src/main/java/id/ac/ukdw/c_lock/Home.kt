package id.ac.ukdw.c_lock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_home)

        btn_scanaja()
    }

    private fun btn_scanaja() {
        btn_scan2.setOnClickListener {
            initScan()
        }
    }

    private fun initScan() {
        IntentIntegrator(this).initiateScan()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if(result.contents==null){
                Toast.makeText(this, "the data is empty", Toast.LENGTH_LONG).show()
            }else{
                var text : String = result.contents.toString()
                Toast.makeText(this, text, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)

        }

    }
}
