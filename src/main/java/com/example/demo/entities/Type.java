package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;
public enum Type {

    TYPE1("CSV"),
    TYPE2("MYSQL"),
    TYPE3("JSON"),
    TYPE4("LDAP");

    private String code;

    private Type(String code) {
        this.code=code;
    }


    @JsonCreator
    public static Type decode(final String code) {
        return Stream.of(Type.values()).filter(targetEnum -> targetEnum.code.equals(code)).findFirst().orElse(null);
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}