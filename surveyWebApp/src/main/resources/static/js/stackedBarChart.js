/* Needed Variables Declaration */
var month1 = [7, 0, 0, 5, 1, 10, 1];
var month2 = [10, 5, 1, 5, 5, 10, 4];
var month3 = [7, 8, 9, 8.2, 10, 6];
var month4 = [2, 2, 2, 8, 8, 20, 7];
var month5 = [10, 2, 1, 8, 7, 20, 3];
var month6 = [1, 4, 7, 25, 8];
var month7 = [9, 20, 18, 1];
var month8 = [3, 2, 1, 6, 8];
var month9 = [8, 1, 21, 1];
var month10 = [10, 9, 1, 11, 2];
var month11 = [11, 2, 8, 31];
var month12 = [50, 10, 10];
var arrayOfMonths = [month1, month2, month3, month4, month5, month6, month7, month8, month9, month10, month11, month12];
var arrayOfMonths_TEXT = ["Jan 2018", "Feb 2018", "Mar 2018", "Apr 2018", "May 2018", "Jun 2018", "Jul 2018", "Aug 2018", "Sep 2018", "Oct 2018", "Nov 2018", "Dec 2018"];
var arrayOfLegends = [
  "Have you experienced harassment at work?",
  "Are you happy with your free coffee?",
  "Are you happy with your manager?",
  "Are you happy with your coworkers?",
  "Are you happy with your admin service?",
  "Are you happy with your company benefits?",
  "Are you happy with your pension?",
  "Are you happy with your HR department?"
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
var extraPixels = 100; //Extra space 75 pixels
// 200 pixels per month (Width) + some extra space 
var canvasWidth = (arrayOfMonths.length * 150) + (extraPixels);
// 11 pixels per Respontant (Height) + some extra space
var canvasHeight = (findMaxNumOfResondents() * 100);

/**
 * Setups the Canvas Size 
 * Creates the Canvas
 * Add Chart Base to the Canvas
 * Add Values on the Chart Base
 * Add Legends on the right size of the Chart Base
 */
function setup() {

    /* Setup Canvas Size */
    extraPixels = 100; //Extra space pixels
    // 150 pixels per month (Width) + some extra space 
    canvasWidth = (arrayOfMonths.length * 150) + (extraPixels);
   
    canvasHeight = Math.max((findMaxNumOfResondents() * 35), 500);

    var canvas = createCanvas(canvasWidth+400, canvasHeight);

    chartBase(); //Add X,Y Axis Lines

    addValues(); //Add the Bars (Values) of the Chart

    addLegends(); //Add Lengend Information Bullets3
    
    canvas.parent("statsDiv"); //Add Canvas to be parent to statsDiv
}

/**
 * Finds the Maximum number of respondents from the given arrays
 * @returns Max Num of Respondents (controls height of canvas)
 */
function findMaxNumOfResondents() {

    var myMax = 0; //Number to be returned

    for(var x = 0; x < arrayOfMonths.length; x++) {
        var currentValue = 0; //Create a current value

        for(var y = 0; y < arrayOfMonths[x].length; y++) {
            currentValue += arrayOfMonths[x][y]; //save current value of every possible index in the 2D Array
        }

        if(currentValue > myMax) { //If current value is bigger than previous
            myMax = currentValue; //Save Value
        }
    }
    return myMax; //Return maximum number of respondent
}

/**
 * Creates the Base of the chart
 * @returns 2 lines for X,Y Axis, X Axis Title, Y Axis Title, X Axis Values, Y Axis Values 
 */
function chartBase() {

    strokeWeight(2);
    /* DRAW Y AXIS LINE (VERTICAL) */
   
    line(50, 25, 50, canvasHeight - 150);
    textSize(32); text("↑",42, 40);
    textSize(15); text("(Num of Responses)", 10, 15);

    /* DRAW X AXIS LINE (HORIZONTAL) */
    line(50, canvasHeight - 150, canvasWidth -100, canvasHeight - 150);
    textSize(35); text("→", canvasWidth-130, canvasHeight-141);
    textSize(15); text("(Num of Months)", canvasWidth-85, canvasHeight-145);
    textSize(10); //RESET TEXT SIZE

    /* Line Seperators variables declaration */
    var max = findMaxNumOfResondents(); //Maximum number of Respondents
    var countLineSeperators = max / 10; //Maximum lines devided by 10
    var fixedNumOfLines = countLineSeperators.toFixed(0); //Fix number (remove decimal numbers)
    var jump = 150; //Space between line seperators
    var myText = 0; //Number to be display

    /* DRAW VALUES OF X AXIS (MONTHS) */
    for (var i=0; i<=fixedNumOfLines; i++) {

        stroke(230); //Change the stroke of the line
        jump += 80; // Change location every loop vertically
        myText += 10; //Add value to the number to be displayed

        //Draw Line Seperator
        line(52, canvasHeight - jump, canvasWidth - 100, canvasHeight - jump);
        //Display number per line seperator
        text(myText, 30, (canvasHeight - jump) + 5);
    }

    noStroke(); //Reset Stroke
    jump = 115; //Reset space between elements

    /* DRAW VALUES OF X AXIS (MONTHS) */
    for (var y = 0; y < arrayOfMonths_TEXT.length; y++) {
        text(arrayOfMonths_TEXT[y], jump, canvasHeight - 130);
        jump += 135; // Change location every loop horizontally
    }
}

/**
 * Gets values from the 2D Array for each element in each month
 * @returns rectangles on the chart according to number of elements in the 2D Array
 */
function addValues() {

    var locX = 110; //Location of rectangle in X Axis
    var locY; //Location of rectangle in Y Axis
    var barWidth = 60; //Size of rectangle (each element) in width
    var barHeight; //Size of rectangle (each element) in height

    /* Draw Stack Bars (rectangles) */
    for (var x = 0; x < arrayOfMonths.length; x++) {
        for (var y = 0; y < arrayOfMonths[x].length; y++) {

            barHeight = arrayOfMonths[x][y] * 8;

            if (y == 0) {
                locY = canvasHeight - barHeight - 150;
            } else {
                locY -= barHeight;
            }

            /* ADD RECTANGLES TO Y AXIS */
            fill(arrayOfColors[y], arrayOfColors[y], arrayOfColors[y]);
            rect(locX, locY, barWidth, barHeight);

            /* ADD TEXT TO X AXIS */
            if (arrayOfMonths[x][y] >= 1) {
                fill(255, 255, 255); //White Color For Text
                text(arrayOfMonths[x][y], locX + 25, locY + 10);
            }
        }
        locX += 135;
    }
}

/**
 * Creates the legends on the right side of the chart
 * @returns a list of legends with additional information for each element in the months
 */
function addLegends() {

    var locX = canvasWidth-60; //Start location of the Legend in X Axis 
    var locY = canvasHeight - (canvasHeight*0.6); //Start location of the Legend in Y Axis
    var sizeX = 15; //Size of Legend in width
    var sizeY = 15; //Size of Legend in height

    for (var x = 0; x < arrayOfLegends.length; x++) {

        fill(arrayOfColors[x]); //Get Same Color from the array
        rect(locX, locY, sizeX, sizeY); //Draw rectangle

        fill(0); //Set white color for the text
        //Draw text with Y color for each element
        text(arrayOfLegends[x], locX + 20, locY, 400, 150);

        locY -= 25; //Jump to next location
    }
}