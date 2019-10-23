# Personal Details Account  prototype
>  Skeleton project for usability testing of [Twirl](https://github.com/hmrc/play-frontend-govuk) and [Nunjucks](https://github.com/hmrc/play-nunjucks-spike) UI libraries

## Table of Contents

- [Using Twirl library](#using-twirl-library)
- [Using Nunjucks library](#using-nunjucks-library)


# Using Twirl library

### Getting started
1>  Add [Twirl](https://github.com/hmrc/play-frontend-govuk/releases) library in the App dependencies. Please use the latest available version and report any issues in #event-play-frontend-beta
```scala
"uk.gov.hmrc"       %% "play-frontend-govuk"            % "x.y.z-play-26"
```
2>  Add SASS assets to app/assets/stylesheets.  
3>  Add govuk-frontend routing redirection in app.routes:
```scala
->         /govuk-frontend                      govuk.Routes
```
4>  Add TwirlKeys.templateImports in build.sbt:
```sbt
    TwirlKeys.templateImports ++= Seq(
      "uk.gov.hmrc.govukfrontend.views.html.components._",
      "uk.gov.hmrc.govukfrontend.views.html.helpers._"
    )
```
5>  See more tips [here](https://github.com/hmrc/play-frontend-govuk#getting-started)


### Potential gotchas 
1. Haven't tested a way to get value back from session and set on forms when revisiting them
2. Haven't tested a way to get value back from session and set on summary page  
<br/>   
<br/>   
<hr/>   
   
# Using Nunjucks library

### Getting started 

1>  Add [Nunjucks](https://github.com/hmrc/play-nunjucks-spike/releases) library and required webjars in the App dependencies:
```sbt
    "uk.gov.hmrc"       %% "play-nunjucks-spike"            % "0.12.0-play-26",
    "org.webjars.npm"   %  "govuk-frontend"                 % "3.1.0",
    "org.webjars.npm"   %  "hmrc-frontend"                  % "1.0.4" 
``` 
2>  Add SASS assets to app/assets/stylesheets.  
3>  Configure viewPaths / libPaths in application.conf:
```
nunjucks {
  viewPaths = ["views"]
  libPaths = ["govuk-frontend", "hmrc-frontend"]
}
```
4>  Inject  NunjucksRenderer in controller and extend with NunjucksSupport (See [Nunjucks](https://github.com/hmrc/play-nunjucks-spike) library docs) <br/> 
5>  Add Nunjucks views under conf/views folder   
6>  Continuous logging / spamming in console may need to be prevented using logback.xml config:
```
    <logger name="io.apigee.trireme" level="INFO"/>
    <logger name="org.webjars" level="INFO"/>
```

### Potential gotchas 
1. Mode deserialiser may need to be added if missing 
