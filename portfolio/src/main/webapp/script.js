// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawChart);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.


function drawChart() {

// Create the data table for the pie chart.
    var dataPie = new google.visualization.DataTable();
    dataPie.addColumn('string', 'Topping');
    dataPie.addColumn('number', 'Slices');
    dataPie.addRows([
        ['Pepperoni', 3],
        ['Pineapple', 1],
    ]);

    // Set chart options (size & title)
    var options = {'title':'How Much Pizza I Ate Last Night',
                   'width':400,
                   'height':300};

    // Instantiate and draw our chart, passing in some options.
    var foodChart = new google.visualization.PieChart(document.getElementById('chart_div'));
    foodChart.draw(dataPie, options);

    const dataMyles = new google.visualization.DataTable();
    //fetch data from servlet for bar chart 
    fetch('/met-Myles').then(response => response.json())
    .then((metVotes) => {
    dataMyles.addColumn('string', 'option');
    dataMyles.addColumn('number', 'Votes');
    Object.keys(metVotes).forEach((metMyles) => {
      dataMyles.addRow([metMyles, metVotes[metMyles]]);
    });

    // for the size of the chart
    const options = {
      'title': 'Bar chart on whether or not people have met me before!',
      'width':400,
      'height':300,
      animation: {"startup": true}
    };

    //printing the chart out to the container 
    const chart = new google.visualization.ColumnChart(
        document.getElementById('chart-container'));
    chart.draw(dataMyles, options);
  });
}
 
//Prints out the number of comments that the user specifies
function numComments(commentsNum) {
  fetch('/data?viewComments=' + commentsNum).then(response => response.json()).then((comments) => {
    const taskListElement = document.getElementById('task-list');
    taskListElement.innerHTML="";
    for (let i = 0; i < commentsNum; i++)
    {
        taskListElement.appendChild(createTaskElement(comments));
    }    
  });
}


/** Creates an element that represents a comment, including its delete button. */
function createTaskElement(task) {
  const taskElement = document.createElement('li');
  taskElement.className = 'comment';

  const titleElement = document.createElement('span');
  titleElement.innerText = task.title;

  const deleteButtonElement = document.createElement('button');
  deleteButtonElement.innerText = 'Delete';
  deleteButtonElement.addEventListener('click', () => {
    deleteTask(task);

    // Remove the task from the DOM.
    taskElement.remove();
  });

  taskElement.appendChild(titleElement);
  taskElement.appendChild(deleteButtonElement);
  return taskElement;
}

/** Tells the server to delete the comment. */
function deleteTask(task) {
  const params = new URLSearchParams();
  params.append('id', task.id);
  fetch('/DataDelete', {method: 'POST', body: params});
}

