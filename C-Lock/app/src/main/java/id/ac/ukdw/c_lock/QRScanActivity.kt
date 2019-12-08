package id.ac.ukdw.c_lock

import android.Manifest
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

    }

    fun ChangeData(rid: String, status: String){
        db = FirebaseDatabase.getInstance().getReference("Booking/20191202/$rid")
        if(status.equals("0")){
            db.child("status").setValue("1")
                .addOnSuccessListener {
                    Toast.makeText(baseContext, "TERBUKA", Toast.LENGTH_LONG).show()
            }
        }else{
            db.child("status").setValue("0")
                .addOnSuccessListener {
                Toast.makeText(baseContext, "TERTUTUP", Toast.LENGTH_LONG).show()
            }
        }
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
        txtValue.text = rawResult?.text
        if(txtValue.text.toString().equals(rid)){
            btnAction.visibility= View.VISIBLE
            if(status.equals("0")){
                btnAction.text = "BUKA"
            }else{
                btnAction.text = "TUTUP"
            }
        }
        btnReset.visibility = View.VISIBLE
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnReset -> {
                mScannerView.resumeCameraPreview(this)
                initDefaultView()
            }
            R.id.btnAction -> {
                ChangeData(rid, status)
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }

}
