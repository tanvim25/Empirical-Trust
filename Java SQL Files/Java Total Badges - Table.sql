create table JavaTotalBadges
(
owneruserid int,
NumberOfBadges int
);

insert into JavaTotalBadges
(
select owneruserid, NumberOfBadges from JavaEnlightenedBadge
);

insert into JavaTotalBadges
(
select owneruserid, NumberOfBadges from JavaNiceAnswerBadge
);

insert into JavaTotalBadges
(
select owneruserid, NumberOfBadges from JavaGoodAnswerBadge
);

insert into JavaTotalBadges
(
select owneruserid, NumberOfBadges from JavaGreatAnswerBadge
);

select * from JavaTotalBadges
LIMIT 25000;