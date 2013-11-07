/*
 * This file is a component of thundr, a software library from 3wks.
 * Read more: http://www.3wks.com.au/thundr
 * Copyright (C) 2013 3wks, <thundr@3wks.com.au>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.threewks.thundr.csv;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import au.com.bytecode.opencsv.CSVReader;

import com.atomicleopard.expressive.Cast;
import com.threewks.thundr.introspection.ClassIntrospector;
import com.threewks.thundr.introspection.ParameterDescription;

public class CsvCommon {
	public static boolean isAListOfStringArray(ParameterDescription parameterDescription) {
		return parameterDescription.isA(List.class) && String[].class.equals(parameterDescription.getGenericType(0));
	}

	public static CSVReader createCsvReader(HttpServletRequest req) throws IOException {
		return new CSVReader(req.getReader());
	}

	public static CSVReader createCsvReader(byte[] data) throws UnsupportedEncodingException {
		return new CSVReader(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));
	}

	public static boolean isAListOfBeans(ParameterDescription parameterDescription) {
		return parameterDescription.isA(List.class) && isABean(parameterDescription.getGenericType(0));
	}

	public static boolean isABean(Type type) {
		return ClassIntrospector.isAJavabean(Cast.as(type, Class.class));
	}
}
