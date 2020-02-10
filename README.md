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
open index.html in report directory (you can find report directory in results of task allureReport)