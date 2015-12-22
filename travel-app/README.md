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

### References:
[Mastering observables](http://developer.couchbase.com/documentation/server/4.0/sdks/java-2.2/observables.html)

[Working with N1QL](http://developer.couchbase.com/documentation/server/4.0/sdks/java-2.2/querying-n1ql.html)

[N1QL reference](http://developer.couchbase.com/documentation/server/4.0/n1ql/index.html)

