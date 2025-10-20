package com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeyDerivationResponse {
    private String address;
    private String privateKeyWIF;
}
