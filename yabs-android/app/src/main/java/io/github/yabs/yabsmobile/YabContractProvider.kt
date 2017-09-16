package io.github.yabs.yabsmobile

import io.github.yabs.yabsmobile.solidity.YabsContract
import io.reactivex.Observable
import org.web3j.protocol.core.JsonRpc2_0Web3j
import org.web3j.protocol.infura.InfuraHttpService
import org.web3j.tx.Contract
import org.web3j.tx.ManagedTransaction
import java.util.concurrent.Future

class YabContractService(private val contractAdressApi: ContractAdressApi) {

    private val web3 by lazy { JsonRpc2_0Web3j(InfuraHttpService("https://rinkeby.infura.io/0ZevQ4HkUCzCVBOsYZcQ")) }

    fun <T> executeRx(f: YabsContract.() -> Future<out T>): Observable<out T> {
        return contractAdressApi.address().flatMap {
            val yabsContract = YabsContract.load(it, web3, RetailerDetails.walletManager.getWallet(), ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT)
            Observable.fromFuture(f(yabsContract))
        }
    }

}