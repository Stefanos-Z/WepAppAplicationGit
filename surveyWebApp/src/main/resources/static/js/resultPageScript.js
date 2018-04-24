/* Needed Variables */
var answersArrays = [["test"],["test"]];
var countArrays = [[1],[2]];

var index = 0;

var respondentDataInputArray = answersArrays[index]; //Holds data of Numbers from the Database (44.13%-25.05%)
var answersArray = answersArrays[index]; //Holds data of Answers from the Database ("Agree/Yes-Dissagree/No")

/* Data Declaration */
var sum; //Holds the sum of the answers
var percentagesArray = [respondentDataInputArray.length]; //Holds the persentages in an array
var chartArray = [respondentDataInputArray.length]; //Holds the Arcs of the Pie Chart in an Array

/* Other Variables Declaration */
var pieChartSize = 200; //Size of the Pie Chart
var rectX = 250; //Space between the status rectangles - Constant Variable (in width)
var rectY = 100; //Space between the status rectangles - Dynamic  Variable (in height)
var canvasWidth = 550; //Constant Variable
var canvasHeight = respondentDataInputArray.length * 60; //Dynamic Variable

/**
 * Setup the Pie Chart in the Canvas after the 
 * calculation has been established.
 */
function setup() {
    
    respondentDataInputArray = countArrays[index];
    answersArray = answersArrays[index];

    /* Data Declaration */
    sum = total(respondentDataInputArray); //Holds the sum of the answers
    percentagesArray = [respondentDataInputArray.length]; //Holds the persentages in an array
    chartArray = [respondentDataInputArray.length]; //Holds the Arcs of the Pie Chart in an Array

    /* Other Variables Declaration */
    pieChartSize = 200; //Size of the Pie Chart
    rectX = 250; //Space between the status rectangles - Constant Variable (in width)
    rectY = 100; //Space between the status rectangles - Dynamic  Variable (in height)
    canvasWidth = 550; //Constant Variable
    canvasHeight = respondentDataInputArray.length * 60; //Dynamic Variable

    /* If canvas is hiding elements change the size */
    if(canvasHeight > 250){
        var canvas= createCanvas(canvasWidth, canvasHeight); //Create the Canvas to draw the Pie Chart
        canvas.parent("statsDiv");
    }else{
        var canvas = createCanvas(canvasWidth, 250);
        canvas.parent("statsDiv");
    }

    /* Draw Chart after calculations */
    calculateAngles(); //This method will calculate the angle for the chart arcs
    //pieChart(pieChartSize, chartArray); //Draw Pie Chart
}

/* DO CALCULATION */
function calculateAngles(){

    var persentageMultiplier = 100 / sum; //Add the persentage of the sum in a variable

    for(var i=0; i<respondentDataInputArray.length; i++){

        //Add the persentage of the sum to an array of persentages
        percentagesArray[i] = respondentDataInputArray[i] * persentageMultiplier;

        chartArray[i] = percentagesArray[i] * 3.6; //Hold degrees of each part in the array
    }
}

function pieChart(diameterSize, data) {
    fill(color(0,0,0));
    /* Add title to show how many people had answered */
    textSize(30);
    textStyle(BOLD);
    text("Out of " + sum + " people",245,50);
    textStyle(NORMAL);

    var lastAngle = 0;

    for (var i=0; i<data.length; i++) {

        if(chartArray[i]>0){

            colorMode(RGB); // Try changing to HSB.
            var from = color(random(100,255),0,0);
            var to = color(0, 0, random(100,255));

            var interA = lerpColor(from, to, 0.11*i);
            var interB = lerpColor(from, to, 0.21*i);

            if(i==0){
                fill(from);
            }
            else if(i>i%2){
                fill(interA);
            }
            else if(i==data.length){
                fill(froB);
            }else{
                fill(to);
            }

            fill(random(255),random(255),random(255)); //Generate a random color
            
            /* DRAW PIE CHART */
            arc(pieChartSize/2, height/2+height*0.1, //Arguments : x/y coordinate of the arc's ellipse
            diameterSize, diameterSize,  //width/height of the arc's ellipse by default
            lastAngle, lastAngle+radians(chartArray[i])); //angle to start/stop the arc, specified in radians

            lastAngle += radians(chartArray[i]); //Add to last angle depenting on the next Arc
        }

        /* DRAW LEGENDS OF THE PIE CHART */
        rect(rectX,rectY,20,20); 

        textSize(12); //Change the size of the text

        var percentagesText = text(nf(percentagesArray[i], 2,2), rectX+35, rectY+15); //Remove Decimal numbers
        var str = "          % , " + answersArray[i].substring(0,20);
        if(answersArray[i].length>20)
        {
            str + "...";
        }
        text(str,rectX+35,rectY+15);

        rectY+=40; //Move next point of status

    } //-End of For Loop 
}

/**
 * Counts the array size
 * @returns the number of the array size
 */
function total(array){
    var sum = 0;
    
    for(var i=0; i<array.length; i++){   
        sum+= array[i];
    }
    return sum;
}

//function to update index on click
$(document).ready(function() {
   
   //refresh pie chart in modal when icon is pressed
   $(".statIcon").click(function() {
        //alert("detected");
        index = parseInt($(this).attr("index"));
        console.log($(this).attr("index")+"was printed");
        setup();
        pieChart(pieChartSize, chartArray);
    });
    
});