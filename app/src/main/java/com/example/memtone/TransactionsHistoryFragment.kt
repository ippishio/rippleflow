package com.example.memtone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.room.Dao
import androidx.room.Room
import com.example.memtone.databinding.FragmentTransactionsHistoryBinding
import com.example.memtone.model.RVDatabase
import com.example.memtone.model.Transaction
import com.example.memtone.model.TransactionDao


class TransactionsHistoryFragment : Fragment() {

    private var _binding : FragmentTransactionsHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactionDao: TransactionDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionsHistoryBinding.inflate(inflater, container, false)

        transactionDao = RVDatabase.getInstance(requireContext()).TransactionDao()

/*        transactionDao.addTransaction(
            Transaction(
                address = "address",
                date = "21 september",
                sum = "2+2=5"
            )
        )*/

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