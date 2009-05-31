/* Context: INFO 613 DDL movie data web site*/
/* File Name: Distribute Movie Object Data into Relational
 * Tables*/
/* Date: May 23, 2009*/
/* Overview: This stored proc shall insert xml data
 * to movie tbl */ 

CREATE OR REPLACE PROCEDURE insertMovie 
(movieTitle VARCHAR2,movieYear NUMBER)
IS
BEGIN
INSERT INTO MOVIE
VALUES (movie_seq.nextval, movieTitle, movieYear);
END;
/