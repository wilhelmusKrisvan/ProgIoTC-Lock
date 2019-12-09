package id.ac.ukdw.c_lock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.active_home.*
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {
    lateinit var dbJadwal: DatabaseReference
    var sdf = SimpleDateFormat("yyyyMMdd")
    var curDate = sdf.format(Date())
    var list: ArrayList<Jadwal> = ArrayList<Jadwal>()
    var jadwalAdapter =JadwalAdapter(list,this)
    var layoutManager = LinearLayoutManager(this)
    lateinit var name: String
    var email:String = FirebaseAuth.getInstance().currentUser?.email.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.active_home)

        recycleRuang.adapter = jadwalAdapter
        recycleRuang.layoutManager = layoutManager
        recycleRuang.setHasFixedSize(true)

        txtUsername.text = "Hello, ${GetName(email)}"

        ListData()

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var i: Intent = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
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

    fun ListData(){
        dbJadwal = FirebaseDatabase.getInstance().getReference("Booking/$curDate")
        dbJadwal.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {
                var child = data.children
                child.forEach {
                    val jadwal = it.getValue(Jadwal::class.java)
                    if(jadwal!!.uid.equals(FirebaseAuth.getInstance().currentUser?.uid.toString())){
                        list.add(jadwal!!)
                    }
                }
            }
        })
        jadwalAdapter.notifyDataSetChanged()
    }
}
