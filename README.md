# Movie-Ticket-Booking-System
This project is REST based backend application, built completely on spring boot.

In this project we are able to handle Resource in our application such as movie, theatre, reviews, tickets etc.

Feature of this application:
1. Users can give reviews to the movie they want to give.
2. They can search for the theatres in their city.
3. They can search for running shows in particular theatres.
4. They can also book tickets for a particular show.
5. Once the tickets are booked, email notifications will be sent to the users' registered email.

Technical features of the application:
1. Application written in layered manner, means business logic and persistent logic is written as seperate module.
2. Authentication for users will be happening through JWT Authentication, and Authorization mechanism will be happening through Spring security.
3. Email notification to the user will be sent using Java mail sender API and Apache Kafka.

Application is currently running on: http://mymovieshow.us-east-1.elasticbeanstalk.com
