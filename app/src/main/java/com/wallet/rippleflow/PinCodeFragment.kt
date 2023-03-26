package com.wallet.rippleflow

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.wallet.rippleflow.databinding.FragmentPinCodeBinding
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executor


class PinCodeFragment : Fragment() {
    lateinit var info: String

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private var _binding : FragmentPinCodeBinding? = null
    private val binding get() = _binding!!
    private var passCode = "";

    private var numbers_list: ArrayList<String> = ArrayList()
    private lateinit var num_00: String
    private lateinit var num_01: String
    private lateinit var num_02: String
    private lateinit var num_03: String
    private lateinit var num_04: String

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        preferences = activity?.getSharedPreferences(
            APP_PREFERENCES_PIN, Context.MODE_PRIVATE
        )!!
//        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.show()
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         _binding = FragmentPinCodeBinding.inflate(inflater, container, false)
        authOne()
        listeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_profile).isVisible = false
        super.onPrepareOptionsMenu(menu)
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
            authOne()
        }
        binding.textButtonDelete.setOnClickListener {
            numbers_list.clear()
            passNumber(numbers_list)

        }
    }

    private fun authOne() {
        checkDeviceHasBiometric()
        fingerAuth()
        biometricPrompt.authenticate(promptInfo)
    }
    private fun fingerAuth() {

        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence,
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    /*Toast.makeText(context,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()*/
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult,
                ) {
                    super.onAuthenticationSucceeded(result)
                    /*Toast.makeText(context,
                        "Authentication succeeded!", Toast.LENGTH_SHORT)
                        .show()*/
                    findNavController().navigate(R.id.action_pinCodeFragment_to_mainFragment)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                   /* Toast.makeText(context, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()*/
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Entrance to RippleFlow")/**/
            .setNegativeButtonText("Cancel")
            .build()

    }


    fun checkDeviceHasBiometric() {
        val biometricManager = BiometricManager.from(requireContext())
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("app_tag", "App can authenticate using biometrics.")
                info = "App can authenticate using biometrics."

            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.e("app_tag", "No biometric features available on this device.")
                info = "No biometric features available on this device."

            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.e("app_tag", "Biometric features are currently unavailable.")
                info = "Biometric features are currently unavailable."

            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                }


            }
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

                    if (getPassCode().equals(passCode)) {
                        findNavController().navigate(R.id.action_pinCodeFragment_to_mainFragment)
                    } else {
                        numbers_list.clear()
                        passNumber(numbers_list)
                        vibratePhone()
                        Toast.makeText(activity, "wrong PIN", Toast.LENGTH_LONG).show()
                    }
                }

            }
        }

    }

    fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }

    private fun getPassCode(): String? {
        return preferences.getString(APP_PREFERENCES_PIN, "")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}