/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    $('#groupSelect').change(function(){
        console.log($('#groupSelect option:selected').text());
        
        var group = {
            groupName : $('#groupSelect option:selected').text()
        }
        
        if(group.groupName !== "New Group")
        {
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
//            $('#newGroupName').hide();
            $('#newGroupName').val(group.groupName);
            $('#groupEmailDelete').prop('disabled', false);// enebles delete button
            $("#saveEmails").val("Update Email Group");
        }else{
            $("#saveEmails").val("Save new Group");
            $('#newGroupName').show();
            $('#newGroupName').val("");
            $("#emailList").val('');
            $('#groupEmailDelete').prop('disabled', true);//disables delete button
        }
        
    });
    
    
});



