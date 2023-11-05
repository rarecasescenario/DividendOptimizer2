# Dividend Optimizer on SpringBoot platform

# TODO
```
1. Add different attributes: Invested amount, Available Cash
2. Design decision page
3. Build Decision Service for Decision page
4. Things about Admin override events (holidays, Fed and Bank of Canada Meetings)
5. Fix the error when no positions
```

##App start
```
C:\Tools\jdk-17.0.2\bin\java.exe -jar C:\AV\WorkProjects\DividendOptimizer2\Optimizer\build\libs\divoptimizer-1.0.0.jar
```

##Build
```
Command line: gradle clean bootJar
```

##Project setup
Gradle version: 8.1.1
Java Version: jdk-17.0.2

Decision data
http://localhost:8586/api/decision-data

User Data
http://localhost:8586/api/user-data
http://localhost:8587/api/user-data

A working list of symbols
http://localhost:8586/api/watch-symbols

Data downloaded from Yahoo based on working list of symbols
http://localhost:8586/api/symbol-current-state

Current positions (shares)
http://localhost:8586/api/positions

User Current positions with average price
http://localhost:8586/api/user-current-positions

All scenarios
http://localhost:8586/api/allscenarios
http://localhost:8586/api/scenario/1

Delete Scenario
http://localhost:8586/api/delete-scenario/1

http://localhost:8586/api/add-scenario POST
con.setRequestProperty("Content-Type", "application/json; charset=utf8")
{
    "investedAmount": 255068.20,
    "availableCash": 557.32,
    "updatedOn": "2023-11-01T19:17:44",
    "id": 1
}

##Usefull Links
```
https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/#publishing-your-application
```

