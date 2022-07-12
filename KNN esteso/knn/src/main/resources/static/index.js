var modal = document.getElementById("modal");
var span = document.getElementsByClassName("close")[0];

// Quando l'utente clicca la x, si chiude la finestra
span.onclick = function () {
    modal.style.display = "none";
}

// Quando l'utente clicca fuori dalla finestra, questa si chiude
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function createSecondForm() {
    let variables = [["motr", "discrete"], ["moto", "discrete"], ["motor", "discrete"], ["screw", "discrete"], ["pgain", "continuous"], ["vgain", "continuous"], ["gay", "continuous"]];
    let form = "";

    for (let i = 0; i < variables.length; i++) {
        let type = variables[i][1] === "discrete" ? "text" : "number";
        form += "<label for='" + variables[i][0] + "'>Inserire la variabile " + variables[i][0] + "</label><br>";
        form += "<input type='" + type + "' name='" + variables[i][0] + "' id='" + variables[i][0] + "' autocomplete='off' placeholder='Valore attributo' required></input>";
        form += "<br>";
    }

    form += "<label for='k'>Inserire la distanza da tenere in considerazione per la predizione</label><br>";
    form += "<input type='number' name='k' id='k' autocomplete='off' placeholder='Valore k' required></input><br>";

    form += "<button class='form-button'>Avvia predizione</button>";

    document.getElementById("second_form").innerHTML = form;
}


function checkFirstModule() {
    let select = document.getElementById('load_method');

    if (select.value === 'novalue') {
        document.getElementById('validate').innerHTML = '*Campo obbligatorio';
        select.style.borderColor = 'red';
        return false;
    }

    let method = document.getElementById('file_name');

    if (method.offsetParent != null && !method.value) {
        document.getElementById('validate').innerHTML = '*Campo obbligatorio';
        method.style.borderColor = 'red';
        return false;
    }

    method = document.getElementById('serialized_file_name');

    if (method.offsetParent != null && !method.value) {
        document.getElementById('validate').innerHTML = '*Campo obbligatorio';
        method.style.borderColor = 'red';
        return false;
    }

    method = document.getElementById('table_name');

    if (method.offsetParent != null && !method.value) {
        document.getElementById('validate').innerHTML = '*Campo obbligatorio';
        method.style.borderColor = 'red';
        return false;
    }

    modal.style.display = "block";

    createSecondForm();

    return true;
}

$(document).ready(function () {

    $('#load_method').on('change', function () {
        let decision = $(this).val();

        $("#file").hide()
        $("#serialized_file").hide()
        $("#database").hide()

        switch (decision) {
            case "file":
                $("#file").show()
                break;
            case "serialized_file":
                $("#serialized_file").show()
                break;
            case "database":
                $("#database").show()
                break;
        }
    });

    $('#first_module').on('submit', function (e) {
        e.preventDefault();
    });

    $(window).scroll(function () {

        /* Controlla la locazione di ogni elemento desiderato. */
        $('.hideme').each(function () {

            let bottom_of_object = $(this).offset().top + $(this).outerHeight();
            let bottom_of_window = $(window).scrollTop() + $(window).height();

            /* Se l'elemento è completamente visibile lo fa comparire. */
            if (bottom_of_window > bottom_of_object) {
                $(this).addClass('showme');
            } else {
                $(this).removeClass('showme');
            }
        });
    });

    /**
     * Navbar
     */

    $('a.homenav').click(function () {
        $(window).scrollTop(0);
    });

    $('a.knnnav').click(function () {
        $(window).scrollTop($('#knn').position().top);
    });

    $('a.aboutnav').click(function () {
        $(window).scrollTop($('#about').position().top);
    });

    $('a.howtousenav').click(function () {
        $(window).scrollTop($('#howtouse').position().top);
    });

    $('a.contactnav').click(function () {
        $(window).scrollTop($('#footer').position().top);
    });

});

function clickButton(txt) {
    $("#test").html("Gay chi legge");

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "http://localhost:8080/getModello",
        data: JSON.stringify({
            id: id,
            formato: 1,
            nome: txt
        }),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            popola([], data[0]);
            console.log(data);
        },
        error: function (e) {

            console.log(e);
        }
    });
}

function clickButton2(k) {

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "http://localhost:8080/getPredizione",
        data: JSON.stringify({
            id: id,
            example: JSON.stringify({
                example: ["A", 2.00]
            }),
            k: k
        }),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            popola(data[1], data[2], data[0]);
            console.log(data);
        },
        error: function (e) {

            console.log(e);
        }
    });
}