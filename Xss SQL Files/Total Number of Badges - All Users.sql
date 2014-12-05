create table AllBadgeInfoXSS
(select * from NiceABadgeXSS);

insert into AllBadgeInfoXSS
(select * from GoodABadgeXSS);

insert into AllBadgeInfoXSS
(select * from GreatABadgeXSS);

insert into AllBadgeInfoXSS
(select * from EnlightenedABadgeXSS);

select OwnerUserId, sum(no_badges)
from AllBadgeInfoXSS
group by OwnerUserId
order by sum(no_badges) desc
LIMIT 3000