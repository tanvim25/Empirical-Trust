select owneruserid, sum(NumberOfBadges) as TotalNumberOfBadges
from JavaTotalBadges
group by owneruserid
order by TotalNumberOfBadges desc
LIMIT 25000;