package id.ac.ukdw.c_lock

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_qrscan.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Result as Result1

class QRScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler, View.OnClickListener {
    private lateinit var mScannerView: ZXingScannerView
    lateinit var db: DatabaseReference
    private var isCaptured = false
    var sdf = SimpleDateFormat("yyyyMMdd")
    var curDate = sdf.format(Date())
    lateinit var rid: String
    lateinit var status: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscan)
        rid = intent.getStringExtra("rid")
        status = intent.getStringExtra("stat")
        initScannerView()
        initDefaultView()

        btnReset.setOnClickListener(this)
        btnAction.setOnClickListener {
            db = FirebaseDatabase.getInstance().getReference("Booking/$curDate/$rid/status")
            if(status.equals("0")){
                db.setValue("1")
                    .addOnSuccessListener {
                        val i: Intent = Intent(baseContext, CountDownActivity::class.java)
                        i.putExtra("stat", status)
                        startActivity(i)
                        finish()
                    }
            }else{
                db.setValue("0")
                    .addOnSuccessListener {
                        val i: Intent = Intent(baseContext, CountDownActivity::class.java)
                        i.putExtra("stat", status)
                        startActivity(i)
                        finish()
                    }
            }
        }

    }

    fun ChangeData(rid: String, status: String){

    }

    override fun onStart() {
        mScannerView.startCamera()
        doRequestPermission()
        super.onStart()
    }

    private fun initScannerView() {
        mScannerView = ZXingScannerView(this)
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        frame_layout_camera.addView(mScannerView)
    }

    private fun doRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            100 -> {
                initScannerView()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }

    private fun initDefaultView() {
        txtValue.text = "QR Code Value"
        btnAction.visibility = View.GONE
        btnReset.visibility = View.GONE
    }

    override fun handleResult(rawResult: Result) {
        if(rawResult?.text.equals(rid)){
            txtValue.text = rawResult?.text
            btnAction.visibility= View.VISIBLE
            if(status.equals("0")){
                btnAction.text = "BUKA"
            }else{
                btnAction.text = "TUTUP"
            }
        }
        else{
            txtValue.text = "NOT MATCH"
        }
        btnReset.visibility = View.VISIBLE
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnReset -> {
                mScannerView.resumeCameraPreview(this)
                initDefaultView()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }

}
