package edu.wm.camckenna.no;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
implements OnClickListener, OnInitListener {

	protected static final int REQUEST_OK = 1;
	private TextToSpeech tts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.button1).setOnClickListener(this);
		tts = new TextToSpeech(this, this);
	}

	@Override
	public void onClick(View v) {
	Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	         i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
	        	 try {
	             startActivityForResult(i, REQUEST_OK);
	         } catch (Exception e) {
	        	 	Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
	         }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_OK && resultCode == RESULT_OK) {
			ArrayList<String> thingsYouSaid = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			((TextView) findViewById(R.id.text1)).setText(thingsYouSaid.get(0));
		}

		String text = "No";
		if (text != null) {
			if (!tts.isSpeaking()) {
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			}
		}
	    }
	
	@Override
	public void onInit(int code) {
	      if (code==TextToSpeech.SUCCESS) {
	tts.setLanguage(Locale.getDefault());


	} else {
	tts = null;
	Toast.makeText(this, "Failed to initialize TTS engine.",
	Toast.LENGTH_SHORT).show();
	}


	}
	
	@Override
	protected void onDestroy() {
	      if (tts!=null) {
	tts.stop();


	tts.shutdown();


	}


	      super.onDestroy();
	}
}
