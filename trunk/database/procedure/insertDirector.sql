/* Context: INFO 613 DDL movie data web site*/
/* File Name: Distribute Movie Object Data into Relational
 * Tables*/
/* Date: May 23, 2009*/
/* Overview: This stored proc shall insert xml data
 * to director tbl */

CREATE OR REPLACE PROCEDURE insertDirector 
(directorName VARCHAR2)
IS
id NUMBER;
cnt NUMBER;
movie_id NUMBER;
BEGIN
SELECT COUNT(*) INTO cnt from DIRECTOR
WHERE director_name = directorName;
SELECT MAX(movie_id) INTO movie_id from MOVIE;
IF cnt > 0 THEN 
SELECT director_id INTO id FROM DIRECTOR 
WHERE director_name = directorName;
INSERT INTO MOVIE_DIRECTOR
VALUES(movie_id,id);
ELSE
INSERT INTO DIRECTOR
VALUES (director_seq.nextval, directorName);
INSERT INTO MOVIE_DIRECTOR
VALUES(movie_id,director_seq.currval);
END IF;
END;
/