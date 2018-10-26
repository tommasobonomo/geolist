CREATE TABLE users (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    cookie VARCHAR(30),
    username VARCHAR(30) NOT NULL UNIQUE, 
    name VARCHAR(30) NOT NULL ,
    lastname VARCHAR(30),
    email VARCHAR(30) NOT NULL UNIQUE, 
    image VARCHAR(20),
    password VARCHAR(60) NOT NULL,
    "admin" BOOLEAN,
    CONSTRAINT user_pk PRIMARY KEY (id)
  );

CREATE TABLE email(
    idmail INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    info INTEGER, 
    text  VARCHAR(400),
    sender INTEGER NOT NULL,
    receiver INTEGER NOT NULL,
    CONSTRAINT email_pk PRIMARY KEY (idmail),
    FOREIGN KEY (sender) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (receiver) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idmail) 
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE usersanonimous (
    cookie VARCHAR(30),
    PRIMARY KEY (cookie)

);

CREATE TABLE clist (
    idcategory INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    "name" VARCHAR(30),
    description VARCHAR(30),
    image VARCHAR(10),
    CONSTRAINT clist_pk PRIMARY KEY (idcategory)
);


CREATE TABLE list (
    idlist INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    usercreator INTEGER,  
    idcat INTEGER,
    "name" VARCHAR(30),
    description VARCHAR(30),
    image VARCHAR(50),
    FOREIGN KEY (usercreator) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idcat) 
        REFERENCES clist(idcategory)
        ON DELETE CASCADE,
    CONSTRAINT idlist_pk PRIMARY KEY (idlist)
);

CREATE TABLE citem (
    idcategory INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    "name" VARCHAR(30),
    description VARCHAR(30),
    image VARCHAR(10),
    CONSTRAINT citem_pk PRIMARY KEY (idcategory)
);


CREATE TABLE item (
    iditem INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    iddcat INTEGER NOT NULL,
    FOREIGN KEY (iddcat) 
        REFERENCES citem(idcategory)
        ON DELETE CASCADE,
    calorie INTEGER,
    "name" VARCHAR(30),
    logo VARCHAR(50),
    note VARCHAR(30),
    CONSTRAINT item_pk PRIMARY KEY (iditem)
);


CREATE TABLE compose(
    cclist INTEGER,
    ccitem INTEGER ,
    FOREIGN KEY (ccitem) 
        REFERENCES item(iditem)
        ON DELETE CASCADE,
    FOREIGN KEY (cclist) 
        REFERENCES list(idlist)
        ON DELETE CASCADE,
    PRIMARY KEY (ccitem, cclist)  
);

CREATE TABLE isfriend(
    usr1 INTEGER ,
    usr2 INTEGER ,
    FOREIGN KEY (usr1) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (usr2) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    PRIMARY KEY (usr1, usr2)  
);

CREATE TABLE access(
    id INTEGER NOT NULL PRIMARY KEY,
    aauser INTEGER,
    aalist INTEGER,
    FOREIGN KEY (aauser) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (aalist) 
        REFERENCES list(idlist)
        ON DELETE CASCADE,
    UNIQUE (aauser, aalist)    
);


