package com.example.myapplication

import okhttp3.HttpUrl.Companion.toHttpUrl
import org.xrpl.xrpl4j.client.XrplClient
import org.xrpl.xrpl4j.wallet.DefaultWalletFactory


class XRPL {
    val walletSecret: String = "sEdVSeKU5vPryw9QKWu2qx7ALDTm98V";
    var rippledUrl = "https://s.altnet.rippletest.net:51234/".toHttpUrl()
   // var xrplClient = XrplClient(rippledUrl)
    var walletFactory = DefaultWalletFactory.getInstance()
    var testWallet = walletFactory.fromSeed(walletSecret, true)
    public var classicAddress = testWallet.classicAddress();



}