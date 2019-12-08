package id.ac.ukdw.c_lock

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class JadwalAdapter(val list:ArrayList<Jadwal>, val context: Context) : RecyclerView.Adapter<JadwalAdapter.JadwalHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JadwalHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_kelas, parent, false)
        return JadwalHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: JadwalHolder, position: Int) {
        val jadwal = list.get(position)
        holder.id!!.text = jadwal.rid
        holder.ruang!!.text = jadwal.lab
        holder.jadwal!!.text = "Lab : ${jadwal.waktu}, jam ${jadwal.jamStart} - ${jadwal.jamEnd}"
        if(jadwal.status.equals("0")){
            holder.btnTutup!!.isClickable = false
            holder.btnTutup!!.visibility = View.INVISIBLE
            holder.btnBuka!!.setOnClickListener {
                val i : Intent = Intent(context, QRScanActivity::class.java)
                i.putExtra("rid", jadwal.rid)
                i.putExtra("stat", "1")
                context.startActivity(i)
            }
        } else if(jadwal.status.equals("1")){
            holder.btnBuka!!.isClickable = false
            holder.btnBuka!!.visibility = View.INVISIBLE
            holder.btnTutup!!.setOnClickListener {
                val i : Intent = Intent(context, QRScanActivity::class.java)
                i.putExtra("rid", jadwal.rid)
                i.putExtra("stat", "0")
                context.startActivity(i)
            }
        }
    }

    class JadwalHolder(val view: View): RecyclerView.ViewHolder(view){
        var id : TextView? = null
        var ruang : TextView? = null
        var jadwal : TextView? = null
        var btnBuka : Button? = null
        var btnTutup : Button? = null

        init{
            id = view.findViewById(R.id.txtBook) as TextView
            ruang = view.findViewById(R.id.txtRuang) as TextView
            jadwal = view.findViewById(R.id.txtRuang) as TextView
            btnBuka = view.findViewById(R.id.btnBuka) as Button
            btnTutup = view.findViewById(R.id.btnTutup) as Button
        }
    }
}