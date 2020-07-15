# Selenide + Allure 2 + JUnit 5 + Gradle example 
# Main target logging information from test 

### Features
- Using Allure without @Step annotation (can skip @Step annotation for methods, which we want to see in report)
- Adding console log in Allure report in test attachment
- Adding selenide methods in Allure report in test steps

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
