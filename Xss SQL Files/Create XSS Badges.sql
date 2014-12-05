create table XSSBadges
(
userId int,
badgename varchar(100),
countOfBadges int,
primary key (userId, badgename)
);