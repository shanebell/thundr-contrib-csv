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
package com.threewks.thundr.action.method.bind.csv;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atomicleopard.expressive.Expressive;
import com.threewks.thundr.bind.BindException;
import com.threewks.thundr.bind.Binder;
import com.threewks.thundr.csv.CsvCommon;
import com.threewks.thundr.http.ContentType;
import com.threewks.thundr.introspection.ParameterDescription;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameMappingStrategy;

/**
 * Can bind text/csv content to {@link CSVReader} or List<String[]> controller parameters.
 */
public class CsvBinder implements Binder {
	@Override
	public void bindAll(Map<ParameterDescription, Object> bindings, HttpServletRequest req, HttpServletResponse resp, Map<String, String> pathVariables) {
		if (Expressive.isNotEmpty(bindings) && canBind(req.getContentType())) {
			for (Map.Entry<ParameterDescription, Object> binding : bindings.entrySet()) {
				if (binding.getValue() == null) {
					ParameterDescription key = binding.getKey();
					Object value = attemptToBind(key, req);
					bindings.put(key, value);
				}
			}
		}
	}

	public boolean canBind(String contentType) {
		return ContentType.TextCsv.matches(contentType);
	}

	@SuppressWarnings("unchecked")
	private Object attemptToBind(ParameterDescription key, HttpServletRequest req) {
		try {
			if (key.isA(CSVReader.class)) {
				return CsvCommon.createCsvReader(req);
			}
			if (CsvCommon.isAListOfStringArray(key)) {
				CSVReader csvReader = CsvCommon.createCsvReader(req);
				List<String[]> listOfStringArray = csvReader.readAll();
				csvReader.close();
				return listOfStringArray;
			}
			if (CsvCommon.isAListOfBeans(key)) {
				Class<Object> type = (Class<Object>) key.getGenericType(0);
				CSVReader csvReader = CsvCommon.createCsvReader(req);
				CsvToBean<Object> beanConverter = new CsvToBean<Object>();
				HeaderColumnNameMappingStrategy<Object> mapper = new HeaderColumnNameMappingStrategy<Object>();
				mapper.setType(type);
				List<Object> result = beanConverter.parse(mapper, csvReader);
				csvReader.close();
				return result;
			}

			return null;
		} catch (IOException e) {
			throw new BindException(e, "Failed to bind csv content: %s", e.getMessage());
		}
	}
}
