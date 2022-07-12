# **KNN MINER**

## Indice

---
[1. Introduzione del progetto](#1-introduzione-del-progetto)

[2. Guida di installazione](#2-guida-di-installazione)
- [2.1 Installazione MYSQL](#21-installazione-mysql)
- [2.2 Installazione applicazione](#22-installazione-applicazione)

[3. Diagrammi delle classi](#3-diagrammi-delle-classi)

[4. Manuale Utente](#4-manuale-utente)

---

## **1. Introduzione del progetto**
> KNN Miner ecc ecc SCRIVERE QUA!

---

## **2. Guida di installazione**
## 2.1 Installazione MYSQL
---
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
Requisiti: **git bash**


Per poter utilizzare l'applicazione è **necessario** prima avviare il server.

Avviare quindi il server direttamente avviando il file `server.sh` da terminale *git bash* tramite il comando `sh server.sh` oppure, in alternativa, direttamente tramite il file __jar__, attraverso il comando da eseguire nella cartella *Server*:
> __`java -jar Server.jar`__

<br />

Allo stesso modo, avviare i/il client direttamente avviando il file `client.sh` da terminale *git bash* tramite il comando `sh client.sh` oppure, in alternativa, direttamente tramite il file __jar__, attraverso il comando da eseguire nella cartella *Client*:
> __`java -jar Client.jar`__

<br />

**NB:** E' possibile avere aperto un unico **Server** alla volta, infatti il sistema impedirà di aprirne più di uno contemporaneamente. Per questo motivo fare attenzione quando si prova a chiudere il terminale del server: usare la combinazione __CTRL+C__ invece di chiudere direttamente il terminale perchè altrimenti il processo java rimarrà ancora in esecuzione(in quel caso bisogna terminare il processo dal task manager)! 

E' invece possibile avviare più **Client** contemporaneamente.

## **3. Diagrammi delle classi**

---

## **4. Manuale Utente**
---
All'apertura dell'applicazione client, l'utente dovrà scegliere con quale modalità caricare il training set come da figura:

![Home](img/home.png)

Se si sceglie l'opzione **1)** o **2)**, l'applicazione chiederà il nome del file. Inserire il file da caricare nella cartella __Server/training_set__ e quindi scrivere il nome del file **senza** l'estensione: verrà presa in automatico dal Server(__.dat__ per i file testuali, __.dmp__ per i file binari).

![File](img/file.png)

Se si sceglie l'opzione **3)** l'applicazione chiederà il nome della tabella, quindi inserire il nome della tabella corretta.

![Database](img/database.png)

Una volta caricato correttamente il training set, verrà stampato il messaggio **"Modello KNN caricato con successo!"**.

A questo punto, l'applicazione chiederà di inserire l'esempio da predire facendo attenzione al tipo di dato (discreto o continuo) ed infine il valore di k:

![Esempio](img/example.png)

Il server risponderà quindi con la predizione corretta:

![Predizione](img/predizione.png)

Se si vuole continuare ad eseguire predizioni sul modello caricato, scrivere **Y**, altrimenti **N**.
Se si preme **N**, verrà chiesto se caricare un nuovo modello (rispondere quindi allo stesso modo con **Y** o **N**).

Se si risponde con **N** anche a questa seconda domanda, l'applicazione si chiuderà.

![Chiusura](img/close.png)