package com.example.discussiondatastore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.example.discussiondatastore.R
import com.example.discussiondatastore.datastore.UserLoginManager
import com.example.discussiondatastore.model.GetAllUserResponseItem
import com.example.discussiondatastore.network.ApiClient
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private lateinit var userLoginManager: UserLoginManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userLoginManager = UserLoginManager(requireContext())
        userLoginManager.boolean.asLiveData().observe(viewLifecycleOwner){
            if(it == true){
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
            }else{
                button_login.setOnClickListener {
                    getDataUser()
                }
            }
        }
    }

    private fun getDataUser() {
        ApiClient.instance.getAllUser()
            .enqueue(object : Callback<List<GetAllUserResponseItem>>{
                override fun onResponse(
                    call: Call<List<GetAllUserResponseItem>>,
                    response: Response<List<GetAllUserResponseItem>>
                ) {
                    if(response.isSuccessful){
                        loginAuth(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<List<GetAllUserResponseItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun loginAuth(listUser: List<GetAllUserResponseItem>) {
        val inputanUsername = login_input_username.text.toString()
        val inputanPassword = login_input_password.text.toString()
        userLoginManager = UserLoginManager(requireContext())

        if(inputanUsername.isNotEmpty() && inputanPassword.isNotEmpty()){
            for(i in listUser.indices){
                if(inputanUsername == listUser[i].username && inputanPassword == listUser[i].password){
                    Toast.makeText(requireContext(), "login berhasil", Toast.LENGTH_SHORT).show()
                    GlobalScope.launch {
                        userLoginManager.setBoolean(true)
                        userLoginManager.saveDataLogin(listUser[i].username, listUser[i].password)
                    }
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                    break
                }else if(i == listUser.lastIndex && inputanUsername != listUser[i].username && inputanPassword != listUser[i].password){
                    Toast.makeText(requireContext(), "username/password salah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}