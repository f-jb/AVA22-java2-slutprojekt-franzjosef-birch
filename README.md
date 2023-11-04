# Producer-Consumer complex
Detta var ett relativt intressant projekt. Särskilt då jag inte skrivit så mycket concurrencykod tidigare.

## Concurrency
Enligt vad jag läst mig till i olika böcker så borde man inte använda Threads direkt, vilket jag antog som en utmaning. Det är skrivet i dels *Head First Java*[^1], *Effective Java*[^2] och *Java Concurrency in Practice*[^3]

## Enums som singletons
**Item 3** i *Effective Java*[^2] anger att man böra använda static factories eller enums för att säkerställe singletonheten i singletons. Jag känner att det finns mer att förstå vid användadet av enums.

## Testdrivet
Jag började projektet testdrivet med röd-grön-gul testning. Det gick bra till början då jag kunde skriva testen. Så fort det blev komplicerat med ThreadLocalRandom och Concurrency så fick jag tyvärr ge upp.

## Programbeskrivning
Det första jag skrev var WorkFactory och WorkerFactory. Därefter kopplade jag ihop det med LinkedBlockingQueue för att kunna lägga till i ena änden och ta bort i andra. Så fort logiken var på plats skrev jag till concurrency, först med lika många trådar som datorn hade kärnor. Detta visade sig ett misstag då det slutade fungera med logiken. Däremot genom att starta en ny thread för varje task så fungerade det. Log var relativt enkelt att skriva, särskilt då jag kunde göra det som en enum.

Med logiken färdig så var det bara att skriva Controller och View. Dessa skrevs ganska så parallellt. GUI är krångligt att få till rätt och det finns lite udda beteende i programmet.


[^1]: Sierra, Kathy & Bates, Bert & Gee, Trisha - Head First Java, Third Edition (2022). O'Reilly Media, Inc. 
[^2]: Bloch, Joshua  - Effective Java, Third Edition (2018). Addison-Wesley. 
[^3]: Goetz, Brian & Peierls, Tim & Bloch, Joshua & Bowbeer, Joseph & Holmes, David & Lea, Doug - Java Concurrency in Practice (2006). Addison-Wesley.
