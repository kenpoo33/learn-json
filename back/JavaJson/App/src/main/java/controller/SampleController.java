package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// http://サーバホスト名:ポート番号/Eclipseプロジェクト名/@WebServletアノテーション
@WebServlet(name = "SampleController", urlPatterns = { "/SampleController" })
public class SampleController extends HttpServlet {

  private static final long serialVersionID = 1L;
  private static final String URL_FIX="https://b.hatena.ne.jp/entry/jsonlite/?url=";

  /**
   * @see HttpServlet#HttpServlet()
   */
  public SampleController() {
    super();
  }

  /**
   * GETプロトコルの時、強制で呼ばれる.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    HttpURLConnection urlConn =null;
    InputStream in = null;
    BufferedReader reader = null;
    String paramUrl= request.getParameter("url");

      // 接続URL
      URL url = new URL(URL_FIX + paramUrl);

      // コネクション取得
      urlConn = (HttpURLConnection) url.openConnection();

      //接続タイムアウトを設定する。
      urlConn.setConnectTimeout(100000);
      //レスポンスデータ読み取りタイムアウトを設定する。
      urlConn.setReadTimeout(100000);
      //ヘッダーにContent-Typeを設定する
      urlConn.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
      //HTTPのメソッドをPOSTに設定する。
      urlConn.setRequestMethod("GET");

//      //ヘッダーにUser-Agentを設定する。
//      urlConn.setRequestProperty("User-Agent", "Android");
//      //ヘッダーにAccept-Languageを設定する。
//      urlConn.setRequestProperty("Accept-Language", Locale.getDefault().toString());
//      //リクエストのボディ送信を許可する
//      urlConn.setDoOutput(true);
//      //レスポンスのボディ受信を許可する
//      urlConn.setDoInput(true);

      urlConn.connect(); // 接続

      int status = urlConn.getResponseCode();
      String responseData = "";
      InputStream stream = urlConn.getInputStream();
      StringBuffer sb = new StringBuffer();
      String line = "";
      BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      try {
        stream.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      responseData = sb.toString();

      PrintWriter out = response.getWriter(); // 1.応答から出力ストリームを取得する
      response.setContentType("application/json"); //2.レスポンスヘッダを記述
      response.setCharacterEncoding("UTF-8");
      // CORS対策
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS");
      response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
      response.setHeader("Access-Control-Max-Age", "-1");

      out.print(responseData); //4. //3.出力ストリームに内容を書き込む
      out.flush(); // 応答をコミットする
  }

  /**
   * POSTプロトコルの時、強制で呼ばれる.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

  }
}
