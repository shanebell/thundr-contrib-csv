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

import au.com.bytecode.opencsv.CSVReader;

import com.threewks.thundr.action.method.bind.BindException;
import com.threewks.thundr.action.method.bind.http.BinaryParameterBinder;
import com.threewks.thundr.csv.CsvCommon;
import com.threewks.thundr.introspection.ParameterDescription;

/**
 * This binder knows how to convert multipart binary data into a List<String[]> for csv rows
 */
public class CsvRowsBinaryParameterBinder implements BinaryParameterBinder<List<String[]>> {
	@Override
	public boolean willBind(ParameterDescription parameterDescription) {
		return CsvCommon.isAListOfStringArray(parameterDescription);
	}

	@Override
	public List<String[]> bind(ParameterDescription parameterDescription, byte[] data) {
		try {
			CSVReader csvReader = CsvCommon.createCsvReader(data);
			List<String[]> result = csvReader.readAll();
			csvReader.close();
			return result;
		} catch (Exception e) {
			throw new BindException(e, "Failed to bind csv rows: %s", e.getMessage());
		}
	}
}
