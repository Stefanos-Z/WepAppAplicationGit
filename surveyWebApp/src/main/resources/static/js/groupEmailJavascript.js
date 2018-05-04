/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {//only rune when page is fully loaded
    $('#groupSelect').change(function(){
        //console.log($('#groupSelect option:selected').text());
        
        var group = {
            groupName : $('#groupSelect option:selected').text()//gets selected group from the drop down menu
        }
        
        //If the group selected is no New Group
        if(group.groupName !== "New Group")
        {
            //ajax post
            $.ajax({
                type : "POST",
                contentType : "application/x-www-form-urlencoded",
                url : "/getemails",
                dataType : 'text',
                data : group,
                success : function(response) {
                    console.log("Success!");
                    $('#emailList').val(response)
                },
                error : function(e) {
                        alert("ERROR: Unable to retrieve groupid");
                        console.log("ERROR: ", e);
                }
            });
            $('#newGroupName').val(group.groupName);//makes the value in the text field equal to the selected group name
            $('#groupEmailDelete').prop('disabled', false);// enebles delete button
            $("#saveEmails").val("Update Email Group");//update the text of th button
        }else{
            $("#saveEmails").val("Save new Group");//update the text of th button
            $('#newGroupName').val("");//empties the new group name
            $("#emailList").val('');//empties the emial list
            $('#groupEmailDelete').prop('disabled', true);//disables delete button
        }
        
    });
    
    
});



