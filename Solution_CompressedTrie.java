import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Solution_CompressedTrie {

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
		Map<Character, TrieNode> children = new HashMap<Character, TrieNode>();
	}

	public void insert(String word) {
		TrieNode currentTrieNode = root;
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			TrieNode node = currentTrieNode.children.get(ch);
			if (node == null) {
				node = new TrieNode();
				currentTrieNode.children.put(ch, node);
			}
			currentTrieNode = node;
			currentTrieNode.numberOfWords_stemmingFromThisTrieNode++;
		}
		currentTrieNode.isEndOfWord = true;
	}

	public int search_numberOfWords_staringWithThisPrefix(String word) {
		TrieNode currentTrieNode = root;
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			TrieNode node = currentTrieNode.children.get(ch);
			if (node == null) {
				return 0;
			}
			currentTrieNode = node;
		}
		return currentTrieNode.numberOfWords_stemmingFromThisTrieNode;
	}
}
