# Spring Boot application that displays various countries, cities and languages spoken. Uses MySQL world schema.
Can be deployed on a Docker container or Heroku.

### Build and Run locally:
`./gradlew clean stage`
`./gradlew clean stage -Penv=heroku` to build with Heroku property file

### Deploying to Heroku:
  * Run `heroku local web` to verify that stuff works.
  * Run `heroku create abhijitsarkar-hello-world` from project root.
  * Heroku adds a git remote named `heroku`. Rename it using `git remote rename heroku abhijitsarkar-hello-world`.
  * Run `git subtree push --prefix travel-app abhijitsarkar-hello-world master` from git root.
    (Read [this](http://brettdewoody.com/deploying-a-heroku-app-from-a-subdirectory/)).
  * If working from a branch, either first merge to master or run `git subtree push --prefix hello-world abhijitsarkar-hello-world yourbranch:master`

