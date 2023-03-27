package com.wallet.rippleflow

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcAdapter.CreateNdefMessageCallback
import android.nfc.NfcAdapter.getDefaultAdapter
import android.nfc.NfcEvent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wallet.rippleflow.databinding.FragmentBottomSheetNfcBinding


class BottomSheetNFCFragment: BottomSheetDialogFragment(), CreateNdefMessageCallback {
    private lateinit var binding: FragmentBottomSheetNfcBinding
    private var nfcAdapter: NfcAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetNfcBinding.inflate(inflater, container, false)

        nfcAdapter = getDefaultAdapter(requireContext())
        if (nfcAdapter == null) {
            Toast.makeText(requireContext(), "NFC is not available", Toast.LENGTH_LONG).show();
        }
        nfcAdapter?.setNdefPushMessageCallback(this, activity);


        (binding.imageView13.drawable as AnimatedVectorDrawable).start()
        return binding.root
    }

    @SuppressLint("NewApi")
    override fun createNdefMessage(event: NfcEvent?): NdefMessage? { //before the record can be sent it has to be in a message
        val address = "SDFSDF"
        val StringBytes =
            address.toByteArray() // the string has to be converted to bytes.
        val ndefRecordOut =
            NdefRecord( // the record has to be created first then the message
                NdefRecord.TNF_MIME_MEDIA,  // record type of text
                address.toByteArray(), byteArrayOf(),  //empty byte array
                StringBytes
            ) // text as bytes in the array
        return NdefMessage(ndefRecordOut)
    }

    override fun onResume() {
        super.onResume()
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(requireActivity().getIntent().action)) {
            processIntent(requireActivity().getIntent())
        }
        if (!nfcAdapter!!.isEnabled) {
            Toast.makeText(context, "NFC is disabled. Please turn on in settings.", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(context, "NFC is enabled. You can begin transfer.", Toast.LENGTH_LONG)
                .show()
        }
    }

    @SuppressLint("MissingSuperCall")
    fun onNewIntent(intent: Intent?) {
        activity?.intent = intent
    }


    fun processIntent(intent: Intent) {
        var textView = "TEXTETXTEXT"
        val rawMsgs = intent.getParcelableArrayExtra(
            NfcAdapter.EXTRA_NDEF_MESSAGES
        ) //Extra containing an array of NdefMessage present on the discovered tag.
        // only one message sent during the beam
        val msg = rawMsgs!![0] as NdefMessage
        // record 0 contains the MIME type, record 1 is the AAR, if present
//        textView.setText(String(msg.records[0].payload)) // sets the textview to the message
        Toast.makeText(context, String(msg.records[0].payload), Toast.LENGTH_LONG).show()
    }

}