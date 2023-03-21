package com.wallet.rippleflow

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wallet.rippleflow.databinding.FragmentBottomSheetChangePinBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetChangePINFragment: BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetChangePinBinding
    private lateinit var preferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        preferences = activity?.getSharedPreferences(APP_PREFERENCES_PIN, Context.MODE_PRIVATE)!!
        binding.btnChangePin.setOnClickListener {
            val newPin = binding.editTextPin.text.toString()
            if(newPin.length != 5)
                Toast.makeText(activity, "PIN-code must contain 5 numbers",
                    Toast.LENGTH_SHORT).show()
            else {
                changePIN(newPin)
            }
        }
    }

    private fun changePIN(pin: String) {
        preferences.edit()
            .putString(APP_PREFERENCES_PIN, pin)
            .apply()
//        Toast.makeText(activity, "$pin CHANGED", Toast.LENGTH_SHORT).show()
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetChangePinBinding.inflate(inflater, container, false)

        return binding.root
    }


}