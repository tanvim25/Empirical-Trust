create table JavaAcceptedAnswers
(
owneruserid int primary key,
NoOfAcAns int
);

LOAD DATA LOCAL INFILE 'C://Users//AnkitJ//Desktop//JavaCSV//UserAcceptedAnswers.csv' INTO TABLE JavaAcceptedAnswers FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n';

select * from JavaAcceptedAnswers
order by NoOfAcAns desc;