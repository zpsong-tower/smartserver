package com.tower.smartservice.provider;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 规定LocalDateTime与Json的相互转换
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/20 15:46
 */
public class LocalDateTimeConverter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
	/**
	 * 时间转换的格式为：yyyy-MM-dd'T'HH:mm:ss.SSS
	 */
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
	        "yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);

	@Override
	public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
		// LocalDateTime 转 JsonElement
		return new JsonPrimitive(FORMATTER.format(src));
	}

	@Override
	public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		// JsonElement 转 LocalDateTime
		return FORMATTER.parse(json.getAsString(), LocalDateTime::from);
	}
}