create table AllBadgeInfoXSS
(select x2.OwnerUserId, count(x2.id) as NoOfBadges
from XSSQuestions x1, XSSAnswers x2 
where x2.parentid = x1.id
and x2.score >= 10 #Score of the Answer should be greater than 10
and x2.Id = x1.acceptedanswerid #This answer should be accepted
and x2.CreationDate = (select min(x5.CreationDate) from XSSAnswers x5 where x5.ParentId = x1.id)  #First Person To Answer a Question
and x2.owneruserid <> x1.owneruserid  #Should Not Be Accepted By Self
group by x2.OwnerUserId
order by NoOfBadges desc);

insert into AllBadgeInfoXSS
(select x2.owneruserid, count(x2.id) as NoOfBadges
from XSSQuestions x1, XSSAnswers x2
where x2.parentid = x1.id
and x2.score >=10
group by x2.OwnerUserId
order by NoOfBadges desc);

insert into AllBadgeInfoXSS
(select x2.owneruserid, count(x2.id) as NoOfBadges
from XSSQuestions x1, XSSAnswers x2
where x2.parentid = x1.id
and x2.score >=25
group by x2.OwnerUserId
order by NoOfBadges desc);

insert into AllBadgeInfoXSS
(select x2.owneruserid, count(x2.id) as NoOfBadges
from XSSQuestions x1, XSSAnswers x2
where x2.parentid = x1.id
and x2.score >=100
group by x2.OwnerUserId
order by NoOfBadges desc);

select OwnerUserId, sum(NoOfBadges) as TotalNoOfBadges
from AllBadgeInfoXSS
group by OwnerUserId
order by TotalNoOfBadges desc
LIMIT 3000;

commit;