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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.MimeTypes;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import com.threewks.thundr.view.ViewResolutionException;
import com.threewks.thundr.view.ViewResolver;

public class PegdownViewResolver implements ViewResolver<PegdownView> {
	private PegDownProcessor pegdownProcessor;

	public PegdownViewResolver() {
		this(new PegDownProcessor(Extensions.AUTOLINKS | Extensions.SMARTYPANTS));
	}

	public PegdownViewResolver(PegDownProcessor pegDownProcessor) {
		this.pegdownProcessor = pegDownProcessor;
	}

	@Override
	public void resolve(HttpServletRequest req, HttpServletResponse resp, PegdownView viewResult) {
		try {
			String result = pegdownProcessor.markdownToHtml(viewResult.getMarkdown());
			resp.getWriter().write(result);
			resp.setContentType(MimeTypes.MIME_TEXT_HTML);
			resp.setContentLength(result.getBytes().length);
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			throw new ViewResolutionException(e, "Failed to render markdown: %s", e.getMessage());
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
