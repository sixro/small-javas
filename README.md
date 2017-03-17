# small-javas

A container of small Java objects.

This is not a project, but a sort of container of small Java objects. Every sub-directory is a very simple maven project containing one file (and probably one class but you can use inner classes).
This is not for any good reason, it is an exercise forcing me to write small objects with the purpose to create anyway some sort of value in that (the object can be used for real :D).

## Usage

In order to use the dependency, you can use [jitpack.io](https://jitpack.io/#maven)

## TODOs

  * un oggetto __DB__, perche' semplicemente leggere/scrivere i dati e' sempre troppo lento e vorrei qualcosa che con convention over configuration mi possa aiutare ad esser rapido a prototipizzare. Meno cazzi con OneToMany e paduli da ORM, ma neanche scriviti la SQL senno muori... avevo gia' sperimento a riguardo... va ripreso il tema. L'idea l'altra volta era scrivi l'SQL e poi ci penso io a fare il mapper basta che esponi un metodo statico valueOf con i param nell'ordine della query (preso spunto da WEB4J framework)... credo serva qualcosa di piu'... salva sto oggetto in tabella, leggi sto oggetto da tabella, (ripeto) con qualche convention-over-configuration dovrebbe esser fattibile... magari invece di __DB__ trovare un nome piu' chiaro...
    ** download ubuntu 32 bit
    ** install a VM
    ** configure a MySql DB
    ** configure VM to be able to connect to the VM MySQL using local MySqlWorkbench
    ** start developing the object
  * oggetto __DataSourceFactory__ che fornisca factory methods per creare datasource al volo per specifici DB. Potrebbe usare le datasource dei driver e specificare in javadoc cosa serva come dependencies per poterlo usare, cosi da non richiedere l'introduzione esplicita dei pacchetti e fornire documentazione su eventuali deps da includere (visto che ogni volta e' un casino)
  * oggetto __JSON__? via reflection... troppo complesso? da ragionare...
  * oggetto __REST__ che faccia sia possa esser usato sia come client che come creatore di server? potrebbe sfruttare __JSON__
  * oggetto __WebHook__ che faccia da WebHook... gia' scritto in altro progetto, ma se usasse __REST__ sarebbe fantastico... soprattutto quell'helper che gli consente di mandare via WebHook chiamate prox-ate di un eventuale *listener*
  * oggetto __HttpRequestSigner__ per sign-are le request come suggerito nella doc di Amazon AWS?
