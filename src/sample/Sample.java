package sample;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 最新の地震情報を表示する
 */
public class Sample {
	
	public static void main(String[] args) {
		
		//　P2P地震情報のURL
        String url = "https://api.p2pquake.net/v2/history?codes=551&limit=1";
        
        // HttpClientオブジェクトを生成
        HttpClient client = HttpClient.newHttpClient();
        
        // HttpRequestオブジェクトを生成
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build(); 		

		try{
			//　リクエスト送信、レスポンス取得
			HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
			
			//　Parameterクラスのインスタンス化
			Parameter param = new Parameter();
			//　Jsonから必要情報の抽出、Parameterクラスにセット
			param = getQuakeInfo(param,res.body());
			
			//　画面出力
			view(param);
			
		}catch (IOException | InterruptedException ex) {
			ex.printStackTrace();
		}
		
	}

	/**
	 * Jsonから必要情報を抽出し、Parameterクラスの変数にそれぞれセットする
	 * @param param　Parameterクラス
	 * @param res　　　Json文字列
	 * @return　必要情報が設定されたParameterクラス
	 */
	private static Parameter getQuakeInfo(Parameter param, String res) {
		
		String maxScale;//最大震度
		String scale;//表示用震度
		
		//ObjectMapperの生成
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			//Json（文字列）からJsonNodeオブジェクトに変換
			//Jsonが配列形式なので一番目の情報を取得
			JsonNode jNode = mapper.readTree(res).get(0);

			//発生時間
			param.setTime(jNode.get("earthquake").get("time").asText());
			//名称
			param.setName(jNode.get("earthquake").get("hypocenter").get("name").asText());
			//深さ
			param.setDepth(jNode.get("earthquake").get("hypocenter").get("depth").asText());
			//マグニチュード
			param.setMagnitude(jNode.get("earthquake").get("hypocenter").get("magnitude").asText());
			//発表元
			param.setSource(jNode.get("issue").get("source").asText());	
			//最大震度
			maxScale = jNode.get("earthquake").get("maxScale").asText();
			
			//最大震度から表示用震度の判定
			switch (maxScale){
			case "-1":
				scale = "震度情報なし";
				break;
			case "10":
				scale = "1";
				break;
			case "20":
				scale = "2";
				break;
			case "30":
				scale = "3";
				break;
			case "40":
				scale = "4";
				break;
			case "45":
				scale = "5弱";
				break;
			case "50":
				scale = "5強";
				break;
			case "55":
				scale = "6弱";
				break;
			case "60":
				scale = "6強";
				break;
			case "70":
				scale = "7";
				break;
			default:
				scale = "-";
				break;
			}
			
			//震度
			param.setScale(scale);
			
		}catch (IOException  ex) {
			ex.printStackTrace();
		}
		
		return param;
	}
	
	/**
	 * 画面表示する
	 * @param param　表示情報をもったParameterクラス
	 */
	private static void view(Parameter param) {

		String html;

		//HTML文字列作成
		html = "<html>&emsp;震源地：" + param.getName()
				+ "<br>&emsp;発生日時：" + param.getTime()
				+ "<br>&emsp;最大震度：" + param.getScale()
				+ "<br>&emsp;マグニチュード：" + param.getMagnitude()
				+ "<br>&emsp;深さ：" + param.getDepth() + "km"
				+ "<br>"
				+ "<br>&emsp;引用元：" + param.getSource()
				+ "<html>";
		
		// 画面タイトル
        JFrame frame = new JFrame("最新の地震情報");
        
        // 画面上に表示する内容の設定
        JLabel label = new JLabel(html);
        frame.add(label);
        
        // 画面のサイズ
        frame.setSize(400,400);
        
        // 表示
        frame.setVisible(true);

        // 中央に表示
        frame.setLocationRelativeTo(null);
        
        // 「X」ボタンで画面を閉じる
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
	}

}
