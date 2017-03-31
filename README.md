# small-javas

A container of small Java objects.

This is not a project, but a sort of container of small Java objects. Every sub-directory is a very simple maven project containing one file (and probably one class but you can use inner classes).
This is not for any good reason, it is an exercise forcing me to write small objects with the purpose to create anyway some sort of value in that (the object can be used for real :D).

## Usage

In order to use the dependency, you can use [jitpack.io](https://jitpack.io/#maven)

## TODOs

  * oggetto __JSON__? via reflection... troppo complesso? da ragionare...
  * oggetto __REST__ che faccia sia possa esser usato sia come client che come creatore di server? potrebbe sfruttare __JSON__
  * oggetto __WebHook__ che faccia da WebHook... gia' scritto in altro progetto, ma se usasse __REST__ sarebbe fantastico... soprattutto quell'helper che gli consente di mandare via WebHook chiamate prox-ate di un eventuale *listener*
  * oggetto __HttpRequestSigner__ per sign-are le request come suggerito nella doc di Amazon AWS?
