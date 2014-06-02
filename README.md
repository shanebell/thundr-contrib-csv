thundr-contrib-csv
=================

A thundr module for easing working with CSVs.

This module provides facilities for input and output of csv files through controllers.

## Controller Binding

When posting multipart data containing a text/csv part, this module will handle automatically binding the following types. 
Note that the variable name must match the uploaded form field name.

    public View post1(List<String[]> data){ ... }
    public View post2(CsvReader data){ ... }
    public View post2(List<MyJavaBean> data){ ... }
    
In the case of the third variation, this will only work on javabean properties (and you require a default or no-args ctor). The uploaded
csv also must have a header row where the column names match the bean properties to set.

When posting data as a csv which is not multipart, but has a mime type of text/csv, the above bindings will also work.

## Views

This module also provides the CsvView and CsvViewResolver, which will result in a csv file being received by the caller. You create a 
CsvView by invoking one of the static factory methods, which allow you to supply the row data in a variety of structures.

    public CsvView get(){ ... return CsvView.fromArrays(rows) }  
    public CsvView get(){ ... return CsvView.fromLists(rows) }
      
## Getting it going

Include the thundr-csv dependency using maven or your favourite dependency management tool.
    
    <dependency>
  		<groupId>com.threewks.thundr</groupId>
			<artifactId>thundr-csv</artifactId>
			<version>1.1.0</version>
			<scope>compile</scope>
		</dependency>
    

After including the thundr-csv module in your dependencies
    
    dependencyRegistry.addDependency(CsvModule.class);
    
You can now use the CsvView and any of the controller bindings.