create table JavaResponseTime
(
owneruserid int primary key,
AvgRespTime double,
NumberOfAns int
);

LOAD DATA LOCAL INFILE 'C://Users//AnkitJ//Desktop//JavaCSV//UserAverageResponseTime.csv' INTO TABLE JavaResponseTime FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n';

SET SQL_SAFE_UPDATES = 1;

commit;

select * from JavaResponseTime
order by AvgRespTime asc
LIMIT 3000;