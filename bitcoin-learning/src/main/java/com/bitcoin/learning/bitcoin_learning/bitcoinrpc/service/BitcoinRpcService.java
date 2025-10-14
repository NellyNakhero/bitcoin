package com.bitcoin.learning.bitcoin_learning.bitcoinrpc.service;

import com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos.BlockInfo;
import com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos.BlockchainInfo;
import com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos.WalletDirResult;
import com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos.WalletLoadResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BitcoinRpcService {

    @Value("${bitcoin.rpc-url}")
    private String rpcUrl;

    @Value("${bitcoin.rpc-user}")
    private String rpcUser;

    @Value("${bitcoin.rpc-password}")
    private String rpcPassword;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Generic method to call bitcoin RPC and map result to given class
     */
    public <T> T callRpcMethod(String methodName, Object[] params, Class<T> resultClass) throws IOException {
        Map<String, Object> request = new HashMap<>();
        request.put("jsonrpc", "1.0");
        request.put("id", "springboot-client");
        request.put("method", methodName);
        request.put("params", params == null? new Object[]{} : params);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(rpcUser, rpcPassword);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(rpcUrl, requestEntity, String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new IOException("RPC call " + methodName + "failed" + responseEntity.getBody());
        }

        JsonNode node = objectMapper.readTree(responseEntity.getBody());
        JsonNode resultNode = node.get("result");

        return objectMapper.treeToValue(resultNode, resultClass);
    }

    /**
     * Simplified chain info as string
     */
    public String getChainInfo() throws IOException {
        BlockchainInfo blockchainInfo = callRpcMethod("getblockchaininfo", null, BlockchainInfo.class);
        return objectMapper.writeValueAsString(blockchainInfo);
    }

    /**
     * Get block by hash
     */
    public BlockInfo getBlock(String blockHash) throws IOException {
        return callRpcMethod("getblock", new Object[]{blockHash}, BlockInfo.class);
    }

    /**
     * List wallet directories
     */
    public List<Map<String, Object>> listWalletDir() throws IOException {
        WalletDirResult result = callRpcMethod("listwalletdir", null, WalletDirResult.class);
        return result.getWallets();
    }

    /**
     * List Wallets
     */
    public List<String> listWallets() throws IOException {
        String json = objectMapper.writeValueAsString(callRpcMethod("listwallets", null, Object.class));
        return objectMapper.readValue(json, new TypeReference<>() {});
    }

    public String getNewAddress(String label, String addressType) throws IOException {
        return callRpcMethod("getnewaddress", new Object[]{label, addressType}, String.class);
    }

    public String loadWallet(String walletName) throws IOException {
        WalletLoadResult result = callRpcMethod("loadwallet", new Object[]{walletName}, WalletLoadResult.class);
        return result.getName();
    }
}
