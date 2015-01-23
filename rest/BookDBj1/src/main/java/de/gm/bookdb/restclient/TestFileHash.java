package de.gm.bookdb.restclient;

import java.io.IOException;

import org.clapper.util.misc.FileHashMap;

public class TestFileHash {

	public TestFileHash() {
		try {
			this.mydata = new FileHashMap<Integer, String>("/local/mydata", 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FileHashMap<Integer, String> mydata;

	public static void main(String[] args) {
		TestFileHash testFileHash = new TestFileHash();
		testFileHash.mydata.put(1, "aaa");
		try {
			testFileHash.mydata.save();
			testFileHash.mydata.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
