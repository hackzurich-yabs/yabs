package io.github.yabs.yabsmobile

import java.io.Serializable

data class Retailer(val publicKey: String,
                    val name: String,
                    val balance: String) : Serializable