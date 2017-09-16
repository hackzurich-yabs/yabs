package io.github.yabs.yabsmobile

import java.io.Serializable
import java.math.BigInteger

data class Retailer(val publicKey: String,
                    val name: String,
                    val balance: BigInteger,
                    val promoCodePrice: BigInteger) : Serializable