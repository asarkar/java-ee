# How to deploy a subdirectory to Heroku without Heroku CLI

1. Create a new app by logging into Heroku.

2. Find the Git URL from _settings_ and add it to the local repo as a new remote.

   ```
   asarkar:java-ee$ git remote add heroku-ig-client git@heroku.com:ig-client.git
   ```

3. Commit changes.

4. Push.

   ```
   asarkar:java-ee$ git subtree push --prefix ig-client heroku-ig-client master
   ```

`ig-client` is the name of the subdirectory that's to be deployed, `heroku-ig-client` is the remote name added in step 1
 and `master` is the branch.
 
> If need to force update, use the following command:

  ```
  asarkar:java-ee$ git push heroku-ig-client $(git subtree split --prefix ig-client master):master --force
  ```
 
 
# How to fetch top Instagram posts

1. Go to [http://ig-client.herokuapp.com/url](http://ig-client.herokuapp.com/url). It will return a URL.

2. Take the output, paste in the browser address bar and press enter. It will present Instagram login page 
   (if already logged in, skip to step 4). 
   
3. Login with your username and password. It will return a URL.

4. Copy the `access_token` from the output and replace the `{accessToken}` with it in the URL:
   https://ig-client.herokuapp.com/top?accessToken={accessToken}
   
5. Take the output, paste in the browser address bar and press enter. 
   It will return your top 20 posts with the image URLs and number of likes.
   



