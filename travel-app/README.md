# Spring Boot application that that allows users to find flights by entering airports and travel dates. Uses Couchbase travel-sample bucket.
Can be deployed on a Docker container or Heroku.

### To load Couchbase travel-sample bucket:
`curl -u admin:admin123 --data-ascii '["travel-sample"]' http://$(docker-machine ip default):8091/sampleBuckets/install`

### To create primary index necessary for querying:
   * Run `/opt/couchbase/bin/cbq`
   * `CREATE PRIMARY INDEX ``travel-sample-primary-index`` ON ``travel-sample`` USING GSI;`
   * Exit by `CTRL + c`
   * Using Java SDK
   ```
   Query createIndex = Query.simple("CREATE PRIMARY INDEX ``travel-sample-primary-index`` ON ``travel-sample`` USING GSI;");
   bucket.query(createIndex);
   ```

### Build and Run locally:
`./gradlew clean stage`
`./gradlew clean stage -Penv=heroku` to build with Heroku property file

### Deploying to Heroku:
   * Run `heroku local web` to verify that stuff works.
   * Run `git remote -v | grep heroku` to make sure heroku is added as a git remote.
     If not, run `heroku create` from root.
   * Run `git subtree push --prefix travel-app heroku master` from root.
     (Read [this](http://brettdewoody.com/deploying-a-heroku-app-from-a-subdirectory/)).
   * If working from a branch, either first merge to master or run `git subtree push --prefix travel-app heroku yourbranch:master`

### Find flights:
```
SELECT airln.name AS airline, schedule.flight AS flightNum, schedule.utc AS departureTimeUTC, \
route.sourceairport AS srcAirport, route.destinationairport AS destAirport \
FROM `travel-sample` route UNNEST route.schedule schedule JOIN `travel-sample` airln ON KEYS route.airlineid \
WHERE route.sourceairport='SEA' AND route.destinationairport='MCO' AND schedule.day=6 ORDER BY airln.name
```

### Find airport info:
```
SELECT airportname AS airport, faa AS airportCode, city, country, tz AS timeZone \
FROM `travel-sample` WHERE faa IN ['SEA', 'MCO']
```
