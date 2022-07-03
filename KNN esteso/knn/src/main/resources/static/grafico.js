var popCanvas = document.getElementById("bubble-chart");
var r = 10;

var popData = {
    datasets: [{
        label: ['Popolazione'],
        data: [{
            x: 100,
            y: 0,
            r: r,
            backgroundColor: "#000000"
        }, {
            x: 60,
            y: 30,
            r: r
        }, {
            x: 40,
            y: 60,
            r: r
        }, {
            x: 80,
            y: 80,
            r: r
        }, {
            x: 20,
            y: 30,
            r: r
        }, {
            x: 0,
            y: 100,
            r: r
        }],
        backgroundColor: ["#FF9966", "#000000"]
    }]
};

var bubbleChart = new Chart(popCanvas, {
    type: 'bubble',
    data: popData
});