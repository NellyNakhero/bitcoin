package com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BlockInfoResponse {
    @JsonProperty("id")
    private String hash;

    @JsonProperty("height")
    private int height;

    @JsonProperty("version")
    private int version;

    @JsonProperty("merkle_root")
    private String merkleroot;

    @JsonProperty("timestamp")
    private long time;

    @JsonProperty("tx_count")
    private int tx_count;

    @JsonProperty("size")
    private int size;

    @JsonProperty("weight")
    private int weight;

    @JsonProperty("previousblockhash")
    private String previousblockhash;

    @JsonProperty("tx")
    private List<String> tx;
}
