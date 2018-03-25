var answerCounter = 0;
var questionCounter = 0;
var questionsArray = new Array;

function createAnswer(question)
{	
	var elementID = "dropDown" + question;
	var answerType = document.getElementById(elementID).value;
	
	
	switch(answerType) 
	{
		case 'ScoreRange':
                //delete all previous answer fields
			//console.log("Score Range Selected");					
			break;
		case 'RadioButtons':
                //create new answer field
			//console.log("Radio Buttons Selected");
			var title = document.createTextNode("Answer: ");	
			var ans = document.createElement("input");
			ans.setAttribute('type',"text");
			ans.setAttribute('name', "answer"+question);	
			ans.setAttribute('id', "ans"+question);
                        ans.setAttribute("class","textbox");
			var div = document.getElementById("div"+question);
                        
                        var br = document.createElement("br");          
                       
                        div.appendChild(br);
			div.appendChild(title);
			div.appendChild(ans);
                        
			break;
		/*case 'MultipleChoice':
                
			//console.log("Multiple Choice Selected");
			var title = document.createTextNode("Answer: ");	
			var ans = document.createElement("input");
			ans.setAttribute('type',"text");
			ans.setAttribute('name', "answer");	
			var div = document.getElementById("div"+question);	
			div.appendChild(title);
			div.appendChild(ans);
			break;*/
		case 'OpenText':
                //delete all answer fields
			//console.log("Open Text Selected");
			/*var title = document.createTextNode("Limit: ");	
			var ans = document.createElement("input");
			ans.setAttribute('type',"text");
			ans.setAttribute('name', "answer");
			var div = document.getElementById("div"+question);			
			div.appendChild(title);
			div.appendChild(ans);*/
			break;		
	}	
	answerCounter++;	
}

function createQuestionObj(question)
{
	var questionText = document.getElementById("question"+question).value;
	//console.log(questionText);	
	
	var answerType = document.getElementById("dropDown" + question).value;	
	//console.log(answerType);
	
	var required;
	var value = document.getElementById("required" + question).value;	
	if (value == 'yes')
	{
		var required = true;		
	}
	//console.log("Required :" + required);
	
	var answers = document.getElementsByName("answer"+question);
	var ansValue = new Array;
	for(i = 0; i < answers.length; i++)
	{
		ansValue[i] = answers[i].value;
	}
	
	var Question = {question: questionText,answers: ansValue, required: required, type: answerType};
	
	questionsArray[question] = Question;
	
	/*for(i = 0; i < questionsArray.length; i++)
	{
		console.log(questionsArray[i].question);
	}*/
}

function constructString()
{
	for(m = 0; m < questionCounter; m++)
	{
		createQuestionObj(m);
		console.log('Iteration:' + m);
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
				temp += '\t\t\t\t\t<answerText>' + questionsArray[i].answers[j] + '</answerText>\n';
			}
		}
		temp += '\t\t\t\t</answers>\n';		
		temp += '\t\t\t\t<required>' + questionsArray[i].required + '</required>\n';		
		temp += '\t\t\t</question>\n';
	}
	temp += '\t\t</questions>\n';
	temp += '</survey>';
	console.log(temp);

        var textField = document.getElementById("xmlForm");
        textField.value = temp;
}

function createQuestion()
{
	var newQ ='<br><form> Q'+(questionCounter+1)+': <input id="question'+questionCounter+'"type="text" name="questionText" class="textbox">';
		newQ +='<select id="dropDown'+questionCounter+'" name="qType">';
		newQ +='<option value="RadioButtons">Radio Button</option>';
		//newQ +='<option value="MultipleChoice">Multiple Choice</option>';
		newQ +='<option value="ScoreRange">Score Range</option>';
		newQ +='<option value="OpenText">Open Text</option>';
		newQ +='</select><select id="required'+questionCounter+'" name="Required">';
		newQ +='<option value="yes">Yes</option>';
		newQ +='<option value="no">No</option>'	;		
		newQ +='</select></form>';
		newQ +='<button onclick="createAnswer('+questionCounter+')" type="button" name="createQ">Create Answer</button>';
		//newQ +='<button onclick="createQuestionObj('+questionCounter+')" type="button" name="saveQuestion">Save Question</button>'
		
		//var newDiv ='<div id="div'+(questionCounter + 1)+'"></div>'
		var newDivE = document.createElement("div");
		newDivE.setAttribute('id', "div"+(questionCounter + 1));
                newDivE.setAttribute("class","questions");
                newDivE.setAttribute("style","display:none");
		
                //insert html into empty div
                var nextDiv = document.getElementById("div" + questionCounter)
		nextDiv.innerHTML = newQ;
                nextDiv.removeAttribute("style");
		var body = document.getElementsByTagName("body")[0];
		body.appendChild(newDivE);
		
		questionCounter++;
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







	