package id.ac.ukdw.c_lock


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_list.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {
    lateinit var dbJadwal: DatabaseReference
    lateinit var jadwalAdapter: RecyclerView.Adapter<JadwalAdapter.JadwalHolder>
    lateinit var layoutManager: RecyclerView.LayoutManager
    var list: ArrayList<Jadwal> = ArrayList<Jadwal>()
    var sdf = SimpleDateFormat("yyyyMMdd")
    var curDate = sdf.format(Date())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_list, container, false)
        var recycRuang: RecyclerView = root.findViewById(R.id.recycleRuang)

        jadwalAdapter =JadwalAdapter(list,root.context)
        layoutManager = LinearLayoutManager(root.context)
        recycRuang.adapter = jadwalAdapter
        recycRuang.layoutManager = layoutManager
        recycRuang.setHasFixedSize(true)

        ListData()

        return root
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
                jadwalAdapter.notifyDataSetChanged()
            }
        })
    }

}
