package com.example.memtone

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.memtone.databinding.FragmentPinCodeBinding

class PinCodeFragment : Fragment() {

    private var _binding : FragmentPinCodeBinding? = null
    private val binding get() = _binding!!
    private var passCode = "";
    private var numbers_list: ArrayList<String> = ArrayList()
    private lateinit var num_00: String
    private lateinit var num_01: String
    private lateinit var num_02: String
    private lateinit var num_03: String
    private lateinit var num_04: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         _binding = FragmentPinCodeBinding.inflate(inflater, container, false)

        listeners()


        setHasOptionsMenu(true)
        return binding.root
    }

    private fun listeners() {
        binding.textButton1.setOnClickListener {
            numbers_list.add("1")
            passNumber(numbers_list)
        }
        binding.textButton2.setOnClickListener {
            numbers_list.add("2")
            passNumber(numbers_list)
        }
        binding.textButton3.setOnClickListener {
            numbers_list.add("3")
            passNumber(numbers_list)
        }
        binding.textButton4.setOnClickListener {
            numbers_list.add("4")
            passNumber(numbers_list)
        }
        binding.textButton5.setOnClickListener {
            numbers_list.add("5")
            passNumber(numbers_list)
        }
        binding.textButton6.setOnClickListener {
            numbers_list.add("6")
            passNumber(numbers_list)
        }
        binding.textButton7.setOnClickListener {
            numbers_list.add("7")
            passNumber(numbers_list)
        }
        binding.textButton8.setOnClickListener {
            numbers_list.add("8")
            passNumber(numbers_list)
        }
        binding.textButton9.setOnClickListener {
            numbers_list.add("9")
            passNumber(numbers_list)
        }
        binding.textButton0.setOnClickListener {
            numbers_list.add("0")
            passNumber(numbers_list)
        }
        binding.textButtonFingerPrint.setOnClickListener {
            Toast.makeText(activity, "FingerPrint", Toast.LENGTH_LONG).show()
        }
        binding.textButtonDelete.setOnClickListener {
            numbers_list.clear()
            passNumber(numbers_list)
        }
    }

    private fun passNumber(numbersList: ArrayList<String>) {
        if (numbersList.isEmpty()) {
            binding.imageView1.setBackgroundResource(R.drawable.pc_view_grey)
            binding.imageView2.setBackgroundResource(R.drawable.pc_view_grey)
            binding.imageView3.setBackgroundResource(R.drawable.pc_view_grey)
            binding.imageView4.setBackgroundResource(R.drawable.pc_view_grey)
            binding.imageView5.setBackgroundResource(R.drawable.pc_view_grey)
        }   else {
            when(numbersList.size) {
                1 -> {
                    num_00 = numbersList.get(0)
                    binding.imageView1.setBackgroundResource(R.drawable.pc_view_blue)
                }
                2 -> {
                    num_01 = numbersList.get(1)
                    binding.imageView2.setBackgroundResource(R.drawable.pc_view_blue)
                }
                3 -> {
                    num_02 = numbersList.get(2)
                    binding.imageView3.setBackgroundResource(R.drawable.pc_view_blue)
                }
                4 -> {
                    num_03 = numbersList.get(3)
                    binding.imageView4.setBackgroundResource(R.drawable.pc_view_blue)
                }
                5 -> {
                    num_04 = numbersList.get(4)
                    binding.imageView5.setBackgroundResource(R.drawable.pc_view_blue)
                    passCode = num_00 + num_01 + num_02 +num_03 +num_04
                    findNavController().navigate(R.id.action_pinCodeFragment_to_mainFragment)
//
//                    if (getPassCode()?.isEmpty() == true) {
//                        Toast.makeText(activity, "getpassLeng = 0", Toast.LENGTH_LONG).show()
//                        savePassCode(passCode)
//                    } else {
//                        matchPassCode()
//                        Log.d("DOESN'T WORK", "matchPassCode")
//                    }
//                    Log.e("First", getPassCode().toString())
                }

            }
        }

    }

    private fun savePassCode(passCode: String): SharedPreferences.Editor? {
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("passcode", passCode)
        editor?.apply()
        return editor
    }

    private fun matchPassCode() {
        if(getPassCode().equals(passCode)) {
            findNavController().navigate(R.id.action_pinCodeFragment_to_mainFragment)
        } else {
            Toast.makeText(activity, "password doesn't match", Toast.LENGTH_SHORT).show()
            numbers_list.clear()
            passNumber(numbers_list)
        }
    }
    private fun getPassCode(): String? {
        val sharedPref = (activity?.getPreferences(Context.MODE_PRIVATE))
        return sharedPref?.getString("passcode", "11111")

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