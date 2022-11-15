
//Handler for the new asset submission button
$("#subNewForm").trigger(function(){
    console.log("Click")
    //Execute the submit new asset function
    submitVideo();

});


function submitVideo(){


    let submitData = new FormData();
//Get form variables and append them to the form data object
    submitData.append('title', $('#Title').val());
    submitData.append('publisher', $('#Publisher').val());
    submitData.append('producer', $('#Producer').val());
    submitData.append('genre', $('#Genre').val());
    submitData.append('ageRating', $('#AgeRating').val());
    submitData.append('videoFile', $("#VideoFile")[0].files[0]);
//Post the form data to the endpoint, note the need to set the content type header
    $.ajax({
        url: "/videos",
        data: submitData,
        cache: false,
        contentType: false,
        processData: false,
        async: true,
        type: 'POST',
        success: function(data){
            alert(data)
        }
    });


}