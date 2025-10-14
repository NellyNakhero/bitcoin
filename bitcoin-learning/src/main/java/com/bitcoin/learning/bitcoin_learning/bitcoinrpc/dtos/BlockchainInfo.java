package com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BlockchainInfo {
    private String chain;
    private int blocks;
}
