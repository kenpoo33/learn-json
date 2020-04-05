'use strict';
{
  let url = document.getElementById('url');

  document.getElementById('btn').addEventListener('click', () => {

    let result = document.getElementById('result');
    let xhr = new XMLHttpRequest();
    // 非同期通信時の処理の定義
    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4) { //通信完了時
        if (xhr.status === 200) { // 通信成功時
          let data = JSON.parse(xhr.responseText);

          if (data === null) {
            result.textContent = 'ブックマークは存在しませんでした。';
          } else {
            // データを表示する。
            let bms = data.bookmarks;
            let ul = document.createElement('ul');
            for (let i = 0; i < bms.length; i++) {
              let li = document.createElement('li');
              let a = document.createElement('a');
              a.href = 'http://b.hatena.ne.jp/' + bms[i].user;
              let text = document.createTextNode(bms[i].user + ' ' + bms[i].comment);
              a.appendChild(text);
              li.appendChild(a);
              ul.appendChild(li);
            }
            while (result.firstChild){
              result.removeChild(result.firstChild);
            }
            // result 直下を書き換え
            result.appendChild(ul);
          }
        } else {
          result.textContent = 'サーバーエラーが発生しました。';
        }
      }else{
        result.textContent = '通信中...';
      }
    };
    xhr.open('GET', 'http://localhost:8080/App/SampleController?url=' + encodeURIComponent(document.getElementById('url').value), true);
    xhr.send(null);
  }, false);
}