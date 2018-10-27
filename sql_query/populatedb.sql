INSERT INTO GEODB.USERS(ID,COOKIE, USERNAME,"NAME",LASTNAME,EMAIL,IMAGE, PASSWORD, "ADMIN")
VALUES (DEFAULT,' ','rappasta', 'Lorenzo', 'Framba', 'Lorenzoframba@gmail.com','image','4613-650b16-29696468-5818-11-7f-55-7e-5736-6f-9-2b',TRUE),
       (DEFAULT,' ','gesuSesso','Mattia', 'Molon','Molon@gmail.com','IMAGE','29-7076-4506768-67-36-49-2-c-f6e16526b-6e-4245',FALSE),
       (DEFAULT,' ','minestrone','Tommaso','Bonomo','Tommasoni394@gmail.com','IMAGEN','-4f-4d773a05-40-130176787a4f1574-10075-9521e',FALSE),
       (DEFAULT,' ','XDSNIPERXD','Leonardo','Remondini', 'Remoromolo@gmail.com','IMAGEN', '39493509-13f-2c7e-1b-3-1e-366c-60-2f-4e5a-313a75',FALSE),
       (DEFAULT,' ','piediPiatti','Giorgio','Segalla','Giorgionelo@gmail.com','IMAGEN', '39493509-13f-2c7e-1b-3-1e-366c-60-2f-4e5a-313a75',FALSE),
       (DEFAULT,' ','Maialino69','Marta','Tonnona', 'Martinasesso@gmail.com','IMAGEN', '6b-8-8-7a3f2035320026-360-3f-6773c-f09-33-6a',FALSE),
       (DEFAULT,' ','Fratak','Elia','Salame','piselloni@gmail.com','IMAGEN', '27-2b-33-57425e-2-6-38-11-3-44-c636c-401b25-40-7a',FALSE),
       (DEFAULT,' ','OrcoBue','Luca', 'Rossi', 'marione@gmail.com','IMAGEN', '42-a-497b-6c62-4474-15-483e173a21-17-5a64-666e-65',FALSE),
       (DEFAULT,' ','Iphonneeee','Nicola','Nicoli', 'naso@gmail.com','IMAGEN', '-5b-41455d06-3c7b-7e-1c-7e-69-7a2526-1c-5f-7c-53-27-80',FALSE),
       (DEFAULT,' ','Bottiglia','Marco','Bestioni', 'tomcat@gmail.com','IMAGEN', '0e2355-7c11-275d-16-5852-44-67-1730147418-524644',FALSE);

INSERT INTO GEODB.CLIST(ID,"NAME", DESCRIPTION, IMAGE )
VALUES  (DEFAULT, 'PRETTY HEALTHY', 'EATING A LOT FOR LITTLE MONEY','image'),
        (DEFAULT, 'PRETTY UNHEALTHY', 'EATING BADLY FOR LITTLE MONEY','image'),
        (DEFAULT, 'PRETTY AVERAGE', 'SHYLY EATING ','image'),
        (DEFAULT, 'PRETTY CHEAP', 'FOR LITTLE MONEY','image'),
        (DEFAULT, 'KINDA GOOD', 'NOT EATING ENOUGH','image'),
        (DEFAULT, 'PRETTY BAD', 'I CANNOT EAT MORE','image'),
        (DEFAULT, 'SORTA BAD', 'I HAVE TO EAT MORE','image'),
        (DEFAULT, 'KINDA BAD', 'NOT BAD ENOUGH','image'),
        (DEFAULT, 'PRETTY EXPENSIVE', 'WASTING SHIT ON FOOD','image');


INSERT INTO GEODB.CITEM(ID,"NAME",DESCRIPTION, IMAGE)
VALUES  (DEFAULT, 'meat', 'GOOD', 'CIBO'),
        (DEFAULT, 'fridge', 'GOOD', 'CIBO'),
        (DEFAULT, 'vegetable', 'GOOD', 'CIBO'),
        (DEFAULT, 'fruit', 'GOOD', 'CIBO'),
        (DEFAULT, 'bread', 'GOOD', 'CIBO');

INSERT INTO GEODB.LIST(ID,USERCREATOR, IDCAT, "NAME",DESCRIPTION, IMAGE)
VALUES  (DEFAULT, 1, 1, 'BIRTHDAY', 'SO MUCH FOOD','image'),
        (DEFAULT, 2, 2, 'GRANDMA', 'GOING TO DIE','image'),
        (DEFAULT, 3, 3, 'CHEESE', 'CHEESY','image'),
        (DEFAULT, 4, 4, 'PICNIK', 'SANDWICHES AND STUFF','image'),
        (DEFAULT, 5, 4, 'COLLEGE', 'CHEAP AND FAT','image'),
        (DEFAULT, 6, 2, 'WEEKEND', 'OMG LMFAO','image'),
        (DEFAULT, 7, 1, 'FATTY', 'REALLY NOT GOOD','image'),
        (DEFAULT, 8, 3, 'FRIEND', 'BROOOOOOOOOOO','image');
    


INSERT INTO GEODB.ISFRIEND(USR1,USR2)
VALUES  (1,2),
        (2,3),
        (1,3),
        (3,4),
        (4,5),
        (4,6),
        (6,7),
        (8,1),
        (2,6),
        (4,2);

INSERT INTO GEODB.ITEM(IDCAT,ID,CALORIE, "NAME",LOGO, NOTE)
VALUES (1,DEFAULT, 304, 'wurstel','c:\docs\DB_photos\wurstel.png' ,'pasta'),
       (2,DEFAULT, 389,'chicken','c:\docs\DB_photos\chicken.png','sdflkje'),
       (3,DEFAULT, 239,'horse','c:\docs\DB_photos\horse.png','sdflkj'),
       (1,DEFAULT, 769,'cow','c:\docs\DB_photos\cow.png','fdsjlh'),
       (1,DEFAULT, 868,'beef','c:\docs\DB_photos\beef.png','lkdsfgj'),
       (1,DEFAULT, 343,'pork','c:\docs\DB_photos\pork.png','orkewjl'),
       (1,DEFAULT, 234,'ribs','c:\docs\DB_photos\ribs.png','ewlrjk'),
       (1,DEFAULT, 876,'sausage','c:\docs\DB_photos\sausage.png','reuirre'),
       (2,DEFAULT,932,'caciotta','c:\docs\DB_photos\caciotta.png','qwerty'),
       (2,DEFAULT,830, 'yogurt','c:\docs\DB_photos\yogurt.png', 'sdfghj'),
       (2,DEFAULT,830, 'cheese','c:\docs\DB_photos\cheese.png', 'sdfghj'),  
       (3,DEFAULT,102,'carrot','c:\docs\DB_photos\carrot.png', 'sdfghj'),
       (3,DEFAULT,642,'pickle','c:\docs\DB_photos\pickle.png', 'sdfghj'),
       (3,DEFAULT,242,'salad','c:\docs\DB_photos\salad.png', 'sdfghj'),
       (3,DEFAULT,152,'spinach','c:\docs\DB_photos\spinach.png', 'sdfghj'),
       (3,DEFAULT,132,'potato','c:\docs\DB_photos\potato.png', 'sdfghj'),
       (3,DEFAULT,294, 'tomato','c:\docs\DB_photos\tomato.png', 'sdfghjekrf'),
       (4,DEFAULT,489,'apple','c:\docs\DB_photos\apple.png', 'texyckv'),
       (4,DEFAULT,49,'lemon','c:\docs\DB_photos\lemon.png', 'texyckv'),
       (4,DEFAULT,39,'orange','c:\docs\DB_photos\orange.png', 'texyckv'),
       (4,DEFAULT,59,'mandarin','c:\docs\DB_photos\mandarin.png', 'texyckv'),
       (4,DEFAULT,83,'pear','c:\docs\DB_photos\pear.png', 'texyckv'),
       (4,DEFAULT,39,'strawberry','c:\docs\DB_photos\strawberry.png', 'texyckv'),
       (4,DEFAULT,76,'raspberry','c:\docs\DB_photos\raspberry.png', 'texyckv'),
       (4,DEFAULT,49, 'banana','c:\docs\DB_photos\banana.png', 'lieuwhf'),
       (5,DEFAULT,48, 'pizza','c:\docs\DB_photos\pizza.png', 'fieguwkh'),              
       (5,DEFAULT,137, 'breadsticks','c:\docs\DB_photos\breadsticks.png', 'feluakh'),
       (5,DEFAULT,35, 'bread','c:\docs\DB_photos\bread.png', 'feluakh'),
       (5,DEFAULT,865, 'ciabatt','c:\docs\DB_photos\ciabatt.png', 'feluakh'),
       (5,DEFAULT,684, 'cracker','c:\docs\DB_photos\cracker.png', 'feluakh'),
       (5,DEFAULT,76, 'flour','c:\docs\DB_photos\flour.png', 'feluakh'),
       (5,DEFAULT,34, 'piada','c:\docs\DB_photos\piada.png', 'feluakh'),
       (5,DEFAULT,98, 'piadina','c:\docs\DB_photos\piadina.png', 'feluakh'),
       (5,DEFAULT,96, 'burrito','c:\docs\DB_photos\burrito.png', 'feluakh'),
       (5,DEFAULT,34, 'paella','c:\docs\DB_photos\paella.png', 'feluakh'); 

 
 
INSERT INTO GEODB.COMPOSE(LIST,ITEM)
VALUES (1,1),
       (1,2),
       (1,3),
       (1,4),
       (1,5),
       (1,6),
       (1,7),
       (2,2),
       (2,5),
       (3,10),
       (3,11),
       (3,2),
       (3,1),
       (4,2),
       (4,1),
       (4,3),
       (4,4),
       (5,6),
       (5,1),
       (5,2),
       (5,5),
       (6,3),
       (6,6),
       (7,6),
       (7,1),
       (8,2),
       (8,4),
       (8,5);

