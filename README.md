# Selenide + Allure 2 + JUnit 5 + Gradle example

### Features
- Logging without @Step annotation
- Adding console log in allure attachment
- Adding selenide methods in allure steps

### To run tests and generate Allure report:
```sh
gradlew clean test
 
gradlew allureReport
```

if you have a selenoid or selenium hub 
```sh
gradlew clean test -Pbrowser=chrome -PHeadless=false -PHubUrl=http://localhost:4444/wd/hub

gradlew allureReport
```



### To see a report:
open http://localhost:63342/ReportAndLoggingDemo/build/reports/allure-report/index.html in your browser