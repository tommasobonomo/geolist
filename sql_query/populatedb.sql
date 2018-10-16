INSERT INTO GEODB.USERS(USERNAME,"NAME",LASTNAME,EMAIL,IMAGE, PASSWORD, "ADMIN")
VALUES ('rappasta', 'Lorenzo', 'Framba', 'Lorenzoframba@gmail.com','image','pasta',TRUE),
       ('gesuSesso','Mattia', 'Molon','Molon@gmail.com','IMAGE','c123456',FALSE),
       ( 'minestrone','Tommaso','Bonomo','Tommasoni394@gmail.com','IMAGEN','qwerty',FALSE),
       ('XDSNIPERXD','Leonardo','Remondini', 'Remoromolo@gmail.com','IMAGEN', 'sdfghj',FALSE),
       ('piediPiatti','Giorgio','Segalla','Giorgionelo@gmail.com','IMAGEN', 'sdfghj',FALSE),
       ('Maialino69','Marta','Tonnona', 'Martinasesso@gmail.com','IMAGEN', 'sdfghjekrf',FALSE),
       ('Fratak','Elia','Salame','piselloni@gmail.com','IMAGEN', 'texyckv',FALSE),
       ( 'OrcoBue','Luca', 'Rossi', 'marione@gmail.com','IMAGEN', 'lieuwhf',FALSE),
       ( 'Iphonneeee','Nicola','Nicoli', 'naso@gmail.com','IMAGEN', 'fieguwkh',FALSE),
       ('Bottiglia','Marco','Bestioni', 'tomcat@gmail.com','IMAGEN', 'feluakh',FALSE);


INSERT INTO GEODB.CITEM(IDCATEGORY,"NAME",DESCRIPTION, IMAGE)
VALUES  (3627, 'meat', 'GOOD', 'CIBO'),
        (9382, 'fridge', 'GOOD', 'CIBO'),
        (4873, 'vegetable', 'GOOD', 'CIBO'),
        (3903, 'fruit', 'GOOD', 'CIBO'),
        (6348, 'bread', 'GOOD', 'CIBO');


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
 
