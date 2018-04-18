/* Needed Variables Declaration */
var month1 = [10, 0, 0, 5, 1, 10, 1];
var month2 = [10, 5, 1, 5, 5, 10, 4];
var month3 = [7, 8, 9, 8.2, 10, 6];
var month4 = [2, 2, 2, 8, 8, 7];
var month5 = [10, 2, 1, 8, 7, 20, 3];
var month6 = [1, 4, 7, 25, 8];
var month7 = [9, 20, 18, 1];
var month8 = [3, 2, 1, 6, 8];
var month9 = [8, 1, 21, 1];
var month10 = [10, 9, 1, 11, 2];
var month11 = [11, 2, 8, 31];
var month12 = [10, 5, 20, 30, 5,10];
var arrayOfMonths = [month1, month2, month3, month4, month5, month6, month7, month8, month9, month10, month11, month12];

var arrayOfMonths_TEXT = ["Jan 2018", "Feb 2018", "Mar 2018", "Apr 2018", "May 2018", "Jun 2018", "Jul 2018", "Aug 2018", "Sep 2018", "Oct 2018", "Nov 2018", "Dec 2018"];
var arrayOfLegends = [
  "his is a free online calculator which counts the number of characters or letters in a text, useful for your tweets on Twitter, as well as a multitude of other applications.his is a free online calculator which counts the number of characters or letter 255",
  "his is a free online calculator which counts the number of characters or letters in a text, useful for your tweets on Twitter, as well as a multitude of other applications.his is a free online calculator which counts the number of characters or letter 255",
  "Are you happy with your manager?",
  "his is a free online calculator which counts the number of characters or letters in a text, useful for your tweets on Twitter, as well as a multitude of other applications.his is a free online calculator which counts the number of characters or letter 255",
  "his is a free online calculator which counts the number of characters or letters in a text, useful for your tweets on Twitter, as well as a multitude of other applications.his is a free online calculator which counts the number of characters or letter 255",
  "Are you happy with your manager?",
  "Are you happy with your coworkers?",
  "Are you happy with your admin service?",
  "1","2","3","4","5","6","7","8","9"
];

/* Create an array of Colors (RGB ColorMode) */
var arrayOfColors = [
  [68, 114, 196],  [237, 125, 49], [165, 165, 165],  [255, 192, 0],  [91, 155, 213], //5
  [128, 129, 42],  [170, 4, 90],  [252, 178, 128],  [70, 52, 105], [190, 190, 190], //10
  [169, 194, 135], [161, 111, 41], [133, 35, 96],  [140, 155, 204], [81, 120, 94], //15
  [68, 114, 196],  [237, 125, 49], [165, 165, 165],  [255, 192, 0], [91, 155, 213], //20
  [128, 129, 42],  [170, 4, 90], [252, 178, 128], [70, 52, 105], [190, 190, 190], //25
  [169, 194, 135], [161, 111, 41], [133, 35, 96],  [140, 155, 204], [81, 120, 94] //35 Colors
]; //End of Array of Colors

/* Setup Canvas Size */
var legendsWidth = 450; //Extra space 75 pixels
// 200 pixels per month (Width) + some extra space 
var canvasWidth = (arrayOfMonths.length * 90) + (legendsWidth);
var canvasHeight; //Depents on the Chart/Legends Height
// 11 pixels per Respontant (Height) + some extra space
var chartHeight = (findMaxNumOfResondents() * 11);
var canvasHeight_Legends = ((arrayOfLegends.length*75)+20);

function setup() {

    if(chartHeight>canvasHeight_Legends){
      canvasHeight = chartHeight;
    }
    else{
      canvasHeight = canvasHeight_Legends;
    }
    
    /* Create Canvas to Draw the Chart */
    createCanvas(canvasWidth,canvasHeight);
    background(200); /* !!!  (REMOVE THIS TO MAKE BACKGROUND TRANSPARENT)  !!! */

    chartBase(); //Add X,Y Axis Lines

    addValues(); //Add the Bars (Values) of the Chart

    addLegends(); //Add Lengend Information Bullets

}

function findMaxNumOfResondents() {

  var myMax = 0; //Number to be returned

  for (var x = 0; x < arrayOfMonths.length; x++) {
    var currentValue = 0; //Create a current value

    for (var y = 0; y < arrayOfMonths[x].length; y++) {
      currentValue += arrayOfMonths[x][y]; //save current value of every possible index in the 2D Array
    }
    if (currentValue > myMax) { //If current value is bigger than previous
      myMax = currentValue; //Save Value
    }
  }
  return myMax; //Return maximum number of respondent
}


function chartBase() {

  strokeWeight(2.5);
  /* DRAW Y AXIS LINE (VERTICAL) */
  line(50, canvasHeight-(chartHeight+50), 50, canvasHeight - 45);

  /* DRAW X AXIS LINE (HORIZONTAL) */
  line(20, canvasHeight - 75, canvasWidth-legendsWidth-25, canvasHeight - 75);
	strokeWeight(1);
  /* DRAW LINE SEPERATORS & VALUES ON Y AXIS */
  var max = findMaxNumOfResondents(); //Maximum number of Respondents
  var countLineSeperators = max / 10; //Maximum lines devided by 10
  var fixedNumOfLines = countLineSeperators.toFixed(0); //Fix number (remove decimal numbers)
  var jump = 75; //Space between line seperators
  var myText = 0; //Number to be display
	textSize(16); //Set Text Size
  /* For a calculated number of lines */
  for (var i = 0; i <= fixedNumOfLines; i++) {

    stroke(230); //Change the stroke of the line
    jump += 90; // Change location every loop vertically
    myText += 10; //Add value to the number to be displayed

    //Draw Line Seperator
    line(50, canvasHeight - jump, canvasWidth-legendsWidth-50, canvasHeight - jump);
    //Display number per line seperator
    text(myText, 30, (canvasHeight - jump) + 5);
  }

  noStroke(); //Reset Stroke
  jump = 75; //Reset space between elements

  /* DRAW VALUES OF X AXIS */
  for (var y = 0; y < arrayOfMonths_TEXT.length; y++) {
    text(arrayOfMonths_TEXT[y], jump, canvasHeight - 50);
    jump += 80; // Change location every loop horizontally
  }
}

function addValues() {


  var locX = 80; //Location of rectangle in X Axis
  var locY; //Location of rectangle in Y Axis
  var barWidth = 40; //Size of rectangle (each element) in width
  var barHeight; //Size of rectangle (each element) in height
	textSize(13);
  
  for (var x = 0; x < arrayOfMonths.length; x++) {

    /* Draw Stack Bars (rectangles) */
    for (var y = 0; y < arrayOfMonths[x].length; y++) {

      barHeight = arrayOfMonths[x][y] * 9;

      if (y == 0) {
        locY = canvasHeight - barHeight - 75;
      } else {
        locY -= barHeight;
      }

      /* ADD RECTANGLES TO Y AXIS */
      fill(arrayOfColors[y], arrayOfColors[y], arrayOfColors[y]);
      rect(locX, locY, barWidth, barHeight);

      /* ADD TEXT TO X EVERY ELEMENT */
      if (arrayOfMonths[x][y] >= 1) {
        fill(255, 255, 255); //White Color For Text
        text(arrayOfMonths[x][y], locX + 15, locY + 10);
      }

    }
    locX += 80;
  }
}

function addLegends() {

  var locX = canvasWidth-legendsWidth; //Start location of the Legend in X Axis 
  var locY = canvasHeight - 75; //Start location of the Legend in Y Axis
  var sizeX = 15; //Size of Legend in width
  var sizeY = 15; //Size of Legend in height

  for (var x = 0; x < arrayOfLegends.length; x++) {

    fill(arrayOfColors[x]); //BLACK
    rect(locX, locY, sizeX, sizeY);

    fill(0);

    text(arrayOfLegends[x], locX + 20, locY, 400, 150);
    
		stroke(160); //Change the stroke of the line
    //Draw Line Seperator
    line(locX, locY-8, canvasWidth+325,locY-8);
    

    locY-=75;
  }
}