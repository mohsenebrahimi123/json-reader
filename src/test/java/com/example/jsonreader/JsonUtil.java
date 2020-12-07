package com.example.jsonreader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.AutoCloseInputStream;

import java.io.InputStream;
import java.util.Arrays;

import static java.nio.charset.Charset.defaultCharset;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static String loadFromFile(String resource) {
        final InputStream is = JsonUtil.class.getClassLoader().getResourceAsStream(resource);
        return IOUtils.toString(new AutoCloseInputStream(is), defaultCharset());
    }

    public static String loadFromFile(String resource, Object... args) {
        return String.format(loadFromFile(resource), Arrays.stream(args).map(JsonUtil::toJson).toArray());
    }

    @SneakyThrows
    public static <T> String toJson(final T t) {
        return MAPPER.writeValueAsString(t);
    }
}
