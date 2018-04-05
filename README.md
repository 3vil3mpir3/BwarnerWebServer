# BwarnerWebServer


What is this?
=======
Working through a server problem as an academic exercise.  

Having never written my own server before, there was much learning.

Some of this work is influenced by structures produced by others. Notably, I was influenced by https://github.com/dasanjos/java-WebServer. I appreciated the organization initially.  I also borrowed the enums for both content types and http methods from this approach.  That said, I think i was able to improve on that approach. There are clearly more elegant solutions, but this one is mine.

Caveats
=======
##### Responses 
At this point, the server will honor GET, HEAD, and TRACE.  I have an unrecognized case in there as well, but all other request types will return with 501.
##### Testing 
In my research, I came across several examples that included some levels of build testing. I elected to forgo his based on the small project size.
##### Organization and Enums 
I referenced a few apache libraries because I didn't want to have to define my own enums.  I'm sure this is a debatable approach.  

###On with the show! 

To Compile
=======

These instructions assume: Modern variants of Java and Maven.

Once this repo is cloned, run:

    mvn clean package
    
from the root directory of this project.  Within the generated target folder, find:

    bwarner-Webserver-1.0-jar-with-dependencies.jar
    

To Start
=======

From /target, run the following:

    $ java -jar bwarner-Webserver-1.0-jar-with-dependencies.jar <port> <threads>
    
##### The Port 
For the purposes of this exercise, the server expects a valid port number between 1025 and 65535.  Any other number will default to 8080

##### The Threads 
For the purposes of this exercise, the server expects a valid number from 1 to 10.  Any other number will default to 5.

##### The Content 
This server returns content from /www directory, sibling to /src. I've included one of the sample "Skeleton" (http://getskeleton.com) example pages as it has several css and image files.