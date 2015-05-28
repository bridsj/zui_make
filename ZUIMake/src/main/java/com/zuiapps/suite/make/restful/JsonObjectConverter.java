/*
 * Copyright (C) 2012 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zuiapps.suite.make.restful;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.zuiapps.suite.utils.log.LogUtil;
import com.zuiapps.suite.utils.string.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class JsonObjectConverter implements Converter {
	
	private String encoding;

	public JsonObjectConverter() {
		this("UTF-8");
	}

	public JsonObjectConverter(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public Object fromBody(TypedInput body, Type type) throws ConversionException {
		String charset = "UTF-8";
		if (body.mimeType() != null) {
			charset = MimeUtil.parseCharset(body.mimeType());
		}
		try {
			String json = StringUtils.inputSteamToString(body.in(), charset);
			LogUtil.i("Json Object Converter", json);
			
			JSONObject jsonObject = new JSONObject(json);
			if (jsonObject.optInt("result") == 1) {
				if (jsonObject.has("data")) {
					JSONObject dataObj = jsonObject.optJSONObject("data");
					if (dataObj != null) {
						return dataObj;
					} else {
						JSONArray array = jsonObject.optJSONArray("data");
						if (array != null) {
							return array;
						} else {
							return new JSONObject();
						}
					}
				} else {
//					throw new ConversionException("No data object found.");
					return new JSONObject();
				}
			} else {
				throw new ConversionException("result is not true.");
			}
			
		} catch (IOException | JSONException e) {
			throw new ConversionException(e);
		} catch (JsonParseException e) {
			throw new JsonParseException(e);
		} finally {
			try {
				body.in().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public TypedOutput toBody(Object object) {
		try {
			return new JsonTypedOutput(new Gson().toJson(object).getBytes(encoding), encoding);
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError(e);
		}
	}

	private static class JsonTypedOutput implements TypedOutput {
		private final byte[] jsonBytes;
		private final String mimeType;

		JsonTypedOutput(byte[] jsonBytes, String encode) {
			this.jsonBytes = jsonBytes;
			this.mimeType = "application/json; charset=" + encode;
		}

		@Override
		public String fileName() {
			return null;
		}

		@Override
		public String mimeType() {
			return mimeType;
		}

		@Override
		public long length() {
			return jsonBytes.length;
		}

		@Override
		public void writeTo(OutputStream out) throws IOException {
			out.write(jsonBytes);
		}
	}
}
