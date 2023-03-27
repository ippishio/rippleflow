package com.wallet.rippleflow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.wallet.rippleflow.databinding.FragmentQrBinding



class qrFragment : Fragment() {
    private var _binding : FragmentQrBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val integrator = IntentIntegrator.forSupportFragment(this)

        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)

        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(false)
        integrator.setOrientationLocked(false)

        integrator.initiateScan();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qr, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        val result = IntentIntegrator.parseActivityResult(resultCode, intent)
        if (result.contents != null) {
            var mytoast = Toast.makeText(this.activity, result.contents, Toast.LENGTH_SHORT)
            mytoast.show()
            findNavController().navigate(R.id.action_qrFragment_to_mainFragment)
        } else {
            findNavController().navigate(R.id.action_qrFragment_to_mainFragment)
        }
    }

}