package com.example.jsonreader;

import static java.nio.charset.Charset.defaultCharset;
import static lombok.AccessLevel.PRIVATE;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.AutoCloseInputStream;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

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

	public static String loadFromTemplateFile(String resource, Map<String, Object> values) {
		VelocityEngine engine = new VelocityEngine();
		VelocityContext context = new VelocityContext();
		values.keySet().forEach(key -> context.put(key, values.get(key)));
		StringWriter sw = new StringWriter();
		engine.getTemplate(resource).merge(context, sw);
		//System.out.println(sw.toString());
		return sw.toString();
	}

	@SneakyThrows
	public static <T> String toJson(final T t) {
		return MAPPER.writeValueAsString(t);
	}
}
