package com.wallet.rippleflow

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.wallet.rippleflow.databinding.FragmentProfile2Binding


class ProfileFragm : Fragment() {

    private var _binding : FragmentProfile2Binding? = null
    private val binding get() = _binding!!
    private lateinit var preferencesKEY: SharedPreferences
    private lateinit var preferencesPIN: SharedPreferences

    private lateinit var address: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfile2Binding.inflate(inflater, container, false)
        preferencesKEY = activity?.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)!!
        preferencesPIN = activity?.getSharedPreferences(APP_PREFERENCES_PIN, Context.MODE_PRIVATE)!!
        address = XRPL(preferencesKEY.getString(APP_PREFERENCES_KEY, "")!!).address.toString()
        binding.btnChangePIN.setOnClickListener {
            BottomSheetChangePINFragment().show(requireFragmentManager(), "SS")
        }


        binding.btnLogOut.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.CustomAlertDialog)
            builder
                .setMessage("Do you want logout from account?")

                .setPositiveButton("YES",
                    DialogInterface.OnClickListener { _, _ ->
                        preferencesKEY.edit()
                            .putString(APP_PREFERENCES_KEY, "")
                            .apply()
                        preferencesPIN.edit()
                            .putString(APP_PREFERENCES_PIN, "")
                            .apply()
                        findNavController().navigate(R.id.action_profileFragm_to_registrationFragment)
                    })

                .setNegativeButton("NO",
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }


        binding.textViewAddress.text = preferencesKEY.getString(APP_PREFERENCES_KEY, "").toString()

        binding.btnShare.setOnClickListener {
            val shareText = Intent(Intent.ACTION_SEND)
            shareText.type = "text/plain"
            val dataToShare = "my address is $address"
            shareText.putExtra(Intent.EXTRA_SUBJECT, "Subject from my application")
            shareText.putExtra(Intent.EXTRA_TEXT, dataToShare)
            startActivity(Intent.createChooser(shareText, "Share"))
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