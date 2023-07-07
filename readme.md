# Dividend Optimizer on SpringBoot platform


##Project setup
Gradle version: 8.1.1
Java Version: jdk-17.0.2

Decision data
http://localhost:8586/api/decision-data

User Data
http://localhost:8586/api/user-data

A working list of symbols
http://localhost:8586/api/watch-symbols

Data downloaded from Yahoo based on working list of symbols
http://localhost:8586/api/symbol-current-state

Current positions (shares)
http://localhost:8586/api/positions

User Current positions with average price
http://localhost:8586/api/user-current-positions



TODO:
1. Add different atrtributes: Invested amount, Available Cash
2. Design decision page
3. Build Decision Service for Decision page
4. Things about Admin override events (holidays, Fed and Bank of Canada Meetings)
5.