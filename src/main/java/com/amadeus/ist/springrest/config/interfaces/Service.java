package com.amadeus.ist.springrest.config.interfaces;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface Service<T> {
    default List<T> filter(List<T> list, Predicate<T> predicate) {
        return list.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    default List<T> sort(List<T> list) {
        return list.stream()
                .sorted()
                .collect(Collectors.toList());
    }
}
