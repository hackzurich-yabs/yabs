package com.yabs.hackzurich.solidity

import groovy.transform.CompileStatic
import org.junit.Ignore
import org.junit.Test
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.JsonRpc2_0Web3j
import org.web3j.protocol.infura.InfuraHttpService
import org.web3j.tx.Contract

import static org.web3j.tx.ManagedTransaction.GAS_PRICE

@CompileStatic
class YabsTest {

    private File credentialsFile = new File(ClassLoader.getSystemClassLoader().getResource("rinkeby.json").file)
    private Credentials credentials = WalletUtils.loadCredentials("rinkeby", credentialsFile)
    private Web3j web3j = new JsonRpc2_0Web3j(new InfuraHttpService("https://rinkeby.infura.io/0ZevQ4HkUCzCVBOsYZcQ"))

    @Ignore
    @Test
    void shouldDeploy() throws Exception {
        println(YabsContract.deploy(web3j, credentials, GAS_PRICE, Contract.GAS_LIMIT, BigInteger.ZERO)
                .get()
                .contractAddress)
    }
}
