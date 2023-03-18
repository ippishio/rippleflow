package com.example.memtone

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.view.*
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.memtone.databinding.FragmentMainBinding
import org.json.JSONObject
import org.json.JSONTokener
import java.math.RoundingMode
import java.net.URL
import java.text.DecimalFormat
import java.util.concurrent.Executor


class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)




        /*binding.btnTransfer.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_transferFragment)
        }*/

        /*binding.btnNFC.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_nfcFragment)
        }*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        convertToDollars("1")

        binding.btnNFC.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_nfcFragment)
        }

        binding.btnTransfer.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_transferFragment)
        }
    }

    fun convertToDollars(balance: String){
        var ansT: String? = null
        Thread {
            var ans: String?
            ans = URL("https://api3.binance.com/api/v3/avgPrice?symbol=XRPUSDT").readText()
            activity?.runOnUiThread {
                if(ans != null) {
                    val jsonObject = JSONTokener(ans).nextValue() as JSONObject

                    val df = DecimalFormat("#.##")
                    df.roundingMode = RoundingMode.DOWN
                    ansT = df.format(jsonObject.getString("price").toFloat() * balance.toFloat())

                    binding.textViewDollarXRPAmount.text = "$$ansT USD"
//                    balanceDollarsVal = jsonObject.getString("price").toFloat() * balance.toFloat()
                }
            }
        }.start()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}