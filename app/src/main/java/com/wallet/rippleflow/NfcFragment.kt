package com.wallet.rippleflow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wallet.rippleflow.databinding.FragmentNfcBinding


class NfcFragment : Fragment() {

    private var _binding : FragmentNfcBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNfcBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGO.setOnClickListener {
            val bundle = Bundle()
            val text = binding.editTextAmount.text.toString()
            if (text == "" || text.toDoubleOrNull() == null) binding.textInputLayout2.error = "Enter amount!"
            else {
                binding.textInputLayout2.error = null
                bundle.putString(
                    "amount",
                    text
                )

                val bottomSheetNFCFragment = BottomSheetNFCFragment()
                bottomSheetNFCFragment
                    .arguments = bundle
                bottomSheetNFCFragment
                    .show((context as AppCompatActivity).supportFragmentManager, "NFCtag")
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