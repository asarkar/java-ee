# Spring Boot application that displays various countries, cities and languages spoken. Uses MySQL world schema.
Can be deployed on a Docker container or Heroku.

### Build and Run locally:
`./gradlew clean stage`
`./gradlew clean stage -Penv=heroku` to build with Heroku property file

### Deploying to Heroku:
   * Run `heroku local web` to verify that stuff works.
   * Run `git remote -v | grep heroku` to make sure heroku is added as a git remote.
     If not, run `heroku create` from root.
   * Run `git subtree push --prefix hello-world heroku master` from root.
     (Read [this](http://brettdewoody.com/deploying-a-heroku-app-from-a-subdirectory/)).
   * If working from a branch, either first merge to master or run `git subtree push --prefix hello-world heroku yourbranch:master`

