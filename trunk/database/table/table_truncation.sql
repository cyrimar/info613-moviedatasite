/* Context: INFO 613 DDL movie data web site*/
/* File Name: DDL Truncation Script*/
/* Date: May 14, 2009*/
/* The cleanup script shall remove only the data 
 * from the tables and leave the structure intact.*/

DELETE FROM movie_genre;
DELETE FROM movie_director;
DELETE FROM plays;
DELETE FROM actor;
DELETE FROM movie;
DELETE FROM director;
DELETE FROM genre;
DROP SEQUENCE movie_seq;
DROP SEQUENCE director_seq;
DROP SEQUENCE genre_seq; 
DROP SEQUENCE actor_seq;
--Auto-increment id-numbers
CREATE sequence movie_seq 
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



