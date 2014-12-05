create table JavaMaxUpvote
(
owneruserid int primary key,
NumberOfMaxUpAns int
);

LOAD DATA LOCAL INFILE 'C://Users//AnkitJ//Desktop//JavaCSV//UserMaxTwoUpVotedAnswers.csv' INTO TABLE JavaMaxUpvote FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n';

set SQL_SAFE_UPDATES = 0;

delete from JavaMaxUpvote;

select * from JavaMaxUpvote
order by NumberOfMaxUpAns desc
LIMIT 2000;