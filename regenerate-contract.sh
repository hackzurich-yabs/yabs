./yabs-solidity/compile.sh
web3j solidity generate yabs-solidity/YabsContract.bin yabs-solidity/YabsContract.abi -o yabs-backend/src/main/groovy -p com.yabs.hackzurich.solidity
web3j solidity generate yabs-solidity/YabsContract.bin yabs-solidity/YabsContract.abi -o yabs-android/app/src/main/java -p io.github.yabs.yabsmobile.solidity