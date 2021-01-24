### Quick summary ###
Given a movie script in the format described in the file screenplay.txt, the application will have to extract the movie
settings, the characters and the dialogues, identifying the characters that appeared in each
setting, and counting the dialogue words for each character.

It will save the script content in an Embedded MongoDB:

http://localhost:8080/starwars-script/script

```$xslt
curl --location --request POST 'http://localhost:8080/script' \
--header 'Content-Type: text/plain' \
--data-raw '[YOUR SCRIPT GOES HERE]'
```

To execute GET requests:

http://localhost:8080/starwars-script/settings

http://localhost:8080/starwars-script/settings/1

http://localhost:8080/starwars-script/characters

http://localhost:8080/starwars-script/characters/1


### Who do I talk to? ###
admin: Juliano Cervelin

@: juliano.cervelin@gmail.com