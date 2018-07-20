package nl.komtek.moneybird;

import com.google.gson.JsonObject;
import nl.komtek.moneybird.helper.Response;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MoneybirdClient {

	private final String baseUrl;
	private String format;
	private List<NameValuePair> headers = new ArrayList<>();

	public MoneybirdClient(String token, String version) {
		version = version == null ? "V2" : version;
		this.format = "json";
		this.baseUrl = String.format("https://moneybird.com/api/%s", version);
		headers.add(new BasicNameValuePair("Authorization", "Bearer " + token));
	}

	public MoneybirdClient(String token, String administrationId, String version) {
		version = version == null ? "V2" : version;
		this.format = "json";
		this.baseUrl = String.format("https://moneybird.com/api/%s/%s", version, administrationId);
		headers.add(new BasicNameValuePair("Authorization", "Bearer " + token));
	}

	public Response get(String path) throws Exception {
		String url = String.format("%s/%s.%s", baseUrl, path, format);
		return HttpClientManager.getHttp(url, headers);
	}

	public Response post(String path, JsonObject params) throws Exception {
		String url = String.format("%s/%s.%s", baseUrl, path, format);
		return HttpClientManager.postHttp(url, params, headers);
	}

	public Response put(String path, JsonObject params) throws Exception {
		String url = String.format("%s/%s.%s", baseUrl, path, format);
		return HttpClientManager.putHttp(url, params, headers);
	}

	public Response patch(String path, JsonObject params) throws Exception {
		String url = String.format("%s/%s.%s", baseUrl, path, format);
		return HttpClientManager.patchHttp(url, params, headers);
	}

	public Response delete(String path) throws Exception {
		String url = String.format("%s/%s.%s", baseUrl, path, format);
		return HttpClientManager.deleteHttp(url, headers);
	}
}
