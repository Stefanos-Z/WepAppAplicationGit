/*JavaScript for creating and editing surveys,
Author: Dallos
Creation Date: 13/02/18
*/


var answerCounter = 0;
var questionCounter;
var questionsArray = new Array;

function onLoadInstantiate()
{
    questionCounter = $('#questionCount').val();
    console.log(questionCounter);
}

/**
 * creates the answer field for a question
 * @param {type} question
 * @returns {undefined}
 */
function createAnswer(question)
{	
    var elementID = "dropDown" + question;
    var answerType = document.getElementById(elementID).value;

    switch(answerType)
    {
        case 'CheckBox':
            //delete all previous answer fields
            var aDiv = document.createElement("div")
            aDiv.setAttribute('id', "ansDiv"+answerCounter);
            var title = document.createTextNode("Answer: ");	
            var ans = document.createElement("input");
            ans.setAttribute('type',"text");
            ans.setAttribute('name', "answer"+question);	
            ans.setAttribute('id', "ans"+question);
            ans.setAttribute("class","textbox");
            ans.setAttribute("placeholder", "Enter Answer");
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
            ans.setAttribute("placeholder", "Enter Answer");
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
    }	
    answerCounter++;	
}

/**
 * creates a javascript object that can be used when generating the xml
 * @param {type} question
 * @returns {undefined}
 */
function createQuestionObj(question)
{
    try{
	var questionText = document.getElementById("question"+question).value;        
	var answerType = document.getElementById("dropDown" + question).value;      
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

/**
 * constructs the xml string
 * @returns {Boolean}
 */
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


/**
 * creates the question box that can be filled by the user
 * @returns {undefined}
 */
function createQuestion()
{
    var newQ ='<br><form><input id="question'+questionCounter+'"type="text" name="questionText" class="questiontext" placeholder="Enter Question Here">';
    newQ +='<select id="dropDown'+questionCounter+'" onchange="removeAllAnswers('+questionCounter+')" name="qType">';
    newQ +='<option value="RadioButtons">Radio Button</option>';
    newQ +='<option value="ScoreRange">Score Range</option>';
    newQ +='<option value="CheckBox">Check Box</option>';
    newQ +='<option value="OpenText">Open Text</option>';			
    newQ +='</select>';
    newQ +='<button onclick="deleteQuestion('+questionCounter+')" type="button" name="deleteQ">Delete Question</button>';
    newQ +='</form>';
    newQ +='<button id="createAnswer'+questionCounter+'" onclick="createAnswer('+questionCounter+')" type="button" name="createQ">Create Answer</button>';

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

    $('html, body').animate({
        scrollTop: $("#div"+questionCounter).offset().top
     }, 750);

    questionCounter++;
}

/**
 * removes a question from the page
 * @param {type} questionNum
 * @returns {undefined}
 */
function deleteQuestion(questionNum)
{
    var questionDiv = document.getElementById("div" + questionNum);
    questionDiv.parentNode.removeChild(questionDiv);
}

/**
 * removes an answer from a uestion
 * @param {type} ansNum
 * @returns {undefined}
 */
function deleteAnswer(ansNum)
{
    var aDiv = document.getElementById("ansDiv"+ ansNum);
    aDiv.parentNode.removeChild(aDiv);
}

/**
 * Defines a Question object
 * @type type
 */
class Question
{		
    /**
     * constructor for class
     * @param {type} question
     * @param {type} answers
     * @param {type} required
     * @param {type} type
     * @returns {Question}
     */    
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
    
    /**
     * getter for question
     * @type type
     */
    get question()
    {
        return this.question;
    }
    
    /**
     * getter for answers
     * @type type
     */
    get answers()
    {
        return this.answers;
    }
    
    /**
     * getter for required
     * @type type
     */
    get required()
    {
        return this.required;
    }
    
    /**
     * getter for type
     * @type type
     */
    get type()
    {
        return this.type;
    }	
}

/**
 * checks that all questions field have been completed
 * @returns {Boolean}
 */
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

/**
 * removes all answer fields of a given question
 * @param {type} questionNum
 * @returns {undefined}
 */
function removeAllAnswers(questionNum) {
    
    var elementVal = document.getElementsByName("answer"+questionNum);
    var element = document.getElementById("dropDown"+questionNum);
    console.log("answer :" + elementVal.length);
    if(elementVal.length >= 1)
    {
        //if the question type is switched between radio and check keep answers there
        if(element.value === "RadioButtons" || element.value === "CheckBox")
        {
            document.getElementById('createAnswer'+questionNum).style.visibility = 'visible';
        }
    
        else
        {
            document.getElementById('createAnswer'+questionNum).style.visibility = 'hidden';

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
        }    
    }
    
    if(element.value === "RadioButtons" || element.value === "CheckBox")
    {
        document.getElementById('createAnswer'+questionNum).style.visibility = 'visible';
    }
    else if(element.value === "OpenText" || element.value === "ScoreRange")
    {
        document.getElementById('createAnswer'+questionNum).style.visibility = 'hidden';
    }
}

