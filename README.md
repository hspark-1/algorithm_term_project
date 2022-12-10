# algorithm_term_project
This project is a term project for the 2022-2 Algorithm Class Team 8.
This project is a program that plans your itinerary for you.

The main algorithm in this program is the travel.java file.
The java file written in the file plans the travel itinerary based on the data entered by the user.
All entered data is saved in the database, and calculation results are also saved.

I used spring boot-web framework and it is version 2.6.8
jdk version is 11.

The database used a volatile db as h2-database.

The view page used html, css, and js using Mustache.

You can run a private server by running the file SchedulerProjectApplication with springbootapplication.
The localhost port number is set to 8080, and you can access the first page by accessing 8080/mainpage.

Access the main page and enter basic travel itinerary data. -> arrival time, return time, departure time, travel day.
Proceed to the next step and enter your travel destination. ex)제주국제공항, 부산역 . . .

Move on to the next one and enter where you would like to have lunch.
And enter the estimated time required at the location.

On the next and subsequent pages, enter the same places you want to have dinner, places you want to see, and the estimated time required at those places.

You can check the itinerary along with an alert message on the last page.
The trip can be rescheduled or amended.
Since the AppKey is a free version, it may take a long time to calculate the travel time to a place in the process of printing out the travel results.
This is not an error. Just wait.
