package id.ac.ukdw.c_lock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.active_home.*
import java.sql.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {
    lateinit var dbJadwal: DatabaseReference
    var sdf = SimpleDateFormat("yyyyMMdd")
    var curDate = sdf.format(Date())
    var list: ArrayList<Jadwal> = ArrayList<Jadwal>()
    var jadwalAdapter = JadwalAdapter(list,this)
    var layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.active_home)
        txtUsername.text = "Hello, ${FirebaseAuth.getInstance().currentUser?.email.toString()}"
        ListData()
        val handler: Handler = Handler()
            handler.postDelayed(Runnable {
            recycleRuang.adapter = jadwalAdapter
            recycleRuang.layoutManager = layoutManager
            recycleRuang.setHasFixedSize(true)
                print("terpanggil")
        }, 5000)



        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var i: Intent = Intent(this, LoginActivity::class.java)
            startActivity(i)
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

    fun ListData(){
        dbJadwal = FirebaseDatabase.getInstance().getReference("Booking")
        dbJadwal.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {
                var child = data.children
                child.forEach {
                    var child2 = it.children
                    child2.forEach {
                        val jadwal = it.getValue(Jadwal::class.java)
                        if(jadwal!!.uid.equals(FirebaseAuth.getInstance().currentUser?.uid.toString())){
                            print("=================================================================")
                            println(jadwal.rid)
                            list.add(jadwal!!)
                        }
                    }
                }
            }
        })
    }
}
