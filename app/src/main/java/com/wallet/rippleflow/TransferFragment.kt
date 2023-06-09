package com.wallet.rippleflow

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wallet.rippleflow.databinding.FragmentTransferBinding
import com.wallet.rippleflow.contact.*
import com.wallet.rippleflow.contact.Adapter.ContactsAdapter
import com.wallet.rippleflow.contact.Model.Contact
import com.wallet.rippleflow.contact.ViewModel.ContactViewModel
import com.wallet.rippleflow.transaction.Adapter.TransactionsAdapter
import com.wallet.rippleflow.transaction.Model.Transaction
import com.wallet.rippleflow.transaction.ViewModel.TransactionViewModel
import java.time.LocalDateTime

class TransferFragment : Fragment() {

    private var _binding : FragmentTransferBinding? = null
    private val binding get() = _binding!!

    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var contactViewModel: ContactViewModel

    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var transactionsAdapter: TransactionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransferBinding.inflate(inflater, container, false)


        binding.btnSend.setOnClickListener {
            var view1: EditText = binding.editTextAmount
            var amount: String = view1.text.toString()
            var view2: EditText = binding.editTextAddress
            var address: String = view2.text.toString()
            if (amount == "") binding.inputLayoutAmountTransfer.error = "Enter amount!"
            else if (amount.toFloatOrNull() == null) binding.inputLayoutAmountTransfer.error = "Amount must be a number!"
            else {
                binding.inputLayoutAmountTransfer.error = null
                if (address.isEmpty()) binding.inputLayoutAddressTransfer.error = "Enter address!"
                else {
                    binding.inputLayoutAddressTransfer.error = null
                    var preferences: SharedPreferences =
                        activity?.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)!!
                    println(address)
                    Thread {
                        var result_hash =
                            (XRPL(preferences.getString(APP_PREFERENCES_KEY, "")!!).sendXRP(
                                address,
                                (amount.toFloat() * 1000000f).toLong()
                            ));
                        activity?.runOnUiThread {
                            println("done")
                            println(result_hash); //need to show this to user

                        }
                    }.start()

                    val transaction = Transaction(
                        address = address,
                        date = LocalDateTime.now().toString(),
                        sum = amount
                    )
                    transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
                    transactionViewModel.insert(requireContext(), transaction)

                    findNavController().navigate(R.id.action_transferFragment_to_zaebokFragment)
                }
            }
        }

        binding.btnFabAddContact.setOnClickListener {
            BottomSheetTransferFragment().show(requireFragmentManager(), "newContactTag")
        }

        binding.recyclerViewContacts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 10 && binding.btnFabAddContact.isShown) {
                    binding.btnFabAddContact.hide()
                }

                if (dy < -10 && !binding.btnFabAddContact.isShown) {
                    binding.btnFabAddContact.show()
                }

                if (!recyclerView.canScrollVertically(-1)) {
                    binding.btnFabAddContact.show()
                }
            }
        })

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        contactViewModel.getAllContactData(requireContext()).observe(viewLifecycleOwner, Observer {
            contactsAdapter.setData(it as ArrayList<Contact>)
        })

        contactsAdapter = ContactsAdapter(
            requireContext(),
            object : ContactActionListener{
                override fun onContactClick(contact: Contact) {
//                    findNavController().navigate(R.id.action_transferFragment_to_zaebokFragment)
                    binding.editTextAddress.setText(contact.address)
                }

                override fun onContactMoreClick(contact: Contact) {
                    contactViewModel.deleteContact(requireContext(), contact)
                }

            },
            contacts = ArrayList<Contact>()
            )

        binding.recyclerViewContacts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contactsAdapter
        }

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