package com.example.junior.test_token_video;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.HttpResponseException;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class SecondScreenHelper {
	public static final String DOMAIN = "http://tvinterativa.interaje.com.br";
	// public static final String DOMAIN = "http://192.168.0.18:3000";

	public static final Integer USER_STATUS_NEW = 1;
	public static final Integer USER_STATUS_DUMMY = 2;
	public static final Integer USER_STATUS_BLOCKED = 3;
	public static final Integer USER_STATUS_OFFLINE = 4; // Only on Devices

	public static final String STATION_CODE = "ESHQB46575";

	// public static final String STATION_CODE = "UEOJO66678";

	public static String getFormatedData(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",
				Locale.getDefault());
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}

	public static Date convertStringDateToDate(String date) {
		try {
			if (date != null && date.compareTo("null") != 0
					&& date.compareTo("") != 0) {
				if (date.length() > 24) {
					return (Date) new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
							.parse(date.replaceAll("Z$", "+0000"));
				} else if (date.length() > 11) {
					return (Date) new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
							.parse(date);
				} else {
					return (Date) new SimpleDateFormat("yyyy-MM-dd",
							Locale.getDefault()).parse(date);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getTimeCurrent() {
		Calendar cal = Calendar.getInstance();

		Date timeNow = new Date();

		cal.setTime(timeNow);

		// Um numero negativo vai decrementar a data
		cal.add(Calendar.MILLISECOND,
				TimeZone.getDefault().getOffset(timeNow.getTime()) * -1);

		return cal.getTime();
	}

	public static String getHumanData(Date date) {
		if (date != null) {
			return new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
					.format(date);
		} else {
			return "NULL";
		}
	}


	public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
		if (bitmap != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// TODO see if it raise problems
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
			return baos.toByteArray();
		} else {
			return null;
		}
	}

	public static Bitmap convertByteArrayToBitmap(byte[] byteArray) {
		if (byteArray != null) {
			ByteArrayInputStream imageStream = new ByteArrayInputStream(
					byteArray);
			return BitmapFactory.decodeStream(imageStream);
		} else {
			return null;
		}
	}

	public static Bitmap getBitmapFromWeb(String imageURL) {
		Bitmap bitmap = null;
		if (imageURL != null && imageURL.compareTo("null") != 0) {
			try {
				InputStream in = new URL(DOMAIN + imageURL).openStream();

				bitmap = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}


	public static String makePOSTRequest(String url, cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder meb) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		try {
			post.setEntity(meb.build());

			// setup the request headers
			// post.setHeader("Accept", "application/json");
			// post.setHeader("Content-Type", "application/json");
			// post.setHeader("Content-Type", "image/jpeg");

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(post, responseHandler);
		} catch (HttpResponseException e) {
			e.printStackTrace();
			return "{ \"success\":false, \"code\":" + e.getStatusCode() + " }";
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String saveImageIntoDisk(String imageURL) {
		try {
			Bitmap bitmap = getBitmapFromWeb(imageURL);

			File picturesDirectory = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			;
			String path = picturesDirectory
					+ "/"
					+ getImageName(imageURL).replaceAll("(.jpg|.png|.gif)",
							".jpeg");

			File filename = new File(path);

			FileOutputStream out = new FileOutputStream(filename);

			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();

			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getImageName(String imagePath) {
		String[] parts = imagePath.split("/");
		return parts[parts.length - 1];
	}

	public String encodeVideoBase64(){
		String videoEncode=null;



		return videoEncode;
	}
}