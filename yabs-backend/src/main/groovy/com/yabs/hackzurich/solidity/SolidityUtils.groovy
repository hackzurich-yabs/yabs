package com.yabs.hackzurich.solidity

import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.JsonRpc2_0Web3j
import org.web3j.protocol.infura.InfuraHttpService
import org.web3j.utils.Convert;

class SolidityUtils {

    private static final String yabsAddress = "0x358552e3c1a6e1071426d841b4ed301a56f30612"

    private static final File credentialsFile = new File(ClassLoader.getSystemClassLoader().getResource("rinkeby.json").file)
    private static final Credentials credentials = WalletUtils.loadCredentials("rinkeby", credentialsFile)
    private static final Web3j web3j = new JsonRpc2_0Web3j(new InfuraHttpService("https://rinkeby.infura.io/0ZevQ4HkUCzCVBOsYZcQ"))

    static final YabsContract getYabsContract() {
        return YabsContract.load(SolidityUtils.yabsAddress,
                web3j,
                credentials,
                Convert.toWei("21", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(338742L))
    }
}
