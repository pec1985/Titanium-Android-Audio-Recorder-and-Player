/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package in.shivakumars.audiorecorder;

import java.io.File;
import java.io.IOException;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;

@Kroll.module(name = "Audiorecorder", id = "in.shivakumars.audiorecorder")
public class AudiorecorderModule extends KrollModule {

	// Standard Debugging variables
	private static final String LCAT = "AudiorecorderModule";
	private static final boolean DBG = TiConfig.LOGD;
	private MediaRecorder mRecorder = null;
	private MediaPlayer mPlayer = null;

	// You can define constants with @Kroll.constant, for example:
	// @Kroll.constant public static final String EXTERNAL_NAME = value;

	public AudiorecorderModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
		Log.d(LCAT, "inside onAppCreate");
		// put module init code that needs to run when the application is
		// created
	}

	private void startPlaying(String fileName) {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(fileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LCAT, "prepare() failed");
			Log.e(LCAT, e.toString());
		}
	}

	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	private void startRecording(String fileName) {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		mRecorder.setOutputFile(Environment.getExternalStorageDirectory()
				.getPath()
				+ "/"
				+ TiApplication.getAppCurrentActivity().getPackageName()
				+ "/"
				+ fileName + ".mp4");
		if (android.os.Build.VERSION.SDK_INT >= 16)
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC_ELD);
		else
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        try {
			mRecorder.setAudioEncoder(MediaRecorder.getAudioSourceMax());
		} catch (Exception e) {
			Log.e(LCAT, "setAudioEncoder(MediaRecorder.getAudioSourceMax()) failed");
			Log.e(LCAT, e.toString());
		}

        try {
			mRecorder.setAudioEncodingBitRate(16);
		} catch (Exception e) {
			Log.e(LCAT, "setAudioEncodingBitRate(16) failed");
			Log.e(LCAT, e.toString());
		}

        try {
			mRecorder.setAudioSamplingRate(44100);
		} catch (Exception e) {
			Log.e(LCAT, "setAudioSamplingRate(44100) failed");
			Log.e(LCAT, e.toString());
		}
        
		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(LCAT, "prepare() failed");
			Log.e(LCAT, e.toString());
		}

		mRecorder.start();
	}

	private void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}

	String file;

	// Methods
	@Kroll.method
	public void startRec(String fileName) {
		Log.d(LCAT, "start Rec called");
		startRecording(fileName);
		file = fileName;
	}

	@Kroll.method
	public String stopRec() {
		Log.d(LCAT, "stop Rec called");
		stopRecording();
		return Environment.getExternalStorageDirectory().getPath() + "/"
				+ TiApplication.getAppCurrentActivity().getPackageName() + "/"
				+ file + ".mp4";
	}

	@Kroll.method
	public void startPlay(String fileName) {
		Log.d(LCAT, "start Play called");
		startPlaying(fileName);
	}

	@Kroll.method
	public void stopPlay() {
		Log.d(LCAT, "stop Play called");
		stopPlaying();
	}

	@Kroll.method
	public Boolean isPlayerPlaying() {
		// Log.d(LCAT, "stop Play called");
		return mPlayer.isPlaying();
	}

}
