package br.com.pensarcomodev.controller;

import io.micronaut.data.model.Pageable;

public class PageUtils {

    public static Pageable sanitize(Pageable page) {
        if (page.getSize() == 0) {
            page = Pageable.from(page.getNumber(), 10, page.getSort());
        }
        return page;
    }
}
