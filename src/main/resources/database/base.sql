create table emp(
    id serial primary key,
    matricule varchar(50) unique not null,
    nom varchar(100) not null,
    prenom varchar(100) not null,
    dtn date check ( dtn < now() ),
    genre int not null,
    actif boolean
);
