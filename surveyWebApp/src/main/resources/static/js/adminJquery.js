/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    
    setEventListeners();

    //prevent modal from being hidden if not completed
    $('#addModal').on('hide.bs.modal',function(e){
        
        //get user from form
        var user = validateAddUser();
        
        //if not valid, don't close modal.
        if(!user.validUser && document.activeElement.id == 'addUserDB'){
           e.preventDefault();
           alert('Please complete the highlighted fields');
        }
        else
        {
            clearAddLines();
        }
     });
     
     //update a user record in the database
    $("#addUserDB").click(function() {
        //alert("detected");
        saveUserDB();
    });
    
    //prevent modal from being hidden if not completed
    $('#editModal').on('hide.bs.modal',function(e){
        
        //get user from form
        var user = validateEditUser();
        
        //if not valid, don't close modal.
        if(!user.validUser && document.activeElement.id == 'editUserDB'){
           e.preventDefault();
           alert('Please complete the highlighted fields');
        }
        else
        {
            clearEditLines();
        }
     });
    
    //update a user record in the database
    $("#editUserDB").click(function() {
        //alert("detected");
        editUserDB($(this).val());
    });

    //deletes a user record from the database
    $("#deleteUserDB").click(function() {
        //alert("detected");
        deleteUserDB($(this).val());
    });
    
    //function to insert user into database
    function saveUserDB(){

        // create form data
        var user = validateAddUser();

        if(user.validUser)
        {
            //Post data to the database
            $.ajax({
                type : "POST",
                contentType : "application/x-www-form-urlencoded",
                url : "/add_user",
                data : user,
                dataType : 'text',
                success : function(response) {
                    console.log("Success!");
                    console.log(response);
                    $("tbody").fadeToggle(200);
                    $('tbody').html(response);
                    $("tbody").fadeToggle(200);
                    setEventListeners();
                },
                error : function(e) {
                        alert("ERROR: Unable to add user");
                        console.log("ERROR: ", e);
                }
            });
            return true;
        }
        else
        {
            return false;
        }

    }
    
    //function to validate the contents of the add user form
    function validateAddUser()
    {
        //get parameters from form.
        var user = {
                validUser : false,
                username : $("#usernameInput").val(),
                password : $("#passwordInput").val(),
                email : $("#emailInput").val(),
                phoneNumber : $("#phoneInput").val(),
                role : $('#roleInput').val()
        }
        
        var errors = 0;
        //check for errors
        if(user.username==='')
        {
            $("#usernameInput").attr("class","invalidInput");
            errors++;
        }
        if(user.password==='')
        {
            $("#passwordInput").attr("class","invalidInput");
            errors++;
        }
        if(user.email==='')
        {
            $("#emailInput").attr("class","invalidInput");
            errors++;
        }
        if(user.role==='')
        {
            $("#roleInput").attr("class","invalidInput");
            errors++;
        }
        
        if(errors===0)
        {
            user.validUser = true;
        }
        
        return user;
    }
    
    //function to clear red highlighting
    function clearAddLines()
    {
 
        $("#usernameInput").attr("class","");
        $("#usernameInput").val("");

        $("#passwordInput").attr("class","");
        $("#passwordInput").val("");

        $("#emailInput").attr("class","");
        $("#emailInput").val("");

        $("#roleInput").attr("class","");
        $("#roleInput").val("");

    }
    
    //function to delete survet from database
    function editUserDB(userid){

        // create form data
        var user = validateEditUser(userid);


        if(!user.validUser)
        {
            return false;
        }
        else
        {
            //Post data to the database
            $.ajax({
                type : "POST",
                contentType : "application/x-www-form-urlencoded",
                url : "/edit_user",
                data : user,
                dataType : 'text',
                success : function(response) {
                    console.log("Success!");
                    console.log(response);
                    console.log("#row"+userid);
                    $("tbody").fadeToggle(200);
                    $('tbody').html(response);
                    $("tbody").fadeToggle(200);
                    setEventListeners();
                },
                error : function(e) {
                        alert("ERROR: Unable to add user");
                        console.log("ERROR: ", e);
                }
            });
            return true;
        }
    }

    //function to validate the contents of the edit user form
    function validateEditUser(userid)
    {
        //get parameters from form.
        var user = {
                validUser : false,
                userid : userid,
                username : $("#usernameEdit").val(),
                password : $("#passwordEdit").val(),
                email : $("#emailEdit").val(),
                phoneNumber : $("#phoneEdit").val(),
                role : $('#roleEdit').val()
        }
        
        var errors = 0;
        //check for errors
        if(user.username==='')
        {
            $("#usernameEdit").attr("class","invalidInput");
            errors++;
        }
        if(user.password==='')
        {
            $("#passwordEdit").attr("class","invalidInput");
            errors++;
        }
        if(user.email==='')
        {
            $("#emailEdit").attr("class","invalidInput");
            errors++;
        }
        if(user.role==='')
        {
            $("#roleEdit").attr("class","invalidInput");
            errors++;
        }
        
        if(errors===0)
        {
            user.validUser = true;
        }
        
        return user;
    }
    
    //function to clear red highlighting
    function clearEditLines()
    {
 
        $("#usernameEdit").attr("class","");

        $("#passwordEdit").attr("class","");

        $("#emailEdit").attr("class","");

        $("#roleEdit").attr("class","");
    }
    
    //function to delete word from database
    function deleteUserDB(userid){

        // create form data
        var user = {
                userid : userid
        }


        //Post data to the database
        $.ajax({
            type : "POST",
            contentType : "application/x-www-form-urlencoded",
            url : "/delete_user",
            data : user,
            dataType : 'text',
            success : function(response) {
                console.log("Success!");
                console.log(response);
                console.log("#row"+userid);
                $("tbody").fadeToggle(200);
                $('tbody').html(response);
                $("tbody").fadeToggle(200);
                setEventListeners();
            },
            error : function(e) {
                    alert("ERROR: Unable to delete user");
                    console.log("ERROR: ", e);
            }
        });

    }

    
    function setEventListeners(){
    
        //function to add row data to form
        $(".glyphicon-edit").click(function() {
            var cRow = $(this).closest("tr")[0];
            console.log($(this).closest("tr").attr("id"));

            //pass id value to button
            $("#editUserDB").val($(cRow).find("td").eq(0).html()); 

            //add user details
            $("#usernameEdit").val($(cRow).find("td").eq(1).html());  
            $("#passwordEdit").val($(cRow).find("td").eq(2).html());
            $("#emailEdit").val($(cRow).find("td").eq(3).html());
            $("#phoneEdit").val($(cRow).find("td").eq(4).html());  
            $("#roleEdit").val($(cRow).find("td").eq(5).html());

        });

        //function to add row data to form
        $(".glyphicon-remove-sign").click(function() {
            var cRow = $(this).closest("tr")[0];
            console.log($(this).closest("tr").attr("id"));

            //pass id value to delete button
            $("#deleteUserDB").val($(cRow).find("td").eq(0).html()); 
            
        });
        
    }
});