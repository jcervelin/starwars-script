### Quick summary ###
Given a movie script in the format described below, the application will have to extract the movie
settings, the characters and the dialogues, identifying the characters that appeared in each
setting, and counting the dialogue words for each character.

### How do I get set up? ###
execute the command:
**mvn jetty:run**

After that you can execute the SOAP UI project for tests
/starwars-script/soapui/StarWars-Script-soapui-project.xml

There is a TestSuite with some test cases. 

Or you can execute the command **mvn test** to do that as described below.

**Important:** to execute mvn test or to use SOAP UI project you have to execute mvn jetty:run before.


### How to run tests ###
Execute: **mvn test** 

This command will execute /starwars-script/soapui/StarWars-Script-soapui-project.xml

It will put the script content in HSQL database:

http://localhost:8080/starwars-script/script


And execute a simple test in which resource URL:

http://localhost:8080/starwars-script/settings

http://localhost:8080/starwars-script/settings/1

http://localhost:8080/starwars-script/characters

http://localhost:8080/starwars-script/characters/1


Logs will be created in:

/starwars-script/soapui.log

/starwars-script/soapui-errors.log

For reference there are some SOAPUI print screens in:

/starwars-script/test prints

![TestSuite.PNG](https://bitbucket.org/repo/8By8jg/images/622219524-TestSuite.PNG)

![script POST.PNG](https://bitbucket.org/repo/8By8jg/images/752797338-script%20POST.PNG)

![characters GET.PNG](https://bitbucket.org/repo/8By8jg/images/1246167989-characters%20GET.PNG)

![characters id=1 GET.PNG](https://bitbucket.org/repo/8By8jg/images/2829995344-characters%20id=1%20GET.PNG)

![settings GET.PNG](https://bitbucket.org/repo/8By8jg/images/2069932857-settings%20GET.PNG)

![settings id=1 GET.PNG](https://bitbucket.org/repo/8By8jg/images/859714778-settings%20id=1%20GET.PNG)

### Who do I talk to? ###
admin: Juliano Cervelin

@: juliano.cervelin@gmail.com