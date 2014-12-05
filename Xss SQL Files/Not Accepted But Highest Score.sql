select x2.OwnerUserId, count(x2.Id)
from XSSQuestions x1, XSSAnswers x2
where x2.parentid = x1.id #All Answers for that question
and x1.id IN (select id from XSSQuestions) #At One Time One Particular Question
and x2.score = (select max(x3.score) from XSSAnswers x3 where x3.parentid=x1.id) #Answers for that question having the max score
and (x2.id <> x1.AcceptedAnswerId or x1.AcceptedAnswerId is null)#Answers for that question having the max score but not accepted.
group by x2.owneruserid
order by count(x2.id) desc