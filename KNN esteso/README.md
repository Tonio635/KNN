# **KNN MINER esteso**

## Indice

---
[1. Introduzione del progetto](#1-introduzione-del-progetto)

[2. Guida di installazione](#2-guida-di-installazione)
- [2.1 Installazione MYSQL](#21-installazione-mysql)
- [2.2 Installazione applicazione](#22-installazione-applicazione)
- [2.3 Importazione su eclipse](#23-importazione-su-eclipse)

[3. Diagrammi delle classi](#3-diagrammi-delle-classi)

[4. Manuale Utente](#4-manuale-utente)

---

## **1. Introduzione del progetto**

---

## **2. Guida di installazione**
## 2.1 Installazione MYSQL
---
La libreria per connettersi a mysql è inclusa nei package di spring. Le modalità di connessione al DB sono equivalenti a quelle del progetto base, ovvero:

- Autenticarsi da terminale su *mysql* con l'utente __root__;
- Spostarsi nella directory corrente tramite comando `cd directory` (o qualunque altra directory, l'importante è che ci sia il file **setup.sql**);
- Eseguire il seguente comando: 
    > __`SOURCE setup.sql;`__

Lo script sql eseguirà le seguenti operazioni:
1. Creare l'utente **map** con password **map** (se c'era già prima lo elimina);
2. Creare il database **map** se non già presente;
3. Dare tutti i privilegi all'utente **map** sul database **map**;
4. Creare la tabella **provaC** nel database **map** (se c'era già prima la elimina);

## 2.2 Installazione applicazione
---
Requisiti: **jdk-17**, **git bash**


Per poter utilizzare l'applicazione è **necessario** prima avviare il **server web** di spring (la porta utilizzata sarà la 8080).

Avviare quindi il server direttamente avviando il file `server.sh` da terminale *git bash* tramite il comando `sh server.sh` oppure, in alternativa, direttamente tramite il file __jar__, attraverso il comando da eseguire nella cartella *Server*:
> __`java -jar Server.jar`__

<br />

Per poter avviare l'applicazione tramite **Client** è necessario aprire un browser qualunque e recarsi all'indirizzo:
> __`localhost:8080`__

<br />

**NB:** E' possibile avere aperto un unico **Server** alla volta, infatti il sistema impedirà di aprirne più di uno contemporaneamente. Per questo motivo fare attenzione quando si prova a chiudere il terminale del server: usare la combinazione __CTRL+C__ invece di chiudere direttamente il terminale perchè altrimenti il processo java rimarrà ancora in esecuzione(in quel caso bisogna terminare il processo dal task manager)! 

E' invece possibile connettersi tramite più **Client** in diverse pagine web contemporaneamente.


## 2.3 Importazione su eclipse
---
Il progetto esteso funziona solamente con la **jdk-17** di Java. Se non si dispone di questa versione scaricarla da [questo link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

1) Per poter importare su eclipse il progetto, è necessario scaricare dal marketplace di eclipse l'estensione `'Buildship Gradle Integration 3.0'`. 

2) Una volta fatto ciò, importare tramite Gradle con la voce `'Existing Gradle Project'` e selezionare nella voce `'Project root directory'` la cartella `'KNN esteso/knn'` e cliccare su Finish.

3) L'ultima cosa da fare è indicare a Gradle il corretto percorso della jdk-17. Recarsi in `'Window -> Preferences -> Gradle'`, selezionare la Java home indicando il corretto percorso (dovrebbe trovarsi in questo percorso `'C:\Program Files\Java\jdk-17.0.0'` su Windows).

Se tutto è andato a buon fine, il progetto è stato correttamente importato in Eclipse.

## **3. Diagrammi delle classi**

---

## **4. Manuale Utente**
---