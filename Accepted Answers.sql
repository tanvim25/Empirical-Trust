select OwnerUserId, count(id)
from XSSAnswers
where id IN (select AcceptedAnswerId from XSSQuestions)
group by OwnerUserId
order by count(id) desc