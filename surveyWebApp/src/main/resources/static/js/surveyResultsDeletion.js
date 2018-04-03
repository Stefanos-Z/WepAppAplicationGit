
function updateModal(surveyname, surveyid)
{
    var modalSpan = document.getElementById("modalSurvey");
    modalSpan.innerHTML = surveyname;
    var deleteButton = document.getElementById("deleteButton");
    deleteButton.setAttribute("value",surveyid);
}

$(document).ready(function() {
    
    //code to remove the row from a table
    $("#deleteButton").click(function() {
        deleteSurveyDB($("#deleteButton").val());
        $("#srv"+$("#deleteButton").val()).remove();
    });
    
    //function to delete survet from database
    function deleteSurveyDB(surveyid){
    	
    	// create form data
    	var survey = {
    		surveyid : surveyid
    	}
    	
    	//Post data to the database
    	$.ajax({
            type : "POST",
            contentType : "application/x-www-form-urlencoded",
            url : "/deleteSurvey",
            data : survey,
            dataType : 'text',
            success : function() {
                console.log("Success!");
            },
            error : function(e) {
                    alert("ERROR: Unable to delete");
                    console.log("ERROR: ", e);
            }
        });
 
    }
});







	