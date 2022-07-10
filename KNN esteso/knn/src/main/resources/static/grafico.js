var popCanvas = document.getElementById("bubble-chart");
var r = 10;
var bubbleChart = null;

popola();

function popola(arrayColorato = [], arrayNonColorato = [], example = []) {
    if (bubbleChart !== null)
        bubbleChart.destroy();

    let arrayColors = [];
    let array = [];

    if (example.length > 0) {
        arrayColors.push("#0000ff");
        array.push(({ "x": example[0], "y": example[1], "r": r }));
    }

    array.push(arrayColorato.map(e => ({ "x": e[0], "y": e[1], "r": r })));
    for (let i = 0; i < arrayColorato.length; i++) {
        arrayColors.push("#FF0000");
    }
    array.push(arrayNonColorato.map(e => ({ "x": e[0], "y": e[1], "r": r })));
    for (let i = 0; i < arrayNonColorato.length; i++) {
        arrayColors.push("#61b254");
    }

    array = array.flat();

    var popData = {
        datasets: [{
            label: ['Popolazione'],
            data: array,
            backgroundColor: arrayColors
        }]
    };

    bubbleChart = new Chart(popCanvas, {
        type: 'bubble',
        data: popData
    });
}