package com.yabs.hackzurich.solidity;

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

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 * <p>
 * <p>Generated with web3j version 2.3.0.
 */
public final class YabsContract extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b5b600160a060020a0333166000908152602081815260408083208380529091529020683635c9adc5dea0000090555b5b6108ab8061004e6000396000f300606060405236156100ac5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416632db678bf81146100b15780633340ab2f146100d5578063403b2ba7146100ff578063507cd30f146101235780635b14972c146101475780635e238103146101715780636c951ccc146101c9578063a5633b6914610221578063b77751ab14610245578063c23f001f14610269578063d4fac45d146102a0575b600080fd5b34156100bc57600080fd5b6100d3600160a060020a03600435166024356102d7565b005b34156100e057600080fd5b6100d3600435600160a060020a0360243516604435606435610334565b005b341561010a57600080fd5b6100d3600160a060020a03600435166024356103ba565b005b341561012e57600080fd5b6100d3600160a060020a0360043516602435610534565b005b341561015257600080fd5b6100d3600435600160a060020a0360243516604435606435610563565b005b341561017c57600080fd5b610193600160a060020a03600435166024356105e9565b6040518084600160a060020a0316600160a060020a03168152602001838152602001828152602001935050505060405180910390f35b34156101d457600080fd5b610193600160a060020a036004351660243561061d565b6040518084600160a060020a0316600160a060020a03168152602001838152602001828152602001935050505060405180910390f35b341561022c57600080fd5b6100d3600160a060020a0360043516602435610651565b005b341561025057600080fd5b6100d3600160a060020a03600435166024356106c2565b005b341561027457600080fd5b61028e600160a060020a036004358116906024351661083a565b60405190815260200160405180910390f35b34156102ab57600080fd5b61028e600160a060020a0360043581169060243516610854565b60405190815260200160405180910390f35b600160a060020a03338116600090815260208181526040808320938616835292905220548190101561030557fe5b600160a060020a03338116600090815260208181526040808320938616835292905220805482900390555b5050565b60606040519081016040908152600160a060020a038086168352602080840186905282840185905233909116600090815260018252828120888252909152208151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0391909116178155602082015181600101556040820151600290910155505b50505050565b600160a060020a03821660008181526001602090815260408083208584528252808320600281015494845283835281842084805290925290912054909190101561040057fe5b6001810154600160a060020a03338116600090815260208181526040808320865490941683529290522054101561043357fe5b600281018054600160a060020a0385811660009081526020818152604080832083805280835281842080549690960390955594543384168352828252858320838052808352868420805490920190915560018701805488548616855295835286842080549096019095559354865490931682529290925290829020805491909103905560609051908101604090815260008083526020808401829052828401829052600160a060020a0387168252600181528282208683529052208151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0391909116178155602082015181600101556040820151600290910155505b505050565b600160a060020a03828116600090815260208181526040808320339094168352929052208054820190555b5050565b60606040519081016040908152600160a060020a038086168352602080840186905282840185905233909116600090815260028252828120888252909152208151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0391909116178155602082015181600101556040820151600290910155505b50505050565b60026020818152600093845260408085209091529183529120805460018201549190920154600160a060020a039092169183565b60016020818152600093845260408085209091529183529120805491810154600290910154600160a060020a039092169183565b600160a060020a0333166000908152602081815260408083208380529091529020548190101561067d57fe5b600160a060020a0333811660009081526020818152604080832083805282528083208054869003905592851682528181528282208280529052208054820190555b5050565b600160a060020a038083166000818152600260209081526040808320868452825280832060018101549484528383528184208154909616845294909152902054101561070a57fe5b6002810154600160a060020a033316600090815260208181526040808320838052909152902054101561073957fe5b600281018054600160a060020a0385811660009081526020818152604080832083805280835281842080549096019095559454338416835282825285832083805280835286842080549290920390915560018701805488548616855295835286842080549690960390955593548654909316825292909252908290208054909101905560609051908101604090815260008083526020808401829052828401829052600160a060020a0387168252600281528282208683529052208151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0391909116178155602082015181600101556040820151600290910155505b505050565b600060208181529281526040808220909352908152205481565b600160a060020a03808316600090815260208181526040808320938516835292905220545b929150505600a165627a7a723058203da61e95974d9861ab87ad6222060584ef29345621d376e9c2eba900f490eb6a0029";

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
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<List<Type>> buyOffers(Address param0, Uint256 param1) {
        Function function = new Function("buyOffers",
                Arrays.<Type>asList(param0, param1),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> transferYabs(Address receiver, Uint256 yabs) {
        Function function = new Function("transferYabs", Arrays.<Type>asList(receiver, yabs), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> acceptSellOffer(Address user, Uint256 id) {
        Function function = new Function("acceptSellOffer", Arrays.<Type>asList(user, id), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint256> balances(Address param0, Address param1) {
        Function function = new Function("balances",
                Arrays.<Type>asList(param0, param1),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> getBalance(Address user, Address retailer) {
        Function function = new Function("getBalance",
                Arrays.<Type>asList(user, retailer),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
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
