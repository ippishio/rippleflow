package com.wallet.rippleflow

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.wallet.rippleflow.databinding.FragmentCreatePinCodeBinding
import com.wallet.rippleflow.databinding.FragmentMainBinding


class CreatePinCodeFragment : Fragment() {


    private var _binding : FragmentCreatePinCodeBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePinCodeBinding.inflate(inflater, container, false)
        preferences = activity?.getSharedPreferences(APP_PREFERENCES_PIN, Context.MODE_PRIVATE)!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogButton.setOnClickListener {
            val pin = binding.editTextPinCode.text.toString()
            if (pin.length != 5)
                Toast.makeText(activity, "PIN MUST CONTAIN 5 NUMBERS", Toast.LENGTH_SHORT).show()
            else {
                preferences.edit()
                    .putString(APP_PREFERENCES_PIN, pin)
                    .apply()
                Toast.makeText(activity, "PIN CODE $pin created", Toast.LENGTH_SHORT).show()
//                Toast.makeText(activity, preferences.getString(APP_PREFERENCES_PIN, "NON FOUND").toString(), Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_createPinCodeFragment_to_mainFragment)
            }
        }
        setHasOptionsMenu(true)
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