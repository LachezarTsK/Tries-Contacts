import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_StandardTrie {

	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
		BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(java.io.FileDescriptor.out), "ASCII"), 512);

		int numberOfOperations = Integer.parseInt(stringTokenizer.nextToken());
		int currentOperation = 0;
		Trie trie = new Trie();

		while (currentOperation < numberOfOperations) {
			stringTokenizer = new StringTokenizer(bufferedReader.readLine());
			String operation = stringTokenizer.nextToken();
			String key = stringTokenizer.nextToken();

			if (operation.equals("add")) {
				trie.insert(key);
			} else if (operation.equals("find")) {
				int total = trie.search_numberOfWords_staringWithThisPrefix(key);
				bufferedWriter.write(total + "\n");
			}
			currentOperation++;
		}

		bufferedReader.close();
		bufferedWriter.flush();
		bufferedWriter.close();
	}
}

class Trie {
	private final int ALPHABET_SIZE = 26;
	private TrieNode root = new TrieNode();

	private class TrieNode {
		/**
		 * Especially for this challenge, implementing the boolean variable
		 * "isEndOfWord" is not necessary.
		 * 
		 * It is implemented for completeness and aesthetics.
		 */
		boolean isEndOfWord;
		int numberOfWords_stemmingFromThisTrieNode;
		TrieNode[] children = new TrieNode[ALPHABET_SIZE];
	}

	public void insert(String word) {
		TrieNode currentTrieNode = root;
		for (int level = 0; level < word.length(); level++) {
			int index = word.charAt(level) - 'a';
			if (currentTrieNode.children[index] == null) {
				currentTrieNode.children[index] = new TrieNode();
			}
			currentTrieNode = currentTrieNode.children[index];
			currentTrieNode.numberOfWords_stemmingFromThisTrieNode++;
		}
		currentTrieNode.isEndOfWord = true;
	}

	public int search_numberOfWords_staringWithThisPrefix(String prefix) {
		TrieNode currentTrieNode = root;
		for (int level = 0; level < prefix.length(); level++) {
			int index = prefix.charAt(level) - 'a';
			if (currentTrieNode.children[index] == null) {
				return 0;
			}
			currentTrieNode = currentTrieNode.children[index];
		}
		return currentTrieNode.numberOfWords_stemmingFromThisTrieNode;
	}
}
