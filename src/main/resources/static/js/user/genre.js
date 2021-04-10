$(document).ready(function() {
    getGenreList();
});
function getGenreList(){
    $.ajax({
        url: "/book/genre/list",
        method: 'GET',
        dataType: "json",
        data: {
        }
    }).done(function(data) {
        var result = "";
        var genre;

        for(var i = 0; i < data.genreList.length; i++) {
            genre = "<input type = 'checkbox' name='genre' value='"
                + data.genreList[i].genreType + "'>"
                + data.genreList[i].genreType + "</>";
            result += genre;
            if(i == 5) {
                genre += "<br>";
            }
        }
        $('#result').append(result);
    })
        .fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
}