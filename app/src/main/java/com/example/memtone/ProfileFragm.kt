package com.example.memtone

import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.memtone.databinding.FragmentProfile2Binding
import com.example.memtone.contact.ViewModel.ContactViewModel


class ProfileFragm : Fragment() {

    private var _binding : FragmentProfile2Binding? = null
    private val binding get() = _binding!!
    private lateinit var preferencesKEY: SharedPreferences
    private lateinit var preferencesPIN: SharedPreferences
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var address: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfile2Binding.inflate(inflater, container, false)
        preferencesKEY = activity?.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)!!
        preferencesPIN = activity?.getSharedPreferences(APP_PREFERENCES_PIN, Context.MODE_PRIVATE)!!
        address = "lsakdjfjf12oiupiofu9o287cn0948yrnoi1u3"
        binding.btnChangePIN.setOnClickListener {
            BottomSheetChangePINFragment().show(fragmentManager!!, "SS")
        }

        binding.btnTransactionsHistory.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragm_to_transactionsHistoryFragment)
        }

        binding.btnCopy.setOnClickListener {
            var myClipboard = getSystemService(requireContext(), ClipboardManager::class.java) as ClipboardManager
            val copyText = address
            val clip = ClipData.newPlainText("Copied",copyText)
            myClipboard.setPrimaryClip(clip)
            Toast.makeText(requireActivity(), "Copied", Toast.LENGTH_SHORT).show()
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

                        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
                        contactViewModel.deleteAllDataContact(requireContext())

                        findNavController().navigate(R.id.action_profileFragm_to_registrationFragment)
                    })

                .setNegativeButton("NO",
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }


        binding.textViewAddress.text = address//preferencesKEY.getString(APP_PREFERENCES_KEY, "").toString()

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