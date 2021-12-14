package com.example.wecare_app
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewConfiguration.get
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.wecare_app.databinding.FragmentSettingsBinding
import com.example.wecare_app.databinding.FragmentUpdatePasswordBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.fragment_update_password.*


class updatePassword : Fragment() {

    private lateinit var binding: FragmentUpdatePasswordBinding
    val database = FirebaseFirestore.getInstance()
    var value: String? = ""
    //var pass2 : String? = ""
    //var phone2 : String? = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_update_password,
            container,
            false
        )

        binding.upBtn.setOnClickListener{
            if (binding.currentpass.text.isNotEmpty() && binding.newpass.text.isNotEmpty() && binding.confirmpass.text.isNotEmpty()){
                val userRegisterData = hashMapOf<String, String>(
                "password" to binding.confirmpass.toString()
                )

                val query = database.collection("userRegisterData")
                    .whereEqualTo("phone",binding.phoneR.toString())
                    .get()
                query.addOnSuccessListener {
                    for (document in it )
                    database.collection("userRegisterData"). document(document.id).set(SetOptions.merge())
                }

            }
            else{
                Toast.makeText(activity, "failed!", Toast.LENGTH_LONG).show()
            }
        }

        /*binding.verify.setOnClickListener{

            phone2 = binding.phoneR.text.toString()

            readDatabase(object : updatePassword.FirestoreCallback {
                override fun onCallback() {
                    if(value == phone2){
                        Toast.makeText(activity, "Verified ! ", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(activity, "Try Again ! ", Toast.LENGTH_LONG).show()
                    }
                }
            })

        }*/



        binding.upBtn.setOnClickListener {
          //  if (binding.currentpass.text.isNotEmpty() && binding.newpass.text.isNotEmpty() && binding.confirmpass.text.isNotEmpty()){
            saveData(object : FirestoreCallback {
                override fun onCallback()  {
                    Log.d("wq","testing1")
                    }
            } )
            }
               /* else {
                Toast.makeText(
                    requireActivity(),
                    "Please insert value first",
                    Toast.LENGTH_SHORT
                ).show()
            }*/

        //}
        return binding.root
}


    private interface FirestoreCallback {
        fun onCallback()
    }

    private fun saveData(firestoreCallback: FirestoreCallback){
        Log.d("wq","testing2")
        database.collection("userRegisterData")
            .document(binding.phoneR.text.toString())
            .update("password",binding.confirmpass.text.toString())

            .addOnSuccessListener { Log.d("qq", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("ww", "Error updating document", e) }
    }

    override fun onResume() {
        super.onResume()

        // Set title bar
        (activity as MainActivity).setActionBarTitle("Update Password")
    }


/* val userRegisterData = hashMapOf<String, String>(
     "password" to binding.confirmpass.toString(),
 )
*/
                           /* database.collection("userRegisterData")
                            .document(binding.phoneR.text.toString())
                                .update("password",binding.confirmpass.text.toString())

                                .addOnSuccessListener { Log.d("qq", "DocumentSnapshot successfully updated!") }
                                .addOnFailureListener { e -> Log.w("ww", "Error updating document", e) }*/

               /*             .addOnSuccessListener {
                                database.collection("userRegisterData"). document(document.id).set(SetOptions.merge())
                                Toast.makeText(activity, "okok!", Toast.LENGTH_LONG).show()
                            }
                                .addOnFailureListener {
                                    Log.d("aaaahhhrrgghh", "failed ")
                                }
*/
                            /*else {
                            Toast.makeText(
                                requireActivity(),
                                "Password Does Not Match,make acc",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "Please fill out all the fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    }*/





    /*  private fun savaToDatabase() {

        val userRegisterData = hashMapOf<String, String>(
            "password" to confirmpass.toString(),

            )

        database.collection("userRegisterData").document(sharedViewModel.dataPhoneNumber())
            .set(userRegisterData)
            .addOnSuccessListener {
                Toast.makeText(activity, "Password Changed Suceesfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.w("failed changed", it)
            }
    }*/
        /*  override fun onCallback() {
        if (binding.currentpass.text.isNotEmpty() && binding.newpass.text.isNotEmpty() && binding.confirmpass.text.isNotEmpty()) {
            if (binding.newpass.text.toString() == binding.currentpass.text.toString()) {
                Toast.makeText(activity, "Reset Successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Password Does Not Match",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                requireActivity(),
                "Please fill out all the fields",
                Toast.LENGTH_SHORT
            ).show()
        }
    }*/

        /* private fun uppassuppass(){
        if (binding.currentpass.text.isNotEmpty() && binding.newpass.text.isNotEmpty() && binding.confirmpass.text.isNotEmpty()){
            if (binding.newpass.text.toString()== binding.currentpass.text.toString()){

            }
            else{
                Toast.makeText(requireActivity(),"Password Does Not Match", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(requireActivity(),"Please fill out all the fields", Toast.LENGTH_SHORT).show()
        }

    }
*/


        /* override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_view, container, false)

        textViewName = view.findViewById(R.id.text_name_another) as TextView

        val bundle = arguments
        if (bundle != null) {
            val details = bundle.getParcelable<Details>(KEY_PARSE_DATA)
            textViewName!!.setText(details.tp)
        }


        return view
    }*/

   /*     private fun readDatabase(firestoreCallback: FirestoreCallback) {

            fun readDatabase(firestoreCallback: updatePassword.FirestoreCallback) {
                val updatePassword = database.collection("userRegisterData")
                val testing = binding.phoneR.text.toString()

                updatePassword.document(testing).get()
                    .addOnSuccessListener { document ->
                        value = document.getString("phoneNumber").toString()
                        Log.d("X1", "This phone number have been verified ! You may reset password")
                        firestoreCallback.onCallback()
                    }
                    .addOnFailureListener {
                        Log.d("aarrrgghhhhh", "This phone number does not exist")
                    }

            }*/

          /*  val userRegisterRef = database.collection("userRegisterData")
            val testing = binding.currentpass.text.toString()

            userRegisterRef.document(testing).get()
                .addOnSuccessListener { document ->
                    value = document.getString("password").toString()
                    Log.d("eee", "DocumentSnapshot data: ${document.data}")
                    firestoreCallback.onCallback()
                }
                .addOnFailureListener {
                    Log.d("aaaahhhrrgghh", "failed ")
                }*/
}




