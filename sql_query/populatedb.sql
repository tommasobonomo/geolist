INSERT INTO GEODB.USERS(ID,COOKIE, USERNAME,"NAME",LASTNAME,EMAIL,IMAGE, PASSWORD, TOKEN, ACTIVE, "ADMIN")
VALUES (DEFAULT,null,'rappasta', 'Lorenzo', 'Framba', 'lorenzoframba@gmail.com','IMAGEN','4613650b16296964685818117f557e57366f92b', null, TRUE, TRUE),
       (DEFAULT,null,'gesuSesso','Mattia', 'Molon','molon@gmail.com','IMAGEN','29707645067686736492cf6e16526b6e4245',null, TRUE,FALSE),
       (DEFAULT,null,'minestrone','Tommaso','Bonomo','tommasoni394@gmail.com','IMAGEN','4f4d773a0540130176787a4f1574100759521e',null, TRUE,FALSE),
       (DEFAULT,null,'XDSNIPERXD','Leonardo','Remondini', 'remoromolo@gmail.com','IMAGEN', '3949350913f2c7e1b31e366c602f4e5a313a75',null, TRUE,FALSE),
       (DEFAULT,null,'piediPiatti','Giorgio','Segalla','giorgionelo@gmail.com','IMAGEN', '3949350913f2c7e1b31e366c602f4e5a313a75',null, TRUE,FALSE),
       (DEFAULT,null,'Maialino69','Marta','Tonnona', 'martinasesso@gmail.com','IMAGEN', '6b887a3f20353200263603f6773cf09336a',null, TRUE,FALSE),
       (DEFAULT,null,'Fratak','Elia','Salame','piselloni@gmail.com','IMAGEN', '272b3357425e263811344c636c401b25407a',null, TRUE,FALSE),
       (DEFAULT,null,'OrcoBue','Luca', 'Rossi', 'marione@gmail.com','IMAGEN', '42a497b6c62447415483e173a21175a64666e65',null, TRUE,FALSE),
       (DEFAULT,null,'Iphonneeee','Nicola','Nicoli', 'naso@gmail.com','IMAGEN', '5b41455d063c7b7e1c7e697a25261c5f7c532780',null, TRUE,FALSE),
       (DEFAULT,null,'Bottiglia','Marco','Bestioni', 'tomcat@gmail.com','IMAGEN', '0e23557c11275d16585244671730147418524644',null, TRUE,FALSE);

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

INSERT INTO GEODB.USERSANONIMOUS(ID, COOKIE)
VALUES (DEFAULT, 'cookiediprova');

INSERT INTO GEODB.LIST(ID,USEROWNER, USERANONOWNER, IDCAT, "NAME",DESCRIPTION, IMAGE)
VALUES  (DEFAULT, 1, null, 1,  'BIRTHDAY', 'SO MUCH FOOD','image'),
        (DEFAULT, 1, null, 1,  'LISTA DI PROVA DI RAPPASTA', 'DESCRIZIONE DI POSTA RAPPASTA','image'),
        (DEFAULT, 2, null, 2, 'GRANDMA', 'GOING TO DIE','image'),
        (DEFAULT, 3, null, 3, 'CHEESE', 'CHEESY','image'),
        (DEFAULT, 4, null, 4, 'PICNIK', 'SANDWICHES AND STUFF','image'),
        (DEFAULT, 5, null, 4, 'COLLEGE', 'CHEAP AND FAT','image'),
        (DEFAULT, 6, null, 2, 'WEEKEND', 'OMG LMFAO','image'),
        (DEFAULT, 7, null, 1, 'FATTY', 'REALLY NOT GOOD','image'),
        (DEFAULT, 8, null, 3, 'FRIEND', 'BROOOOOOOOOOO','image'),
        (DEFAULT, null, 1, 3, 'LISTA DI PROVA ANONIMA', 'DESCRIZIONE','image');
    

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
/*
INSERT INTO GEODB.ITEM(IDCAT,ID,"NAME",LOGO, NOTE)
VALUES (1,DEFAULT,'wurstel','c:\docs\DB_photos\wurstel.png' ,'pasta'),
       (2,DEFAULT,'chicken','c:\docs\DB_photos\chicken.png','sdflkje'),
       (3,DEFAULT,'horse','c:\docs\DB_photos\horse.png','sdflkj'),
       (1,DEFAULT,'cow','c:\docs\DB_photos\cow.png','fdsjlh'),
       (1,DEFAULT,'beef','c:\docs\DB_photos\beef.png','lkdsfgj'),
       (1,DEFAULT,'pork','c:\docs\DB_photos\pork.png','orkewjl'),
       (1,DEFAULT,'ribs','c:\docs\DB_photos\ribs.png','ewlrjk'),
       (1,DEFAULT,'sausage','c:\docs\DB_photos\sausage.png','reuirre'),
       (2,DEFAULT,'caciotta','c:\docs\DB_photos\caciotta.png','qwerty'),
       (2,DEFAULT,'yogurt','c:\docs\DB_photos\yogurt.png', 'sdfghj'),
       (2,DEFAULT,'cheese','c:\docs\DB_photos\cheese.png', 'sdfghj'),  
       (3,DEFAULT,'carrot','c:\docs\DB_photos\carrot.png', 'sdfghj'),
       (3,DEFAULT,'pickle','c:\docs\DB_photos\pickle.png', 'sdfghj'),
       (3,DEFAULT,'salad','c:\docs\DB_photos\salad.png', 'sdfghj'),
       (3,DEFAULT,'spinach','c:\docs\DB_photos\spinach.png', 'sdfghj'),
       (3,DEFAULT,'potato','c:\docs\DB_photos\potato.png', 'sdfghj'),
       (3,DEFAULT,'tomato','c:\docs\DB_photos\tomato.png', 'sdfghjekrf'),
       (4,DEFAULT,'apple','c:\docs\DB_photos\apple.png', 'texyckv'),
       (4,DEFAULT,'lemon','c:\docs\DB_photos\lemon.png', 'texyckv'),
       (4,DEFAULT,'orange','c:\docs\DB_photos\orange.png', 'texyckv'),
       (4,DEFAULT,'mandarin','c:\docs\DB_photos\mandarin.png', 'texyckv'),
       (4,DEFAULT,'pear','c:\docs\DB_photos\pear.png', 'texyckv'),
       (4,DEFAULT,'strawberry','c:\docs\DB_photos\strawberry.png', 'texyckv'),
       (4,DEFAULT,'raspberry','c:\docs\DB_photos\raspberry.png', 'texyckv'),
       (4,DEFAULT,'banana','c:\docs\DB_photos\banana.png', 'lieuwhf'),
       (5,DEFAULT,'pizza','c:\docs\DB_photos\pizza.png', 'fieguwkh'),              
       (5,DEFAULT,'breadsticks','c:\docs\DB_photos\breadsticks.png', 'feluakh'),
       (5,DEFAULT,'bread','c:\docs\DB_photos\bread.png', 'feluakh'),
       (5,DEFAULT,'ciabatt','c:\docs\DB_photos\ciabatt.png', 'feluakh'),
       (5,DEFAULT,'cracker','c:\docs\DB_photos\cracker.png', 'feluakh'),
       (5,DEFAULT,'flour','c:\docs\DB_photos\flour.png', 'feluakh'),
       (5,DEFAULT,'piada','c:\docs\DB_photos\piada.png', 'feluakh'),
       (5,DEFAULT,'piadina','c:\docs\DB_photos\piadina.png', 'feluakh'),
       (5,DEFAULT,'burrito','c:\docs\DB_photos\burrito.png', 'feluakh'),
       (5,DEFAULT,'paella','c:\docs\DB_photos\paella.png', 'feluakh'); 


 
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

 */
INSERT INTO GEODB.ACCESS(IDUSER, IDLIST)
VALUES(1,1),
      (1,2),
      (2,1),
      (3,1),
      (4,2);

INSERT INTO GEODB.MESSAGE(ID,IDUSER,IDLIST,TEXT,SENDTIME)
VALUES (DEFAULT,1,1,'state studiando web?',CURRENT_TIMESTAMP),
       (DEFAULT,2,1,'no',CURRENT_TIMESTAMP);
