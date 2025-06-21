package com.tcs.trainTicketManagementSystem.train.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Enum representing different train class types and fare categories.
 * The enum names match the DB values for compatibility.
 */
public enum ClassType {
    _1AC("1AC"),
    _2AC("2AC"),
    _3AC("3AC"),
    SL("SL"),
    Sleeper_AC("Sleeper-AC"),
    Sleeper_NonAC("Sleeper-NonAC"),
    Seat("Seat");

    private final String dbValue;

    ClassType(String dbValue) {
        this.dbValue = dbValue;
    }

    @JsonValue
    @Override
    public String toString() {
        return dbValue;
    }

    @JsonCreator
    public static ClassType fromJson(String value) {
        for (ClassType type : values()) {
            if (type.dbValue.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown class type: " + value);
    }

    public static ClassType fromDbValue(String value) {
        return fromJson(value);
    }

    public String getDbValue() {
        return dbValue;
    }

    @Converter(autoApply = true)
    public static class ClassTypeConverter implements AttributeConverter<ClassType, String> {
        @Override
        public String convertToDatabaseColumn(ClassType attribute) {
            return attribute != null ? attribute.dbValue : null;
        }

        @Override
        public ClassType convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return ClassType.fromDbValue(dbData);
        }
    }
} 