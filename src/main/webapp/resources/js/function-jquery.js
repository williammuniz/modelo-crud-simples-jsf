$(document).ready(function() {
    $(".numero").keydown(function(event) {
        if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 ) {
        }
        else {   
            if (event.keyCode < 48 || event.keyCode > 57 ) {
                event.preventDefault();	
            }	
        }
    });
    $('.email').blur(function(){
        var email= $(this).val();        
        var exclude=/[^@\-\.\w]|^[_@\.\-]|[\._\-]{2}|[@\.]{2}|(@)[^@]*\1/;
        var check=/@[\w\-]+\./;
        var checkend=/\.[a-zA-Z]{2,3}$/;
        if(((email.search(exclude) != -1)||(email.search(check)) == -1)||(email.search(checkend) == -1)){
            alert('email inválido');
        }
    });
});

