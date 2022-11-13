package br.com.pensarcomodev;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JavaUtils {

    public static <T, U> List<U> map(Collection<T> input, Function<T, U> map) {
        return input.stream().map(map).collect(Collectors.toList());
    }

    public static <T, U> Set<U> set(Collection<T> input, Function<T, U> map) {
        return input.stream().map(map).collect(Collectors.toSet());
    }
}
