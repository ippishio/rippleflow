package com.wallet.rippleflow

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.drawable.AnimatedVectorDrawable
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcAdapter.CreateNdefMessageCallback
import android.nfc.NfcAdapter.getDefaultAdapter
import android.nfc.NfcEvent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wallet.rippleflow.HceCardEmulationApduService.Companion.NFC_NDEF_KEY
import com.wallet.rippleflow.databinding.FragmentBottomSheetNfcBinding
import timber.log.Timber


class BottomSheetNFCFragment: BottomSheetDialogFragment(){
    private lateinit var binding: FragmentBottomSheetNfcBinding

    private var nfcAdapter: NfcAdapter? = null
    private lateinit var data: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nfcAdapter = NfcAdapter.getDefaultAdapter(requireContext())

//        binding.startServiceBtn.setOnClickListener {
        initNFCFunction()
//            binding.status.text = getString(R.string.service_enabled)
//        }
//        binding.stopServiceBtn.setOnClickListener {
//            stopNfcService()
//            binding.status.text = getString(R.string.service_disabled)
//        }
    }


    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetNfcBinding.inflate(inflater, container, false)

        val data = arguments?.getString("amount").toString()

        nfcAdapter = getDefaultAdapter(requireContext())
        if (nfcAdapter == null) {
            Toast.makeText(requireContext(), "NFC is not available", Toast.LENGTH_LONG).show();
        }
        initNfcService()
//        nfcAdapter?.setNdefPushMessageCallback(this, activity);

        (binding.imageView13.drawable as AnimatedVectorDrawable).start()
        return binding.root
    }

    private fun initNFCFunction() {
        if (!requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION)) {
//            binding.status.text = getString(R.string.hce_not_available)
            Toast.makeText(requireContext(), "hce not available", Toast.LENGTH_SHORT).show()
            return
        }

        if (nfcAdapter?.isEnabled == true) {
            initNfcService()
        } else {
            showTurnOnNfcDialog()
        }
    }

    private fun initNfcService() {
        val inputText = "11000980981"

        val intent = Intent(requireContext(), HceCardEmulationApduService::class.java)
        intent.putExtra(NFC_NDEF_KEY, inputText)
        requireContext().startService(intent)

        val filter = IntentFilter(NFC_BROADCAST)
        requireContext().registerReceiver(nfcReceiver, filter)
    }

    private fun stopNfcService() {
        if (nfcAdapter?.isEnabled == true) {
            requireContext().stopService(Intent(requireContext(), HceCardEmulationApduService::class.java))
        }

//        binding.status.text = getString(R.string.service_disabled)
        Toast.makeText(requireContext(), "service disabled", Toast.LENGTH_SHORT).show()

        try {
            requireContext().unregisterReceiver(nfcReceiver)
        } catch (ex: Exception) {
            Timber.d("nfcReceiver not registered.")
        }
    }

    private fun showTurnOnNfcDialog() {
        val nfcDialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.nfc_turn_on_title))
            .setMessage(getString(R.string.nfc_turn_on_message))
            .setPositiveButton(getString(R.string.nfc_turn_on_positive)) { dialog, _ ->
                startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.nfc_turn_on_negative)) { dialog, _ -> dialog.dismiss() }
            .create()
        nfcDialog.show()
    }

    private val nfcReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.hasExtra(NFC_EXTRA_NDEF_SENT)) {
                if (intent.getBooleanExtra(NFC_EXTRA_NDEF_SENT, false)) {
                    Toast.makeText(
                        requireContext(),
                        "NDEF was sent successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    companion object {
        const val NFC_BROADCAST = "com.app.host_based_card_emulation"
        const val NFC_EXTRA_NDEF_SENT = "nfc_ndef_sent"
    }

}