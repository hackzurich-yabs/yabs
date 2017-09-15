pragma solidity ^0.4.16;


contract Yabs {

    mapping (address => mapping (address => uint256)) public balances;

    mapping (address => mapping (uint256 => Offer)) public buyOffers;

    mapping (address => mapping (uint256 => Offer)) public sellOffers;

    struct Offer
    {
    address retailer;
    uint256 points;
    uint256 yabs;
    }

    function Yabs() {
    }

    function getBalance(address user, address retailer) constant returns (uint256) {
        return balances[user][retailer];
    }

    function addPoints(address user, uint256 points) {
        balances[user][msg.sender] = balances[user][msg.sender] + points;
    }

    function claimPromoCode(address retailer, uint256 points) {
        assert(balances[msg.sender][retailer] >= points);
        balances[msg.sender][retailer] = balances[msg.sender][retailer] - points;
    }

    function createBuyOffer(uint256 id, address retailer, uint256 points, uint256 yabs) {
        buyOffers[msg.sender][id] = Offer(retailer, points, yabs);
    }

    function createSellOffer(uint256 id, address retailer, uint256 points, uint256 yabs) {
        sellOffers[msg.sender][id] = Offer(retailer, points, yabs);
    }
}