create table JavaNumberOfAnswers
(
owneruserid int primary key,
NumberOfAns int
);

LOAD DATA LOCAL INFILE 'C://Users//AnkitJ//Desktop//JavaCSV//UserNumberOfAnswers.csv' INTO TABLE JavaNumberOfAnswers FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n';

SET SQL_SAFE_UPDATES = 0;

commit;

select * from JavaNumberOfAnswers
order by NumberOfAns desc
LIMIT 3000;