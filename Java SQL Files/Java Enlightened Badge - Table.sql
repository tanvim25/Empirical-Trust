create table JavaEnlightenedBadge
(
owneruserid int primary key,
NumberOfBadges int
);

LOAD DATA LOCAL INFILE 'C://Users//AnkitJ//Desktop//JavaCSV//UserEnlightenedBadgeTemp.csv' INTO TABLE JavaEnlightenedBadge FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n';

set SQL_SAFE_UPDATES = 0;

delete from JavaEnlightenedBadge;

select * from JavaEnlightenedBadge
order by NumberOfBadges desc
LIMIT 5000;