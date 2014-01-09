-- ===============================================================================
-- Jede SQL-Anweisung muss in genau 1 Zeile
-- Kommentare durch -- am Zeilenanfang
-- ===============================================================================

--
-- kunde
--
INSERT INTO kunde (id, nachname, vorname, email, erzeugt, aktualisiert, seit) VALUES (1,'Admin','Admin','admin@hs-karlsruhe.de','01.08.2006 00:00:00','01.08.2006 00:00:00', '01.01.2001');
INSERT INTO kunde (id, nachname, vorname, email, erzeugt, aktualisiert, seit) VALUES (101,'Alpha','Adriana','101@hs-karlsruhe.de','01.08.2006 00:00:00','01.08.2006 00:00:00', '31.01.2001');
INSERT INTO kunde (id, nachname, vorname, email, erzeugt, aktualisiert, seit) VALUES (102,'Alpha','Alfred','102@hs-karlsruhe.de','02.08.2006 00:00:00','02.08.2006 00:00:00', '28.01.2001');
INSERT INTO kunde (id, nachname, vorname, email, erzeugt, aktualisiert, seit) VALUES (103,'Alpha','Anton','103@hs-karlsruhe.de','03.08.2006 00:00:00','03.08.2006 00:00:00', '15.01.2001');
INSERT INTO kunde (id, nachname, vorname, email, erzeugt, aktualisiert, seit) VALUES (104,'Delta','Dirk','104@hs-karlsruhe.de','04.08.2006 00:00:00','04.08.2006 00:00:00', '09.01.2001');
INSERT INTO kunde (id, nachname, vorname, email, erzeugt, aktualisiert, seit) VALUES (105,'Epsilon','Emil','105@hs-karlsruhe.de','05.08.2006 00:00:00','05.08.2006 00:00:00', '10.01.2001');

--
-- adresse
--
INSERT INTO adresse (id, plz, ort, strasse, hausnr, kunde_fk, erzeugt, aktualisiert) VALUES (200,'76133','Karlsruhe','Moltkestrasse','30',1,'01.08.2006 00:00:00','01.08.2006 00:00:00');
INSERT INTO adresse (id, plz, ort, strasse, hausnr, kunde_fk, erzeugt, aktualisiert) VALUES (201,'76133','Karlsruhe','Moltkestrasse','31',101,'02.08.2006 00:00:00','02.08.2006 00:00:00');
INSERT INTO adresse (id, plz, ort, strasse, hausnr, kunde_fk, erzeugt, aktualisiert) VALUES (202,'76133','Karlsruhe','Moltkestrasse','32',102,'03.08.2006 00:00:00','03.08.2006 00:00:00');
INSERT INTO adresse (id, plz, ort, strasse, hausnr, kunde_fk, erzeugt, aktualisiert) VALUES (203,'76133','Karlsruhe','Moltkestrasse','33',103,'04.08.2006 00:00:00','04.08.2006 00:00:00');
INSERT INTO adresse (id, plz, ort, strasse, hausnr, kunde_fk, erzeugt, aktualisiert) VALUES (204,'76133','Karlsruhe','Moltkestrasse','34',104,'05.08.2006 00:00:00','05.08.2006 00:00:00');
INSERT INTO adresse (id, plz, ort, strasse, hausnr, kunde_fk, erzeugt, aktualisiert) VALUES (205,'76133','Karlsruhe','Moltkestrasse','35',105,'06.08.2006 00:00:00','06.08.2006 00:00:00');

--
-- kunde_hobby : S = SPORT, L = LESEN, R = REISEN
--
INSERT INTO kunde_hobby (kunde_fk, hobby) VALUES (101,'S');
INSERT INTO kunde_hobby (kunde_fk, hobby) VALUES (101,'L');
INSERT INTO kunde_hobby (kunde_fk, hobby) VALUES (102,'S');
INSERT INTO kunde_hobby (kunde_fk, hobby) VALUES (102,'R');
INSERT INTO kunde_hobby (kunde_fk, hobby) VALUES (105,'L');
INSERT INTO kunde_hobby (kunde_fk, hobby) VALUES (105,'R');

--
-- wartungsvertrag
--
--INSERT INTO wartungsvertrag (nr, datum, inhalt, kunde_fk, idx, erzeugt, aktualisiert) VALUES (1,'31.01.2005','Wartungsvertrag_1_K101',101,0,'01.08.2006 00:00:00','01.08.2006 00:00:00');
--INSERT INTO wartungsvertrag (nr, datum, inhalt, kunde_fk, idx, erzeugt, aktualisiert) VALUES (2,'31.01.2006','Wartungsvertrag_2_K101',101,1,'02.08.2006 00:00:00','02.08.2006 00:00:00');
--INSERT INTO wartungsvertrag (nr, datum, inhalt, kunde_fk, idx, erzeugt, aktualisiert) VALUES (1,'30.06.2006','Wartungsvertrag_1_K102',102,0,'03.08.2006 00:00:00','03.08.2006 00:00:00');

--
-- artikel
--
INSERT INTO artikel (id, bezeichnung, preis, ausgesondert, erzeugt, aktualisiert) VALUES (300,'Tisch ''Oval''',80,0,'01.08.2006 00:00:00','01.08.2006 00:00:00');
INSERT INTO artikel (id, bezeichnung, preis, ausgesondert, erzeugt, aktualisiert) VALUES (301,'Stuhl ''Sitz bequem''',10,0,'02.08.2006 00:00:00','02.08.2006 00:00:00');
INSERT INTO artikel (id, bezeichnung, preis, ausgesondert, erzeugt, aktualisiert) VALUES (302,'Tür ''Hoch und breit''',300,0,'03.08.2006 00:00:00','03.08.2006 00:00:00');
INSERT INTO artikel (id, bezeichnung, preis, ausgesondert, erzeugt, aktualisiert) VALUES (303,'Fenster ''Glasklar''',150,0,'04.08.2006 00:00:00','04.08.2006 00:00:00');
INSERT INTO artikel (id, bezeichnung, preis, ausgesondert, erzeugt, aktualisiert) VALUES (304,'Spiegel ''Mach mich schöner''',60,0,'05.08.2006 00:00:00','05.08.2006 00:00:00');
INSERT INTO artikel (id, bezeichnung, preis, ausgesondert, erzeugt, aktualisiert) VALUES (305,'Kleiderschrank ''Viel Platz''',500,0,'06.08.2006 00:00:00','06.08.2006 00:00:00');
INSERT INTO artikel (id, bezeichnung, preis, ausgesondert, erzeugt, aktualisiert) VALUES (306,'Bett ''Mit Holzwurm''',600,0,'07.08.2006 00:00:00','07.08.2006 00:00:00');

--
-- bestellung
--
INSERT INTO bestellung (id, kunde_fk, idx, erzeugt, aktualisiert, ausgeliefert) VALUES (400,101,0,'01.08.2006 00:00:00','01.08.2006 00:00:00',0);
INSERT INTO bestellung (id, kunde_fk, idx, erzeugt, aktualisiert, ausgeliefert) VALUES (401,101,1,'02.08.2006 00:00:00','02.08.2006 00:00:00',0);
INSERT INTO bestellung (id, kunde_fk, idx, erzeugt, aktualisiert, ausgeliefert) VALUES (402,102,0,'03.08.2006 00:00:00','03.08.2006 00:00:00',0);
INSERT INTO bestellung (id, kunde_fk, idx, erzeugt, aktualisiert, ausgeliefert) VALUES (403,102,1,'04.08.2006 00:00:00','04.08.2006 00:00:00',0);
INSERT INTO bestellung (id, kunde_fk, idx, erzeugt, aktualisiert, ausgeliefert) VALUES (404,104,0,'05.08.2006 00:00:00','05.08.2006 00:00:00',0);

--
-- bestellposition
--
INSERT INTO posten (id, bestellung_fk, artikel_fk, anzahl, erzeugt, aktualisiert) VALUES (500,400,300,1,'01.08.2006 00:00:00','01.08.2006 00:00:00');
INSERT INTO posten (id, bestellung_fk, artikel_fk, anzahl, erzeugt, aktualisiert) VALUES (501,400,301,4,'01.08.2006 00:00:00','01.08.2006 00:00:00');
INSERT INTO posten (id, bestellung_fk, artikel_fk, anzahl, erzeugt, aktualisiert) VALUES (502,401,302,5,'02.08.2006 00:00:00','02.08.2006 00:00:00');
INSERT INTO posten (id, bestellung_fk, artikel_fk, anzahl, erzeugt, aktualisiert) VALUES (503,402,303,3,'03.08.2006 00:00:00','03.08.2006 00:00:00');
INSERT INTO posten (id, bestellung_fk, artikel_fk, anzahl, erzeugt, aktualisiert) VALUES (504,402,304,2,'03.08.2006 00:00:00','03.08.2006 00:00:00');
INSERT INTO posten (id, bestellung_fk, artikel_fk, anzahl, erzeugt, aktualisiert) VALUES (505,403,305,1,'04.08.2006 00:00:00','04.08.2006 00:00:00');
INSERT INTO posten (id, bestellung_fk, artikel_fk, anzahl, erzeugt, aktualisiert) VALUES (506,404,300,5,'05.08.2006 00:00:00','05.08.2006 00:00:00');
INSERT INTO posten (id, bestellung_fk, artikel_fk, anzahl, erzeugt, aktualisiert) VALUES (507,404,301,2,'05.08.2006 00:00:00','05.08.2006 00:00:00');
INSERT INTO posten (id, bestellung_fk, artikel_fk, anzahl, erzeugt, aktualisiert) VALUES (508,404,302,8,'05.08.2006 00:00:00','05.08.2006 00:00:00');

--
-- lieferung
--
--INSERT INTO lieferung (id, liefernr, transport_art, erzeugt, aktualisiert) VALUES (600,'20051005-001','ST','01.08.2006 00:00:00','01.08.2006 00:00:00');
--INSERT INTO lieferung (id, liefernr, transport_art, erzeugt, aktualisiert) VALUES (601,'20051005-002','SCH','02.08.2006 00:00:00','02.08.2006 00:00:00');
--INSERT INTO lieferung (id, liefernr, transport_art, erzeugt, aktualisiert) VALUES (602,'20051005-003','L','03.08.2006 00:00:00','03.08.2006 00:00:00');
--INSERT INTO lieferung (id, liefernr, transport_art, erzeugt, aktualisiert) VALUES (603,'20051008-001','W','04.08.2006 00:00:00','04.08.2006 00:00:00');

--
-- bestellung_lieferung
--
--INSERT INTO bestellung_lieferung (bestellung_fk, lieferung_fk) VALUES (400,600);
--INSERT INTO bestellung_lieferung (bestellung_fk, lieferung_fk) VALUES (401,600);
--INSERT INTO bestellung_lieferung (bestellung_fk, lieferung_fk) VALUES (402,601);
--INSERT INTO bestellung_lieferung (bestellung_fk, lieferung_fk) VALUES (402,602);
--INSERT INTO bestellung_lieferung (bestellung_fk, lieferung_fk) VALUES (403,602);
--INSERT INTO bestellung_lieferung (bestellung_fk, lieferung_fk) VALUES (404,603);
