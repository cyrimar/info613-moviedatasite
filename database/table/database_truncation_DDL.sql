/* Context: INFO 613 DDL movie data web site*/
/* File Name: DDL Truncation Script*/
/* Date: May 14, 2009*/
/* The cleanup script shall remove only the data from the tables and leave the structure intact.*/

TRUNCATE TABLE sequence;
TRUNCATE TABLE movie_genre;
TRUNCATE TABLE movie_director;
TRUNCATE TABLE genre;
TRUNCATE TABLE director;
TRUNCATE TABLE plays;
TRUNCATE TABLE movie;
TRUNCATE TABLE actor;
