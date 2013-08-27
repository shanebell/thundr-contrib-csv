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
package com.threewks.thundr.pegdown;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.threewks.thundr.injection.InjectionContextImpl;
import com.threewks.thundr.injection.UpdatableInjectionContext;
import com.threewks.thundr.view.ViewResolverRegistry;
import com.threewks.thundr.view.pegdown.PegdownView;
import com.threewks.thundr.view.pegdown.PegdownViewResolver;

public class PegdownInjectionConfigurationTest {
	private PegdownInjectionConfiguration injectionConfiguration = new PegdownInjectionConfiguration();
	private UpdatableInjectionContext injectionContext = new InjectionContextImpl();
	private ViewResolverRegistry viewResolverRegistry = spy(new ViewResolverRegistry());

	@Test
	public void shouldRegisterPegdownViewResolver() {
		injectionConfiguration.addViewResolvers(viewResolverRegistry, injectionContext);

		verify(viewResolverRegistry).addResolver(eq(PegdownView.class), Mockito.any(PegdownViewResolver.class));
	}
}
