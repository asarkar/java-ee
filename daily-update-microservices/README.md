### Movies

Provider: http://api.themoviedb.org

   * Get popular movies:
     - Direct: http:\<HOST\>:10020/movies/popular
     - Daily update: http:\<HOST\>:10050/dailyupdate/movies/popular
     - Gateway: http:\<HOST\>:8080/[dailyupdate/]movies/popular

### News

Provider: http://api.nytimes.com

   * Get top stories:
     - Direct: http:\<HOST\>:10020/news/topstories[?sections=world,home,...]
     - Daily update: http:\<HOST\>:10050/dailyupdate/news/topstories[?sections=world,home,...]
     - Gateway: http:\<HOST\>:8080/[dailyupdate/]news/topstories[?sections=world,home,...]

   List of [all sections](http://developer.nytimes.com/docs/read/top_stories_api)

### Weather

Provider: OpenWeatherMap

   * Get weather by zip code:
     - Direct: http:\<HOST\>:10030/weather/zipCode/{zipCode}
     - Daily update: http:\<HOST\>:10050/dailyupdate/weather/zipCode/{zipCode}
     - Gateway: http:\<HOST\>:8080/[dailyupdate/]weather/zipCode/{zipCode}

### Notes
   * Full-strength JCE must be installed to use encryption
   * [Spring Cloud Integration Tests](https://github.com/spring-cloud-samples/tests)

