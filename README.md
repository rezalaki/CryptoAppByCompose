# CryptoAppByCompose
This an android app which is developed via JetPackCompose very fast. It is an employment project that I completed in about 19 hours,
while I had only some basics of JetpackCompose UI. But my technical experiences helped me so much.
\
I will update its UI soon :)


### Screenshots
![screenshot-one](https://github.com/rezalaki/CryptoAppByCompose/blob/main/arts/one.jpg?raw=true)
![screenshot-two](https://github.com/rezalaki/CryptoAppByCompose/blob/main/arts/two.jpg?raw=true)

### Technologies
+ Kotlin
+ MVVM
+ JetpackCompose
+ Paging 3
+ Dagger-Hilt
+ Room
+ Flow
+ Coroutines
+ Retrofit

### Scenario
There is an api (coingecko.com) which returns lots of digital currencies. The app should connect to the api and show data,
at the same time save data to local database.
If the user's connection is not OK, the app must show data from local database.
\
api -> https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&page=1&sparkline=false

### APK File
debug-apk (GoogleDrive) -> https://drive.google.com/file/d/1vfrWxH-oLeMW3kyTZpZs-mrY28P1TlNh/view?usp=sharing