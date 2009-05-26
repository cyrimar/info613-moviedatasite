/* Context: INFO 613 DDL movie data web site*/
/* File Name: Distribute Movie Object Data into Relational
 * Tables*/
/* Date: May 23, 2009*/
/* Overview: This stored proc shall shred xml data
 * to DB tables */ 

CREATE OR REPLACE PROCEDURE insertMovie 
(movieTitle VARCHAR2,movieYear NUMBER)
IS
BEGIN
INSERT INTO MOVIE
VALUES (movie_seq.nextval, movieTitle, movieYear);
END;
/
CREATE OR REPLACE PROCEDURE insertActor 
(actorFName VARCHAR2,actorLName VARCHAR2)
IS
cnt NUMBER;
id NUMBER;
BEGIN
SELECT COUNT(*) INTO cnt from ACTOR 
WHERE first_name = actorFName
AND last_name = actorLName;
IF cnt > 0 THEN 
SELECT actor_id into id from ACTOR 
WHERE first_name = actorFName
AND last_name = actorLName;
INSERT INTO PLAYS
VALUES(movie_seq.currval,id);
ELSE
INSERT INTO ACTOR
VALUES (actor_seq.nextval, actorFName, actorLName);
INSERT INTO PLAYS
VALUES(movie_seq.currval,actor_seq.currval);
END IF;
END;
/
CREATE OR REPLACE PROCEDURE insertDirector 
(directorName VARCHAR2)
IS
id NUMBER;
cnt NUMBER;
BEGIN
SELECT COUNT(*) INTO cnt from DIRECTOR
WHERE director_name = directorName;
IF cnt > 0 THEN 
SELECT director_id INTO id FROM DIRECTOR 
WHERE director_name = directorName;
INSERT INTO MOVIE_DIRECTOR
VALUES(movie_seq.currval,id);
ELSE
INSERT INTO DIRECTOR
VALUES (director_seq.nextval, directorName);
INSERT INTO MOVIE_DIRECTOR
VALUES(movie_seq.currval,director_seq.currval);
END IF;
END;
/
CREATE OR REPLACE PROCEDURE insertGenre
(genreName VARCHAR2)
IS
id NUMBER;
cnt NUMBER;
BEGIN
SELECT COUNT(*) INTO cnt from GENRE
WHERE genre_name = genreName;
IF cnt > 0 THEN 
SELECT genre_id INTO id FROM GENRE 
WHERE genre_name = genreName;
INSERT INTO MOVIE_GENRE
VALUES(movie_seq.currval,id);
ELSE
INSERT INTO GENRE 
VALUES (genre_seq.nextval,genreName);
INSERT INTO MOVIE_GENRE
VALUES(movie_seq.currval,genre_seq.currval);
END IF;
END;
/