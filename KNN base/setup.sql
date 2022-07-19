/** ELIMINA L'UTENTE MAP SE VIENE TROVATO **/
DROP USER IF EXISTS map;

/** RICREA L'UTENTE MAP CON PASSWORD MAP **/
CREATE USER 'map' IDENTIFIED BY 'map';

/** CREA IL DATABASE map SE NON ESISTE GIA' **/
CREATE DATABASE IF NOT EXISTS MAP;

USE MAP;

/** DA I PRIVILEGI ALL'UTENTE MAP VERSO IL DATABASE MAP **/
GRANT ALL PRIVILEGES ON MAP.* TO 'map'@'%';

/** CREA UNA TABELLA DI ESEMPIO provaC **/
DROP TABLE IF EXISTS provaC;

CREATE TABLE provaC(
    X varchar(10),
    Y float(5,2),
	C float(5,2)
);

INSERT INTO provaC VALUES('A', 2, 1);
INSERT INTO provaC VALUES('A', 2, 1);
INSERT INTO provaC VALUES('A', 1, 1);
INSERT INTO provaC VALUES('A', 2, 1);
INSERT INTO provaC VALUES('A', 5, 1.5);
INSERT INTO provaC VALUES('A', 5, 1.5);
INSERT INTO provaC VALUES('A', 6, 1.5);
INSERT INTO provaC VALUES('B', 6, 10);
INSERT INTO provaC VALUES('A', 6, 1.5);
INSERT INTO provaC VALUES('A', 6, 1.5);
INSERT INTO provaC VALUES('B', 10, 10);
INSERT INTO provaC VALUES('B', 5, 10);
INSERT INTO provaC VALUES('B', 12, 10);
INSERT INTO provaC VALUES('B', 14, 10);
INSERT INTO provaC VALUES('A', 1, 1);

DROP TABLE IF EXISTS servo;
CREATE TABLE servo(
	motor varchar(10), 
	screw varchar(10),
	pgain float(5, 2), 
	vgain float(5, 2), 
	Y double
);

insert into servo values('E', 'E', 5, 4, 0.28125095);
insert into servo values('B', 'D', 6, 5, 0.5062525);
insert into servo values('D', 'D', 4, 3, 0.35625148);
insert into servo values('B', 'A', 3, 2, 5.500033);
insert into servo values('D', 'B', 6, 5, 0.35625148);
insert into servo values('E', 'C', 4, 3, 0.8062546);
insert into servo values('C', 'A', 3, 2, 5.100014);
insert into servo values('A', 'A', 3, 2, 5.7000422);
insert into servo values('C', 'A', 6, 5, 0.76875436);
insert into servo values('D', 'A', 4, 1, 1.0312537);
insert into servo values('B', 'E', 6, 5, 0.46875226);
insert into servo values('E', 'C', 5, 4, 0.39375174);
insert into servo values('B', 'C', 4, 1, 0.28125095);
insert into servo values('E', 'C', 3, 1, 1.1);
insert into servo values('C', 'C', 5, 4, 0.5062525);
insert into servo values('E', 'B', 3, 2, 1.8999897);
insert into servo values('D', 'C', 3, 1, 0.9000011);
insert into servo values('B', 'C', 5, 4, 0.46875226);
insert into servo values('B', 'B', 5, 4, 0.5437528);
insert into servo values('C', 'E', 4, 2, 0.20625044);
insert into servo values('E', 'D', 4, 3, 0.9187554);
insert into servo values('A', 'D', 4, 3, 1.1062483);
insert into servo values('B', 'C', 6, 5, 0.46875226);
insert into servo values('A', 'C', 4, 2, 0.58125305);
insert into servo values('A', 'B', 6, 5, 0.58125305);
insert into servo values('E', 'C', 6, 5, 0.39375174);
insert into servo values('A', 'A', 3, 1, 5.3000236);
insert into servo values('A', 'E', 4, 2, 0.46875226);
insert into servo values('C', 'D', 3, 2, 1.8999897);
insert into servo values('B', 'B', 3, 2, 4.299977);
insert into servo values('B', 'E', 4, 2, 0.35625148);
insert into servo values('B', 'C', 3, 1, 3.899964);
insert into servo values('C', 'E', 4, 1, 0.5437528);
insert into servo values('C', 'A', 6, 2, 0.5437528);
insert into servo values('C', 'C', 6, 5, 0.5062525);
insert into servo values('E', 'E', 3, 2, 1.1);
insert into servo values('D', 'E', 3, 1, 0.5000003);
insert into servo values('E', 'C', 4, 2, 0.13124992);
insert into servo values('C', 'B', 6, 5, 0.5437528);
insert into servo values('C', 'D', 4, 1, 0.20625044);
insert into servo values('D', 'B', 4, 1, 0.69375384);
insert into servo values('C', 'B', 4, 3, 0.88125515);
insert into servo values('C', 'C', 4, 3, 0.9187554);
insert into servo values('B', 'D', 4, 1, 0.2437507);
insert into servo values('B', 'A', 5, 3, 0.6562536);
insert into servo values('A', 'B', 4, 3, 1.0312537);
insert into servo values('B', 'A', 4, 1, 0.8062546);
insert into servo values('E', 'D', 4, 2, 0.431252);
insert into servo values('C', 'E', 3, 2, 4.0999675);
insert into servo values('D', 'D', 3, 1, 0.7000007);
insert into servo values('D', 'A', 6, 5, 0.431252);
insert into servo values('C', 'B', 3, 2, 4.499986);
insert into servo values('B', 'E', 3, 2, 4.6999955);
insert into servo values('C', 'D', 5, 4, 0.5062525);
insert into servo values('B', 'B', 4, 2, 0.7312541);
insert into servo values('A', 'E', 4, 3, 1.1437455);
insert into servo values('A', 'A', 4, 2, 0.88125515);
insert into servo values('B', 'D', 4, 3, 1.0312537);
insert into servo values('E', 'A', 3, 2, 6.9000983);
insert into servo values('B', 'C', 4, 3, 0.9562557);
insert into servo values('E', 'B', 4, 2, 0.58125305);
insert into servo values('E', 'A', 5, 4, 0.58125305);
insert into servo values('E', 'B', 5, 4, 0.431252);
insert into servo values('C', 'A', 6, 1, 0.5437528);
insert into servo values('D', 'A', 4, 3, 0.7312541);
insert into servo values('C', 'B', 4, 2, 0.5062525);
insert into servo values('D', 'B', 3, 2, 1.6999923);
insert into servo values('D', 'C', 3, 2, 1.2999974);
insert into servo values('C', 'A', 5, 2, 0.5437528);
insert into servo values('B', 'D', 4, 2, 0.39375174);
insert into servo values('B', 'A', 6, 5, 0.8062546);
insert into servo values('D', 'A', 4, 2, 0.28125095);
insert into servo values('C', 'B', 5, 4, 0.5437528);
insert into servo values('A', 'E', 6, 5, 0.5062525);
insert into servo values('A', 'C', 4, 1, 0.35625148);
insert into servo values('A', 'E', 5, 4, 0.5062525);
insert into servo values('E', 'C', 4, 1, 0.28125095);
insert into servo values('B', 'B', 3, 1, 4.499986);
insert into servo values('A', 'D', 3, 2, 4.6999955);
insert into servo values('E', 'D', 3, 2, 1.2999974);
insert into servo values('E', 'A', 3, 1, 7.1001077);
insert into servo values('A', 'C', 6, 5, 0.5062525);
insert into servo values('C', 'E', 5, 4, 0.46875226);
insert into servo values('C', 'A', 5, 4, 0.76875436);
insert into servo values('E', 'A', 6, 5, 0.58125305);
insert into servo values('B', 'E', 5, 4, 0.46875226);
insert into servo values('E', 'E', 4, 3, 0.8437549);
insert into servo values('B', 'A', 4, 2, 0.8437549);
insert into servo values('B', 'D', 5, 4, 0.5062525);
insert into servo values('C', 'C', 4, 2, 0.35625148);
insert into servo values('A', 'A', 5, 3, 0.69375384);
insert into servo values('C', 'E', 4, 3, 1.068751);
insert into servo values('A', 'A', 4, 3, 1.1062483);
insert into servo values('C', 'A', 6, 3, 0.5437528);
insert into servo values('A', 'E', 4, 1, 0.2437507);
insert into servo values('A', 'D', 6, 5, 0.5062525);
insert into servo values('E', 'D', 3, 1, 0.9000011);
insert into servo values('C', 'B', 4, 1, 0.431252);
insert into servo values('B', 'D', 3, 2, 4.0999675);
insert into servo values('B', 'B', 4, 3, 0.99375594);
insert into servo values('B', 'C', 4, 2, 0.5062525);
insert into servo values('A', 'E', 3, 2, 4.499986);
insert into servo values('B', 'D', 3, 1, 3.899964);
insert into servo values('D', 'B', 5, 4, 0.39375174);
insert into servo values('C', 'C', 4, 1, 0.2437507);
insert into servo values('C', 'D', 4, 2, 0.2437507);
insert into servo values('E', 'B', 4, 1, 1.1812428);
insert into servo values('D', 'B', 3, 1, 1.2999974);
insert into servo values('E', 'B', 6, 5, 0.431252);
insert into servo values('D', 'A', 3, 1, 2.499982);
insert into servo values('A', 'D', 5, 4, 0.5062525);
insert into servo values('C', 'A', 4, 1, 0.7312541);
insert into servo values('C', 'D', 6, 5, 0.46875226);
insert into servo values('B', 'A', 4, 3, 1.068751);
insert into servo values('E', 'A', 4, 3, 1.2187401);
insert into servo values('A', 'A', 4, 1, 0.8437549);
insert into servo values('A', 'C', 4, 3, 0.99375594);
insert into servo values('E', 'D', 6, 5, 0.31875122);
insert into servo values('E', 'A', 4, 2, 0.99375594);
insert into servo values('C', 'D', 3, 1, 1.4999949);
insert into servo values('B', 'B', 4, 1, 0.58125305);
insert into servo values('C', 'A', 4, 2, 0.76875436);
insert into servo values('C', 'A', 5, 1, 0.5437528);
insert into servo values('C', 'E', 3, 1, 1.2999974);
insert into servo values('C', 'A', 3, 1, 4.299977);
insert into servo values('C', 'A', 4, 3, 1.0312537);
insert into servo values('C', 'C', 3, 1, 1.8999897);
insert into servo values('D', 'A', 5, 4, 0.431252);
insert into servo values('A', 'B', 5, 4, 0.58125305);
insert into servo values('C', 'C', 3, 2, 4.299977);
insert into servo values('E', 'D', 5, 4, 0.31875122);
insert into servo values('D', 'C', 4, 3, 0.5437528);
insert into servo values('E', 'E', 6, 5, 0.28125095);
insert into servo values('D', 'B', 4, 2, 0.35625148);
insert into servo values('A', 'D', 4, 2, 0.46875226);
insert into servo values('B', 'B', 6, 5, 0.5437528);
insert into servo values('A', 'B', 4, 1, 0.6187533);
insert into servo values('A', 'C', 5, 4, 0.5062525);
insert into servo values('B', 'E', 4, 1, 0.20625044);
insert into servo values('C', 'B', 3, 1, 3.899964);
insert into servo values('E', 'E', 4, 2, 0.5062525);
insert into servo values('B', 'E', 4, 3, 1.1062483);
insert into servo values('A', 'E', 3, 1, 3.899964);
insert into servo values('A', 'B', 4, 2, 0.8062546);
insert into servo values('A', 'C', 3, 1, 3.899964);
insert into servo values('E', 'C', 3, 2, 1.4999949);
insert into servo values('B', 'A', 3, 1, 5.100014);
insert into servo values('D', 'D', 3, 2, 1.4999949);
insert into servo values('A', 'C', 3, 2, 4.6999955);
insert into servo values('E', 'A', 4, 1, 0.88125515);
insert into servo values('B', 'A', 5, 4, 0.8062546);
insert into servo values('E', 'E', 3, 1, 0.7000007);
insert into servo values('D', 'E', 3, 2, 0.9000011);
insert into servo values('E', 'B', 3, 1, 1.4999949);
insert into servo values('A', 'D', 4, 1, 0.2437507);
insert into servo values('A', 'D', 3, 1, 4.0999675);
insert into servo values('E', 'B', 4, 3, 0.99375594);
insert into servo values('A', 'B', 3, 1, 4.6999955);
insert into servo values('D', 'B', 4, 3, 0.58125305);
insert into servo values('A', 'A', 5, 4, 0.8062546);
insert into servo values('D', 'A', 3, 2, 2.6999795);
insert into servo values('C', 'E', 6, 5, 0.46875226);
insert into servo values('B', 'C', 3, 2, 4.499986);
insert into servo values('B', 'E', 3, 1, 3.6999667);
insert into servo values('C', 'D', 4, 3, 0.9562557);
insert into servo values('A', 'B', 3, 2, 4.499986);
insert into servo values('A', 'A', 6, 5, 0.8062546);
