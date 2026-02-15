package com.fase4.fiap.infraestructure.auxiliary.configuration.web;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import jakarta.servlet.http.HttpServletRequest;

public class AcceptHeaderResolver extends AcceptHeaderLocaleResolver {

    List<Locale> LOCALES = Arrays.asList(new Locale("en"), new Locale("pt-BR"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
                ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
    }

}