package com.example.memtone

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.memtone.databinding.FragmentMainBinding
import org.json.JSONObject
import org.json.JSONTokener
import java.math.RoundingMode
import java.net.URL
import java.text.DecimalFormat


class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)



        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object :OnBackPressedCallback(true){
            @Override
            override fun handleOnBackPressed() {
                val builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.CustomAlertDialog)
                builder
                    .setMessage("Do you want exit from RiplleFlow?")
                    .setPositiveButton("YES",
                        DialogInterface.OnClickListener { dialog, i -> activity?.finish() })
                    .setNegativeButton("NO",
                        DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
            }
                //setEnabled(false); // call this to disable listener
                //remove(); // call to remove listener
                //Toast.makeText(getContext(), "Listing for back press from this fragment", Toast.LENGTH_SHORT).show();
        });

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        convertToDollars("1")

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