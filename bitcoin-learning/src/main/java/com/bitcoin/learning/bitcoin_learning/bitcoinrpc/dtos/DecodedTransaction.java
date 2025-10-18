package com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DecodedTransaction {
    private String txid;
    private String hash;
    private int size;
    private int vsize;
    private int version;
    private long locktime;
    private List<Vin> vin;
    private List<Vout> vout;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Vin {
        private String txid;
        private int vout;
        private ScriptSig scriptSig;
        private long sequence;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ScriptSig {
            private String asm;
            private String hex;
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Vout {
        private double value;
        private int n;
        private ScriptPubKey scriptPubKey;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ScriptPubKey {
            private String asm;
            private String hex;
            private int reqSigs;
            private String type;
            private List<String> addresses;
        }
    }
}

