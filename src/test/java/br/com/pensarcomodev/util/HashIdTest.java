package br.com.pensarcomodev.util;

import br.com.pensarcomodev.utils.HashId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashIdTest {

    @Test
    public void testIds() {
        testHashId(1L);
        testHashId(2L);
        testHashId(100L);
        testHashId(1000L);
        testHashId(1001L);
        testHashId(10000L);
        testHashId(1999999999999L);
    }

    public void testHashId(Long id) {
        String hashId = HashId.hash(id);
        System.out.println(hashId);
        Long decodedId = HashId.decodeHash(hashId);
        assertEquals(id, decodedId);
    }
}
