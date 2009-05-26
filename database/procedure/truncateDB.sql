/* Context: INFO 613 DDL movie data web site*/
/* File Name: Truncation of Data from DB*/
/* Date: May 23, 2009*/
/* Overview: This stored proc shall shall remove only 
 * the data from the tables and leave the 
 * structure intact.*/ 

CREATE OR REPLACE PROCEDURE truncateDB 
IS
m_id NUMBER;
a_id NUMBER;
g_id NUMBER;
d_id NUMBER;
BEGIN
SELECT MAX(MOVIE_ID) INTO m_id FROM MOVIE;
SELECT MAX(ACTOR_ID) INTO a_id FROM ACTOR;
SELECT MAX(GENRE_ID) INTO g_id FROM GENRE;
SELECT MAX(DIRECTOR_ID) INTO d_id FROM DIRECTOR;
DELETE FROM movie_genre;
DELETE FROM movie_director;
DELETE FROM plays;
DELETE FROM actor;
DELETE FROM movie;
DELETE FROM director;
DELETE FROM genre; 
IF m_id > 0
EXECUTE IMMEDIATE
'alter sequence movie_seq increment by -' || m_id;
IF m_seq.currval = 0
EXECUTE IMMEDIATE
'alter sequence movie_seq increment by 1';
END IF;
END IF;
IF g_id > 0
EXECUTE IMMEDIATE
'alter sequence genre_seq increment by -' || g_id;
IF g_seq.currval = 0
EXECUTE IMMEDIATE
'alter sequence genre_seq increment by 1';
END IF;
END IF;
IF d_id > 0
EXECUTE IMMEDIATE
'alter sequence director_seq increment by -' || d_id;
IF d_seq.currval = 0
EXECUTE IMMEDIATE
'alter sequence director_seq increment by 1';
END IF;
END IF;
IF a_id > 0
EXECUTE IMMEDIATE
'alter sequence actor_seq increment by -' || a_id;
IF a_seq.currval = 0
EXECUTE IMMEDIATE
'alter sequence actor_seq increment by 1';
END IF;
END IF;
END;
/