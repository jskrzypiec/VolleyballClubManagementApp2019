use jskrzypiec;

/*
select * from dzienTygodnia
select * from pozycja
select * from kategoria
select * from miasto
select * from adres
select * from zawodnik
select * from klub
select * from trener
select * from sala
select * from grupa
select * from zajecia
select * from administrator
*/

INSERT INTO dbo.dzienTygodnia VALUES 
('poniedzia³ek'), ('wtorek'), ('œroda'), ('czwartek'), ('pi¹tek'), ('sobota'), ('niedziela');
go
select * from dzienTygodnia;
go

INSERT INTO dbo.pozycja VALUES 
('przyjmuj¹cy'), ('œrodkowy'), ('atakuj¹cy'), ('rozgrywaj¹cy'), ('libero');
go
select * from pozycja;
go

INSERT INTO dbo.kategoria VALUES 
('m³odzik'), ('kadet'), ('junior'), ('senior');
go
select * from kategoria;
go

INSERT INTO dbo.miasto VALUES 
('Warszawa');
go
select * from miasto;
go
--
INSERT INTO dbo.adres VALUES 
('Nowolipie', '8', null, '00-150', 1), ('Polna', '7a', null, '00-625',1), ('Ho¿a', '11', null, '00-528',1),
('Czerniakowska', '201', '43', '00-436',1),  ('£abiszyñska', '23', '22', '05-077',1), ('Kochanowskiego', '13', '22', '01-716',1), 
('Okr¹g', '3d', '114', '05-077',1), ('Szmulowizna', '23', '1', '05-077',1), ('R.Traugutta', '44', '17', '00-067',1), 
('Foksal', '8', '3', '00-366',1), ('Al. Jerozolimskie', '15', '13', '05-077',1), ('Radna', '17d', '2', '00-341',1), 
('Topiel', '2', '6', '00-350',1), ('Krakowskie Przedmieœcie', '17', '1', '00-071',1), ('Bednarska', '30a', '16', '00-321',1),
('Midowoa', '3a', '1', '05-077',1), ('Konwiktorska', '9', '38', '00-216',1), ('Jana Paw³a II', '82', '3', '00-175',1),
('Ludwika Rydygiera', '13', '7', '01-793',1), ('Krasiñskiego', '38d', '120', '05-077',1), ('Lindego', '10', '33', '01-995',1),
('Broniewskiego', '24', '2', '05-077',1), ('Kie³piñska', '6a', '23', '01-707',1), ('Ziemniaczanki', '5', '69', '01-205',1),
('Kluskacza', '12a', '3', '00-077',1), ('Staffa', '20', '14', '05-077',1), ('Krzypka', '4b', '17', '05-077',1),
('Na³owskiej', '17a', '6', '01-886',1), ('Smolna', '144', '203', '01-123',1), ('Lipowa', '2', '28', '01-090',1),
('Kasprowicza', '42a', '4', '05-075',1), ('Marymoncka', '34', '1', '01-813',1);
go
select * from adres;
go
--
INSERT INTO dbo.klub VALUES 
('MKS MDK Warszawa', '1960', NULL);
go
select * from klub;
go
--
INSERT INTO dbo.trener VALUES 
('Wojciech', 'Góra', '1949', '534718809', 4, 1, 'wgora', 'skoda312'),
('Roman', 'Ho³opiak', '1955', '664668757', 5, 1, 'rholopiak', 'mazury495g'),
('Marek', 'Rybak', '1986', '696067014', 6, 1, 'mrybak', 'fizjo4fizjo23');
go
select * from trener
/*DECLARE @responseMessage NVARCHAR(250)
EXEC dbo.dodajTren 'Wojciech', 'Góra', '1949', '534718809', 4, 1, 'wgora', 'skoda312', @responseMessage OUTPUT;
EXEC dbo.dodajTren 'Roman', 'Ho³opiak', '1955', '664668757', 5, 1, 'rholopiak', 'mazury495g', @responseMessage OUTPUT;
EXEC dbo.dodajTren 'Marek', 'Rybak', '1986', '696067014', 6, 1, 'mrybak', 'fizjo4fizjo23', @responseMessage OUTPUT;
go
select * from trener*/
--
INSERT INTO dbo.sala VALUES 
('Oœrodek Sportu i Rekreakcji Dzielnicy Œródmieœcie', 1),
('V LO im. Ksiêcia Józefa Poniatowskiego', 2),
('Gimnazjum nr 39 im. Agaty Mróz-Olszewskiej', 3);
go
select * from sala;
go
--
INSERT INTO dbo.grupa VALUES 
('Juniorzy MDK', 3, 1),
('Kadeci MDK', 2, 2),
('M³odzicy MDK', 1, 3);
go
select * from grupa;
go
--
INSERT INTO dbo.zajecia VALUES 
('18:00', '20:00', 1, 1, 1), ('18:00', '20:00', 3, 1, 1), ('16:00', '18:00', 5, 1, 1),
('18:00', '20:30', 1, 2, 3), ('16:00', '17:45', 4, 2, 3), ('10:00', '12:15', 6, 2, 3),
('18:00', '20:00', 1, 3, 2), ('17:00', '19:20', 2, 3, 2), ('19:00', '21:00', 4, 3, 2);
go
select * from zajecia;
go
--
INSERT INTO dbo.zawodnik VALUES 
('Andrzej', 'Szczypalski', '2001', '195', '89', '664659717', 1, 7, 1, 1, 'aszczypalski', 'szczyp351q'),
('Micha³', 'Nosek', '2002', '187', '89', '717800332', 1, 8, 1, 1, 'mnosek', 'nos4win91'),
('Artur', 'Szczygie³', '2001', '191', '85', '606913241', 1, 9, 1, 1, 'aszczygiel', 'ppnatka3123'),
('Robert', 'Boczek', '2001', '195', '86', '784561890', 2, 10, 1, 1, 'rboczek', 'mdkq302boq'),
('Arnold', 'Steckiwicz', '2002', '192', '95', '678921125', 2, 11, 1, 1, 'asteckiewicz', 'rower359d'),
('Marcin', 'Wasilewski', '2001', '201', '87', '791348851', 2, 12, 1, 1, 'mawsilewski', 'turbo546tgb'),
('Adam', 'Mickiewicz', '2001', '202', '100', '601563321', 3, 13, 1, 1, 'amickiewicz', 'amicky42'),
('Leon', 'Tabor', '2002', '184', '74', '534142678', 3, 14, 1, 1, 'ltabor', 'decha16'),
('Marian', 'Kie³basa', '2002', '184', '80', '567890754', 4, 15, 1, 1, 'mkielbasa', 'mafatuk13'),
('Adam', 'Nowak', '2001', '179', '80', '704657643', 4, 16, 1, 1, 'anowak', 'ewqewq333'),
('Maciej', 'Kowalski', '2001', '175', '68', '888542544', 5, 17, 1, 1, 'mkowalski', '1234567'),
('Pawe³', 'Grzyb', '2001', '169', '64', '523546708', 5, 18, 1, 1, 'pgrzyb', 'qzwxecrv66'),
--
('Tomasz', 'Paczewski', '2003', '189', '80', '601565938', 1, 19, 1, 2, 'tpaczewski', 'qpalzm555'),
('Grzegorz', 'Kosmaty', '2004', '191', '79', '693345883', 1, 20, 1, 2, 'gkosmaty', 'pwlsxm5g'),
('Filip', 'Bobowski', '2003', '204', '89', '600345213', 2, 21, 1, 2, 'fbobowski', '66g12ee'),
('Miko³aj', 'Witkowski', '2003', '198', '88', '500436032', 2, 22, 1, 2, 'mwitkowski', 'doda12'),
('Edward', 'Rybak', '2004', '190', '75', '600437070', 3, 23, 1, 2, 'erybak', 'pudzian231'),
('Jerzy', 'Papuszko', '2003', '183', '74', '790813463', 4, 24, 1, 2, 'jpapuszko', 'tata444nie'),
('Krzysztof', 'Krawczyk', '2003', '171', '67', '780913412', 5, 25, 1, 2, 'kkrawczyk', 'haslo9g3d2'),
--
('Zbigniew', 'Ma³ysz', '2005', '180', '70', '787562940', 1, 26, 1, 3, 'zmalysz', 'srdpln213'),
('Jan', 'Cichy', '2005', '177', '67', '721504673', 1, 27, 1, 3, 'jcichy', 'jan54241612'),
('Jakub', 'Sosnowski', '2006', '187', '76', '696066018', 2, 28, 1, 3, 'jsosnowski', 'sosna5120cc'),
('Piotr', 'Zêbacz', '2005', '192', '80', '696042129', 2, 29, 1, 3, 'pzebacz', 'pp62zeby2'),
('Bartosz', 'Topp', '2005', '184', '72', '664666864', 3, 30, 1, 3, 'btopp', 'topp44ppot'),
('Kamil', 'Domel', '2006', '181', '67', '781506342', 4, 31, 1, 3, 'kdomel', 'okna921'),
('Rafa³', 'Domañski', '2005', '172', '59', '504346718', 5, 32, 1, 3, 'rdomanski', 'hayabusa1');
go
select * from zawodnik
/*DECLARE @responseMessage NVARCHAR(250)
EXEC dbo.dodajZaw 'Andrzej', 'Szczypalski', '2001', '195', '89', '664659717', 1, 7, 1, 1, 'aszczypalski', 'szczyp351q', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Micha³', 'Nosek', '2002', '187', '89', '717800332', 1, 8, 1, 1, 'mnosek', 'nos4win91', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Artur', 'Szczygie³', '2001', '191', '85', '606913241', 1, 9, 1, 1, 'aszczygiel', 'ppnatka3123', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Robert', 'Boczek', '2001', '195', '86', '784561890', 2, 10, 1, 1, 'rboczek', 'mdkq302boq', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Arnold', 'Steckiwicz', '2002', '192', '95', '678921125', 2, 11, 1, 1, 'asteckiewicz', 'rower359d', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Marcin', 'Wasilewski', '2001', '201', '87', '791348851', 2, 12, 1, 1, 'mawsilewski', 'turbo546tgb', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Adam', 'Mickiewicz', '2001', '202', '100', '601563321', 3, 13, 1, 1, 'amickiewicz', 'amicky42', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Leon', 'Tabor', '2002', '184', '74', '534142678', 3, 14, 1, 1, 'ltabor', 'decha16', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Marian', 'Kie³basa', '2002', '184', '80', '567890754', 4, 15, 1, 1, 'mkielbasa', 'mafatuk13', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Adam', 'Nowak', '2001', '179', '80', '704657643', 4, 16, 1, 1, 'anowak', 'ewqewq333', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Maciej', 'Kowalski', '2001', '175', '68', '888542544', 5, 17, 1, 1, 'mkowalski', '1234567', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Pawe³', 'Grzyb', '2001', '169', '64', '523546708', 5, 18, 1, 1, 'pgrzyb', 'qzwxecrv66', @responseMessage OUTPUT;
--
EXEC dbo.dodajZaw 'Tomasz', 'Paczewski', '2003', '189', '80', '601565938', 1, 19, 1, 2, 'tpaczewski', 'qpalzm555', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Grzegorz', 'Kosmaty', '2004', '191', '79', '693345883', 1, 20, 1, 2, 'gkosmaty', 'pwlsxm5g', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Filip', 'Bobowski', '2003', '204', '89', '600345213', 2, 21, 1, 2, 'fbobowski', '66g12ee', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Miko³aj', 'Witkowski', '2003', '198', '88', '500436032', 2, 22, 1, 2, 'mwitkowski', 'doda12', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Edward', 'Rybak', '2004', '190', '75', '600437070', 3, 23, 1, 2, 'erybak', 'pudzian231', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Jerzy', 'Papuszko', '2003', '183', '74', '790813463', 4, 24, 1, 2, 'jpapuszko', 'tata444nie', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Krzysztof', 'Krawczyk', '2003', '171', '67', '780913412', 5, 25, 1, 2, 'kkrawczyk', 'haslo9g3d2', @responseMessage OUTPUT;
--
EXEC dbo.dodajZaw 'Zbigniew', 'Ma³ysz', '2005', '180', '70', '787562940', 1, 26, 1, 3, 'zmalysz', 'srdpln213', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Jan', 'Cichy', '2005', '177', '67', '721504673', 1, 27, 1, 3, 'jcichy', 'jan54241612', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Jakub', 'Sosnowski', '2006', '187', '76', '696066018', 2, 28, 1, 3, 'jsosnowski', 'sosna5120cc', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Piotr', 'Zêbacz', '2005', '192', '80', '696042129', 2, 29, 1, 3, 'pzebacz', 'pp62zeby2', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Bartosz', 'Topp', '2005', '184', '72', '664666864', 3, 30, 1, 3, 'btopp', 'topp44ppot', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Kamil', 'Domel', '2006', '181', '67', '781506342', 4, 31, 1, 3, 'kdomel', 'okna921', @responseMessage OUTPUT;
EXEC dbo.dodajZaw 'Rafa³', 'Domañski', '2005', '172', '59', '504346718', 5, 32, 1, 3, 'rdomanski', 'hayabusa1', @responseMessage OUTPUT;
go
select * from zawodnik;
go*/
--
/*DECLARE @responseMessage NVARCHAR(250)
EXEC dbo.dodajAdmin 'Jakub', 'Skrzypiec', 'jskrzypiec', '123123123', @responseMessage OUTPUT;
go*/
INSERT INTO dbo.administrator VALUES 
('Jakub', 'Skrzypiec', 'jskrzypiec', '123123123')

select * from administrator;
go
--




