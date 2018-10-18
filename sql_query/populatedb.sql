INSERT INTO GEODB.USERS(ID,COOKIE, USERNAME,"NAME",LASTNAME,EMAIL,IMAGE, PASSWORD, "ADMIN")
VALUES (1423879,' ','rappasta', 'Lorenzo', 'Framba', 'Lorenzoframba@gmail.com','image','4613-650b16-29696468-5818-11-7f-55-7e-5736-6f-9-2b',TRUE),
       (6743328,' ','gesuSesso','Mattia', 'Molon','Molon@gmail.com','IMAGE','29-7076-4506768-67-36-49-2-c-f6e16526b-6e-4245',FALSE),
       (5673876,' ','minestrone','Tommaso','Bonomo','Tommasoni394@gmail.com','IMAGEN','-4f-4d773a05-40-130176787a4f1574-10075-9521e',FALSE),
       (5678934,' ','XDSNIPERXD','Leonardo','Remondini', 'Remoromolo@gmail.com','IMAGEN', '39493509-13f-2c7e-1b-3-1e-366c-60-2f-4e5a-313a75',FALSE),
       (2345678,' ','piediPiatti','Giorgio','Segalla','Giorgionelo@gmail.com','IMAGEN', '39493509-13f-2c7e-1b-3-1e-366c-60-2f-4e5a-313a75',FALSE),
       (0798313,' ','Maialino69','Marta','Tonnona', 'Martinasesso@gmail.com','IMAGEN', '6b-8-8-7a3f2035320026-360-3f-6773c-f09-33-6a',FALSE),
       (9743898,' ','Fratak','Elia','Salame','piselloni@gmail.com','IMAGEN', '27-2b-33-57425e-2-6-38-11-3-44-c636c-401b25-40-7a',FALSE),
       (9872438,' ','OrcoBue','Luca', 'Rossi', 'marione@gmail.com','IMAGEN', '42-a-497b-6c62-4474-15-483e173a21-17-5a64-666e-65',FALSE),
       (8907234,' ','Iphonneeee','Nicola','Nicoli', 'naso@gmail.com','IMAGEN', '-5b-41455d06-3c7b-7e-1c-7e-69-7a2526-1c-5f-7c-53-27-80',FALSE),
       (9780815,' ','Bottiglia','Marco','Bestioni', 'tomcat@gmail.com','IMAGEN', '0e2355-7c11-275d-16-5852-44-67-1730147418-524644',FALSE);

INSERT INTO GEODB.CLIST(IDCATEGORY,"NAME", DESCRIPTION, IMAGE )
VALUES  (45673, 'PRETTY HEALTHY', 'EATING A LOT FOR LITTLE MONEY','image'),
        (98034, 'PRETTY UNHEALTHY', 'EATING BADLY FOR LITTLE MONEY','image'),
        (54054, 'PRETTY AVERAGE', 'SHYLY EATING ','image'),
        (45573, 'PRETTY CHEAP', 'FOR LITTLE MONEY','image'),
        (78443, 'KINDA GOOD', 'NOT EATING ENOUGH','image'),
        (59348, 'PRETTY BAD', 'I CANNOT EAT MORE','image'),
        (43985, 'SORTA BAD', 'I HAVE TO EAT MORE','image'),
        (45676, 'KINDA BAD', 'NOT BAD ENOUGH','image'),
        (54646, 'PRETTY EXPENSIVE', 'WASTING SHIT ON FOOD','image');


INSERT INTO GEODB.CITEM(IDCATEGORY,"NAME",DESCRIPTION, IMAGE)
VALUES  (3627, 'meat', 'GOOD', 'CIBO'),
        (9382, 'fridge', 'GOOD', 'CIBO'),
        (4873, 'vegetable', 'GOOD', 'CIBO'),
        (3903, 'fruit', 'GOOD', 'CIBO'),
        (6348, 'bread', 'GOOD', 'CIBO');

INSERT INTO GEODB.LIST(IDLIST,USERCREATOR, IDCAT, "NAME",DESCRIPTION, IMAGE)
VALUES  (3627, 1423879, 45673, 'BIRTHDAY', 'SO MUCH FOOD','image'),
        (7893, 6743328, 78443, 'GRANDMA', 'GOING TO DIE','image'),
        (6545, 2345678, 98034, 'CHEESE', 'CHEESY','image'),
        (3456, 1423879, 43985, 'PICNIK', 'SANDWICHES AND STUFF','image'),
        (7453, 0798313, 54054, 'COLLEGE', 'CHEAP AND FAT','image'),
        (8675, 1423879, 59348, 'WEEKEND', 'OMG LMFAO','image'),
        (8465, 9780815, 54646, 'FATTY', 'REALLY NOT GOOD','image'),
        (7635, 9743898, 45676, 'FRIEND', 'BROOOOOOOOOOO','image');
    


INSERT INTO GEODB.ISFRIEND(CC1,CC2)
VALUES  (1423879,6743328),
        (1423879,2345678),
        (9872438,9743898),
        (9743898,9780815),
        (9743898,1423879),
        (6743328,9780815),
        (9743898,6743328),
        (1423879,9780815),
        (9743898,8907234),
        (2345678,9780815);

INSERT INTO GEODB.ITEM(IDDCAT,IDITEM,CALORIE, "NAME",LOGO, NOTE)
VALUES (3627,374895, 304, 'wurstel','c:\docs\DB_photos\wurstel.png' ,'pasta'),
       (3627,475326, 389,'chicken','c:\docs\DB_photos\chicken.png','sdflkje'),
       (3627,329755, 239,'horse','c:\docs\DB_photos\horse.png','sdflkj'),
       (3627,324555, 769,'cow','c:\docs\DB_photos\cow.png','fdsjlh'),
       (3627,439855, 868,'beef','c:\docs\DB_photos\beef.png','lkdsfgj'),
       (3627,765855, 343,'pork','c:\docs\DB_photos\pork.png','orkewjl'),
       (3627,376855, 234,'ribs','c:\docs\DB_photos\ribs.png','ewlrjk'),
       (3627,339655, 876,'sausage','c:\docs\DB_photos\sausage.png','reuirre'),
       (9382,463895,932,'caciotta','c:\docs\DB_photos\caciotta.png','qwerty'),
       (9382,983857,830, 'yogurt','c:\docs\DB_photos\yogurt.png', 'sdfghj'),
       (9382,983257,830, 'cheese','c:\docs\DB_photos\cheese.png', 'sdfghj'),  
      (4873,959764,102,'carrot','c:\docs\DB_photos\carrot.png', 'sdfghj'),
       (4873,359764,642,'pickle','c:\docs\DB_photos\pickle.png', 'sdfghj'),
      (4873,559764,242,'salad','c:\docs\DB_photos\salad.png', 'sdfghj'),
       (4873,789764,152,'spinach','c:\docs\DB_photos\spinach.png', 'sdfghj'),
       (4873,953264,132,'potato','c:\docs\DB_photos\potato.png', 'sdfghj'),
       (4873,849056,294, 'tomato','c:\docs\DB_photos\tomato.png', 'sdfghjekrf'),
       (3903,730496,489,'apple','c:\docs\DB_photos\apple.png', 'texyckv'),
       (3903,720436,49,'lemon','c:\docs\DB_photos\lemon.png', 'texyckv'),
       (3903,320496,39,'orange','c:\docs\DB_photos\orange.png', 'texyckv'),
       (3903,430496,59,'mandarin','c:\docs\DB_photos\mandarin.png', 'texyckv'),
       (3903,736496,83,'pear','c:\docs\DB_photos\pear.png', 'texyckv'),
       (3903,738826,39,'strawberry','c:\docs\DB_photos\strawberry.png', 'texyckv'),
      (3903,730465,76,'raspberry','c:\docs\DB_photos\raspberry.png', 'texyckv'),
       (3903,739446,49, 'banana','c:\docs\DB_photos\banana.png', 'lieuwhf'),
       (6348,948586,48, 'pizza','c:\docs\DB_photos\pizza.png', 'fieguwkh'),              
       (6348,938586,137, 'breadsticks','c:\docs\DB_photos\breadsticks.png', 'feluakh'),
       (6348,933586,35, 'bread','c:\docs\DB_photos\bread.png', 'feluakh'),
       (6348,842390,865, 'ciabatt','c:\docs\DB_photos\ciabatt.png', 'feluakh'),
       (6348,954805,684, 'cracker','c:\docs\DB_photos\cracker.png', 'feluakh'),
       (6348,039986,76, 'flour','c:\docs\DB_photos\flour.png', 'feluakh'),
       (6348,938055,34, 'piada','c:\docs\DB_photos\piada.png', 'feluakh'),
       (6348,095689,98, 'piadina','c:\docs\DB_photos\piadina.png', 'feluakh'),
       (6348,039289,96, 'burrito','c:\docs\DB_photos\burrito.png', 'feluakh'),
       (6348,940467,34, 'paella','c:\docs\DB_photos\paella.png', 'feluakh'); 

 
 
INSERT INTO GEODB.COMPOSE(CCLIST,CCITEM)
VALUES (3627,374895),
       (3627,948586),
       (3627,938055),
       (3627,039289),
       (3627,765855),
       (3627,938586),
       (3627,475326),
       (7893,765855),
       (7893,463895),
       (7893,739446),
       (7893,039986),
       (7893,940467),
       (7893,953264),
       (6545,463895),
       (6545,983857),
       (6545,983257),
       (3456,374895),
       (3456,983257),
       (3456,339655),
       (3456,736496),
       (3456,842390),
       (3456,320496),
       (7453,374895),
       (7453,983257),
       (7453,339655),
       (7453,736496),
       (7453,842390),
       (7453,320496);

