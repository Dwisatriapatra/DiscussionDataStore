package com.example.discussiondatastore.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import com.example.discussiondatastore.R
import com.example.discussiondatastore.datastore.UserLoginManager
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var userLoginManager: UserLoginManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        userLoginManager = UserLoginManager(requireContext())
        userLoginManager.username.asLiveData().observe(viewLifecycleOwner){
            profile_username.setText(it)
        }
        userLoginManager.password.asLiveData().observe(viewLifecycleOwner){
            profile_password.setText(it)
        }
        button_logout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        userLoginManager = UserLoginManager(requireContext())
        AlertDialog.Builder(requireContext())
            .setTitle("LOG OUT")
            .setMessage("Apakah anda yakin ingin logout?")
            .setNegativeButton("TIDAK"){ dialogInterface : DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("YA"){ _: DialogInterface, _: Int ->
                GlobalScope.launch {
                    userLoginManager.clearDataLogin()
                }
                val mIntent =activity?.intent
                activity?.finish()
                startActivity(mIntent)
            }.show()
    }
}