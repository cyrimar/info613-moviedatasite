/* Context: INFO 613 DDL movie data web site*/
/* File Name: Distribute Movie Object Data into Relational
 * Tables*/
/* Date: May 23, 2009*/
/* Overview: This stored proc shall insert xml data
 * to actor tbl */

CREATE OR REPLACE PROCEDURE insertActor 
(actorFName VARCHAR2,actorLName VARCHAR2)
IS
cnt NUMBER;
id NUMBER;
movie_id NUMBER;
BEGIN
SELECT COUNT(*) INTO cnt from ACTOR 
WHERE first_name = actorFName
AND last_name = actorLName;
SELECT MAX(movie_id) INTO movie_id from MOVIE;
IF cnt > 0 THEN 
SELECT actor_id into id from ACTOR 
WHERE first_name = actorFName
AND last_name = actorLName;
INSERT INTO PLAYS
VALUES(movie_id,id);
ELSE
INSERT INTO ACTOR
VALUES (actor_seq.nextval, actorFName, actorLName);
INSERT INTO PLAYS
VALUES(movie_id,actor_seq.currval);
END IF;
END;
/