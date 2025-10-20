package com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos;

import lombok.Data;

@Data
public class KeyDerivationRequest {
    private String descriptor; // full descriptor, e.g., pkh(tprv.../44h/1h/0h/0/*)
    private int index;         // index to derive (e.g., 0, 1, 2, ...)
    private String addressType; // e.g., "p2pkh", "p2wpkh"
}
