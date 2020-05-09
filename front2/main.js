/**JSON 呼び出し方1 */
// async function callApi(){

//   const res=await fetch('https://jsonplaceholder.typicode.com/users') // promiseで結果取得
//   const users=await res.json(); //responseで結果取得
//   console.log(users);
// }
/**JSON 呼び出し方2 */
// function callApi(){
//   fetch('https://jsonplaceholder.typicode.com/users')
//   .then(function(res){
//     return res.json();
//   })
//   .then(function (users){
//     console.log(users);
//   });
// }
// /**JSON 呼び出し方3　昔の書き方*/
// function callApi(){
//   const xhr=new XMLHttpRequest();
//   xhr.open('GET','https://jsonplaceholder.typicode.com/users');
//   xhr.responseType="json";
//   xhr.send();
//   xhr.onload=function(){
//     console.log(xhr.response);    
//   }
// }

// callApi();

// DOM
const button = document.getElementById('addBtn');
const lists = document.getElementById('lists');

// 関数
async function getUsers(){
  // データのやりとり
  const res = await fetch('https://jsonplaceholder.typicode.com/users');
  const users = await res.json(); //両方にawaitつけないといけない
  return users;
}

async function listUsers() {
  const users= await getUsers();
  // DOM操作
  // for (let index = 0; index < users.length; index++) {
  //   const user = users[index];
  //   const list =document.createElement('li');
  //   list.innerText= user.name;
  //   lists.appendChild(list);    
  // };
  users.forEach(user => {
    const list = document.createElement('li');
    list.innerText = user.name;
    lists.appendChild(list);
  });
}

// イベント
window.addEventListener('load', listUsers);
button.addEventListener('click',listUsers);
