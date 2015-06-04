
$(document).ready(function(){
    var requestAddress = "https://ahome.azure-mobile.net/tables/devices";
    $.get( requestAddress, function( data ) {
        var top = "47px";

        //var itemsDiv = $('#items');
        var i = 1;
        data.forEach(function(element){
            var divClass = "firstItem";
            if (i == 2) {
                divClass = "secondItem";
            }
            if (i == 3) {
                divClass = "thirdItem";
            }
            if (i == 4) {
                divClass = "fourthItem";
                i = 0;
            }

            var itemHtml = "<div class=\"item " + divClass + "\" style=\"top: " + top + ";\">" + element["label"] + "</div>";

            $('#items').append(itemHtml);

            i++;
        });
    });
});