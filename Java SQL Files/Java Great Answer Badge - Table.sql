create table JavaGreatAnswerBadge
(
owneruserid int primary key,
NumberOfBadges int
);

LOAD DATA LOCAL INFILE 'C://Users//AnkitJ//Desktop//JavaCSV//UserGreatAnswer.csv' INTO TABLE JavaGreatAnswerBadge FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n';

set SQL_SAFE_UPDATES = 0;

delete from JavaGreatAnswerBadge;

select * from JavaGreatAnswerBadge
order by NumberOfBadges desc
LIMIT 2000;