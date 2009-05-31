/* Context: INFO 613 DDL movie data web site*/
/* File Name: Distribute Movie Object Data into Relational
 * Tables*/
/* Date: May 23, 2009*/
/* Overview: This stored proc shall insert xml data
 * to genre tbl */ 

CREATE OR REPLACE PROCEDURE insertGenre
(genreName VARCHAR2)
IS
id NUMBER;
cnt NUMBER;
movie_id NUMBER;
BEGIN
SELECT COUNT(*) INTO cnt from GENRE
WHERE genre_name = genreName;
SELECT MAX(movie_id) INTO movie_id from MOVIE;
IF cnt > 0 THEN 
SELECT genre_id INTO id FROM GENRE 
WHERE genre_name = genreName;
INSERT INTO MOVIE_GENRE
VALUES(movie_id,id);
ELSE
INSERT INTO GENRE 
VALUES (genre_seq.nextval,genreName);
INSERT INTO MOVIE_GENRE
VALUES(movie_id,genre_seq.currval);
END IF;
END;
/