------------- PICTOGUESS  README -------------

PictoGuess is a learning project that allowed me to leverage some of the technologies used in a web application, 
although some of them are quite old (JSP). I was mainly interested in the interaction with the database.

The only reason PictoGuess won't be the new Instagram is that the user interface is written in the most basic HTML, with no CSS :)
So, white pages everywhere!



------------- Technologies used -------------
Spring MVC through Spring Boot v2.0.0.RELEASE
Maven (build/dependencies tool) v4.8.0-22-generic
JSP (view layer)
PostgreSQL (through JDBC) v9.5
Apache Tomcat v8.5
Eclipse (IDE)



------------- Functionality -------------
A new user is required to register an account by providing a nickname and a password. The latter cannot be changed and the account cannot be deleted.
The nickname will appear on the Hall of Fame screen, which is visible only to logged-in users. No other parts of the application can be accessed
without being logged in. (this made me learned about Servlet Filters; Spring Security was another, complex option)

Once logged in, the user is presented with a Home screen where he/she can choose between uploading a picture to the server or guessing the pictures
uploaded by other users, each action being rewarded with a number of points that can be configured through the file "application.properties", thus without
recompiling the app. The user's score, read from the database, is also present here. 

The Upload screen is simple: the user must only upload ".jpg/jpeg" files, whose content type is checked server-side by comparing the file extension.
Each picture must go along with a description: this text will be added to the database together with the picture and represents the exact text  
(barring the leading/trailing spaces) another user must type when this specific picture is displayed. Javascript, if enabled client-side, ensures that 
the description is not empty, even if it only consists of blank spaces.

The uploaded pictures will be renamed to a random string with a hard-coded length. In the database are persisted only their new names (BLOBs suffer
from a series of disadvantages) and the exact path must be declared in "application.properties".

The 'Let's play!' screen, also very basic, presents the user with the current score, the number of total guessed pictures, a picture and an input field.
The user has 3 (configurable in "application.properties") tries to enter the correct description and the number of permitted attempts is displayed, bolded. 
If the third guess fails, a new random picture is retrieved from the database, with no penalties. The same happens if the user clicks the "skip" hyperlink.
If all the pictures have been guessed, the user will be redirected to his/her Home page, where a message will be displayed describing the situation.
Clicking on "Play" again will result in the same screen, unless a different user uploads a new picture.

By default, PictoGuess contains 10 pictures with no known uploader.

All the pictures that have been guessed correctly, along with those uploaded by the user, will never be displayed to the same user. 
This information is persisted in the database.

The Scores screen lists all the users and their current scores. The list is dynamically created on each page refresh by leveraging JSP and database access. 
The user's name is bordered by two horizontal lines. The user is not able to see the "admin" user in this list or his/her score, while "admin" can
see him/herself. This special user is also not counted as one, so on the first page the number of users is displayed as 0.

By clicking on "Exit" on any page, the user's session will be invalidated and a redirection to the main page will occur.


------------- Installation -------------
1. Fill in the missing data in the file "src/main/resources/application.properties" according to the comments there
2. Run the PostgreSQL script "createTables.sql" (provided you use this database, else adapt this schema to your own)
3. Run the application as "Spring Boot Application" within Eclipse with Spring Tool Suite
4. Direct your browser to the root "/", the welcome page will be displayed