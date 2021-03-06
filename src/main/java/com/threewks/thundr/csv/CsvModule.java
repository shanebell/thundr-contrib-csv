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

import com.threewks.thundr.action.method.bind.ActionMethodBinderRegistry;
import com.threewks.thundr.action.method.bind.csv.CsvActionMethodBinder;
import com.threewks.thundr.action.method.bind.csv.CsvJavabeanBinaryParameterBinder;
import com.threewks.thundr.action.method.bind.csv.CsvReaderBinaryParameterBinder;
import com.threewks.thundr.action.method.bind.csv.CsvRowsBinaryParameterBinder;
import com.threewks.thundr.action.method.bind.http.ParameterBinderSet;
import com.threewks.thundr.injection.BaseModule;
import com.threewks.thundr.injection.UpdatableInjectionContext;
import com.threewks.thundr.view.ViewResolverRegistry;
import com.threewks.thundr.view.csv.CsvView;
import com.threewks.thundr.view.csv.CsvViewResolver;

public class CsvModule extends BaseModule {

	@Override
	protected void addServices(UpdatableInjectionContext injectionContext) {
		super.addServices(injectionContext);
		ActionMethodBinderRegistry methodActionResolverRegistry = injectionContext.get(ActionMethodBinderRegistry.class);
		methodActionResolverRegistry.registerActionMethodBinder(new CsvActionMethodBinder());

		ParameterBinderSet.registerGlobalBinder(new CsvReaderBinaryParameterBinder());
		ParameterBinderSet.registerGlobalBinder(new CsvRowsBinaryParameterBinder());
		ParameterBinderSet.registerGlobalBinder(new CsvJavabeanBinaryParameterBinder());
	}

	@Override
	protected void addViewResolvers(ViewResolverRegistry viewResolverRegistry, UpdatableInjectionContext injectionContext) {
		super.addViewResolvers(viewResolverRegistry, injectionContext);
		viewResolverRegistry.addResolver(CsvView.class, new CsvViewResolver());
	}
}
