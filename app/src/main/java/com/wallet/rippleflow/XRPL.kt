
package com.wallet.rippleflow

import okhttp3.HttpUrl.Companion.toHttpUrl
import org.xrpl.xrpl4j.client.XrplClient
import org.xrpl.xrpl4j.crypto.keys.Base58EncodedSecret
import org.xrpl.xrpl4j.crypto.keys.KeyPair
import org.xrpl.xrpl4j.crypto.keys.Seed
import org.xrpl.xrpl4j.crypto.signing.SingleSignedTransaction
import org.xrpl.xrpl4j.crypto.signing.bc.BcSignatureService
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams
import org.xrpl.xrpl4j.model.client.fees.FeeUtils
import org.xrpl.xrpl4j.model.transactions.Address
import org.xrpl.xrpl4j.model.transactions.Payment
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount


class XRPL(secretKey: String = "") {

    var xrplClient = XrplClient("https://s.altnet.rippletest.net:51234/".toHttpUrl())
    val seed = Seed.fromBase58EncodedSecret(Base58EncodedSecret.of(secretKey))
    val keyPair: KeyPair = seed.deriveKeyPair()
    val address = keyPair.publicKey().deriveAddress();
    var signatureService = BcSignatureService()
    var feeResult = xrplClient.fee()
    fun getAddress(): String{
        return address.toString()
    }
    fun getBalance(): Long {
        val requestParams = AccountInfoRequestParams.of(address)
        val accountInfoResult = xrplClient.accountInfo(requestParams)
        return accountInfoResult.accountData().balance().toString().toLong()
    }
    fun sendXRP(receiverAddress: String, amount:Long): String? {
        val requestParams = AccountInfoRequestParams.of(address)
        val accountInfoResult = xrplClient.accountInfo(requestParams)
        println(accountInfoResult.accountData().sequence())
        val payment: Payment = Payment.builder()
            .account(address)
            .fee(FeeUtils.computeNetworkFees(feeResult).recommendedFee())
            .sequence(accountInfoResult.accountData().sequence())
            .destination(Address.of(receiverAddress))
            .amount(XrpCurrencyAmount.ofDrops(amount))
            .signingPublicKey(keyPair.publicKey())
            .build()
        println("Constructed Payment: $payment")

        val signedTransaction: SingleSignedTransaction<Payment>  = signatureService.sign(keyPair.privateKey(),payment);
        val result = xrplClient.submit<Payment>(signedTransaction)
        println(result)
        println("Payment successful: https://testnet.xrpl.org/transactions/"+result.transactionResult().hash());
        return result.transactionResult().hash().toString();
    }
}