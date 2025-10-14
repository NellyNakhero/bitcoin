package com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WalletLoadResult {
    private String name;
}
