use jskrzypiec;
go
---------------------------------------------------
-------------------kasowanie FK--------------------
---------------------------------------------------
IF OBJECT_ID('zawodnik_pozycja_FK', 'F') IS NOT NULL
BEGIN
	ALTER TABLE dbo.zawodnik
	DROP CONSTRAINT zawodnik_pozycja_FK;
END;
GO
IF OBJECT_ID('zawodnik_adres_FK', 'F') IS NOT NULL
BEGIN
	ALTER TABLE dbo.zawodnik
	DROP CONSTRAINT zawodnik_adres_FK;
END;
GO
IF OBJECT_ID('zawodnik_klub_FK', 'F') IS NOT NULL
BEGIN
	ALTER TABLE dbo.zawodnik
	DROP CONSTRAINT zawodnik_klub_FK;
END;
GO
IF OBJECT_ID('zawodnik_grupa_FK', 'F') IS NOT NULL
BEGIN
	ALTER TABLE dbo.zawodnik
	DROP CONSTRAINT zawodnik_grupa_FK;
END;
GO
--
IF OBJECT_ID('adres_miasto_FK', 'F') IS NOT NULL
BEGIN
	ALTER TABLE dbo.adres
	DROP CONSTRAINT adres_miasto_FK;
END;
GO
--
IF OBJECT_ID('grupa_kategoria_FK', 'F') IS NOT NULL
BEGIN
	ALTER TABLE dbo.grupa
	DROP CONSTRAINT grupa_kategoria_FK;
END;
GO
IF OBJECT_ID('grupa_trener_FK', 'F') IS NOT NULL
BEGIN
	ALTER TABLE dbo.grupa
	DROP CONSTRAINT grupa_trener_FK;
END;
GO
--
IF OBJECT_ID('trener_adres_FK', 'F') IS NOT NULL
BEGIN
	ALTER TABLE dbo.trener
	DROP CONSTRAINT trener_adres_FK;
END;
GO
IF OBJECT_ID('trener_klub_FK', 'F') IS NOT NULL
BEGIN
	ALTER TABLE dbo.trener
	DROP CONSTRAINT trener_klub_FK;
END;
GO
--
IF OBJECT_ID('zajecia_dzienTygodnia_FK', 'F') IS NOT NULL
BEGIN
	ALTER TABLE dbo.zajecia
	DROP CONSTRAINT zajecia_dzienTygodnia_FK;
END;
GO
IF OBJECT_ID('zajecia_grupa_FK', 'F') IS NOT NULL
BEGIN
	ALTER TABLE dbo.zajecia
	DROP CONSTRAINT zajecia_grupa_FK;
END;
GO
IF OBJECT_ID('zajecia_sala_FK', 'F') IS NOT NULL
BEGIN
	ALTER TABLE dbo.zajecia
	DROP CONSTRAINT zajecia_sala_FK;
END;
GO
--
IF OBJECT_ID('sala_adres_FK', 'F') IS NOT NULL
BEGIN
	ALTER TABLE dbo.sala
	DROP CONSTRAINT sala_adres_FK;
END;
GO

---------------------------------------------------
-----------------tworzenie tabel-------------------
---------------------------------------------------
IF OBJECT_ID('dbo.zawodnik') IS NOT NULL
  DROP TABLE dbo.zawodnik;
GO
CREATE TABLE dbo.zawodnik
(
	id				INT IDENTITY(1,1) CONSTRAINT zawodnik_PK PRIMARY KEY ,
	imie			NVARCHAR(16) not null CHECK(LEN(imie)>2),
	nazwisko		NVARCHAR(32) not null CHECK(LEN(nazwisko)>2),
	rokUrodzenia	SMALLINT not null CHECK( LEN(rokUrodzenia)>3 AND LEN(rokUrodzenia)<5 AND rokUrodzenia>1700 AND rokUrodzenia<YEAR(GETDATE()) ),
	wzrost			SMALLINT CHECK(wzrost>0 AND wzrost<300),
	waga			SMALLINT CHECK(waga>0 AND waga<1000),
	nrTelefonu		INTEGER CHECK(nrTelefonu>0 AND LEN(nrTelefonu)>8 AND LEN(nrTelefonu)<12),
	pozycja_id		INTEGER not null,
	adres_id		INTEGER,
	klub_id			INTEGER,
	grupa_id		INTEGER,
	login_			NVARCHAR(32) CHECK(LEN(login_)>2),
	haslo_			NVARCHAR(32) CHECK(LEN(haslo_)>4)
);
--
IF OBJECT_ID('dbo.pozycja') IS NOT NULL
  DROP TABLE dbo.pozycja;
GO
CREATE TABLE dbo.pozycja
(
	id				INT IDENTITY(1,1) CONSTRAINT pozycja_PK PRIMARY KEY ,
	nazwa			NVARCHAR(50) not null CHECK(LEN(nazwa)>2)
);
--
IF OBJECT_ID('dbo.adres') IS NOT NULL
  DROP TABLE dbo.adres;
GO
CREATE TABLE dbo.adres
(
	id				INT IDENTITY(1,1) CONSTRAINT adres_PK PRIMARY KEY ,
	ulica			NVARCHAR(50) not null,
	nrDomu			NVARCHAR(10) not null,
	nrMieszkania	SMALLINT CHECK(nrMieszkania>0),
	kodPocztowy		CHAR(6),
	miasto_id		INTEGER not null
);
--
IF OBJECT_ID('dbo.miasto') IS NOT NULL
  DROP TABLE dbo.miasto;
GO
CREATE TABLE dbo.miasto
(
	id				INT IDENTITY(1,1) CONSTRAINT miasto_PK PRIMARY KEY ,
	nazwa			NVARCHAR(40) not null CHECK(LEN(nazwa)>2)
);
--
IF OBJECT_ID('dbo.klub') IS NOT NULL
  DROP TABLE dbo.klub;
GO
CREATE TABLE dbo.klub
(
	id				INT IDENTITY(1,1) CONSTRAINT klub_PK PRIMARY KEY ,
	nazwa			NVARCHAR(50) not null,
	rokZalozenia	SMALLINT CHECK(LEN(rokZalozenia)>3 AND LEN(rokZalozenia)<5),
	rokRozwiazania	SMALLINT CHECK(LEN(rokRozwiazania)>3 AND LEN(rokRozwiazania)<5)
);
--
IF OBJECT_ID('dbo.grupa') IS NOT NULL
  DROP TABLE dbo.grupa;
GO
CREATE TABLE dbo.grupa
(
	id				INT IDENTITY(1,1) CONSTRAINT grupa_PK PRIMARY KEY ,
	nazwa			NVARCHAR(40),
	kategoria_id	INTEGER not null,
	trener_id		INTEGER
);
--
IF OBJECT_ID('dbo.kategoria') IS NOT NULL
  DROP TABLE dbo.kategoria;
GO
CREATE TABLE dbo.kategoria
(
	id				INT IDENTITY(1,1) CONSTRAINT kategoria_PK PRIMARY KEY ,
	nazwa			NVARCHAR(20) not null CHECK(LEN(nazwa)>2)
);
--
IF OBJECT_ID('dbo.trener') IS NOT NULL
  DROP TABLE dbo.trener;
GO
CREATE TABLE dbo.trener
(
	id				INT IDENTITY(1,1) CONSTRAINT trener_PK PRIMARY KEY ,
	imie			NVARCHAR(16) not null CHECK(LEN(imie)>2),
	nazwisko		NVARCHAR(32) not null CHECK(LEN(nazwisko)>2),
	rokUrodzenia	SMALLINT not null CHECK(LEN(rokUrodzenia)>3 AND LEN(rokUrodzenia)<5 AND rokUrodzenia>1700 AND rokUrodzenia<YEAR(GETDATE()) ),
	nrTelefonu		INTEGER CHECK(nrTelefonu>0 AND LEN(nrTelefonu)>8 AND LEN(nrTelefonu)<12),	
	adres_id		INTEGER,
	klub_id			INTEGER,
	login_			NVARCHAR(32) CHECK(LEN(login_)>2),
	haslo_			NVARCHAR(32) CHECK(LEN(haslo_)>4)
);
--
IF OBJECT_ID('dbo.zajecia') IS NOT NULL
  DROP TABLE dbo.zajecia;
GO
CREATE TABLE dbo.zajecia
(
	id					INT IDENTITY(1,1) CONSTRAINT zajecia_PK PRIMARY KEY ,
	godzRozppoczecia	TIME(0) not null,
	godzZakonczenia		TIME(0) not null,
	dzienTygodnia_id	INTEGER not null,
	grupa_id			INTEGER not null,
	sala_id				INTEGER not null
);
--
IF OBJECT_ID('dbo.dzienTygodnia') IS NOT NULL
  DROP TABLE dbo.dzienTygodnia;
GO
CREATE TABLE dbo.dzienTygodnia
(
	id				INT IDENTITY(1,1) CONSTRAINT dzienTygodnia_PK PRIMARY KEY ,
	nazwa			NVARCHAR(15) not null CHECK(LEN(nazwa)>2)
);
--
IF OBJECT_ID('dbo.sala') IS NOT NULL
  DROP TABLE dbo.sala;
GO
CREATE TABLE dbo.sala
(
	id				INT IDENTITY(1,1) CONSTRAINT sala_PK PRIMARY KEY ,
	nazwa			VARCHAR(70),
	adres_id		INTEGER not null
);
--
IF OBJECT_ID('dbo.administrator') IS NOT NULL
  DROP TABLE dbo.administrator;
GO
CREATE TABLE dbo.administrator
(
	id				INT IDENTITY(1,1) CONSTRAINT admin_PK PRIMARY KEY ,
	imie			NVARCHAR(16) not null CHECK(LEN(imie)>2),
	nazwisko		NVARCHAR(32) not null CHECK(LEN(nazwisko)>2),
	login_			NVARCHAR(32) CHECK(LEN(login_)>2) not null,
	haslo_			NVARCHAR(32) CHECK(LEN(haslo_)>8) not null
);

---------------------------------------------------
-------------------tworzenie FK--------------------
---------------------------------------------------
ALTER TABLE zawodnik
	ADD CONSTRAINT zawodnik_pozycja_FK FOREIGN KEY (pozycja_id) REFERENCES pozycja(id);
go
ALTER TABLE zawodnik
	ADD CONSTRAINT zawodnik_adres_FK FOREIGN KEY (adres_id) REFERENCES adres(id);
go
ALTER TABLE zawodnik
	ADD CONSTRAINT zawodnik_klub_FK FOREIGN KEY (klub_id) REFERENCES klub(id);
go
ALTER TABLE zawodnik
	ADD CONSTRAINT zawodnik_grupa_FK FOREIGN KEY (grupa_id) REFERENCES grupa(id);
go
--
ALTER TABLE adres
	ADD CONSTRAINT adres_miasto_FK FOREIGN KEY (miasto_id) REFERENCES miasto(id);
go
--
ALTER TABLE grupa
	ADD CONSTRAINT grupa_kategoria_FK FOREIGN KEY (kategoria_id) REFERENCES kategoria(id);
go
ALTER TABLE grupa
	ADD CONSTRAINT grupa_trener_FK FOREIGN KEY (trener_id) REFERENCES trener(id);
go
--
ALTER TABLE trener
	ADD CONSTRAINT trener_adres_FK FOREIGN KEY (adres_id) REFERENCES adres(id);
go
ALTER TABLE trener
	ADD CONSTRAINT trener_klub_FK FOREIGN KEY (klub_id) REFERENCES klub(id);
go
--
ALTER TABLE zajecia
	ADD CONSTRAINT zajecia_dzienTygodnia_FK FOREIGN KEY (dzienTygodnia_id) REFERENCES dzienTygodnia(id);
go
ALTER TABLE zajecia
	ADD CONSTRAINT zajecia_grupa_FK FOREIGN KEY (grupa_id) REFERENCES grupa(id);
go
ALTER TABLE zajecia
	ADD CONSTRAINT zajecia_sala_FK FOREIGN KEY (sala_id) REFERENCES sala(id);
go
--
ALTER TABLE sala
	ADD CONSTRAINT sala_adres_FK FOREIGN KEY (adres_id) REFERENCES adres(id);
go


