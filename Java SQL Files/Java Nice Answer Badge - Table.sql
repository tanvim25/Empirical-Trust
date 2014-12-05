create table JavaNiceAnswerBadge
(
owneruserid int primary key,
NumberOfBadges int
);

LOAD DATA LOCAL INFILE 'C://Users//AnkitJ//Desktop//JavaCSV//UserNiceAnswer.csv' INTO TABLE JavaNiceAnswerBadge FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n';

set SQL_SAFE_UPDATES = 0;

delete from JavaNiceAnswerBadge;

select * from JavaNiceAnswerBadge
order by NumberOfBadges desc
LIMIT 2000;