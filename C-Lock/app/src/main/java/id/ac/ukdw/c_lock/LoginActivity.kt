package id.ac.ukdw.c_lock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        btnLogin.setOnClickListener {
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(txtEmail.text.toString(), txtPass.text.toString())
                .addOnSuccessListener  {
                    Toast.makeText(baseContext, "berhasil login", Toast.LENGTH_LONG).show()
                    val i: Intent = Intent(baseContext, HomeActivity::class.java)
                    startActivity(i)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(baseContext, "gagal login", Toast.LENGTH_LONG).show()
                }
        }
    }


}
