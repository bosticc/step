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
function loadTasks() {
  fetch('/data').then(response => response.json()).then((comments) => {
    const taskListElement = document.getElementById('task-list');
    comments.forEach((comment) => {
      taskListElement.appendChild(createTaskElement(comment));
    })
  });
}

/** Creates an element that represents a task, including its delete button. */
function createTaskElement(comment) {
  const taskElement = document.createElement('li');
  taskElement.className = 'comment';

  const titleElement = document.createElement('span');
  titleElement.innerText = comment.title;

<<<<<<< HEAD

 
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

function numComments(commentsNum) {
  fetch('/data?viewComments=' + commentsNum).then(response => response.json()).then((comments) => {
    const taskListElement = document.getElementById('task-list');
    taskListElement.innerHTML="";
    for (let i = 0; i < commentsNum; i++)
    {
        taskListElement.appendChild(createTaskElement(comments));
    }    
  });
=======
  const deleteButtonElement = document.createElement('button');
  deleteButtonElement.innerText = 'Delete';
  deleteButtonElement.addEventListener('click', () => {
    deleteTask(comment);

    // Remove the task from the DOM.
    taskElement.remove();
  });

  taskElement.appendChild(titleElement);
  taskElement.appendChild(deleteButtonElement);
  return taskElement;
>>>>>>> e9655925b1c5f3ce6993495b8bbe7ab4c88e3930
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

/** Tells the server to delete the task. */
function deleteTask(task) {
  const params = new URLSearchParams();
  params.append('id', task.id);
  fetch('/DataDelete', {method: 'POST', body: params});
}