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
package com.threewks.thundr.view.csv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.CSVWriter;

import com.threewks.thundr.view.BaseView;
import com.threewks.thundr.view.ViewResolutionException;
import com.threewks.thundr.view.ViewResolver;

public class CsvViewResolver implements ViewResolver<CsvView> {
	private static final String[] EmptyStringArray = new String[0];

	@Override
	public void resolve(HttpServletRequest req, HttpServletResponse resp, CsvView viewResult) {
		BaseView.applyToResponse(viewResult, resp);
		try {
			PrintWriter writer = resp.getWriter();
			writeCsv(viewResult.getRowProvider(), writer);
		} catch (IOException e) {
			throw new ViewResolutionException(e, "Failed to write to csv to response stream: %s", e.getMessage());
		}
	}

	private void writeCsv(Iterable<List<String>> rowProvider, PrintWriter writer) throws IOException {
		CSVWriter csvWriter = new CSVWriter(writer);
		for (List<String> row : rowProvider) {
			csvWriter.writeNext(row.toArray(EmptyStringArray));
		}
		csvWriter.flush();
		csvWriter.close();
	}

}
