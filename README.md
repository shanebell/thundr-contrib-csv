thundr-pegdown
=================

A thundr module for rendering [Markdown](http://daringfireball.net/projects/markdown/) using [pegdown](https://github.com/sirthias/pegdown).

Include the thundr-pegdown dependency using maven or your favourite dependency management tool.
    
    <dependency>
  		<groupId>com.threewks.thundr</groupId>
			<artifactId>thundr-pegdown</artifactId>
			<version>0.9.11</version>
			<scope>compile</scope>
		</dependency>
    

After including the thundr-pegdown module in your modules.properties
    
    com.threewks.thundr.pegdown=
    

You can return PegdownView from controller methods which will render the supplied markdown:
    
    public PegdownView get(){
        return new PegdownView(markdownString);
    }
    