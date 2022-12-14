package edu.bluejack22_1.fidertime.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import edu.bluejack22_1.fidertime.R
import edu.bluejack22_1.fidertime.databinding.ActivityMainBinding
import edu.bluejack22_1.fidertime.fragments.ContactListFragment
import edu.bluejack22_1.fidertime.fragments.MessageFragment
import edu.bluejack22_1.fidertime.fragments.ProfileFragment
import edu.bluejack22_1.fidertime.fragments.StatusFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.itemIconTintList = null

        when(intent.getStringExtra("FragmentOption")) {
            "profile" -> {
                binding.bottomNavigationView.selectedItemId = R.id.profile
                replaceFragment(ProfileFragment())
            }
            "contact" -> {
                binding.bottomNavigationView.selectedItemId = R.id.contact
                replaceFragment(ContactListFragment())
            }"message" -> {
                binding.bottomNavigationView.selectedItemId = R.id.message
                replaceFragment(MessageFragment())
            }
            else -> {
                replaceFragment(MessageFragment())

                binding.bottomNavigationView.selectedItemId = R.id.message
            }
        }




        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.contact -> replaceFragment(ContactListFragment())
                R.id.message -> replaceFragment(MessageFragment())
                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout , fragment)
        fragmentTransaction.commit()
    }
}