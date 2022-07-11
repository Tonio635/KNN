var popCanvas = document.getElementById("bubble-chart");
var r = 10;
var bubbleChart = null;

popola();

function popola(arrayColorato = [], arrayNonColorato = [], example = []) {
    if (bubbleChart !== null)
        bubbleChart.destroy();

    let datasets = [];

    if (example.length > 0) {
        datasets.push({
            label: "Esempio",
            data: [{ "x": example[0], "y": example[1], "r": r }],
            backgroundColor: "#0000ff"
        })
    }

    if (arrayColorato.length > 0) {
        arrayColorato = arrayColorato.map(e => ({ "x": e[0], "y": e[1], "r": r }));
        datasets.push({
            label: "K Esempi più vicini",
            data: arrayColorato,
            backgroundColor: "#FF0000"
        })
    }


    if (arrayNonColorato.length > 0) {
        arrayNonColorato = arrayNonColorato.map(e => ({ "x": e[0], "y": e[1], "r": r }));
        datasets.push({
            label: "Training set",
            data: arrayNonColorato,
            backgroundColor: "#61b254"
        })
    }

    var popData = {
        datasets: datasets
    };

    bubbleChart = new Chart(popCanvas, {
        type: 'bubble',
        data: popData
    });
}