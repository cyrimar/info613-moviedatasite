/* Context: INFO 613 DDL movie data web site*/
/* File Name: Truncation of Data from DB*/
/* Date: May 23, 2009*/
/* Overview: This stored proc shall shall remove 
 * the data, reset sequence numbers, 
 * and leave the 
 * structure intact.*/ 

CREATE OR REPLACE PROCEDURE truncateDB 
IS
l_val number;
BEGIN
DELETE FROM movie_genre;
DELETE FROM movie_director;
DELETE FROM plays;
DELETE FROM actor;
DELETE FROM movie;
DELETE FROM director;
DELETE FROM genre; 

EXECUTE IMMEDIATE 
'SELECT movie_seq.nextval FROM dual' INTO l_val;    
EXECUTE IMMEDIATE 
'alter sequence movie_seq INCREMENT by -' || l_val || ' minvalue 0';    
EXECUTE IMMEDIATE 
'SELECT movie_seq.nextval FROM dual' INTO l_val;    
EXECUTE IMMEDIATE 
'ALTER sequence movie_seq INCREMENT by 1 minvalue 0';

EXECUTE IMMEDIATE 
'SELECT actor_seq.nextval FROM dual' INTO l_val;    
EXECUTE IMMEDIATE 
'ALTER sequence actor_seq INCREMENT by -' || l_val || ' minvalue 0';    
EXECUTE IMMEDIATE 
'SELECT actor_seq.nextval FROM dual' INTO l_val;    
EXECUTE IMMEDIATE 
'ALTER sequence actor_seq INCREMENT by 1 minvalue 0';

EXECUTE IMMEDIATE 
'SELECT genre_seq.nextval FROM dual' INTO l_val;    
EXECUTE IMMEDIATE 
'ALTER sequence genre_seq INCREMENT by -' || l_val || ' minvalue 0';    
EXECUTE IMMEDIATE 
'SELECT genre_seq.nextval FROM dual' INTO l_val;    
EXECUTE IMMEDIATE 
'ALTER sequence genre_seq INCREMENT by 1 minvalue 0';

EXECUTE IMMEDIATE 
'SELECT director_seq.nextval FROM dual' INTO l_val;    
EXECUTE IMMEDIATE 
'ALTER sequence director_seq INCREMENT by -' || l_val || ' minvalue 0';    
EXECUTE IMMEDIATE 
'SELECT director_seq.nextval FROM dual' INTO l_val;    
EXECUTE IMMEDIATE 
'ALTER sequence director_seq INCREMENT by 1 minvalue 0';

END;
/