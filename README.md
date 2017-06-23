
###Notes
- Run with `mvn spring-boot:run`
- Test with 'mvn test'
- Using only ECB API for 90 days data. Seems the values are same as on the daily endpoint.
- Endpoint located at `http://localhost:8080/exchange-rate/EUR/{currency}` with optional date parameter (yyyy-MM-dd).
- Fetching new data on startup and every 30 minutes. If fetching the data fails, next attempts follow with 30 seconds timeout  .
