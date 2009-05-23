/* Context: INFO 613 DDL movie data web site*/
/* File Name: Retrieval of Movies By a Director*/
/* Date: May 23, 2009*/
/* Overview: This stored proc shall retrieve titles and 
 * genres of all movies directed by a director */ 

CREATE OR REPLACE PROCEDURE sp_retrieveMoviesByDirector (directorName IN VARCHAR2)
IS 
BEGIN
	SELECT M.TITLE, G.GENRE_NAME 
    FROM MOVIE M, GENRE G, MOVIE_GENRE MG, DIRECTOR D, MOVIE_DIRECTOR MD 
    WHERE
    MD.DIRECTOR_ID = D.DIRECTOR_ID AND
    MD.MOVIE_ID = M.MOVIE_ID AND
    MG.GENRE_ID = M.MOVIE_ID AND
    MG.GENRE_ID = G.GENRE_ID AND
    D.DIRECTOR_NAME = directorName;
END; 

