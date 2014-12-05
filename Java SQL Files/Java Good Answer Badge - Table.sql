create table JavaGoodAnswerBadge
(
owneruserid int primary key,
NumberOfBadges int
);

LOAD DATA LOCAL INFILE 'C://Users//AnkitJ//Desktop//JavaCSV//UserGoodAnswer.csv' INTO TABLE JavaGoodAnswerBadge FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n';

set SQL_SAFE_UPDATES = 0;

delete from JavaGoodAnswerBadge;

select * from JavaGoodAnswerBadge
order by NumberOfBadges desc
LIMIT 2000;