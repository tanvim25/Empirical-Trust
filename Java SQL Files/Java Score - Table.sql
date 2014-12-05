create table JavaTopScorers
(
owneruserid int primary key,
TechScore int
);

LOAD DATA LOCAL INFILE 'C://Users//AnkitJ//Desktop//JavaCSV//UserScores.csv' INTO TABLE JavaTopScorers FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n';

SET SQL_SAFE_UPDATES = 0;

delete from JavaTopScorers;

commit;

select * from JavaTopScorers
order by techscore desc
LIMIT 2000;