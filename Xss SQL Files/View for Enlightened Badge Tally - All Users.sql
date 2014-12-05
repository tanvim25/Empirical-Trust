create or replace view EnlightenedABadgeXSS
as
(select x2.OwnerUserId, count(x2.id) as no_badges
from XSSQuestions x1, XSSAnswers x2 
where x2.parentid = x1.id
and x2.score >= 10 #Score of the Answer should be greater than 10
and x2.Id = x1.acceptedanswerid #This answer should be accepted
and x2.CreationDate = (select min(x5.CreationDate) from XSSAnswers x5 where x5.ParentId = x1.id)  #First Person To Answer a Question
and x2.owneruserid <> x1.owneruserid  #Should Not Be Accepted By Self
group by x2.OwnerUserId
order by count(x2.id) desc)

UNION

(select distinct x2.owneruserid, 0 as no_badges
from XSSQuestions x1, XSSAnswers x2
where x2.parentid = x1.id
and x2.owneruserid not in 
(
select x2.OwnerUserId
from XSSQuestions x1, XSSAnswers x2 
where x2.parentid = x1.id
and x2.score >= 10 #Score of the Answer should be greater than 10
and x2.Id = x1.acceptedanswerid #This answer should be accepted
and x2.CreationDate = (select min(x5.CreationDate) from XSSAnswers x5 where x5.ParentId = x1.id)  #First Person To Answer a Question
and x2.owneruserid <> x1.owneruserid  #Should Not Be Accepted By Self
group by x2.OwnerUserId
)
order by no_badges desc)