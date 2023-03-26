package com.wallet.rippleflow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wallet.rippleflow.databinding.FragmentTransactionsBinding
import com.wallet.rippleflow.transaction.Adapter.TransactionsAdapter
import com.wallet.rippleflow.transaction.Dao.TransactionDao
import com.wallet.rippleflow.transaction.Model.Transaction
import com.wallet.rippleflow.transaction.TransactionActionListener
import com.wallet.rippleflow.transaction.ViewModel.TransactionViewModel


class TransactionsHistoryFragment : Fragment() {

    private var _binding : FragmentTransactionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactionAdapter: TransactionsAdapter
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)


        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        transactionViewModel.insert(requireContext(), Transaction(
            address = "S:LDKJFS:LDF",
            date = "21.05.2020",
            sum = "9999XRP"
        )
        )

        transactionViewModel.getAllTransactionData(requireContext()).observe(viewLifecycleOwner, Observer {
            transactionAdapter.setData(it as ArrayList<Transaction>)
        })

        transactionAdapter = TransactionsAdapter(
            requireContext(),
            object : TransactionActionListener{
                override fun onTransactionClick(transaction: Transaction) {
                    Toast.makeText(requireContext(), "TOTO", Toast.LENGTH_SHORT).show()
                }
                override fun onTransactionMoreClick(transaction: Transaction) {
                    Toast.makeText(requireContext(), "SDFS", Toast.LENGTH_SHORT).show()
                    transactionViewModel.deleteTransaction(requireContext(), transaction)
                }
            },
            transactions = ArrayList<Transaction>()
        )

        binding.recyclerViewTransactions.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = transactionAdapter
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