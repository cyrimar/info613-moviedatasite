/* Context: INFO 613 DDL movie data web site*/
/* File Name: Retrieval of Movies By an Actor*/
/* Date: May 23, 2009*/
/* Overview: This stored proc shall retrieve titles and 
 * genres of all movies acted by an actor */ 

CREATE OR REPLACE PROCEDURE sp_retrieveMoviesByActor (actorFName VARCHAR2,actorLName VARCHAR2)
AS 
BEGIN
    EXECUTE IMMEDIATE 
	'SELECT 
    M.TITLE, G.GENRE_NAME 
    FROM 
    MOVIE M, GENRE G, MOVIE_GENRE MG, PLAYS P, ACTOR A 
    WHERE
    P.ACTOR_ID = A.ACTOR_ID AND
    P.MOVIE_ID = M.MOVIE_ID AND
    MG.GENRE_ID = M.MOVIE_ID AND
    MG.GENRE_ID = G.GENRE_ID AND
    A.FIRST_NAME =' || actorFName || ' AND 
    A.LAST_NAME =' || actorLName;
END; 
/