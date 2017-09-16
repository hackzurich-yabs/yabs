package com.yabs.hackzurich.solidity;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version 2.3.0.
 */
public final class YabsContract extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b5b5b5b610719806100216000396000f300606060405236156100a15763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416632db678bf81146100a65780633340ab2f146100ca578063403b2ba7146100f4578063507cd30f146101185780635b14972c1461013c5780635e238103146101665780636c951ccc146101be578063b77751ab14610216578063c23f001f1461023a578063d4fac45d14610271575b600080fd5b34156100b157600080fd5b6100c8600160a060020a03600435166024356102a8565b005b34156100d557600080fd5b6100c8600435600160a060020a0360243516604435606435610305565b005b34156100ff57600080fd5b6100c8600160a060020a036004351660243561038b565b005b341561012357600080fd5b6100c8600160a060020a036004351660243561048c565b005b341561014757600080fd5b6100c8600435600160a060020a03602435166044356064356104bb565b005b341561017157600080fd5b610188600160a060020a0360043516602435610541565b6040518084600160a060020a0316600160a060020a03168152602001838152602001828152602001935050505060405180910390f35b34156101c957600080fd5b610188600160a060020a0360043516602435610575565b6040518084600160a060020a0316600160a060020a03168152602001838152602001828152602001935050505060405180910390f35b341561022157600080fd5b6100c8600160a060020a03600435166024356105a9565b005b341561024557600080fd5b61025f600160a060020a03600435811690602435166106a8565b60405190815260200160405180910390f35b341561027c57600080fd5b61025f600160a060020a03600435811690602435166106c2565b60405190815260200160405180910390f35b600160a060020a0333811660009081526020818152604080832093861683529290522054819010156102d657fe5b600160a060020a03338116600090815260208181526040808320938616835292905220805482900390555b5050565b60606040519081016040908152600160a060020a038086168352602080840186905282840185905233909116600090815260018252828120888252909152208151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0391909116178155602082015181600101556040820151600290910155505b50505050565b600160a060020a0382166000818152600160209081526040808320858452825280832060028101549484528383528184208480529092529091205490919010156103d157fe5b6001810154600160a060020a03338116600090815260208181526040808320865490941683529290522054101561040457fe5b600281018054600160a060020a0385811660009081526020818152604080832083805280835281842080549690960390955594543384168352828252858320838052808352868420805490920190915560018701805488548616855295835286842080549096019095559354865490931682529290925291902080549190910390555b505050565b600160a060020a03828116600090815260208181526040808320339094168352929052208054820190555b5050565b60606040519081016040908152600160a060020a038086168352602080840186905282840185905233909116600090815260028252828120888252909152208151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0391909116178155602082015181600101556040820151600290910155505b50505050565b60026020818152600093845260408085209091529183529120805460018201549190920154600160a060020a039092169183565b60016020818152600093845260408085209091529183529120805491810154600290910154600160a060020a039092169183565b600160a060020a03808316600081815260026020908152604080832086845282528083206001810154948452838352818420815490961684529490915290205410156105f157fe5b6002810154600160a060020a033316600090815260208181526040808320838052909152902054101561062057fe5b600281018054600160a060020a0385811660009081526020818152604080832083805280835281842080549096019095559454338416835282825285832083805280835286842080549290920390915560018701805488548616855295835286842080549690960390955593548654909316825292909252919020805490910190555b505050565b600060208181529281526040808220909352908152205481565b600160a060020a03808316600090815260208181526040808320938516835292905220545b929150505600a165627a7a723058200ea34220b0f502482fb8744f381c56f09a351b3522b9ac5afe086106c86a80730029";

    private YabsContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private YabsContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public Future<TransactionReceipt> claimPromoCode(Address retailer, Uint256 points) {
        Function function = new Function("claimPromoCode", Arrays.<Type>asList(retailer, points), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> createBuyOffer(Uint256 id, Address retailer, Uint256 points, Uint256 yabs) {
        Function function = new Function("createBuyOffer", Arrays.<Type>asList(id, retailer, points, yabs), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> acceptBuyOffer(Address user, Uint256 id) {
        Function function = new Function("acceptBuyOffer", Arrays.<Type>asList(user, id), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> addPoints(Address user, Uint256 points) {
        Function function = new Function("addPoints", Arrays.<Type>asList(user, points), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> createSellOffer(Uint256 id, Address retailer, Uint256 points, Uint256 yabs) {
        Function function = new Function("createSellOffer", Arrays.<Type>asList(id, retailer, points, yabs), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<List<Type>> sellOffers(Address param0, Uint256 param1) {
        Function function = new Function("sellOffers", 
                Arrays.<Type>asList(param0, param1), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<List<Type>> buyOffers(Address param0, Uint256 param1) {
        Function function = new Function("buyOffers", 
                Arrays.<Type>asList(param0, param1), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> acceptSellOffer(Address user, Uint256 id) {
        Function function = new Function("acceptSellOffer", Arrays.<Type>asList(user, id), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint256> balances(Address param0, Address param1) {
        Function function = new Function("balances", 
                Arrays.<Type>asList(param0, param1), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> getBalance(Address user, Address retailer) {
        Function function = new Function("getBalance", 
                Arrays.<Type>asList(user, retailer), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<YabsContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(YabsContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<YabsContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(YabsContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static YabsContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new YabsContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static YabsContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new YabsContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
