/* Needed Variables Declaration */
var month1 = [6,7,20,6,5];
var month2 = [9,8,7,6,5];
var month3 = [7,7,7,7,7];
var month4 = [1,2,3,42,5];

var arrayOfMonths = [month1, month2, month3, month4]; //X Axis (Could be String or Array)
//var arrayOfResponses = ["1","2","3","4","5"];     //Y Axis (Could be String ONLY)

/* Calculated Variables */
var numOfMonths = arrayOfMonths.length;
var numOfResponses =  arrayOfMonths[0].length;

/* Other Variable Declarations */
var extraSpace = 400;
// var canvasWidth = (numOfMonths * 100) + extraSpace;
// var canvasHeight = (numOfResponses * 100) + extraSpace;
var canvasWidth =800;
var canvasHeight = 600;

function setup() {

    //Create the canvas to display the Stacked Bar Chart 
    createCanvas(canvasWidth, canvasHeight); 
    background(255); /* !!!  (REMOVE THIS TO MAKE BACKGROUND TRANSPARENT)  !!! */

    chartBase(); //Add X,Y Axis Lines
    var maxNumOfResondents = findMaxNumOfResondents();
    //addPointers(numOfMonths, maxNumOfResondents); //Add Pointers to X,Y Axis

    // addTextToPointers(); //Add Text to pointers of Y Axis

    addValues(); //Add the Bars (Values) of the Chart
}

/**
 * Draws a line to represent a graph (X,Y Axis)
 */
function chartBase() {

  /* DRAW X AXIS LINE */
  line(50,canvasHeight-50,canvasWidth-50,canvasHeight-50,);

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
  var spaceBetweenX = canvasWidth - (canvasWidth - 200);
  var spaceBetweenY = canvasHeight -215;
  
  fill(0, 0, 0); //Black Color for the Dots (Pointers)
  
  /* ADD POINTERS TO X AXIS */
  for (var x = 0; x<numOfMonths; x++) {
    ellipse(spaceBetweenX, canvasHeight - 50, 5, 5);
    spaceBetweenX += (canvasWidth / numOfMonths)-25; //Change width here
  }

  /* ADD POINTERS TO Y AXIS */
  for (var y = 0; y < numOfResponses; y++) {
    ellipse(canvasWidth - (canvasWidth - 100), spaceBetweenY, 5, 5);
    spaceBetweenY -= (canvasHeight / numOfResponses) - 85; //Change height here
  }
}

function addTextToPointers() {

  var spaceBetweenX = canvasWidth - (canvasWidth - 130);
  var spaceBetweenY = (canvasHeight - 100) - 50;
  textSize(15); //Set text size

  
  /* ADD TEXT TO Y AXIS */
  for (var y = 0; y < arrayOfResponses.length; y++) {

    text(arrayOfResponses[y], (canvasWidth - (canvasWidth - 65)), spaceBetweenY);

    spaceBetweenY -= canvasHeight / (numOfResponses + 1);
  }
}

function addValues() {

  var locX = canvasWidth - (canvasWidth - 170); //Start Location X
  //var startLocationY = canvasHeight - 215; //Start Location Y
  
  
  var barWidth = 60;
  var barHeight;
  var locY;
  for (var x = 0; x < arrayOfMonths.length; x++) {
    
     
    /* Draw Stack Bars (rectangles) */
    for (var y = 0; y < arrayOfMonths[x].length; y++) {
      barHeight =  arrayOfMonths[x][y] * 8;
      if(y==0){
        locY = canvasHeight-barHeight-50;
        
      }else{
        locY -= barHeight;
      }
      
      /* ADD RECTANGLES TO Y AXIS */
      fill(random(255), random(255), random(255));
      rect(locX, locY, barWidth, barHeight);
      
      /* ADD TEXT TO X AXIS */
      fill(255,255,255); //White Color For Text
      text(arrayOfMonths[x][y], locX+25, locY+15);

			
    }
    

    locX += 135;
    //locY = canvasHeight - 215; //Start Location on Y Axis (DONT CHANGE)
  }
}


function findMaxNumOfResondents(){

  var myMax = 0;
  for(var i = 0; i<numOfMonths; i++){
    var thisMax = 0;
    for(var j= 0; j<numOfResponses; j++){
        thisMax+= arrayOfMonths[i][j];
      }
      if(thisMax>myMax)
      {
        myMax = thisMax;
      }
    }
    return myMax
}






