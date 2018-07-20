package nl.komtek.moneybird;

import com.google.gson.JsonObject;
import nl.komtek.moneybird.helper.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HttpClientManager {

	private static final int TIMEOUT = 10;
	private static HttpClient client;

	public static HttpClient getHttpClientInstance() {
		if (client == null) {
			client = HttpClientBuilder.create().
					setDefaultRequestConfig(RequestConfig.custom()
							.setConnectTimeout(TIMEOUT * 1000)
							.setConnectionRequestTimeout(TIMEOUT * 1000)
							.setSocketTimeout(TIMEOUT * 1000)
							.build())
					.build();
		}
		return client;
	}

//	public static Response postHttp(String url, JsonObject params, List<NameValuePair> headers) throws IOException {
//		HttpPost request = new HttpPost(url);
//		List<NameValuePair> p2 = new ArrayList<>();
//		p2.add(new BasicNameValuePair("contact", params.toString()));
//		request.setEntity(new UrlEncodedFormEntity(p2, Consts.UTF_8));
//		return request(request, headers);
//	}

	public static Response postHttp(String url, JsonObject params, List<NameValuePair> headers) throws IOException {
		HttpPost request = new HttpPost(url);
		request.setEntity(new StringEntity(params.toString(), ContentType.APPLICATION_JSON));
		return request(request, headers);
	}

	public static Response patchHttp(String url, JsonObject params, List<NameValuePair> headers) throws IOException {
		HttpPatch request = new HttpPatch(url);
		request.setEntity(new StringEntity(params.toString(), ContentType.APPLICATION_JSON));
		return request(request, headers);
	}

	public static Response getHttp(String url, List<NameValuePair> headers) throws Exception {
		HttpGet request = new HttpGet(url);
		return request(request, headers);
	}

	public static Response putHttp(String url, JsonObject params, List<NameValuePair> headers) throws Exception {
		HttpPut request = new HttpPut(url);
		request.setEntity(new StringEntity(params.toString(), ContentType.APPLICATION_JSON));
		return request(request, headers);
	}

	public static Response deleteHttp(String url, List<NameValuePair> headers) throws Exception {
		HttpDelete request = new HttpDelete(url);
		return request(request, headers);
	}

	private static Response request(HttpRequestBase base, List<NameValuePair> headers) throws IOException {
		if (headers != null) {
			for (NameValuePair header : headers) {
				base.addHeader(header.getName(), header.getValue());
			}
		}

		HttpResponse response = getHttpClientInstance().execute(base);
		int responseCode = response.getStatusLine().getStatusCode();

		HttpEntity entity = response.getEntity();
		return new Response(responseCode, Optional.ofNullable(EntityUtils.toString(entity)));
	}
}
