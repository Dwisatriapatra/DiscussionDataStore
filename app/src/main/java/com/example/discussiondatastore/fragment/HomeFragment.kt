package com.example.discussiondatastore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.example.discussiondatastore.R
import com.example.discussiondatastore.datastore.UserLoginManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_login.*

class HomeFragment : Fragment() {
    private lateinit var userLoginManager: UserLoginManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        userLoginManager = UserLoginManager(requireContext())
        userLoginManager.username.asLiveData().observe(viewLifecycleOwner){
            welcome_text.text = "Hello, $it"
        }

        button_go_to_profile.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }
}