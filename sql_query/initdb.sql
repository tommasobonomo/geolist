CREATE TABLE users (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    cookie VARCHAR(30),
    username VARCHAR(30) NOT NULL UNIQUE, 
    name VARCHAR(30) NOT NULL ,
    lastname VARCHAR(30),
    email VARCHAR(30) NOT NULL UNIQUE, 
    image VARCHAR(20),
    password VARCHAR(60) NOT NULL,
    token VARCHAR(255),
    active boolean,
    "ADMIN" BOOLEAN,
    CONSTRAINT user_pk PRIMARY KEY (id)
  );

CREATE TABLE email(
    idUser INTEGER NOT NULL,
    info INTEGER, 
    text  VARCHAR(400),
    sender INTEGER NOT NULL,
    receiver INTEGER NOT NULL,
    CONSTRAINT email_pk PRIMARY KEY (idUser),
    FOREIGN KEY (sender) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (receiver) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idUser) 
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE usersanonimous (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    cookie VARCHAR(30) NOT NULL UNIQUE,
    CONSTRAINT useranonimous_pk PRIMARY KEY (id)
);

CREATE TABLE clist (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    "NAME" VARCHAR(30),
    description VARCHAR(30),
    image VARCHAR(10),
    CONSTRAINT clist_pk PRIMARY KEY (id)
);


CREATE TABLE list (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    usercreator INTEGER,  
    idcat INTEGER,
    "NAME" VARCHAR(30),
    description VARCHAR(30),
    image VARCHAR(50),
    FOREIGN KEY (usercreator) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idcat) 
        REFERENCES clist(id)
        ON DELETE CASCADE,
    CONSTRAINT idlist_pk PRIMARY KEY (id)
);

CREATE TABLE citem (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    "NAME" VARCHAR(30),
    description VARCHAR(30),
    image VARCHAR(10),
    CONSTRAINT citem_pk PRIMARY KEY (id)
);


CREATE TABLE item (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    idcat INTEGER NOT NULL,
    FOREIGN KEY (idcat) 
        REFERENCES citem(id)
        ON DELETE CASCADE,
    calorie INTEGER,
    "NAME" VARCHAR(30),
    logo VARCHAR(50),
    note VARCHAR(30),
    CONSTRAINT item_pk PRIMARY KEY (id)
);


CREATE TABLE compose(
    list INTEGER,
    item INTEGER ,
    FOREIGN KEY (item) 
        REFERENCES item(id)
        ON DELETE CASCADE,
    FOREIGN KEY (list) 
        REFERENCES list(id)
        ON DELETE CASCADE,
    PRIMARY KEY (item, list)  
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
    iduser INTEGER,
    idlist INTEGER,
    FOREIGN KEY (iduser) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idlist) 
        REFERENCES list(id)
        ON DELETE CASCADE,
    primary key (iduser, idlist)    
);

CREATE TABLE own(
    iduser INTEGER,
    idlist INTEGER,
    FOREIGN KEY (iduser) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idlist) 
        REFERENCES list(id)
        ON DELETE CASCADE,
    primary key (idlist)    
);

CREATE TABLE ownAnonimous(
    idUserAnonimous INTEGER,
    idList INTEGER,
    FOREIGN KEY (idUserAnonimous) 
        REFERENCES usersAnonimous(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idList) 
        REFERENCES list(id)
        ON DELETE CASCADE,
    primary key (idUserAnonimous)    
);
