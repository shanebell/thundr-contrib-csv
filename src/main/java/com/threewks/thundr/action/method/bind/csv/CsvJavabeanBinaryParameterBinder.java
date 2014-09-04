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

import java.util.List;

import com.threewks.thundr.bind.BindException;
import com.threewks.thundr.bind.parameter.BinaryParameterBinder;
import com.threewks.thundr.csv.CsvCommon;
import com.threewks.thundr.http.MultipartFile;
import com.threewks.thundr.introspection.ParameterDescription;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameMappingStrategy;

/**
 * This binder knows how to convert csv multipart binary data into a List&lt;Object&gt; where the objects are javabeans.
 * The csv requires a header row
 */
public class CsvJavabeanBinaryParameterBinder implements BinaryParameterBinder<List<?>> {
	@Override
	public boolean willBind(ParameterDescription parameterDescription) {
		return CsvCommon.isAListOfBeans(parameterDescription);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> bind(ParameterDescription parameterDescription, MultipartFile data) {
		Class<Object> type = (Class<Object>) parameterDescription.getGenericType(0);
		try {
			CSVReader csvReader = CsvCommon.createCsvReader(data.getData());
			CsvToBean<Object> beanConverter = new CsvToBean<Object>();
			HeaderColumnNameMappingStrategy<Object> mapper = new HeaderColumnNameMappingStrategy<Object>();
			mapper.setType(type);
			List<Object> result = beanConverter.parse(mapper, csvReader);
			csvReader.close();
			return result;
		} catch (Exception e) {
			throw new BindException(e, "Failed to bind javabeans from CSV for type '%s': %s", type.getName(), e.getMessage());
		}
	}
}
