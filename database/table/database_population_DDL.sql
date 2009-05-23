/* Context: INFO 613 DDL movie data web site*/
/* File Name: DDL Population Script*/
/* Date: May 23, 2009*/
/* Overview: This script shall populate dummy 
 * records to test entities, relations, and data types. */

INSERT INTO ACTOR 
VALUES (1, 'Julia', 'Roberts');
INSERT INTO MOVIE
VALUES (1, 'M', '2001');
INSERT INTO MOVIE 
VALUES (2, 'Duck Soup', '2004');
INSERT INTO PLAYS 
VALUES (1, 1);
INSERT INTO PLAYS 
VALUES (1,2);
INSERT INTO DIRECTOR 
VALUES (1,'Steven Spielberg');
INSERT INTO GENRE 
VALUES (1,'Comedy');
INSERT INTO GENRE 
VALUES (2,'Musical');
INSERT INTO GENRE 
VALUES (3,'(more)');
INSERT INTO MOVIE_DIRECTOR 
VALUES (1,1);
INSERT INTO MOVIE_GENRE 
VALUES (1,1);
INSERT INTO MOVIE_GENRE 
VALUES (2,2);