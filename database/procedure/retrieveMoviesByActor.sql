/* Context: INFO 613 DDL movie data web site*/
/* File Name: Retrieval of Movies By an Actor*/
/* Date: May 23, 2009*/
/* Overview: This stored proc shall retrieve titles and 
 * genres of all movies acted by an actor */ 

CREATE OR REPLACE FUNCTION retrieveMoviesByActor 
(actorFName IN VARCHAR2,actorLName IN VARCHAR2) RETURN SYS_REFCURSOR
IS
l_cursor SYS_REFCURSOR;
fname VARCHAR2(100) := actorFName;
lname VARCHAR2(100) := actorLName;
BEGIN
OPEN l_cursor FOR
SELECT 
M.TITLE, G.GENRE_NAME 
FROM 
MOVIE M, GENRE G, MOVIE_GENRE MG, PLAYS P, ACTOR A 
WHERE
P.ACTOR_ID = A.ACTOR_ID AND
P.MOVIE_ID = M.MOVIE_ID AND
MG.GENRE_ID = M.MOVIE_ID AND
MG.GENRE_ID = G.GENRE_ID AND
A.FIRST_NAME = fname AND 
A.LAST_NAME = lname;
RETURN l_cursor;
END; 
/