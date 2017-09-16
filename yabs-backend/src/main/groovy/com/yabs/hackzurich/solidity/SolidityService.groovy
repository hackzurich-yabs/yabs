package com.yabs.hackzurich.solidity

import com.yabs.hackzurich.dao.WalletRepository
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.JsonRpc2_0Web3j
import org.web3j.protocol.infura.InfuraHttpService
import org.web3j.utils.Convert

@Service
@CompileStatic
class SolidityService {

    private static final Web3j web3j = new JsonRpc2_0Web3j(new InfuraHttpService("https://rinkeby.infura.io/0ZevQ4HkUCzCVBOsYZcQ"))

    private final WalletRepository walletRepository

    @Autowired
    SolidityService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository
    }

    YabsContract getYabsContract() {
        return getYabsContract(walletRepository.readOnlyCredentials)
    }

    YabsContract getYabsContract(String retailerKey) {
        return getYabsContract(walletRepository.readCredentials(retailerKey))
    }

    YabsContract getYabsContract(Credentials credentials) {
        return YabsContract.load(SolidityUtils.yabsAddress,
                web3j,
                credentials,
                Convert.toWei("21", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(338742L))
    }
}
