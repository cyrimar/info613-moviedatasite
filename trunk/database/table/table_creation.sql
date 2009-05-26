/* Context: INFO 613 DDL movie data web site*/
/* File Name: DDL Creation Script*/
/* Date: May 14, 2009*/
/* Overview: This script shall create the tables, with their columns and appropriate data types,
primary and foreign keys, and any other constraints necessary. */

/* Application will connect to the database via username='abc123', password='oracle_password'.*/
/* Application cannot manipulate database structure. Instead, it can only perform select, insert operations on entities. */
--GRANT SELECT,INSERT ON *.* TO 'abc123'@'oracle.cis.drexel.edu'
--REVOKE DROP,CREATE,UPDATE,DELETE ON *.* FROM 'abc123'@'oracle.cis.drexel.edu'

--Actor entity table
CREATE TABLE actor (
	actor_id	numeric(10)		NOT NULL,
	first_name	varchar2(50)	NOT NULL,
	last_name	varchar2(50)	NOT NULL,
	CONSTRAINT actor_pk PRIMARY KEY (actor_id)
);

--Movie entity table
CREATE TABLE movie (
	movie_id	numeric(10)		NOT NULL,
	title		varchar2(200)	NOT NULL,
	year		numeric(4)		NOT NULL,
	CONSTRAINT movie_pk PRIMARY KEY (movie_id)
);

--Association table between Actor and Movie entities
CREATE TABLE plays (
    movie_id	numeric(10)	NOT NULL,
	actor_id	numeric(10)	NOT NULL,
	CONSTRAINT plays_pk PRIMARY KEY (actor_id,movie_id),
	CONSTRAINT plays_actor_fk FOREIGN KEY (actor_id) REFERENCES actor(actor_id),
	CONSTRAINT plays_movie_fk FOREIGN KEY (movie_id) REFERENCES movie(movie_id)	
);

--Director entity table
CREATE TABLE director (
	director_id	numeric(10)		NOT NULL,
	director_name varchar2(100)	NOT NULL,
	CONSTRAINT director_pk PRIMARY KEY (director_id)
);

--Genre entity table
CREATE TABLE genre (
	genre_id	numeric(10)		NOT NULL,
	genre_name	varchar2(100)	NOT NULL,
	CONSTRAINT genre_pk PRIMARY KEY (genre_id)
);

--Associative table bewteen Movie and Director entities
CREATE TABLE movie_director (
	movie_id	numeric(10)		NOT NULL,
	director_id	numeric(10)		NOT NULL,
	CONSTRAINT movie_director_pk PRIMARY KEY (movie_id,director_id),
	CONSTRAINT movie_director_movie_fk FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
	CONSTRAINT movie_director_director_fk FOREIGN KEY (director_id) REFERENCES director(director_id)
);

--Associative table between Movie and Genre entities
CREATE TABLE movie_genre (
	movie_id	numeric(10)		NOT NULL,
	genre_id	numeric(10)		NOT NULL,
	CONSTRAINT movie_genre_pk PRIMARY KEY (movie_id,genre_id),
	CONSTRAINT movie_genre_movie_fk FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
	CONSTRAINT movie_genre_genre_fk FOREIGN KEY (genre_id) REFERENCES genre(genre_id)
);

--The purpose of this table is to provide a centralized location
--for getting IDs for the tables, in bulk if necessary. 
CREATE TABLE sequence (
	table_name	varchar2(50)	NOT NULL,
	next_id		numeric(10)		NOT NULL,
	CONSTRAINT sequence_pk PRIMARY KEY (table_name)
);

--Auto-increment id-numbers
CREATE sequence mov_seq 
START WITH 1 
INCREMENT by 1 
NOMAXVALUE; 

CREATE sequence director_seq 
START WITH 1 
INCREMENT by 1 
NOMAXVALUE;

CREATE sequence genre_seq 
START WITH 1 
INCREMENT by 1 
NOMAXVALUE;

CREATE sequence actor_seq 
START WITH 1 
INCREMENT by 1 
NOMAXVALUE;