package com.wallet.rippleflow

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.wallet.rippleflow.contact.ViewModel.ContactViewModel
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.wallet.rippleflow.databinding.FragmentProfile2Binding


class ProfileFragm : Fragment() {

    private var _binding : FragmentProfile2Binding? = null
    private val binding get() = _binding!!
    private lateinit var preferencesKEY: SharedPreferences
    private lateinit var preferencesPIN: SharedPreferences
    private lateinit var contactViewModel: ContactViewModel

    private lateinit var address: String

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfile2Binding.inflate(inflater, container, false)
        preferencesKEY = activity?.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)!!
        preferencesPIN = activity?.getSharedPreferences(APP_PREFERENCES_PIN, Context.MODE_PRIVATE)!!
        Thread {
            var ans = XRPL(preferencesKEY.getString(APP_PREFERENCES_KEY, "")!!).getAddress()
            activity?.runOnUiThread {
                binding.textViewAddress.text = ans//preferencesKEY.getString(APP_PREFERENCES_KEY, "").toString()

            }
        }.start()
        binding.btnChangePIN.setOnClickListener {
            BottomSheetChangePINFragment().show(requireFragmentManager(), "SS")
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
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.md_theme_light_error));
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.md_theme_light_error));
        }


        binding.btnShare.setOnClickListener {
            val shareText = Intent(Intent.ACTION_SEND)
            shareText.type = "text/plain"
            val dataToShare = "my address is $address"
            shareText.putExtra(Intent.EXTRA_SUBJECT, "Subject from my application")
            shareText.putExtra(Intent.EXTRA_TEXT, dataToShare)
            startActivity(Intent.createChooser(shareText, "Share"))
        }

        var qr = binding.qrCode

        val mWriter = MultiFormatWriter()
        Thread {
            var ans = XRPL(preferencesKEY.getString(APP_PREFERENCES_KEY, "")!!).getAddress()
            activity?.runOnUiThread {
                val mMatrix = mWriter.encode(ans, BarcodeFormat.QR_CODE, 400, 400)
                val mEncoder = BarcodeEncoder()
                val mBitmap = mEncoder.createBitmap(mMatrix)
                qr.setImageBitmap(mBitmap)
            }
        }.start()


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