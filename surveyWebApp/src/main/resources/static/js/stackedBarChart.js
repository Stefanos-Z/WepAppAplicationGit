/* Needed Variables Declaration */
var month1 = ["M01V01", "M01V02","M01V03","M01V04"];
var month2 = ["M02V01", "M02V02", "M02V03", "M02V04", "M02V05"];
var month3 = ["M03V01"];
var month4 = ["M04V01", "M04V02", "M04V03"];

var arrayOfMonths = [month1, month2, month3, month4]; //X Axis (Could be String or Array)
var arrayOfResponses = ["1","2","3","4","5","6"];     //Y Axis (Could be String ONLY)

/* Calculated Variables */
var numOfMonths = arrayOfMonths.length;
var numOfResponses = arrayOfResponses.length;

/* Other Variable Declarations */
var extraSpace = 250;
var canvasWidth = (numOfMonths * 100) + extraSpace;
var canvasHeight = (numOfResponses * 100) + extraSpace;

function setup() {

    //Create the canvas to display the Stacked Bar Chart 
    createCanvas(canvasWidth+100, canvasHeight); 
    background(255); /* !!!  (REMOVE THIS TO MAKE BACKGROUND TRANSPARENT)  !!! */

    chartBase(); //Add X,Y Axis Lines

    addPointers(numOfResponses, numOfMonths); //Add Pointers to X,Y Axis

    addTextToPointers(); //Add Text to pointers of Y Axis

    addValues(); //Add the Bars (Values) of the Chart
}

/**
 * Draws a line to represent a graph (X,Y Axis)
 */
function chartBase() {

  /* DRAW X AXIS LINE */
  line(canvasWidth - (canvasWidth - 50), canvasHeight - 100,
    (canvasWidth - 50) + 25, canvasHeight - 100);

  /* DRAW Y AXIS LINE */
  line(canvasHeight - (canvasHeight - 100), canvasHeight - (canvasHeight - 50) - 25,
    canvasHeight - (canvasHeight - 100), canvasHeight - 50);
}

/**
 * Draws pointers on X,Y Axis according to the parameters values
 * @param {type} numOfMonths is the number of Pointers to be drawn on X Axis
 * @param {type} numOfResponses is the number of Pointers to be drawn on Y Axis
 */
function addPointers(numOfMonths, numOfResponses) {

  /* Find space Between Pointers */
  var spaceBetweenX = canvasWidth - (canvasWidth - 150);
  var spaceBetweenY = canvasHeight - 150;

  /* Count how many pointers */
  numOfMonths = arrayOfMonths.length + 1; //+1 - ????
  numOfResponses = arrayOfResponses.length+1; //+1 - ????
  
  fill(0, 0, 0); //Black Color for the Dots (Pointers)
  
  /* ADD POINTERS TO X AXIS */
  for (var x = 0; x < numOfMonths; x++) {
    ellipse(spaceBetweenX, canvasHeight - 100, 5, 5);
    spaceBetweenX += canvasWidth / numOfMonths;
  }

  /* ADD POINTERS TO Y AXIS */
  for (var y = 0; y < arrayOfResponses.length; y++) {
    ellipse(canvasWidth - (canvasWidth - 100), spaceBetweenY, 5, 5);
    spaceBetweenY -= canvasHeight / numOfResponses;
  }
}

function addTextToPointers() {

  var spaceBetweenX = canvasWidth - (canvasWidth - 130);
  var spaceBetweenY = (canvasHeight - 100) - 50;
  textSize(13); //Set text size

  /* ADD TEXT TO Y AXIS */
  for (var y = 0; y < arrayOfResponses.length; y++) {

    text(arrayOfResponses[y], (canvasWidth - (canvasWidth - 65)), spaceBetweenY);

    spaceBetweenY -= canvasHeight / (numOfResponses + 1);
  }
}

function addValues() {

  var locX = canvasWidth - (canvasWidth - 125);
  var locY = canvasHeight - 215;
  var barWidth = 50;
  var barData = canvasHeight / (numOfResponses + 2);

  for (var x = 0; x < arrayOfMonths.length; x++) {
    /* Draw Stack Bars (rectangles) */
    for (var y = 0; y < arrayOfMonths[x].length; y++) {

      /* ADD RECTANGLES TO Y AXIS */
      fill(random(255), random(255), random(255));
      rect(locX, locY, barWidth, barData);

      /* ADD TEXT TO X AXIS */
      fill(0, 0, 0); //Black Color For Text
      text(arrayOfMonths[x][y], locX, locY + 50);

			locY -= 115;
    }
    

    locX += 130;
    locY = canvasHeight - 215;
  }
}
