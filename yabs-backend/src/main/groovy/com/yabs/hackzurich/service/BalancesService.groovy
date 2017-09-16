package com.yabs.hackzurich.service

import com.yabs.hackzurich.dto.BalanceData
import com.yabs.hackzurich.solidity.SolidityUtils
import com.yabs.hackzurich.solidity.Yabs
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.web3j.abi.datatypes.Address
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.JsonRpc2_0Web3j
import org.web3j.protocol.infura.InfuraHttpService
import org.web3j.utils.Convert

@Service
@CompileStatic
class BalancesService {

    @Autowired
    private RetailersService retailersService

    private File credentialsFile = new File(ClassLoader.getSystemClassLoader().getResource("rinkeby.json").file)
    private Credentials credentials = WalletUtils.loadCredentials("rinkeby", credentialsFile)
    private Web3j web3j = new JsonRpc2_0Web3j(new InfuraHttpService("https://rinkeby.infura.io/0ZevQ4HkUCzCVBOsYZcQ"))

    List<BalanceData> getBalancesForUser(String userPublicKey) {

        Yabs yabsContract = getYabsContract()

        return retailersService.list().stream()
                .map{retailer -> yabsContract.getBalance(new Address(userPublicKey), new Address(retailer.name))}
                .collect()
    }

    private Yabs getYabsContract() {
        return Yabs.load(SolidityUtils.yabsAddress,
                web3j,
                credentials,
                Convert.toWei("21", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(338742L))
    }
}
