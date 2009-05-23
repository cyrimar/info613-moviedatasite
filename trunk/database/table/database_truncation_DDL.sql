/* Context: INFO 613 DDL movie data web site*/
/* File Name: DDL Truncation Script*/
/* Date: May 14, 2009*/
/* The cleanup script shall remove only the data from the tables and leave the structure intact.*/

DELETE FROM movie_genre;
DELETE FROM movie_director;
DELETE FROM plays;
DELETE FROM actor;
DELETE FROM movie;
DELETE FROM director;
DELETE FROM genre;



