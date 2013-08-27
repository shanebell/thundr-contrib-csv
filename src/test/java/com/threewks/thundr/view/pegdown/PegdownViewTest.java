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
package com.threewks.thundr.view.pegdown;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class PegdownViewTest {

	@Test
	public void shouldRetainMarkdown() {
		PegdownView handlebarsView = new PegdownView("markdown string\n=======");
		assertThat(handlebarsView.getMarkdown(), is("markdown string\n======="));
	}

	@Test
	public void shouldReturnMarkdownLimitedTo200CharsForToString() {
		assertThat(new PegdownView("markdown string\n=======").toString(), is("markdown string\n======="));
		String twoHundred = RandomStringUtils.random(200);
		String oneNintyNine = RandomStringUtils.random(199);
		assertThat(new PegdownView(oneNintyNine).toString(), is(oneNintyNine));
		assertThat(new PegdownView(oneNintyNine).toString().length(), is(199));
		assertThat(new PegdownView(twoHundred).toString().substring(100), is(twoHundred.substring(100)));
		assertThat(new PegdownView(twoHundred).toString().length(), is(200));
		assertThat(new PegdownView(RandomStringUtils.random(201)).toString().length(), is(200));
		assertThat(new PegdownView(RandomStringUtils.random(20001)).toString().length(), is(200));

	}
}
