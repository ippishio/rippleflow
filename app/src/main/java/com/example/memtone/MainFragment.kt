package com.example.memtone

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
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
                    .setMessage("Do you want to exit from RippleFlow?")
                    .setPositiveButton("YES",
                        DialogInterface.OnClickListener { dialog, i -> activity?.finish() })
                    .setNegativeButton("NO",
                        DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
            }
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


    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}