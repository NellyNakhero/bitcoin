package com.bitcoin.learning.bitcoin_learning.bitcoinrpc.service;

import com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos.KeyDerivationRequest;
import com.bitcoin.learning.bitcoin_learning.bitcoinrpc.dtos.KeyDerivationResponse;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.script.Script;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyDerivationService {

    private String extractPath(String descriptor, int index) {
        // Extract everything after tprv
        int start = descriptor.indexOf("tprv");
        int pathStart = descriptor.indexOf("/", start);
        int pathEnd = descriptor.lastIndexOf(")");

        if (pathStart == -1) {
            throw new IllegalArgumentException("Descriptor must contain a derivation path.");
        }

        // Extract raw path: e.g., /44h/1h/0h/0/*)
        String rawPath = descriptor.substring(pathStart, pathEnd != -1 ? pathEnd : descriptor.length());

        // Replace '*' with index, and convert to BitcoinJ format (44H instead of 44h)
        return rawPath
                .replace("*", String.valueOf(index))
                .replace("h", "H")
                .replaceAll("^/", ""); // remove leading slash
    }

    private String extractTprv(String descriptor) {
        // Extract string starting with 'tprv' up to the first path slash
        int start = descriptor.indexOf("tprv");
        if (start == -1) {
            throw new IllegalArgumentException("Descriptor must contain 'tprv'");
        }

        // Either ends at first '/' (path start), or at the end of the string
        int end = descriptor.indexOf("/", start);
        return end == -1 ? descriptor.substring(start) : descriptor.substring(start, end);
    }

    public KeyDerivationResponse deriveKeyFromDescriptor(KeyDerivationRequest request) {
        String descriptor = request.getDescriptor().trim();
        int index = request.getIndex();

        //extract tprv and path
        String path = extractPath(descriptor, index);
        String tprv = extractTprv(descriptor);

        //pass tprv and derive key
        NetworkParameters params = TestNet3Params.get();

        //parse the tprv
        DeterministicKey masterKey = DeterministicKey.deserializeB58(null, tprv, params);

        //parse path like: 44H/1H/0H/0/2
        List<ChildNumber> fullPath = HDUtils.parsePath(path);

        //derive child key step by step
        DeterministicKey key = masterKey;
        for (ChildNumber child : fullPath) {
            key = HDKeyDerivation.deriveChildKey(key, child);
        }

        //convert to WIF and address
        String wif = key.getPrivateKeyAsWiF(params);

        // Choose address type: P2PKH (legacy), P2WPKH (bech32), etc.
        Script.ScriptType type = switch (request.getAddressType().toLowerCase()) {
            case "p2wpkh" -> Script.ScriptType.P2WPKH;
            case "p2sh" -> Script.ScriptType.P2SH;
            default -> Script.ScriptType.P2PKH; // fallback to legacy
        };
        Address address = Address.fromKey(params, key, type);

        return new KeyDerivationResponse(address.toString(), wif);
    }
}
