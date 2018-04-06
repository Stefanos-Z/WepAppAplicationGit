var answerCounter = 0;
var questionCounter;
var questionsArray = new Array;

function onLoadInstantiate()
{
    questionCounter = $('#questionCount').val();
    console.log(questionCounter);
}


function createAnswer(question)
{	
	var elementID = "dropDown" + question;
	var answerType = document.getElementById(elementID).value;
	
	switch(answerType)
	{
		case 'ScoreRange':
                //delete all previous answer fields
							
			break;
		case 'RadioButtons':
                //create new answer field
			//console.log("Radio Buttons Selected");
                        var aDiv = document.createElement("div")
                        aDiv.setAttribute('id', "ansDiv"+answerCounter);
			var title = document.createTextNode("Answer: ");	
			var ans = document.createElement("input");
			ans.setAttribute('type',"text");
			ans.setAttribute('name', "answer"+question);	
			ans.setAttribute('id', "ans"+question);
                        ans.setAttribute("class","textbox");
			var div = document.getElementById("div"+question);                        
                        var aSpan = document.createElement("span");
                        aSpan.setAttribute('onClick', "deleteAnswer("+answerCounter+")");                        
                        aSpan.setAttribute('id', "deleteIcon");
                        aSpan.innerHTML = "   ×";                        
			aDiv.appendChild(title);
			aDiv.appendChild(ans);
                        aDiv.appendChild(aSpan);
                        div.appendChild(aDiv);
                        
                        answerCounter++;
                        
			break;
		
		case 'OpenText':
                //delete all answer fields
			
			break;		
	}	
	answerCounter++;	
}

function createQuestionObj(question)
{
    try{
	var questionText = document.getElementById("question"+question).value;
        console.log(questionText);
	var answerType = document.getElementById("dropDown" + question).value;	
	console.log(answerType);	
	var required;
	/*var value = document.getElementById("required" + question).value;	
	if (value == 'yes')
	{
		var required = true;		
	}
        console.log(value);*/
        var required = true;
	var answers = document.getElementsByName("answer"+question);
	var ansValue = new Array;
	for(i = 0; i < answers.length; i++)
	{
		ansValue[i] = answers[i].value;
	}
	
	var Question = {question: questionText,answers: ansValue, required: required, type: answerType};
	
	questionsArray.push(Question);  
        console.log("question added");
    }catch(e)
    {
        console.log(e.message);
    }	
}

function constructString()
{
    var allQuestionsFilled = checkQuestionText();
    if(!allQuestionsFilled)
    {        
        return false;
    }
	for(m = 0; m < questionCounter; m++)
	{
            console.log("calling question builder");
            createQuestionObj(m);		
	}
	var xmlS;
	var questionS;
	var temp = '';
	var surveyName = document.getElementById("surveyName").value;
	
	temp += '<?xml version="1.0"?>\n'; 
	temp += '<survey>\n';
	temp += '\t<surveyName>' + surveyName + '</surveyName>\n';
	temp += '\t<userId></userId>\n';
	temp += '\t\t<questions>\n';
	for(i = 0; i < questionsArray.length; i++)
	{            
		temp += '\t\t\t<question>\n';
		temp += '\t\t\t\t<questionText>' + questionsArray[i].question +'</questionText>\n';
		temp += '\t\t\t\t<questionType>' + questionsArray[i].type + '</questionType>\n';
		temp += '\t\t\t\t<answers>\n';
		if(questionsArray[i].type == 'OpenText')
		{
			//no answers for open text
		}
		else{
			for(j = 0; j < questionsArray[i].answers.length; j++)
			{
                            if(questionsArray[i].answers[j] !== "")
                            {
				temp += '\t\t\t\t\t<answerText>' + questionsArray[i].answers[j] + '</answerText>\n';
                            }
                            else
                            {
                                console.log("Empty Answer Field");
                            }
			}
		}
		temp += '\t\t\t\t</answers>\n';		
		temp += '\t\t\t\t<required>' + questionsArray[i].required + '</required>\n';		
		temp += '\t\t\t</question>\n';
	}
	temp += '\t\t</questions>\n';
	temp += '</survey>';	

        var textField = document.getElementById("xmlForm");
        textField.value = temp;
        console.log(temp);
        
        return true;  //Placeholder- can be used to prevent bad xml from being submitted.
}



function createQuestion()
{
	var newQ ='<br><form><input id="question'+questionCounter+'"type="text" name="questionText" class="questiontext">';
		newQ +='<select id="dropDown'+questionCounter+'" onchange="removeAllAnswers('+questionCounter+')" name="qType">';
		newQ +='<option value="RadioButtons">Radio Button</option>';
		//newQ +='<option value="MultipleChoice">Multiple Choice</option>';
		newQ +='<option value="ScoreRange">Score Range</option>';
		newQ +='<option value="OpenText">Open Text</option>';
		/*newQ +='</select><select id="required'+questionCounter+'" name="Required">';
		newQ +='<option value="yes">Yes</option>';
		newQ +='<option value="no">No</option>'	;	*/	
		newQ +='</select>';
                newQ +='<button onclick="deleteQuestion('+questionCounter+')" type="button" name="deleteQ">Delete Question</button>';
                newQ +='</form>';
		newQ +='<button id="createAnswer'+questionCounter+'" onclick="createAnswer('+questionCounter+')" type="button" name="createQ">Create Answer</button>';
		//newQ +='<button onclick="createQuestionObj('+questionCounter+')" type="button" name="saveQuestion">Save Question</button>'
		
		//var newDiv ='<div id="div'+(questionCounter + 1)+'"></div>'
		var newDivE = document.createElement("div");
		newDivE.setAttribute('id', "div"+(parseInt(questionCounter) + 1));
                newDivE.setAttribute("class","questions");
                newDivE.setAttribute("style","display:none");
		
                //insert html into empty div
                var nextDiv = document.getElementById("div" + questionCounter);
		nextDiv.innerHTML = newQ;
                nextDiv.removeAttribute("style");
		var body = document.getElementsByTagName("body")[0];
		body.appendChild(newDivE);
		
		questionCounter++;
                
                window.scrollTo(0,document.body.scrollHeight);
}

function deleteQuestion(questionNum)
{
    var questionDiv = document.getElementById("div" + questionNum);
    questionDiv.parentNode.removeChild(questionDiv);
}

function deleteAnswer(ansNum)
{
    var aDiv = document.getElementById("ansDiv"+ ansNum);
    aDiv.parentNode.removeChild(aDiv);
}

class Question
{	
	
	constructor(question, answers, required, type)
	{
		this.question = question;
		for(i = 0; i < answers.length; i++)
		{
			this.answers[i] = answers[i];
		}
		this.required = required;
		this.type = type;
	}
	
	get question()
	{
		return this.question;
	}
	
	get answers()
	{
		return this.answers;
	}
	
	get required()
	{
		return this.required;
	}
	
	get type()
	{
		return this.type;
	}
	
}

//jquery function to remove text from textarea
$(document).ready(function() {
    
    //code to remove the row from a table
    $("textarea").click(function() {
        (this).innerHTML = "";
    });
});

function checkQuestionText()
{    
    var toReturn = true;
    var questionsFiled = true;
    var surveyFiled = true;
    if(questionCounter === "0" || questionCounter === 0)
    {
        alert("Must have at least one question!");
        return false;
    }
    for(i = 0; i < questionCounter; i++)
    {
        try
        {
            var questionText = document.getElementById("question"+i).value;
            var surveyText = document.getElementById("surveyName").value;
            if(questionText === "")
            {
                //document.getElementById("question"+i).style.borderColor = "red";
                document.getElementById("question"+i).classList.add("invalidInput");
                questionsFiled = false;
                toReturn = false;
            }
            else
            {
                document.getElementById("question"+i).classList.remove("invalidInput");
                //document.getElementById("question"+i).style.borderColor = "black";
            }
            //send alert telling user of missed fields
           
        }catch(err){
            console.log("no question");
        }
    }
    if(surveyText === "")
    {
        document.getElementById("surveyName").style.borderColor = "red";
        surveyFiled = false;
        toReturn = false;
    }
    else
    {
        document.getElementById("surveyName").style.borderColor = "black";
    }
    
    if(!surveyFiled && !questionsFiled)
    {
        alert("Not all question fields completed!\nSurvey needs a name!");
    }
    else if(!surveyFiled)
    {
        alert("Survey needs a name!");
    }
    else if(!questionsFiled)
    {
        alert("Not all question fields completed!")
    }
    console.log("check valid input: " + toReturn);
    return toReturn;    
}

function removeAllAnswers(questionNum) {
    
    var elementVal = document.getElementsByName("answer"+questionNum);
    console.log("answer :" + elementVal.length);
    if(elementVal.length >= 1)
    {
        if(confirm("Delete All Answers?")) 
        {                   
            var answers = document.getElementsByName("answer"+questionNum);
            console.log(answers.length);
            for(i = answers.length - 1; i >= 0; i--)
            {                
                var element = answers[i].parentNode;
                element.parentNode.removeChild(element);            
            }
        }
        else
        {   
            var element = document.getElementById("dropDown"+questionNum);
            element.value = "RadioButtons";
        }
    //delete all the answer fields from options
    }
    var element = document.getElementById("dropDown"+questionNum);
            
    if(element.value !== "RadioButtons")
    {
        document.getElementById('createAnswer'+questionNum).style.visibility = 'hidden';
    }
    else
    {
        document.getElementById('createAnswer'+questionNum).style.visibility = 'visible';
    }
}
