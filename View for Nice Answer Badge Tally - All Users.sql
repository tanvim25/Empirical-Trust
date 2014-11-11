create or replace view NiceABadgeXSS
as
(select x2.owneruserid, count(x2.id) as no_badges
from XSSQuestions x1, XSSAnswers x2
where x2.parentid = x1.id
and x2.score >=10
group by x2.OwnerUserId)

UNION

(select distinct x2.owneruserid, 0 as no_badges
from XSSQuestions x1, XSSAnswers x2
where x2.parentid = x1.id
and x2.owneruserid not in 
(
select x2.owneruserid
from XSSQuestions x1, XSSAnswers x2
where x2.parentid = x1.id
and x2.score >=10
group by x2.OwnerUserId
)
order by no_badges desc)