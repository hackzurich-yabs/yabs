pragma solidity ^0.4.16;

contract Yabs {

  mapping (address => mapping (address => uint256)) public balances;

  function Yabs() {
  }

  function getBalance(address user, address retailer) constant returns (uint) {
    return balances[user][retailer];
  }
}