

CREATE TABLE users (
    id INTEGER GENERATED ALWAYS AS IDENTITY,
    cookie VARCHAR(100),
    username VARCHAR(40) NOT NULL UNIQUE, 
    name VARCHAR(40) NOT NULL ,
    lastname VARCHAR(40) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE, 
    image BLOB,
    password VARCHAR(150) NOT NULL,
    token VARCHAR(255),
    active boolean,
    "ADMIN" BOOLEAN,
    CONSTRAINT user_pk PRIMARY KEY (id)
  );

CREATE TABLE usersanonimous (
    id INTEGER GENERATED ALWAYS AS IDENTITY,
    cookie VARCHAR(100) NOT NULL UNIQUE,
    CONSTRAINT useranonimous_pk PRIMARY KEY (id)
);

CREATE TABLE clist (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    "NAME" VARCHAR(50),
    description VARCHAR(1000),
    HERECODE VARCHAR(20),
    image BLOB,
    CONSTRAINT clist_pk PRIMARY KEY (id)
);


CREATE TABLE list (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    userowner INTEGER,
    useranonowner INTEGER UNIQUE,
    idcat INTEGER,
    "NAME" VARCHAR(50),
    description VARCHAR(1000),
    image BLOB,
    isPreMade BOOLEAN,
    FOREIGN KEY (userowner) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (useranonowner)
        REFERENCES usersanonimous(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idcat) 
        REFERENCES clist(id)
        ON DELETE CASCADE,
    CONSTRAINT idlist_pk PRIMARY KEY (id)
);

CREATE TABLE citem (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    "NAME" VARCHAR(50),
    description VARCHAR(1000),
    image BLOB,
    CONSTRAINT citem_pk PRIMARY KEY (id)
);


CREATE TABLE item (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    idcat INTEGER NOT NULL,
    FOREIGN KEY (idcat) 
        REFERENCES citem(id)
        ON DELETE CASCADE,
    "NAME" VARCHAR(50),
    logo BLOB,
    note VARCHAR(1000),
    istemplate BOOLEAN NOT NULL,
    idowner INTEGER,
    CONSTRAINT item_pk PRIMARY KEY (id),
    FOREIGN KEY (idowner)
        REFERENCES users(id)
        ON DELETE CASCADE
);


CREATE TABLE compose(
    list INTEGER,
    item INTEGER ,
    quantity INTEGER NOT NULL,
    take BOOLEAN NOT NULL,
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
    havePermission BOOLEAN NOT NULL,
    FOREIGN KEY (iduser) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idlist) 
        REFERENCES list(id)
        ON DELETE CASCADE,
    PRIMARY KEY (iduser, idlist)    
);

CREATE TABLE ItemPermission(
    categoryListId INTEGER,
    categoryItemId INTEGER,
    FOREIGN KEY (categoryListId)
        REFERENCES clist(id)
        ON DELETE CASCADE,
    FOREIGN KEY (categoryItemId)
        REFERENCES citem(id)
        ON DELETE CASCADE,
    PRIMARY KEY (categoryListId, categoryItemId)
);    

CREATE TABLE message(
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    idUser INTEGER,
    idList INTEGER,
    text VARCHAR(200),
    sendTime TIMESTAMP, 
    FOREIGN KEY (idUser) 
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idList) 
        REFERENCES list(id)
        ON DELETE CASCADE,
    CONSTRAINT message_pk PRIMARY KEY (id)
);
