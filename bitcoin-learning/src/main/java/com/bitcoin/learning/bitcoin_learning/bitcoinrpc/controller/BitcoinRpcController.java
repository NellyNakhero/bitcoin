package com.bitcoin.learning.bitcoin_learning.bitcoinrpc.controller;

import com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos.BlockInfo;
import com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos.DecodedTransaction;
import com.bitcoin.learning.bitcoin_learning.bitcoinrpc.service.BitcoinRpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bitcoin")
public class BitcoinRpcController {
    private final BitcoinRpcService bitcoinRpcService;


    @GetMapping("/blockchain-info")
    public String getBlockchainInfo() throws Exception {
        return bitcoinRpcService.getChainInfo();
    }

    @GetMapping("/block/{hash}")
    public ResponseEntity<BlockInfo> getBlock(@PathVariable String hash) throws IOException {
        return ResponseEntity.ok(bitcoinRpcService.getBlock(hash));
    }

    @GetMapping("/loadwallet/{name}")
    public ResponseEntity<String> loadWallet(@PathVariable String name) throws IOException {
        return ResponseEntity.ok(bitcoinRpcService.loadWallet(name));
    }

    @GetMapping("/newaddress")
    public ResponseEntity<String> getNewAddress(
            @RequestParam String label,
            @RequestParam String type // legacy, bech32, bech32m
    ) throws IOException {
        return ResponseEntity.ok(bitcoinRpcService.getNewAddress(label, type));
    }


    @GetMapping("/wallets")
    public ResponseEntity<List<String>> listWallets() throws IOException {
        return ResponseEntity.ok(bitcoinRpcService.listWallets());
    }

    @GetMapping("/walletdir")
    public ResponseEntity<List<Map<String, Object>>> listWalletDir() throws IOException {
        return ResponseEntity.ok(bitcoinRpcService.listWalletDir());
    }

    @PostMapping("/decoderawtransaction")
    public ResponseEntity<DecodedTransaction> decodeRawTransaction(@RequestBody Map<String, String> body) throws IOException {
        String hex = body.get("hex");
        return ResponseEntity.ok(bitcoinRpcService.decodeRawTransaction(hex));
    }
}
