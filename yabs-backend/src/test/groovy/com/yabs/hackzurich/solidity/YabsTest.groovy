package com.yabs.hackzurich.solidity

import groovy.transform.CompileStatic
import org.junit.Ignore
import org.junit.Test
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256
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

    private final File configuration = new File("src/main/resources/solidityConfiguration.properties")
    private final String contractAddress = configuration.readLines().first().split("=")[1]
    private final File credentialsFile = new File(ClassLoader.getSystemClassLoader().getResource("rinkeby.json").file)
    private final Credentials credentials = WalletUtils.loadCredentials("rinkeby", credentialsFile)
    private final String url = "https://rinkeby.infura.io/0ZevQ4HkUCzCVBOsYZcQ"
    private final Web3j web3j = new JsonRpc2_0Web3j(new InfuraHttpService(url))
    private final String mobileAddress = "0x216d80a85bd731ed66f3026e26560cc0316c4fa4"

    @Ignore
    @Test
    void shouldDeploy() throws Exception {
        String contractAddress = YabsContract.deploy(web3j, credentials, GAS_PRICE, Contract.GAS_LIMIT, BigInteger.ZERO)
            .get()
            .contractAddress
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
    void shouldGiveEtherToRetailers() throws Exception {
        giveOneEtherToAddress(addersOfRetailer("Coop"))
        giveOneEtherToAddress(addersOfRetailer("Fashwell"))
        giveOneEtherToAddress(addersOfRetailer("Siroop"))
        giveOneEtherToAddress(addersOfRetailer("Zalando"))
    }

    @Ignore
    @Test
    void shouldGiveSomeMoneyToMobile() throws Exception {
        giveOneEtherToAddress(mobileAddress)
    }

    @Ignore
    @Test
    void shouldGiveSomeYabsToMobile() throws Exception {
        loadContract(credentials).transferYabs(new Address(mobileAddress), new Uint256(BigInteger.valueOf(1000L))).get()
    }

    @Ignore
    @Test
    void shouldGiveRetailersCoinsToMobile() throws Exception {
        giveRetailersCoinsToMobile("Coop", 1000L)
        giveRetailersCoinsToMobile("Zalando", 99L)
        giveRetailersCoinsToMobile("Fashwell", 200L)
    }

    private void giveRetailersCoinsToMobile(String retailerName, long points) {
        loadContract(retailerCredentials(retailerName)).addPoints(new Address(mobileAddress), new Uint256(BigInteger.valueOf(points))).get()
    }

    private void giveOneEtherToAddress(String address) {
        Transfer.sendFunds(web3j, credentials, address, BigDecimal.ONE, Convert.Unit.ETHER)
    }

    private String addersOfRetailer(String retailerName) {
        return retailerCredentials(retailerName).address
    }

    private Credentials retailerCredentials(String retailerName) {
        String name = retailerName
        def file = new File("src/main/resources/retailers/${name}.json")
        return WalletUtils.loadCredentials("password", file)
    }

    private YabsContract loadContract(Credentials credentials) {
        return YabsContract.load(contractAddress, web3j, credentials, GAS_PRICE, Contract.GAS_LIMIT)
    }
}
