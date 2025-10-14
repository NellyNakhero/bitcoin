package com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WalletDirResult {
    private List<Map<String, Object>> wallets;
}
