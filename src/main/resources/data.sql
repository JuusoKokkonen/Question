INSERT INTO public.questions ("order_number", "question", "type") VALUES 
(1,'Ikä',5),
(2,'Sukupuoli',0),
(3,'Asuinkunta',2),
(4,'Asutko',2),
(5,'Asumiskulut ja muut kiinteät kulut (€/kk)',5),
(6,'Saatko opintotukea ja muita Kelan tukia?',1),
(7,'Nostatko opintolainaa?',0),
(8,'Käytkö töissä koulun ohella?',0),
(9,'Pakollisten kulujen jälkeen jäljellä oleva raha (€/kk)',5),
(10,'Kuinka tyytyväinen olet nykyiseen asumistilanteeseesi asteikolla 1-5?',0),
(11,'Koetko, että rahasi riittävät normaaliin elämiseen?',0),
(12,'Vapaa kommentti (max. 500 merkkiä)',3);

INSERT INTO public.choices ("order_number", "choice", "question_id") VALUES
(1,'Nainen',2),
(2,'Mies',2),
(3,'Muu/En halua vastata',2),
(1,'Helsinki',3),
(2,'Espoo',3),
(3,'Kauniainen',3),
(4,'Vantaa',3),
(1,'Yksin',4),
(2,'Kimppakämpässä',4),
(3,'Soluasunnossa',4),
(4,'Puolison kanssa',4),
(5,'Vanhempien tai muun perheen luona',4),
(1,'Opintotukea',6),
(2,'Asumistukea',6),
(3,'Toimeentulotukea',6),
(4,'Muuta tukea',6),
(5,'En saa mitään tukia',6),
(1,'Kyllä',7),
(2,'En',7),
(1,'Kyllä',8),
(2,'En',8),
(1,'5 Olen tyytyväinen, enkä muuttaisi mitään',10),
(2,'4 Melko tyytyväinen, mutta on hieman parannettavaa',10),
(3,'3 Ei hyvä, mutta ei kamalan huonokaan',10),
(4,'2',10),
(5,'1',10),
(1,'Kyllä',11),
(2,'En',11),
(3,'Vaihtelevasti',11);




