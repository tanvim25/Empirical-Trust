create table JavaMaxUpvoteNoAccept
(
owneruserid int primary key,
NumberOfMaxUpAnsNoAc int
);

LOAD DATA LOCAL INFILE 'C://Users//AnkitJ//Desktop//JavaCSV//UserMaxTwoUpVotedUnAc.csv' INTO TABLE JavaMaxUpvoteNoAccept FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n';

set SQL_SAFE_UPDATES = 0;

delete from JavaMaxUpvoteNoAccept;

select * from JavaMaxUpvoteNoAccept
order by NumberOfMaxUpAnsNoAc desc
LIMIT 2000;