## 1. Beschrijving

Dit project is een webapplicatie voor een boekenwinkel ontwikkeld in Java met Spring boot.
Gebruikers krijgen een catalogus met alle boeken in het systeem te zien
verder hebben ze ook de functie om zich te registreren/inloggen waarna ze
de optie krijgen om boeken te bestellen en reviews achter te laten, de reviews en verdere
informatie over een specifiek boek zijn zichtbaar op de detailpagina van het betreffende boek.

De applicatie bevat zowel een REST API voor het beheren en opvragen van gegevens
als een webinterface die met Spring MVC en Thymeleaf wordt opgebouwd.

## 2. Gebruikte technologieën

- Java – programmeertaal.
- Maven - build tool.
- Spring Boot – basis voor de applicatie.
- Spring MVC – verwerking van HTTP-aanvragen en weergave van webpagina's.
- Thymeleaf – genereren van HTML-pagina's.
- Spring Data JPA – toegang tot en beheer van gegevens.
- Spring Security – authenticatie en toegangscontrole.
- MySQL – database.
- BCrypt – hashen van wachtwoorden.
- JUnit en Mockito – unit tests.

## 3. Functionaliteiten

### Catalogus
- Een overzicht van de beschikbare boeken bekijken.
- Boekdetails raadplegen, waaronder ISBN, titel, auteurs, beschrijving, genre, prijs en voorraad.
- De voorraad automatisch aanpassen wanneer boeken worden gereserveerd.

### Gebruikersbeheer
- Een account registreren, inloggen en uitloggen.
- Wachtwoorden veilig hashen met BCrypt.

### Reservaties en aankopen
- Boeken reserveren wanneer er voldoende voorraad is.
- De voorraad verminderen na een geslaagde reservatie.
- Openstaande reservaties in het profiel bekijken.
- Een reservatie kunnen markeren al voltooid.
- Aankoopgeschiedenis raadplegen.

### Reviews
- Reviews bekijken op de detailpagina van een boek.
- Een review schrijven voor een boek dat de gebruiker gekocht heeft.

## 4. Projectstructuur
### Het project is opgebouwd als één Spring Boot-applicatie met een REST API en een webinterface op basis van Spring MVC en Thymeleaf.

- Controller/ – bevat de REST- en MVC-controllers.
- Datasource/ – bevat de DAO-interfaces voor databasebewerkingen.
- Model/ – bevat de modellen voor alle entiteiten.
- Security/ – bevat de configuratiebestanden voor Spring Security.
  - Security/GebruikerService.java – haalt gebruikersgegevens op via e-mail voor de authenticatie door Spring Security.
  - Security/PasswordConfig.java - configureert BCrypt voor het hashen van wachtwoorden
  - Security/SecurityConfig.java configureert de toegangsrechten, inloggen en uitloggen
- resources/templates/ – bevat de Thymeleaf-templates.
- resources/static/css – bevat de css.
- test/java/ – bevat de unit tests.

## 5. Bronnen en hulpmiddelen

- https://www.thymeleaf.org/documentation.html
- https://site.mockito.org/
- https://docs.spring.io/spring-security/reference/index.html
- https://www.baeldung.com/spring-thymeleaf-fragments

### AI Tools
OpenAI ChatGPT werd gebruikt als ondersteuning bij het ontwikkelen en debuggen van onderdelen
van de applicatie:
- ondersteuning bij programmeren en foutopsporing.
- opstellen en aanpassen van unit tests
- ondersteuning bij Thymeleaf-templates en CSS.
- hulp bij het opstellen van documentatie