/* Context: INFO 613 DDL movie data web site*/
/* File Name: Truncation of Data from DB*/
/* Date: May 23, 2009*/
/* Overview: This stored proc shall shall remove only 
 * the data from the tables and leave the 
 * structure intact.*/ 

CREATE OR REPLACE PROCEDURE truncateDB 
IS
BEGIN
DELETE FROM movie_genre;
DELETE FROM movie_director;
DELETE FROM plays;
DELETE FROM actor;
DELETE FROM movie;
DELETE FROM director;
DELETE FROM genre;
END; 
/
