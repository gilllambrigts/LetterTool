# One 16 Opgave
### Handige links:
- Repository: https://github.com/gilllambrigts/LetterTool
- Docker image op Dockerhub: https://hub.docker.com/repository/docker/lambrigtsgill/one16lettertool. Getest op zowel Windows als op Mac OS. 
- Moest het zo zijn dat het Docker image niet werkt, dan kunnen jullie de tool ook altijd bekijken op het adres: https://coral-app-mm5fz.ondigitalocean.app/

### Onderdelen
- Verwerken van de opgave.
- Opslaan data in H2 database.
- Klein API d.m.v. Spring Boot

### Secties:
[Verwerking](#verwerking)
[Hoe te gebruiken](#howto)

### **Verwerking** {#verwerking}
#### **Verwerken van de lijst**

  - Bepalen van het langste woord in de ingevoerde lijst.
  - De woorden van die lengte opslaan in een array en verwijderen uit de invoerlijst.
  - Controle op de delen van de woorden:
    - Een loop (for int i = 0) gebruiken om het eerste deel array(i) van het woord te bewaren. 
      - In een geneste loop (int j = i + 1) het tweede deel array(j) te bewaren. 
      - Controle op de combinatie van de twee delen. Zowel 1+2 als 2+1.
        - Komt waarde voor? Voeg dan toe in een nieuwe array als deze er nog niet aan is toegevoegd **(duplicaten voorkomen)**.
    - Per iteratie doorheen de hoofdloop verwijderen we het item array(i). We weten immers dat we deze niet meer nodig hebben, aangezien we beide combinaties met dat woord al hebben gehad.
  - Doorheen de lijst van resultaten gaan en deze in een string uitvoeren met een gewenste formattering.
  - Een response sturen vanuit de server naar de aanvrager. Voorbeeld:
```Data has been processed and saved to a database.
You can consult it in the future by making a GET request to localhost:8080/api/read?id=3

Result:
shabb+y=shabby
dibab+s=dibabs
h+annah=hannah
h+arley=harley
a+cross=across
n+itric=nitric
soc+cer=soccer
natur+e=nature
pa+llid=pallid
wink+le=winkle
yonde+r=yonder
peppe+r=pepper
tingl+y=tingly
prest+o=presto
in+firm=infirm
b+rooks=brooks
wi+cked=wicked
hunt+er=hunter
appe+al=appeal
```

#### **Opslaan van de lijst**
- Gebruik maken van een H2 memory database om na de verwerking van de lijst deze ook op te slaan. 
- De database bestaat uit twee eenvoudige tabellen. 
  - entry
    - id
    - submissionDate
  - word
    - id
    - entry_id
    - word
  - Relatie: entry || - |< word
  
    
## Hoe te gebruiken? {#howto}

### Starten van de tool 
De tool is ontwikkeld met Spring Boot en de uiteindelijk .jar draait in een Docker image. Het zou dus voldoende zijn het Docker image te downloaden en daarna te runnen. Van zodra deze online staat is de tool klaar om requests te ontvangen.

### Ingave

#### Via een testbestand op de server.

```
curl --location --request POST 'localhost:8080/api/file?name=1
```
```
Parameter name = verplicht
```
    Een aantal bestanden die reeds bestaan:
    input.txt
    500.txt
    whitespaceAndNewLines.txt

#### Via data in de request.

```
    curl --location --request POST 'localhost:8080/api/data' --header 'Content-Type: text/plain'
```


```
    Voorbeeld: 
    curl --location --request POST 'localhost:8080/api/data' \
    --header 'Content-Type: text/plain' \
    --data-raw 'osine
    them
    narro
    es
    awler
    plex
    qu
    rrow
    iny
    shabb
    rrow
    well
```

### Consultatie
#### Bekijken van de lijst via het entryId
    
    curl --location --request GET 'localhost:8080/api/read?id=2'
```
Parameter id = verplicht
```
#### Bekijken van de lijst via het entryId

    curl --location --request GET 'localhost:8080/api/last'
