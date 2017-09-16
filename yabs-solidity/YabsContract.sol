pragma solidity ^0.4.16;


contract YabsContract {

    mapping (address => mapping (address => uint256)) public balances;

    mapping (address => mapping (uint256 => Offer)) public buyOffers;

    mapping (address => mapping (uint256 => Offer)) public sellOffers;

    struct Offer
    {
    address retailer;
    uint256 points;
    uint256 yabs;
    }

    function YabsContract() {
        balances[msg.sender][0x0] = 1000000000000000000000;
    }

    function transferYabs(address receiver, uint256 yabs) {
        assert(balances[msg.sender][0x0] >= yabs);
        balances[msg.sender][0x0] = balances[msg.sender][0x0] - yabs;
        balances[receiver][0x0] = balances[receiver][0x0] + yabs;
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

    function acceptBuyOffer(address user, uint256 id) {
        var offer = buyOffers[user][id];
        assert(balances[user][0x0] >= offer.yabs);
        assert(balances[msg.sender][offer.retailer] >= offer.points);
        balances[user][0x0] = balances[user][0x0] - offer.yabs;
        balances[msg.sender][0x0] = balances[msg.sender][0x0] + offer.yabs;
        balances[user][offer.retailer] = balances[user][offer.retailer] + offer.points;
        balances[msg.sender][offer.retailer] = balances[msg.sender][offer.retailer] - offer.points;
    }

    function createSellOffer(uint256 id, address retailer, uint256 points, uint256 yabs) {
        sellOffers[msg.sender][id] = Offer(retailer, points, yabs);
    }

    function acceptSellOffer(address user, uint256 id) {
        var offer = sellOffers[user][id];
        assert(balances[user][offer.retailer] >= offer.points);
        assert(balances[msg.sender][0x0] >= offer.yabs);
        balances[user][0x0] = balances[user][0x0] + offer.yabs;
        balances[msg.sender][0x0] = balances[msg.sender][0x0] - offer.yabs;
        balances[user][offer.retailer] = balances[user][offer.retailer] - offer.points;
        balances[msg.sender][offer.retailer] = balances[msg.sender][offer.retailer] + offer.points;
    }
}