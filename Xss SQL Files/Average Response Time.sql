select x2.owneruserid, avg(timestampdiff(MINUTE, x1.creationdate, x2.creationdate)), sum(timestampdiff(MINUTE, x1.creationdate, x2.creationdate)), count(x2.id) 
#TIMESTAMPDIFF Returns EXPR2-EXPR1 AnswerCreationTime-QuestionCreationTime
from XSSQuestions x1, XSSAnswers x2
where x2.parentId = x1.id #All Answers of that question
and x1.id IN (select id from XSSQuestions) #All Questions One At A Time
group by x2.OwnerUserId
order by avg(timestampdiff(MINUTE, x1.creationdate, x2.creationdate)) asc;