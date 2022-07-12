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

function createSecondForm(result) {
    let form = "";

    for (let i = 0; i < result.length; i++) {
        let type = result[i][1] === 1 ? "text" : "number";
        let type_text = result[i][1] === 1 ? "discreta" : "continua";
        form +=  `<label for='${result[i][0]}'>Inserire la variabile ${type_text} ${result[i][0]}</label><br>
        <input type='${type}' name='${result[i][0]}' id='${result[i][0]}' autocomplete='off' placeholder='Valore variabile ${type_text}' required></input>
        <br>`;
    }

    form += `<label for='k'>Inserire la distanza</label><br>
    <input type='number' name='k' id='k' autocomplete='off' placeholder='Valore k' required></input><br>
    <button class='form-button'>Avvia predizione</button>`;

    document.getElementById("second_form").innerHTML = form;
}

async function checkFirstModule() {
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
    } else if(select.value == "1"){
        var path = method.value;
    }

    method = document.getElementById('serialized_file_name');

    if (method.offsetParent != null && !method.value) {
        document.getElementById('validate').innerHTML = '*Campo obbligatorio';
        method.style.borderColor = 'red';
        return false;
    } else if (select.value == "2") {
        var path = method.value;
    }

    method = document.getElementById('table_name');

    if (method.offsetParent != null && !method.value) {
        document.getElementById('validate').innerHTML = '*Campo obbligatorio';
        method.style.borderColor = 'red';
        return false;
    } else if (select.value == "3") {
        var path = method.value;
    }

    var result = await caricaModello(path, parseInt(select.value));

    modal.style.display = "block";

    createSecondForm(result);

    return true;
}

$(document).ready(function () {

    $('#load_method').on('change', function () {
        let decision = $(this).val();

        $("#file").hide();
        $("#file_name").val("");
        $("#serialized_file").hide();
        $("#serialized_file_name").val("");
        $("#database").hide();
        $("#table_name").val("");

        switch (decision) {
            case "1":
                $("#file").show();
                break;
            case "2":
                $("#serialized_file").show();
                break;
            case "3":
                $("#database").show();
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

async function caricaModello(pathFile, formato) {

    var result = null;

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "http://localhost:8080/getModello",
        data: JSON.stringify({
            id: id,
            formato: formato,
            nome: pathFile
        }),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        async: false,
        success: function (data) {
            popola([], data[0]);
            result = data[1];
        },
        error: function (e) {
            console.log(e);
        }
    });

    return result;
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