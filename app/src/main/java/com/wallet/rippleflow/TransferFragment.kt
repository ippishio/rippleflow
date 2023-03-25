package com.wallet.rippleflow

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.wallet.rippleflow.databinding.FragmentTransferBinding

class TransferFragment : Fragment() {

    private var _binding : FragmentTransferBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransferBinding.inflate(inflater, container, false)


        binding.btnSend.setOnClickListener {
            var view1: EditText = binding.editTextAmount
            var amount: String = view1.text.toString()
            var view2: EditText = binding.editTextAddress
            var address: String = view2.text.toString()
            var preferences: SharedPreferences = activity?.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)!!
            println(address)
            Thread {
                var result_hash = (XRPL(preferences.getString(APP_PREFERENCES_KEY, "")!!).sendXRP(address, (amount.toFloat()*1000000f).toLong()));
                activity?.runOnUiThread {
                    println("done")
                    println(result_hash); //need to show this to user

                }
            }.start()

            findNavController().navigate(R.id.action_transferFragment_to_zaebokFragment)
        }

        binding.btnFabAddContact.setOnClickListener {
            BottomSheetTransferFragment().show(requireFragmentManager(), "newContactTag")
        }
        setHasOptionsMenu(true)
        return binding.root
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