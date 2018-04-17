/* Needed Variables Declaration */
var month1 = [6, 7,8,5,1,10,1,2,4];
var month2 = [10, 5,1,5,5,10,4];
var month3 = [7,8,9 ,8.2,10,6];
var month4 = [2, 2,2,8,8,20,7];
var month5 = [10,2,1,8,7,20,3];

var arrayOfMonths = [month1, month2, month3, month4,month5]; 
var arrayOfMonths_TEXT = ["Mar 2018","Apr 2018","Jun 2018","Jul 2018","Aug 2018"];
var arrayOfLegends = ["Paragraphs are the building blocks of papers. Many students define paragraphs in terms of length: a paragraph is a group of at least five sentences, a paragraph is half a page long, etc. In reality, though, the unity and coherence of ideas among sente 255",
					  "Paragraphs are the building blocks of papers. Many students define paragraphs in terms of length: a paragraph is a group of at least five sentences, a paragraph is half a page long, etc. In reality, though, the unity and coherence of ideas among sente 255",
					  "Paragraphs are the building blocks of papers. Many students define paragraphs in terms of length: a paragraph is a group of at least five sentences, a paragraph is half a page long, etc. In reality, though, the unity and coherence of ideas among sente 255",
					  "Paragraphs are the building blocks of papers. Many students define paragraphs in terms of length: a paragraph is a group of at least five sentences, a paragraph is half a page long, etc. In reality, though, the unity and coherence of ideas among sente 255",
				      "Paragraphs are the building blocks of papers. Many students define paragraphs in terms of length: a paragraph is a group of at least five sentences, a paragraph is half a page long, etc. In reality, though, the unity and coherence of ideas among sente 255",
					  "Paragraphs are the building blocks of papers. Many students define paragraphs in terms of length: a paragraph is a group of at least five sentences, a paragraph is half a page long, etc. In reality, though, the unity and coherence of ideas among sente 255"
					 ,"Text","Text","TEXT"];

/* Create an array of Colors (RGB ColorMode) */
var arrayOfColors = [ 
  			[ 68,114,196],[237,125, 49],[165,165,165],[255,192,  0],[ 91,155,213],//5
			[128,129, 42],[170,  4, 90],[252,178,128],[ 70, 52,105],[190,190,190],//10
			[169,194,135],[161,111, 41],[133, 35, 96],[140,155,204],[ 81,120, 94],//15
            [ 68,114,196],[237,125, 49],[165,165,165],[255,192,  0],[ 91,155,213],//20
			[128,129,42],[170,   4, 90],[252,178,128],[ 70, 52,105],[190,190,190],//25
			[169,194,135],[161,111, 41],[133, 35, 96],[140,155,204],[ 81,120, 94]//35 Colors
        ];//End of Array of Colors

/* Setup Canvas Size */
var extraPixels = 100; //Extra space 75 pixels
// 200 pixels per month (Width) + some extra space 
var canvasWidth = (arrayOfMonths.length * 150) + (extraPixels-25); 
// 11 pixels per Respontant (Height) + some extra space
var canvasHeight = (findMaxNumOfResondents() * 12) + extraPixels; 

function setup() {

	createCanvas(canvasWidth+400, canvasHeight);
	background(200); /* !!!  (REMOVE THIS TO MAKE BACKGROUND TRANSPARENT)  !!! */

	chartBase(); //Add X,Y Axis Lines


	addValues(); //Add the Bars (Values) of the Chart

	addLegends(); //Add Lengend Information Bullets
}

function findMaxNumOfResondents() {

	var myMax = 0; //Number to be returned

	for (var x=0; x<arrayOfMonths.length; x++) {
		var currentValue = 0; //Create a current value

		for (var y=0; y<arrayOfMonths[x].length; y++) {
			currentValue += arrayOfMonths[x][y]; //save current value of every possible index in the 2D Array
		}
		if(currentValue > myMax) { //If current value is bigger than previous
			myMax = currentValue; //Save Value
		}
	}
	return myMax; //Return maximum number of respondent
}


function chartBase() {

	/* DRAW Y AXIS LINE (VERTICAL) */
	line(50, 25, 50, canvasHeight-110);

	/* DRAW X AXIS LINE (HORIZONTAL) */
	line(10, canvasHeight - 150, canvasWidth - 50, canvasHeight - 150);

	/* DRAW LINE SEPERATORS & VALUES ON Y AXIS */
	var max = findMaxNumOfResondents(); //Maximum number of Respondents
	var countLineSeperators = max/10; //Maximum lines devided by 10
	var fixedNumOfLines = countLineSeperators.toFixed(0); //Fix number (remove decimal numbers)
	var jump = 150; //Space between line seperators
	var myText = 0; //Number to be display

	/* For a calculated number of lines */
	for(var i=0; i<=fixedNumOfLines; i++){

		stroke(230); //Change the stroke of the line
		jump += 80; // Change location every loop vertically
		myText += 10; //Add value to the number to be displayed

		//Draw Line Seperator
		line(50, canvasHeight - jump, canvasWidth - 50, canvasHeight - jump);
		//Display number per line seperator
		text(myText, 30, (canvasHeight - jump) + 5); 
	}

	noStroke(); //Reset Stroke
	jump = 115; //Reset space between elements

	/* DRAW VALUES OF X AXIS */
	for(var y=0; y<arrayOfMonths_TEXT.length; y++){
		text(arrayOfMonths_TEXT[y], jump, canvasHeight-130);
		jump+=135; // Change location every loop horizontally
	}
}

function addValues() {


	var locX = 110; //Location of rectangle in X Axis
	var locY; //Location of rectangle in Y Axis
	var barWidth = 60; //Size of rectangle (each element) in width
	var barHeight; //Size of rectangle (each element) in height

	for (var x = 0; x < arrayOfMonths.length; x++) {

		/* Draw Stack Bars (rectangles) */
		for (var y = 0; y < arrayOfMonths[x].length; y++) {

			barHeight = arrayOfMonths[x][y] * 8;      

			if (y == 0) {
				locY = canvasHeight - barHeight - 150;
			} 
			else {
				locY -= barHeight;
			}

			/* ADD RECTANGLES TO Y AXIS */
			fill(arrayOfColors[y],arrayOfColors[y],arrayOfColors[y]);
			rect(locX, locY, barWidth, barHeight);

			/* ADD TEXT TO X AXIS */
			fill(255, 255, 255); //White Color For Text
			text(arrayOfMonths[x][y], locX + 25, locY + 15);
		}
		locX += 135;
	}
}

function addLegends(){

	var locX = canvasWidth+20; //Start location of the Legend in X Axis 
	var locY = canvasHeight-100; //Start location of the Legend in Y Axis
	var sizeX = 15; //Size of Legend in width
	var sizeY = 15; //Size of Legend in height

	for(var x=0; x<arrayOfLegends.length; x++){

		fill(arrayOfColors[x]); //BLACK
		rect(locX,locY,sizeX,sizeY);
		
		fill(0);
		text(arrayOfLegends[x], locX+20, locY,300, 150);
		

		locY -= 100;
		
	}
}


