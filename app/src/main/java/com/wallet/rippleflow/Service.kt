package com.wallet.rippleflow

import org.xrpl.xrpl4j.crypto.signing.SingleSignedTransaction
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams
import org.xrpl.xrpl4j.model.client.fees.FeeUtils
import org.xrpl.xrpl4j.model.transactions.Address
import org.xrpl.xrpl4j.model.transactions.Payment
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount

interface Service {

    suspend fun getAddress(): String

    suspend fun getBalance(): Long

    suspend fun sendXRP(receiverAddress: String, amount:Long): String?
}