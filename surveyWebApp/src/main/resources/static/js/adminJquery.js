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

    //add new user row to a table
    $("#addUserDB").click(function() {
        //alert("detected");
        saveUserDB();
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
        // create form data
        var user = {
                username : $("#usernameInput").val(),
                password : $("#passwordInput").val(),
                email : $("#emailInput").val(),
                phoneNumber : $("#phoneInput").val(),
                role : $('#roleInput').val()
        }


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
                    alert("ERROR: Unable to add word");
                    console.log("ERROR: ", e);
            }
        });

    }
    
    //function to delete survet from database
    function editUserDB(userid){

        // create form data
        var user = {
                userid : userid,
                username : $("#usernameEdit").val(),
                password : $("#passwordEdit").val(),
                email : $("#emailEdit").val(),
                phoneNumber : $("#phoneEdit").val(),
                role : $('#roleEdit').val()
        }


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
                    alert("ERROR: Unable to add word");
                    console.log("ERROR: ", e);
            }
        });
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
                    alert("ERROR: Unable to delete word");
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