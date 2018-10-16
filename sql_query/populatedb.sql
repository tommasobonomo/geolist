INSERT INTO GEODB.USERS(USERNAME,"NAME",LASTNAME,EMAIL,IMAGE, PASSWORD, "ADMIN")
VALUES ('rappasta', 'Lorenzo', 'Framba', 'Lorenzoframba@gmail.com','image','106438287',TRUE),
       ('gesuSesso','Mattia', 'Molon','Molon@gmail.com','IMAGE','-880873338',FALSE),
       ('minestrone','Tommaso','Bonomo','Tommasoni394@gmail.com','IMAGEN','-946852072',FALSE),
       ('XDSNIPERXD','Leonardo','Remondini', 'Remoromolo@gmail.com','IMAGEN', '-907121836',FALSE),
       ('piediPiatti','Giorgio','Segalla','Giorgionelo@gmail.com','IMAGEN', '-907121836',FALSE),
       ('Maialino69','Marta','Tonnona', 'Martinasesso@gmail.com','IMAGEN', '193997486',FALSE),
       ('Fratak','Elia','Salame','piselloni@gmail.com','IMAGEN', '-1417685348',FALSE),
       ('OrcoBue','Luca', 'Rossi', 'marione@gmail.com','IMAGEN', '169089448',FALSE),
       ('Iphonneeee','Nicola','Nicoli', 'naso@gmail.com','IMAGEN', '-933096028',FALSE),
       ('Bottiglia','Marco','Bestioni', 'tomcat@gmail.com','IMAGEN', '-969038346',FALSE);

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
VALUES  (3627, 'rappasta', 45673, 'BIRTHDAY', 'SO MUCH FOOD','image'),
        (7893, 'gesuSesso', 78443, 'GRANDMA', 'GOING TO DIE','image'),
        (6545, 'piediPiatti', 98034, 'CHEESE', 'CHEESY','image'),
        (3456, 'rappasta', 43985, 'PICNIK', 'SANDWICHES AND STUFF','image'),
        (7453, 'Maialino69', 54054, 'COLLEGE', 'CHEAP AND FAT','image'),
        (8675, 'rappasta', 59348, 'WEEKEND', 'OMG LMFAO','image'),
        (8465, 'Bottiglia', 54646, 'FATTY', 'REALLY NOT GOOD','image'),
        (7635, 'Fratak', 45676, 'FRIEND', 'BROOOOOOOOOOO','image');
    


INSERT INTO GEODB.ISFRIEND(CC1,CC2)
VALUES  ('rappasta','gesuSesso'),
        ('rappasta','piediPiatti'),
        ('OrcoBue','Fratak'),
        ('Fratak','Bottiglia'),
        ('Fratak','rappasta'),
        ('gesuSesso','Bottiglia'),
        ('Fratak','gesuSesso'),
        ('rappasta','Bottiglia'),
        ('Fratak','piediPiatti'),
        ('piediPiatti','Bottiglia');

INSERT INTO GEODB.ITEM(IDDCAT,IDITEM,CALORIE, "NAME",LOGO, NOTE)
VALUES (3627,374895, 304, 'wurstel', 'LIDL','pasta'),
       (3627,475326, 389,'chicken','ROSSETTO','sdflkje'),
       (3627,329755, 239,'horse','AEO','sdflkj'),
       (3627,324555, 769,'cow','ROSSETTO','fdsjlh'),
       (3627,439855, 868,'beef','BILLA','lkdsfgj'),
       (3627,765855, 343,'pork','AEO','orkewjl'),
       (3627,376855, 234,'ribs','LIDL','ewlrjk'),
       (3627,339655, 876,'sausage','BILLA','reuirre'),
       (9382,463895,932,'caciotta','LIDL','qwerty'),
       (9382,983857,830, 'yogurt','ROSSETTO', 'sdfghj'),
       (9382,983257,830, 'cheese','LIDL', 'sdfghj'),
       (4873,959764,102,'carrot','AEO', 'sdfghj'),
       (4873,359764,642,'pickle','ROSSETTO', 'sdfghj'),
       (4873,559764,242,'salad','AEO', 'sdfghj'),
       (4873,789764,152,'spinach','AEO', 'sdfghj'),
       (4873,953264,132,'potato','BILLA', 'sdfghj'),
       (4873,849056,294, 'tomato','BILLA', 'sdfghjekrf'),
       (3903,730496,489,'apple','LIDL', 'texyckv'),
       (3903,720436,49,'lemon','BILLA', 'texyckv'),
       (3903,320496,39,'orange','ROSSETTO', 'texyckv'),
       (3903,430496,59,'mandarin','AEO', 'texyckv'),
       (3903,736496,83,'pear','BILLA', 'texyckv'),
       (3903,738826,39,'strawberry','BILLA', 'texyckv'),
       (3903,730465,76,'raspberry','AEO', 'texyckv'),
       (3903,739446,49, 'banana','LIDL', 'lieuwhf'),
       (6348,948586,48, 'pizza','ROSSETTO', 'fieguwkh'),
       (6348,938586,137, 'breadsticks','BILLA', 'feluakh'),
       (6348,933586,35, 'bread','BILLA', 'feluakh'),
       (6348,842390,865, 'ciabatt','ROSSETTO', 'feluakh'),
       (6348,954805,684, 'cracker','LIDL', 'feluakh'),
       (6348,039986,76, 'flour','BILLA', 'feluakh'),
       (6348,938055,34, 'piada','AEO', 'feluakh'),
       (6348,095689,98, 'piadina','BILLA', 'feluakh'),
       (6348,039289,96, 'burrito','AEO', 'feluakh'),
       (6348,940467,34, 'paella','BILLA', 'feluakh');


 
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


