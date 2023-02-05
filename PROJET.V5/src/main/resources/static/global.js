

function rtn() {
	window.history.back();
}


function mettreEnFormeTable() {
	$('#myTable').DataTable();
}


function msgConfirmationSupprimer(path, id) {
	var answer = confirm("Êtes-vous sûr de vouloir supprimer cet élement ?");
	if (answer) {
		window.location.href = path + id;
	}
}


function preparerGraph(typeDeGraph) {

	google.charts.load('current', { packages: ['corechart'] });

	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {

		switch (typeDeGraph) {
			case "barchart_NbrCommandesParCategories":
				var chart = new google.visualization.BarChart(document.getElementById('myChart'));
				var urlApi = "/projetv4/restAPI/getDataNbrCommandesParCategories";
				var options = {
					hAxis: {
						title: 'Commandes passées'
					},
					vAxis: {
						title: 'Noms catégories'
					},
					backgroundColor: window.getComputedStyle(document.documentElement).getPropertyValue('--couleur4').trim()
				};
				break;

			case "piechart_NbrArticlesPris":
				var chart = new google.visualization.PieChart(document.getElementById('myChart'));
				var urlApi = "/projetv4/restAPI/getDataNbrArticlesPris";
				var options = {
					backgroundColor: window.getComputedStyle(document.documentElement).getPropertyValue('--couleur4').trim(),
					is3D: true
				};
				break;

			case "linechart_EvolutionNbrCommandesPassees":
				var chart = new google.visualization.LineChart(document.getElementById('myChart'));
				var urlApi = "/projetv4/restAPI/getDataEvolutionNbrCommandesPassees";
				var options = {
					hAxis: {
						title: 'Temps (mois)'
					},
					vAxis: {
						title: 'Commandes passées'
					},
					backgroundColor: window.getComputedStyle(document.documentElement).getPropertyValue('--couleur4').trim()
				};
				break;

			default:
				var chart = null;
				var urlApi = null;
				break;
		}

		var jsonData = $.ajax({
			url: urlApi,
			dataType: "json",
			async: false
		}).responseText;

		var dataArray = JSON.parse(jsonData);

		var data = google.visualization.arrayToDataTable(dataArray);

		chart.draw(data, options);

	}
}