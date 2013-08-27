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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.pegdown.PegDownProcessor;

import com.threewks.thundr.test.mock.servlet.MockHttpServletRequest;
import com.threewks.thundr.test.mock.servlet.MockHttpServletResponse;
import com.threewks.thundr.test.mock.servlet.MockHttpSession;
import com.threewks.thundr.view.ViewResolutionException;

public class PegdownViewResolverTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	private MockHttpServletRequest req = new MockHttpServletRequest().session(new MockHttpSession());
	private MockHttpServletResponse resp = new MockHttpServletResponse();

	@Test
	public void shouldRenderMarkdown() throws IOException {
		PegdownViewResolver viewResolver = new PegdownViewResolver();

		PegdownView view = new PegdownView("Markdown\n========\n\nHi!\n\nNew Paragraph.");
		viewResolver.resolve(req, resp, view);

		assertThat(resp.content(), is("<h1>Markdown</h1><p>Hi!</p><p>New Paragraph.</p>"));
		assertThat(resp.status(), is(200));
		assertThat(resp.getContentType(), is("text/html"));
		assertThat(resp.getContentLength(), is(48));
	}

	@Test
	public void shouldThrowViewResolutionExceptionWhenFailedToRenderMarkdown() throws IOException {
		thrown.expect(ViewResolutionException.class);
		thrown.expectMessage("Failed to render markdown: expected exception");

		PegDownProcessor pegDownProcessor = mock(PegDownProcessor.class);
		when(pegDownProcessor.markdownToHtml(anyString())).thenThrow(new RuntimeException("expected exception"));

		PegdownViewResolver viewResolver = new PegdownViewResolver(pegDownProcessor);

		PegdownView view = new PegdownView("MD");
		viewResolver.resolve(req, resp, view);
	}

	@Test
	public void shouldReturnClassNameForToString() {
		assertThat(new PegdownViewResolver().toString(), is("PegdownViewResolver"));
	}
}
