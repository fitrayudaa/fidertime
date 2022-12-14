package edu.bluejack22_1.fidertime.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.fidertime.R
import edu.bluejack22_1.fidertime.common.FirebaseQueries
import edu.bluejack22_1.fidertime.databinding.ActivityRegisterBinding
import edu.bluejack22_1.fidertime.models.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvToLogin.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this , LoginActivity::class.java))
        })

        auth = FirebaseAuth.getInstance()

        binding.buttonRegister.setOnClickListener{
            val email = binding.email.text.toString()
            val name = binding.name.text.toString()
            val username = binding.username.text.toString()
            val phoneNumber = binding.phoneNumber.text.toString()
            val password = binding.password.text.toString()

            // EMAIL VALIDATION
            if(email.isEmpty()){
                binding.email.error = getString(R.string.email_null_validation)
                binding.email.requestFocus()
                return@setOnClickListener
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.email.error = getString(R.string.email_not_valid_validation)
                binding.email.requestFocus()
                return@setOnClickListener
            }

            // NAMEf VALIDATION
            if(name.isEmpty()){
                binding.name.error = getString(R.string.name_null_validation)
                binding.name.requestFocus()
                return@setOnClickListener
            }else if(name.length < 5){
                binding.name.error = getString(R.string.name_length_validation)
                binding.name.requestFocus()
                return@setOnClickListener
            }

            if(username.isEmpty()){
                binding.username.error = getString(R.string.name_null_validation)
                binding.username.requestFocus()
                return@setOnClickListener
            }else if(username.length < 5){
                binding.username.error = getString(R.string.name_length_validation)
                binding.username.requestFocus()
                return@setOnClickListener
            }else if(username.contains(" ")) {
                binding.username.error = getString(R.string.name_contains_space_validation)
                binding.username.requestFocus()
                return@setOnClickListener
            }

            // PHONE NUMBER VALIDATION
            if(phoneNumber.isEmpty()){
                binding.phoneNumber.error = getString(R.string.phone_number_null_validation)
                binding.phoneNumber.requestFocus()
                return@setOnClickListener
            }else if(!phoneNumber.startsWith("+62")){
                binding.phoneNumber.error = getString(R.string.phone_number_starts_validation)
                binding.phoneNumber.requestFocus()
                return@setOnClickListener
            }
            // Password Validation
            if(password.isEmpty()){
                binding.password.error = getString(R.string.password_null_validation)
                binding.password.requestFocus()
                return@setOnClickListener
            }else if(password.length < 5){
                binding.password.error = getString(R.string.password_number_length_validation)
                binding.password.requestFocus()
                return@setOnClickListener
            }



            checkDataToFirebase(email , name , username, phoneNumber , password)
        }
    }

    private fun checkDataToFirebase(email : String, name : String, username: String, phoneNumber : String, password : String){
        val db = Firebase.firestore
        db.collection("users")
            .get()
            .addOnSuccessListener{ result ->
                var checkName = false
                var checkPhoneNumber = false
                var checkUsername = false
                for(doc in result){
                    val user = doc.toObject<User>()
                    user.id = doc.id
                    if(user.phoneNumber == phoneNumber){
                        checkPhoneNumber = true
                    }else if(user.name == name){
                        checkName = true
                    }else if(user.username == username) {
                        checkUsername = true
                    }
                }

                if(checkPhoneNumber){
                    Toast.makeText(this , getString(R.string.phone_number_unique_validation) , Toast.LENGTH_SHORT).show()
                }else if(checkName){
                    Toast.makeText(this , getString(R.string.name_unique_validation) , Toast.LENGTH_SHORT).show()
                }else if(checkUsername){
                    Toast.makeText(this , getString(R.string.username_unique_validation) , Toast.LENGTH_SHORT).show()
                }else{
                    registerUser(email , name , phoneNumber , password)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("ERROR USER", "Error getting documents: ", exception)
            }
        }

    private fun registerUser(email : String, name : String, phoneNumber : String, password : String){
        val db = Firebase.firestore
        auth.createUserWithEmailAndPassword(email , password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val id = auth.currentUser?.uid
                    val userData = User()
                    userData.email = email
                    userData.name = name
                    userData.phoneNumber = phoneNumber
                    userData.status = "Online"
                    userData.lastSeenTimestamp = Timestamp.now()

                    db.collection("users").document(id.toString())
                        .set(userData)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(this , getString(R.string.register) , Toast.LENGTH_SHORT).show()
                            val intent = Intent(this , LoginActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this , e.toString() , Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, getString(R.string.authentication_failed),Toast.LENGTH_SHORT).show()
                }
            }
    }
}