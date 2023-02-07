package br.com.pensarcomodev.utils;

import org.hashids.Hashids;

public class HashId {

    private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    private static final Hashids HASHIDS = new Hashids("ejcn08934dpjy", 6, ALPHABET);

    public static String hash(Long id) {
        return HASHIDS.encode(id);
    }

    public static Long decodeHash(String hashId) {
        return HASHIDS.decode(hashId)[0];
    }
}
