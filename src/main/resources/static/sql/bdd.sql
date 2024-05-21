-- Create the database
CREATE DATABASE IF NOT EXISTS cv24;

-- Use the created database
USE cv24;

-- Create tables corresponding to the JPA entities

CREATE TABLE identite (
    id INT AUTO_INCREMENT PRIMARY KEY,
    genre VARCHAR(5),
    nom VARCHAR(32),
    prenom VARCHAR(32),
    tel VARCHAR(15),
    mel VARCHAR(255)
);

CREATE TABLE objectif (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(128),
    statut VARCHAR(7)
);

CREATE TABLE detail (
    id INT AUTO_INCREMENT PRIMARY KEY,
    datedeb DATETIME,
    datefin DATETIME,
    titre_detail VARCHAR(128)
);

CREATE TABLE competences (
    id INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE diplome (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    institut VARCHAR(32),
    titre VARCHAR(32),
    niveau INT
);

CREATE TABLE certif (
    id INT AUTO_INCREMENT PRIMARY KEY,
    datedeb DATETIME,
    datefin DATETIME,
    titre_cert VARCHAR(32)
);

CREATE TABLE divers (
    id INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE lv (
    id INT AUTO_INCREMENT PRIMARY KEY,
    lang VARCHAR(32),
    cert VARCHAR(5),
    nivs VARCHAR(2),
    nivi INT,
    divers_id INT,
    FOREIGN KEY (divers_id) REFERENCES divers(id)
);

CREATE TABLE autre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(32),
    comment VARCHAR(128),
    divers_id INT,
    FOREIGN KEY (divers_id) REFERENCES divers(id)
);

CREATE TABLE divers_autre (
    divers_id INT,
    autre_id INT,
    PRIMARY KEY (divers_id, autre_id),
    FOREIGN KEY (divers_id) REFERENCES divers(id),
    FOREIGN KEY (autre_id) REFERENCES autre(id)
);

CREATE TABLE divers_lv (
    divers_id INT,
    lv_id INT,
    PRIMARY KEY (divers_id, lv_id),
    FOREIGN KEY (divers_id) REFERENCES divers(id),
    FOREIGN KEY (lv_id) REFERENCES lv(id)
);

CREATE TABLE cv (
    id INT AUTO_INCREMENT PRIMARY KEY,
    competences_id INT,
    identite_id INT,
    objectif_id INT,
    divers_id INT,
    FOREIGN KEY (competences_id) REFERENCES competences(id),
    FOREIGN KEY (identite_id) REFERENCES identite(id),
    FOREIGN KEY (objectif_id) REFERENCES objectif(id),
    FOREIGN KEY (divers_id) REFERENCES divers(id)
);

CREATE TABLE prof (
    id INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE prof_detail (
    prof_id INT,
    detail_id INT,
    PRIMARY KEY (prof_id, detail_id),
    FOREIGN KEY (prof_id) REFERENCES prof(id),
    FOREIGN KEY (detail_id) REFERENCES detail(id)
);

CREATE TABLE cv_prof (
    cv_id INT,
    prof_id INT,
    PRIMARY KEY (cv_id, prof_id),
    FOREIGN KEY (cv_id) REFERENCES cv(id),
    FOREIGN KEY (prof_id) REFERENCES prof(id)
);

CREATE TABLE competences_diplome (
    competences_id INT,
    diplome_id INT,
    PRIMARY KEY (competences_id, diplome_id),
    FOREIGN KEY (competences_id) REFERENCES competences(id),
    FOREIGN KEY (diplome_id) REFERENCES diplome(id)
);

CREATE TABLE competences_certif (
    competences_id INT,
    certif_id INT,
    PRIMARY KEY (competences_id, certif_id),
    FOREIGN KEY (competences_id) REFERENCES competences(id),
    FOREIGN KEY (certif_id) REFERENCES certif(id)
);