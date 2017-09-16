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
import org.web3j.tx.Transfer
import org.web3j.utils.Convert

import static org.web3j.tx.ManagedTransaction.GAS_PRICE

@CompileStatic
class YabsTest {

    private File credentialsFile = new File(ClassLoader.getSystemClassLoader().getResource("rinkeby.json").file)
    private Credentials credentials = WalletUtils.loadCredentials("rinkeby", credentialsFile)
    private Web3j web3j = new JsonRpc2_0Web3j(new InfuraHttpService("https://rinkeby.infura.io/0ZevQ4HkUCzCVBOsYZcQ"))

    @Ignore
    @Test
    void shouldDeploy() throws Exception {
        String contractAddress = YabsContract.deploy(web3j, credentials, GAS_PRICE, Contract.GAS_LIMIT, BigInteger.ZERO)
                .get()
                .contractAddress
        File configuration = new File("src/main/resources/solidityConfiguration.properties")
        configuration.write("yabsAddress=$contractAddress")
        println(contractAddress)
    }

    @Ignore
    @Test
    void createNewRetailer() throws Exception {
        WalletUtils.generateFullNewWalletFile("password", new File("src/main/resources/retailers"))
    }

    @Ignore
    @Test
    void giveEtherToRetailers() throws Exception {
        giveOneEtherToAddress(addersOfRetailer("Coop"))
        giveOneEtherToAddress(addersOfRetailer("Fashwell"))
        giveOneEtherToAddress(addersOfRetailer("Siroop"))
        giveOneEtherToAddress(addersOfRetailer("Zalando"))
    }

    private void giveOneEtherToAddress(String address) {
        Transfer.sendFunds(web3j, credentials, address, BigDecimal.ONE, Convert.Unit.ETHER)
    }

    private String addersOfRetailer(String retailerName) {
        String name = retailerName
        def file = new File("src/main/resources/retailers/${name}.json")
        def credentials = WalletUtils.loadCredentials("password", file)
        return credentials.address
    }
}
