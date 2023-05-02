
    drop table if exists beer;

    drop table if exists customer;

    create table beer (
                      id varchar(36) not null,
                      beerName varchar(50) not null,
                      beerStyle smallint not null,
                      createdDate datetime(6),
                      price decimal(38,2) not null,
                      quantityOnHand integer,
                      upc varchar(255) not null,
                      updatedDate datetime(6),
                      version integer,
                      primary key (id)
    ) engine=InnoDB;

    create table customer (
                          id varchar(36) not null,
                          createdDate datetime(6),
                          name varchar(255),
                          lastModifiedDate datetime(6),
                          version integer,
                          primary key (id)
    ) engine=InnoDB;