/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    $('#groupSelect').change(function(){
        console.log($('#groupSelect option:selected').text());
        
        $.ajax({
            type : "POST",
            contentType : "application/x-www-form-urlencoded",
            url : "/getemails",
            dataType : 'text',
            success : function(response) {
                console.log("Success!");
                $('#emailList').val(response)
            },
            error : function(e) {
                    alert("ERROR: Unable to add word");
                    console.log("ERROR: ", e);
            }
        });
    });
    
    
});


