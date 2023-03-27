package com.wallet.rippleflow

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.wallet.rippleflow.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {

    private var _binding : FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        preferences = activity?.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)!!
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false)


        binding.btnLogin.setOnClickListener {

            val key = binding.editTextKey.text.toString()
            if(validAccount(key)) {

                preferences.edit()
                    .putString(APP_PREFERENCES_KEY, key)
                    .apply()

//                Toast.makeText(activity, "Reg", Toast.LENGTH_SHORT)
//                    .show()
//                Toast.makeText(activity, preferences.getString(APP_PREFERENCES_KEY, "nothing").toString(), Toast.LENGTH_SHORT)
//                    .show()
//                Toast.makeText(activity, preferences.getString(APP_PREFERENCES_PIN, "nothing").toString(), Toast.LENGTH_SHORT)
//                    .show()

                findNavController().navigate(
                    R.id.action_registrationFragment_to_createPinCodeFragment
                )
            }
//            val passwordText = binding.editTextPassword.text.toString()

        }
        (activity as AppCompatActivity).supportActionBar?.hide()
        setHasOptionsMenu(true)

        return binding.root
    }

    fun validAccount(key: String) : Boolean {
        // TODO CHECKING ACCOUNT VALIDATION
        if(key.isEmpty()) {
            Toast.makeText(context, "key cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        } else
            return true
        return false
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_profile).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}