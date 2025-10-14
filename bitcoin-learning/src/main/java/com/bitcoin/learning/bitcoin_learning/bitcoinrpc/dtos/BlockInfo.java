package com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BlockInfo {
    private String hash;
    private int confirmations;
    private int height;
    private int version;
    private String versionHex;
    private String merkleroot;
    private long time;
    private long mediantime;
    private long nonce;
    private String bits;
    private String target;
    private double difficulty;
    private String chainwork;
    private int nTx;
    private int strippedsize;
    private int size;
    private int weight;
    private List<String> tx;
}
