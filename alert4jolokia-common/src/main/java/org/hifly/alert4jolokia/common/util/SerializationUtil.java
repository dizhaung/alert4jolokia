package org.hifly.alert4jolokia.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;


public class SerializationUtil {

    public static <T> T fromJSON(final TypeReference<T> type, final String json) {
        T data = null;

        try {
            data = new ObjectMapper().readValue(json, type);
        } catch (Exception e) {
            // Handle the problem
        }
        return data;
    }

    public static <T> T fromJSON(Class<T> clazz, final File json) {
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (Exception e) {
            // Handle the problem
        }
        return null;
    }


}
