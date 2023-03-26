package com.wallet.rippleflow

import com.wallet.rippleflow.cardEmulator.KHostApduService
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.content.ContextWrapper
import android.nfc.NfcAdapter
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wallet.rippleflow.databinding.FragmentMainBinding
import org.json.JSONObject
import org.json.JSONTokener
import java.math.RoundingMode
import java.net.URL
import java.text.DecimalFormat


class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var mNfcAdapter: NfcAdapter? = null
    private lateinit var mTurnNfcDialog: androidx.appcompat.app.AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.show()
        var view1: TextView = binding.textViewXRPAmount
        var preferences: SharedPreferences = activity?.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)!!
            Thread {
                var ans = (XRPL(preferences.getString(APP_PREFERENCES_KEY, "")!!).getBalance()/1000000f).toString()
                println(ans)
                activity?.runOnUiThread {
                    view1.setText(ans)
                    convertToDollars(ans)
                }
            }.start()
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

        mNfcAdapter = NfcAdapter.getDefaultAdapter(context)
        convertToDollars("1")
        // Ilyas, fix this


//        binding.btnNFC.setOnClickListener {
//            findNavController().navigate(R.id.action_mainFragment_to_nfcFragment)
//        }
        initNFCFunction()

        binding.btnTransfer.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_transferFragment)
        }
        binding.btnQR.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_qrFragment)
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
                    try {
                        binding.textViewDollarXRPAmount.text = "$$ansT USD"
                    } catch (e:java.lang.NullPointerException){}
                }
            }
        }.start()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun initNFCFunction() {

        if (checkNFCEnable() && requireContext().packageManager.hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION)) {
            /* TODO: enable NFC buttons here*/
            initService()
        } else {
            /* TODO: disable NFC buttons here*/
            showTurnOnNfcDialog()
        }
    }

    private fun initService() {
        binding.btnNFC.setOnClickListener {
                val intent = Intent(context, KHostApduService::class.java)
            var preferences: SharedPreferences = activity?.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)!!
           // intent.putExtra("ndefMessage", XRPL(preferences.getString(APP_PREFERENCES_KEY, "")!!).getAddress())
            intent.putExtra("ndefMessage", "VIVSEHUESOSI")
                    requireContext().startService(intent)
        }

    }

    private fun checkNFCEnable(): Boolean {
        return if (mNfcAdapter == null) {
            false
        } else {
            mNfcAdapter!!.isEnabled
        }
    }

    private fun showTurnOnNfcDialog() {
        mTurnNfcDialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.ad_nfcTurnOn_title))
            .setMessage(getString(R.string.ad_nfcTurnOn_message))
            .setPositiveButton(
                getString(R.string.ad_nfcTurnOn_pos)
            ) { _, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
                } else {
                    startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
                }
            }.setNegativeButton(getString(R.string.ad_nfcTurnOn_neg)) { _, _ ->
                // TODO: handle onBackPressed()
            }
            .create()
        mTurnNfcDialog.show()
    }

    override fun onResume() {
        super.onResume()
        if (mNfcAdapter!!.isEnabled) {
//         TODO: handle this shit
//            textView.visibility = View.GONE
//            editText.visibility = View.VISIBLE
//            button.visibility = View.VISIBLE
            initService()
        }
    }


}