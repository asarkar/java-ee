# How to deploy a subdirectory to Heroku without installing Heroku CLI

1. Create a new app by logging into Heroku.

2. Find the Git URL from _settings_ and add it to the local repo as a new remote.

```
asarkar:java-ee$ git remote add heroku-ig-client git@heroku.com:ig-client.git
```
3. Commit changes.

4. Push

```
asarkar:java-ee$ git subtree push --prefix ig-client heroku-ig-client master
```

`ig-client` is the name of the subdirectory that's to be deployed, `heroku-ig-client` is the remote name added in step 1
 and `master` is the branch.

