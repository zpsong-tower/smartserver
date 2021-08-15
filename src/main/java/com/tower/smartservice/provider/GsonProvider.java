package com.tower.smartservice.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * 用于设置Jersey的Json转换器
 * 替换JacksonJsonProvider
 *
 * @param <T> 任意类型泛型定义
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/20 15:32
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GsonProvider<T> implements MessageBodyReader<T>, MessageBodyWriter<T> {
	// 共用一个全局的Gson
	private static final Gson gson;

	static { // 静态代码块初始化Gson
		GsonBuilder builder = new GsonBuilder()
				.serializeNulls() // 序列化为null的字段
				.excludeFieldsWithoutExposeAnnotation() // 仅仅处理带有@Expose注解的变量
				.enableComplexMapKeySerialization(); // 支持Map

		// 添加对 Java8 - LocalDateTime 时间类型的支持
		builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());
		gson = builder.create();
	}

	public GsonProvider() {
	}

	/**
	 * 取得一个全局的Gson
	 *
	 * @return Gson
	 */
	public static Gson getGson() {
		return gson;
	}

	@Override
	public long getSize(T t, Class<?> type, Type genericType,
	                    Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
	                          Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public T readFrom(Class<T> type, Type genericType, Annotation[] annotations,
	                  MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
	                  InputStream entityStream) throws IOException, WebApplicationException {
		// 把Json的字符串数据, 转换为T类型的实例
		try (JsonReader reader = new JsonReader(new InputStreamReader(entityStream, StandardCharsets.UTF_8))) {
			return gson.fromJson(reader, genericType);
		}
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
	                           Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations,
	                    MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
	                    OutputStream entityStream) throws IOException, WebApplicationException {
		// 把一个T类的实例输出到Http输出流中
		try (JsonWriter jsonWriter = gson.newJsonWriter(new OutputStreamWriter(entityStream, StandardCharsets.UTF_8))) {
			gson.toJson(t, genericType, jsonWriter);
		}
	}
}