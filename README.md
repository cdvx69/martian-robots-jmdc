# martian-robots-jmdc
Guidesmiths martian robots challenge by Jose-Manuel Díaz-Cordovés Gómez-Lázaro
Robots application based on the following requirements
https://github.com/guidesmiths/interview-code-challenges/blob/master/java/martian-robots/instructions.md

To run the application:
1. Download from repository () to local disc.
2. run on maven:
    - Open terminal and go to to downloaded folder. Run command: 
       mvn package
3. locate the .jar file generated on previous step (robots-0.0.1-SNAPSHOT.jar) and
   execute it with command java -jar robots-0.0.1-SNAPSHOT.jar
   
   
Application is a REST api with the following endpoints:
1. [POST] /mars/robots/processInput --> processes a payload with the following JSON model:
REQUEST EXAMPLE
{  
   "world": {"x": "5", "y": "3"},
   "robots": [
      {"position": "11E", "movement": "RFRFRFRF" },
      {"position": "32N", "movement": "FRRFLLFFRRFLL"},
      {"position": "03W", "movement": "LLFFFLFLFL"}
   ]
}

RESPONSE EXAMPLE
{
   "robots": [
       {"position": "11E", "movement": "RFRFRFRF", "lost": false},
       {"position": "33N", "movement": "FRRFLLFFRRFLL", "lost": true},
       {"position": "23S", "movement": "LLFFFLFLFL", "lost": false}
   ],
   "message": "OK"
}

2. [POST] /mars/robots/processInputText --> processes a payload with the following directly with text as per
  requirements

3. [GET] /mars/robots/lostRobots --> gets the number of lost robots on each processing

The other two methods are for integration tests purposes resetting database every time processin is done
