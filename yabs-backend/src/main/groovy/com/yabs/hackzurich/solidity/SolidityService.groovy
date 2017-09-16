package com.yabs.hackzurich.solidity

import com.yabs.hackzurich.exception.TransactionVerificationException
import com.yabs.hackzurich.service.WalletService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.JsonRpc2_0Web3j
import org.web3j.protocol.core.Request
import org.web3j.protocol.core.methods.response.EthTransaction
import org.web3j.protocol.core.methods.response.Transaction
import org.web3j.protocol.infura.InfuraHttpService
import org.web3j.utils.Convert

@Service
@CompileStatic
@Slf4j
class SolidityService {

    private static final Web3j web3j = new JsonRpc2_0Web3j(new InfuraHttpService("https://rinkeby.infura.io/0ZevQ4HkUCzCVBOsYZcQ"))

    @Autowired
    private WalletService walletRepository

    @Autowired
    private SolidityConfiguration solidityConfiguration

    YabsContract getYabsContract() {
        return getYabsContract(walletRepository.readOnlyCredentials)
    }

    YabsContract getYabsContract(String retailerKey) {
        return getYabsContract(walletRepository.readCredentials(retailerKey))
    }

    YabsContract getYabsContract(Credentials credentials) {
        return YabsContract.load(
            solidityConfiguration.yabsAddress,
            web3j,
            credentials,
            Convert.toWei("21", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(338742L)
        )
    }

    void checkTransactionOnBlockchain(String userKey, String retailerKey, String transactionHash, BigInteger points) {
        Request<?, EthTransaction> request = web3j.ethGetTransactionByHash(transactionHash)
        EthTransaction transactionResponse = request.send()
        if (!transactionResponse.transaction.isPresent()) {
            log.info("Transaction with hash ${transactionHash} not found")
            throw new TransactionVerificationException()
        }
        Transaction transaction = transactionResponse.transaction.get()
        if (transaction.to != solidityConfiguration.yabsAddress) {
            log.info("Different transaction to: ${transaction.to}, ${retailerKey}")
            throw new TransactionVerificationException()
        }
        // TODO verify userKey, points - with transaction.raw probably
    }
}
