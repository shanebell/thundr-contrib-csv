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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.atomicleopard.expressive.Expressive;
import com.threewks.thundr.http.ContentType;
import com.threewks.thundr.view.BaseView;
import com.threewks.thundr.view.View;

public class CsvView extends BaseView<CsvView> implements View {
	private Iterable<List<String>> rowProvider;

	public CsvView() {
		withContentType(ContentType.TextCsv.value());
	}

	public static CsvView fromArrays(Iterable<String[]> data) {
		List<List<String>> asLists = new ArrayList<List<String>>();
		for (String[] d : data) {
			asLists.add(Arrays.asList(d));
		}
		return new CsvView(asLists);
	}

	public static CsvView fromArrays(Iterator<String[]> data) {
		return fromArrays(Expressive.iterable(data));
	}

	public static CsvView fromLists(Iterable<List<String>> data) {
		return new CsvView(data);
	}

	public static CsvView fromLists(Iterator<List<String>> data) {
		return new CsvView(Expressive.iterable(data));
	}

	private CsvView(Iterable<List<String>> rowProvider) {
		this.rowProvider = rowProvider;
	}

	public Iterable<List<String>> getRowProvider() {
		return rowProvider;
	}
}
