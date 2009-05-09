--General Comments/Questions
--Should we use IDs? I prefer this - particularly because the inclusion of
--multivalued attrivutes (like Genre and Director) becomes complicated without
--this mechanism

CREATE TABLE actor (
	actor_id	numeric(10)		NOT NULL,
	first_name	varchar2(50)	NOT NULL,
	last_name	varchar2(50)	NOT NULL,
	CONSTRAINT actor_pk PRIMARY KEY (actor_id)
);

--Should we use a numeric for the year? This data type is not enforced
--in the DTD, so we'd have to ensure it at application level
CREATE TABLE movie (
	movie_id	numeric(10)		NOT NULL,
	title		varchar2(200)	NOT NULL,
	year		numeric(4)		NOT NULL,
	CONSTRAINT movie_pk PRIMARY KEY (movie_id)
);

CREATE TABLE plays (
	actor_id	numeric(10)	NOT NULL,
	movie_id	numeric(10)	NOT NULL,
	CONSTRAINT plays_pk PRIMARY KEY (actor_id,movie_id),
	CONSTRAINT plays_actor_fk FOREIGN KEY (actor_id) REFERENCES actor(actor_id),
	CONSTRAINT plays_movie_fk FOREIGN KEY (movie_id) REFERENCES movie(movie_id)	
);

CREATE TABLE movie_director (
	movie_id	numeric(10)		NOT NULL,
	director	varchar2(100)	NOT NULL,
	CONSTRAINT movie_director_pk PRIMARY KEY (movie_id,director),
	CONSTRAINT movie_director_movie_fk FOREIGN KEY (movie_id) REFERENCES movie(movie_id)
);

CREATE TABLE movie_genre (
	movie_id	numeric(10)		NOT NULL,
	genre		varchar2(100)	NOT NULL,
	CONSTRAINT movie_genre_pk PRIMARY KEY (movie_id,genre),
	CONSTRAINT movie_genre_movie_fk FOREIGN KEY (movie_id) REFERENCES movie(movie_id)
);

--The purpose of this table is to provide a centralized location
--for getting IDs for the tables, in bulk if necessary. 
CREATE TABLE sequence (
	table_name	varchar2(50)	NOT NULL,
	next_id		numeric(10)		NOT NULL,
	CONSTRAINT sequence_pk PRIMARY KEY (table_name)
);
