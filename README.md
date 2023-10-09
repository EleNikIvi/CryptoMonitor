# CryptoMonitor

This is my sample application. It was created using system architecture guidelines - **Clean architecture**, software design pattern - **MVVM+MVI**. For building UI it uses **Jetpack Compose** and **navigation-compose** for navigation.

As for API, it uses http://coinapi.io/. To run the application it is necessary to add apikey to local.properties (apikey="api_key_received_from_coin_api"). The API key can be requested for free from [CoinAPI](https://www.coinapi.io/). For free api-key there are limits to the number of requests that can be made using a single API key within a 24-hour time frame - 100.

The application uses **Retrofit2+Moshi+okhttp** for communication with API, as offline storage - **Room**. API sends a too big list of assets, so I added a pager for smooth loading.

It also uses:
- **Hilt** - for dependency injection
- **Coil** - image loading
- **JUnit5, Mockk, Turbine** - for unit-testing
- **Gradle JDK** - 18
