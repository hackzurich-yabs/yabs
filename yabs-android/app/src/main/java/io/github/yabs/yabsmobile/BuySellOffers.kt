package io.github.yabs.yabsmobile

import java.math.BigInteger

data class BuySellOffers(val buyOffers: List<OfferData>,
                         val sellOffers: List<OfferData>)

data class OfferData(val userKey: String,
                     val uid: Long,
                     val retailersKey: String,
                     val points: BigInteger,
                     val yabsPoints: BigInteger)