select OwnerUserId, count(id) #Number of Answers Given
from XSSAnswers
where parentid IN (Select id from XSSQuestions) #All Answers
group by OwnerUserId
order by count(id) desc
LIMIT 10000