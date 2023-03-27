package com.wallet.rippleflow.transaction.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.wallet.rippleflow.R
import com.wallet.rippleflow.databinding.ItemTransactionBinding
import com.wallet.rippleflow.transaction.Model.Transaction
import com.wallet.rippleflow.transaction.TransactionActionListener
import com.wallet.rippleflow.transaction.ViewModel.TransactionViewModel

class TransactionsAdapter(private val context: Context,
                          private val actionListener: TransactionActionListener,
                          private var transactions: ArrayList<Transaction>):
    RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder>(),
    View.OnClickListener{

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)
        return TransactionsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TransactionsViewHolder,
        position: Int
    ) {
        val transaction = transactions[position]
        with(holder.binding) {
            holder.itemView.tag = transaction
            moreImageViewButton.tag = transaction

            textViewNameOrAddress.text = transaction.address
            textViewSum.text = transaction.sum
            textViewDate.text = transaction.date
        }
    }

    override fun getItemCount(): Int = transactions.size

    override fun onClick(v: View) {
        val transaction = v.tag as Transaction
        when(v.id) {
            R.id.moreImageViewButton -> {
                showPopupMenu(v)
            }
            else -> {
                actionListener.onTransactionClick(transaction)
            }
        }
    }

    fun setData(transactions: ArrayList<Transaction>) {
        this.transactions = transactions
        notifyDataSetChanged()
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val transaction = v.tag as Transaction
        popupMenu.menu.add(0,0, Menu.NONE, "Delete")

        popupMenu.setOnMenuItemClickListener {
            actionListener.onTransactionMoreClick(transaction)
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    class TransactionsViewHolder(
        val binding: ItemTransactionBinding
    ): RecyclerView.ViewHolder(binding.root)
}